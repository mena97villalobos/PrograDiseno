package Model;

import javafx.scene.control.Alert;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileHandle {
    public static void guardarJSON(ConfiguracionDTO configuracion){
        String JSON = "{\"PISOS\":{\"P1\":[" +
                recorrerArrayJSON(configuracion.getP1S()) +
                "], \"P2\":[" +
                recorrerArrayJSON(configuracion.getP2S()) +
                "]}, \"ELEVADORES\":{\"P3\":[" +
                recorrerArrayJSON(configuracion.getP3S()) +
                "], \"P4\":[" +
                recorrerArrayJSON(configuracion.getP4S()) +
                "]}, \"MAXCAP\":" + Integer.toString(configuracion.getNumeroPasajeros()) +
                ", \"RECORRIDO\":" + Integer.toString(configuracion.getUtEntrePisos()) +
                ", \"PUERTA\":" + Integer.toString(configuracion.getUtPuertasAbiertas()) +
                "}";
        guardarArchivo(configuracion.getPathSalida(), ".json", JSON);
    }

    private static String recorrerArrayJSON(ArrayList<Float> aux){
        StringBuilder JSON = new StringBuilder();
        for (int i = 0; i < aux.size(); i++) {
            if(i + 1 == aux.size())
                JSON.append(Float.toString(aux.get(i)));
            else
                JSON.append(Float.toString(aux.get(i))).append(", ");
        }
        return JSON.toString();
    }

    public static void guardarXML(ConfiguracionDTO configuracion){
        String XML = "<CONFIGURACION>\n<PISOS>\n\t<NUMEROPISOS>" +
                Integer.toString(configuracion.getNumeroPisos()) +
                "</NUMEROPISOS>\n" +
                recorreArrayXML("P1", configuracion.getP1S()) +
                recorreArrayXML("P2", configuracion.getP2S()) +
                "</PISOS>\n<ELEVADORES>\n\t<NUMEROELEVADORES>" +
                Integer.toString(configuracion.getNumeroElevadores()) +
                "</NUMEROELEVADORES>\n" +
                recorreArrayXML("P3", configuracion.getP3S()) +
                recorreArrayXML("P4", configuracion.getP4S()) +
                "</ELEVADORES>\n<MAXCAP>" + Integer.toString(configuracion.getNumeroPasajeros()) +
                "</MAXCAP>\n<UT>\n\t<RECORRIDO>" + Integer.toString(configuracion.getUtEntrePisos()) +
                "</RECORRIDO>\n\t<PUERTA>" + Integer.toString(configuracion.getUtPuertasAbiertas()) +
                "</PUERTA>\n</UT>\n</CONFIGURACION>";
        guardarArchivo(configuracion.getPathSalida(), ".xml", XML);

    }

    private static String recorreArrayXML(String tag, ArrayList<Float> aux){
        StringBuilder XML = new StringBuilder();
        String tagInicio = "\t<" + tag + ">";
        String tagFin = "</" + tag + ">\n";
        for (Float aFloat : aux) {
            XML.append(tagInicio)
                .append(Float.toString(aFloat))
                .append(tagFin);
        }
        return XML.toString();
    }

    public static void guardarTEXT(ConfiguracionDTO configuracion){
        String texto = "Numero de pisos: " +
                Integer.toString(configuracion.getNumeroPisos()) + "\n" +
                recorrerArrayTexto("P1: ", configuracion.getP1S()) +
                recorrerArrayTexto("P2: ", configuracion.getP2S()) +
                "Numero de elevadores: " + Integer.toString(configuracion.getNumeroElevadores()) + "\n" +
                recorrerArrayTexto("P3: ", configuracion.getP3S()) +
                recorrerArrayTexto("P4: ", configuracion.getP4S()) +
                "Capacidad Maxima: " + Integer.toString(configuracion.getNumeroPasajeros()) +
                "\nDuracion entre pisos: " + Integer.toString(configuracion.getUtEntrePisos()) +
                "\nDuracion puerta abierta: " + Integer.toString(configuracion.getUtPuertasAbiertas());
        guardarArchivo(configuracion.getPathSalida(), ".txt", texto);
    }

    private static String recorrerArrayTexto(String tag, ArrayList<Float> aux){
        StringBuilder texto = new StringBuilder();
        for (Float aFloat : aux) {
            texto.append(tag).append(Float.toString(aFloat)).append("\n");
        }
        return texto.toString();
    }

    private static void guardarArchivo(String path, String extension, String contenido){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path + extension), "utf-8"))) {
            writer.write(contenido);
            Alertas.invocarAlerta("Archivo guardado", Alert.AlertType.INFORMATION);
        } catch (UnsupportedEncodingException e) {
            Alertas.invocarAlerta("Encoding no soportado", Alert.AlertType.ERROR);
        } catch (FileNotFoundException e) {
            Alertas.invocarAlerta("Archivo no encontrado", Alert.AlertType.ERROR);
        } catch (IOException e) {
            Alertas.invocarAlerta("IOException", Alert.AlertType.ERROR);
        }
    }

    public static ConfiguracionDTO cargarArchivo(File file){
        String extension = FilenameUtils.getExtension(file.getPath());
        String contenido = "";
        try {
            contenido = readFile(file.getPath(), Charset.forName("UTF-8"));
        } catch (IOException e) {
            Alertas.invocarAlerta("Error al abrir archivo", Alert.AlertType.ERROR);
        }
        switch (extension){
            case "xml":
                return parseXML(contenido);
            case "json":
                return parseJSON(contenido);
            case "txt":
                return parseTexto(contenido);
            default:
                Alertas.invocarAlerta("Archivo no es de un tipo soportado", Alert.AlertType.ERROR);
        }
        return null;
    }

    private static ConfiguracionDTO parseXML(String XMLString){
        Document doc = Jsoup.parse(XMLString, "", Parser.xmlParser());
        String[] tagsProbabilidad = {"P1", "P2", "P3", "P4"};
        ConfiguracionDTO c = new ConfiguracionDTO();
        try {
            for (String s : tagsProbabilidad) {
                ArrayList<Float> aux = new ArrayList<>();
                for (Element e : doc.select(s)) {
                    aux.add(Float.parseFloat(e.html()));
                }
                setArrays(c, s, aux);
            }
            c.setNumeroPasajeros(Integer.parseInt(doc.selectFirst("MAXCAP").html()));
            c.setUtPuertasAbiertas(Integer.parseInt(doc.selectFirst("PUERTA").html()));
            c.setUtEntrePisos(Integer.parseInt(doc.selectFirst("RECORRIDO").html()));
        }
        catch (Exception e){
            Alertas.invocarAlerta("El archivo no posee un schema correcto", Alert.AlertType.ERROR);
        }
        return c;
    }

    private static ConfiguracionDTO parseJSON(String JSON){
        ConfiguracionDTO c = new ConfiguracionDTO();
        JSONObject jsonObject = new JSONObject(JSON);
        String[] tags = {"P1", "P2", "P3", "P4"};
        for (String tag : tags) {
            JSONObject json = new JSONObject();
            switch (tag){
                case "P1": case "P2":
                    json = jsonObject.getJSONObject("PISOS");
                    break;
                case "P3": case "P4":
                    json = jsonObject.getJSONObject("ELEVADORES");
                    break;
            }
            JSONArray array = json.getJSONArray(tag);
            ArrayList<Float> arrayList = new ArrayList<>();
            for(int i=0;i < array.length();i++){
                arrayList.add(((Double) array.getDouble(i)).floatValue());
            }
            setArrays(c, tag, arrayList);
        }
        c.setNumeroPasajeros(jsonObject.getInt("MAXCAP"));
        c.setUtEntrePisos(jsonObject.getInt("RECORRIDO"));
        c.setUtPuertasAbiertas(jsonObject.getInt("PUERTA"));
        return c;
    }

    private static ConfiguracionDTO parseTexto(String texto){
        String[] tags = {"P1", "P2", "P3", "P4"};
        ConfiguracionDTO c = new ConfiguracionDTO();
        for (String tag : tags) {
            Pattern p = Pattern.compile(tag + ":\\s(\\d(\\.*)\\d*)");
            Matcher m = p.matcher(texto);
            ArrayList<Float> array = new ArrayList<>();
            while(m.find()){
                array.add(Float.parseFloat(m.group(1)));
            }
            setArrays(c, tag, array);
        }
        Pattern p = Pattern
        .compile("(Capacidad Maxima|Duracion entre pisos|Duracion puerta abierta):\\s(\\d(\\.*)\\d*)");
        Matcher m = p.matcher(texto);
        int i = 1;
        while(m.find()){
            switch (i){
                case 1:
                    c.setNumeroPasajeros(Integer.parseInt(m.group(2)));
                    break;
                case 2:
                    c.setUtEntrePisos(Integer.parseInt(m.group(2)));
                    break;
                case 3:
                    c.setUtPuertasAbiertas(Integer.parseInt(m.group(2)));
                    break;
            }
            i++;
        }
        return c;
    }

    private static void setArrays(ConfiguracionDTO c, String s, ArrayList<Float> aux) {
        switch (s) {
            case "P1":
                c.setP1S(aux);
                c.setNumeroPisos(aux.size());
                break;
            case "P2":
                c.setP2S(aux);
                break;
            case "P3":
                c.setP3S(aux);
                c.setNumeroElevadores(aux.size());
                break;
            case "P4":
                c.setP4S(aux);
                break;
        }
    }

    private static String readFile(String path, Charset encoding) throws IOException{
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }
}
