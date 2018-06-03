package Controller;

import Model.TiposArchivo;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ControllerConfiguracion implements Initializable {
    @FXML
    public TextField nPisos;
    @FXML
    public Button saveNPisos;
    @FXML
    public TitledPane p1Pane;
    @FXML
    public TextField p1;
    @FXML
    public Button saveP1;
    @FXML
    public ComboBox<String> p1s;
    @FXML
    public TitledPane p2Pane;
    @FXML
    public TextField p2;
    @FXML
    public Button saveP2;
    @FXML
    public ComboBox<String> p2s;
    @FXML
    public TextField maxPeople;
    @FXML
    public TextField utPisos;
    @FXML
    public TextField utAbiertas;
    @FXML
    public TextField cantElevadores;
    @FXML
    public Button saveCantElev;
    @FXML
    public TitledPane p3Pane;
    @FXML
    public TextField p3;
    @FXML
    public Button saveP3;
    @FXML
    public ComboBox<String> p3s;
    @FXML
    public TitledPane p4Pane;
    @FXML
    public TextField p4;
    @FXML
    public Button saveP4;
    @FXML
    public ComboBox<String> p4s;
    @FXML
    public ComboBox<TiposArchivo> saveType;
    @FXML
    public TextField savePath;
    @FXML
    public Button saveAll;
    @FXML
    public Button cargar;

    private ArrayList<Float> p1S = new ArrayList<>();
    private ArrayList<Float> p2S = new ArrayList<>();
    private ArrayList<Float> p3S = new ArrayList<>();
    private ArrayList<Float> p4S = new ArrayList<>();

    private int numeroPisos;
    private int numeroElevadores;
    private int numeroPasajeros;
    private int utEntrePisos;
    private int utPuertasAbiertas;
    private String pathSalida;
    private TiposArchivo tipoSalida;
    final FileChooser fileChooser = new FileChooser();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTiposArchivos();
        saveNPisos.setOnAction(event -> {
            int numeroPisos = 0;
            try {
                numeroPisos = Integer.parseInt(nPisos.getText());
            }
            catch (Exception e){
                invocarAlerta("Debe indicar un número entero", Alert.AlertType.ERROR);
            }
            hiloAtributos(numeroPisos, p1s, p1S);
            hiloAtributos(numeroPisos, p2s, p2S);
            p1Pane.setDisable(false);
            p2Pane.setDisable(false);
            nPisos.setDisable(true);
            saveNPisos.setDisable(true);
            this.numeroPisos = numeroPisos;
        });
        saveP1.setOnAction(event -> manejarAnnadir(p1, p1s));
        saveP2.setOnAction(event -> manejarAnnadir(p2, p2s));

        saveCantElev.setOnAction(event -> {
            int numeroElevadores = 0;
            try {
                 numeroElevadores = Integer.parseInt(cantElevadores.getText());
            }
            catch (Exception e){
                invocarAlerta("Debe indicar un número entero", Alert.AlertType.ERROR);
            }
            hiloAtributos(numeroElevadores, p3s, p3S);
            hiloAtributos(numeroElevadores, p4s, p4S);
            p3Pane.setDisable(false);
            p4Pane.setDisable(false);
            cantElevadores.setDisable(true);
            saveCantElev.setDisable(true);
            this.numeroElevadores = numeroElevadores;
        });
        saveP3.setOnAction(event -> manejarAnnadir(p3, p3s));
        saveP4.setOnAction(event -> manejarAnnadir(p4, p4s));

        saveAll.setOnAction(event -> {
            try {
                this.numeroPasajeros = Integer.parseInt(maxPeople.getText());
                this.utEntrePisos = Integer.parseInt(utPisos.getText());
                this.utPuertasAbiertas = Integer.parseInt(utAbiertas.getText());
                this.tipoSalida = saveType.getSelectionModel().getSelectedItem();
                this.pathSalida = savePath.getText();
            }
            catch (Exception e) {
                invocarAlerta("Error de tipo de datos", Alert.AlertType.ERROR);
            }
            switch (tipoSalida){
                case JSON:
                    guardarJSON();
                    break;
                case XML:
                    guardarXML();
                    break;
                case TEXT:
                    guardarTEXT();
                    break;
                default:
                    invocarAlerta("Error de tipo de archivo", Alert.AlertType.ERROR);
                    break;

            }
        });
        cargar.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(cargar.getScene().getWindow());
            if (file != null)
                cargarArchivo(file);
            else
                invocarAlerta("Seleccione algún archivo", Alert.AlertType.ERROR);
        });

    }

    private void hiloAtributos(int cantidadDeseada, ComboBox<String> revisar, ArrayList<Float> guardar){
        Task task = new Task() {
            @Override
            protected Void call() {
                int count = 0;
                revisar.setItems(FXCollections.observableArrayList(new ArrayList<>()));
                while(count != cantidadDeseada)
                    count = new ArrayList<>(revisar.getItems()).size();
                float probabilidad = 0;
                String floatPattern = "^(0\\.\\d+).*";
                Pattern p = Pattern.compile(floatPattern);
                ArrayList<Float> aux = new ArrayList<>();
                for(String s : new ArrayList<>(revisar.getItems())){
                    Matcher m = p.matcher(s);
                    if(m.matches()) {
                        String numero = m.group(1);
                        float current = Float.parseFloat(numero);
                        aux.add(current);
                        probabilidad += current;
                    }
                    else{
                        probabilidad = 0;
                        break;
                    }
                }
                if(probabilidad != 1.0) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("La probabilidad debe ser 1.0");
                        alert.show();
                    });
                    revisar.setItems(FXCollections.observableArrayList());
                    hiloAtributos(cantidadDeseada, revisar, guardar);
                    return null;
                }
                else{
                    (revisar.getParent()).setDisable(true);
                    guardar.clear();
                    guardar.addAll(aux);
                    return null;
                }
            }
        };
        Thread t = new Thread(task);
        t.start();
    }

    private void invocarAlerta(String mensaje, Alert.AlertType tipo) {
        Alert nuevaAlerta = new Alert(tipo);
        nuevaAlerta.setTitle("");
        nuevaAlerta.setContentText(mensaje);
        nuevaAlerta.showAndWait();
    }

    private void setTiposArchivos(){
        List<TiposArchivo> aux = Arrays.asList(TiposArchivo.values());
        saveType.setItems(FXCollections.observableArrayList(aux));
    }

    private void manejarAnnadir(TextField manejarTexto, ComboBox<String> manejarItems){
        try{
            ArrayList<String> aux = new ArrayList<>(manejarItems.getItems());
            String s = manejarTexto.getText() + ": Para piso #" + Integer.toString(aux.size()+1);
            aux.add(s);
            manejarItems.setItems(FXCollections.observableArrayList(aux));
        }
        catch (Exception e){
            invocarAlerta("Error al recuperar datos", Alert.AlertType.ERROR);
        }
    }

    private void guardarJSON(){
        StringBuilder JSON = new StringBuilder("{\"PISOS\":{\"P1\":[");
        for (int i = 0; i < p1S.size(); i++) {
            if(i + 1 == p1S.size())
                JSON.append(Float.toString(p1S.get(i)));
            else
                JSON.append(Float.toString(p1S.get(i))).append(", ");
        }
        JSON.append("], \"P2\":[");
        for (int i = 0; i < p2S.size(); i++) {
            if(i + 1 == p2S.size())
                JSON.append(Float.toString(p2S.get(i)));
            else
                JSON.append(Float.toString(p2S.get(i))).append(", ");
        }
        JSON.append("]}, \"ELEVADORES\":{\"P3\":[");
        for (int i = 0; i < p3S.size(); i++) {
            if(i + 1 == p3S.size())
                JSON.append(Float.toString(p3S.get(i)));
            else
                JSON.append(Float.toString(p3S.get(i))).append(", ");
        }
        JSON.append("], \"P4\":[");
        for (int i = 0; i < p4S.size(); i++) {
            if(i + 1 == p4S.size())
                JSON.append(Float.toString(p4S.get(i)));
            else
                JSON.append(Float.toString(p4S.get(i))).append(", ");
        }
        JSON.append("]}, \"MAXCAP\":").append(Integer.toString(numeroPasajeros))
                .append(", \"RECORRIDO\":").append(Integer.toString(utEntrePisos))
                .append(", \"PUERTA\":").append(Integer.toString(utPuertasAbiertas))
                .append("}");
        guardarArchivo(pathSalida, ".json", JSON.toString());
    }

    private void guardarXML(){
        StringBuilder XML = new StringBuilder("<CONFIGURACION>\n<PISOS>\n\t<NUMERO>");
        XML.append(Integer.toString(numeroPisos)).append("</NUMERO>\n");
        for (Float aFloat : p1S) {
            XML.append("\t<P1>").append(Float.toString(aFloat)).append("</P1>\n");
        }
        for (Float aFloat : p2S) {
            XML.append("\t<P2>").append(Float.toString(aFloat)).append("</P2>\n");
        }
        XML.append("</PISOS>\n<ELEVADORES>\n\t<NUMERO>").append(Integer.toString(numeroElevadores)).append("</NUMERO>\n");
        for (Float aFloat : p3S) {
            XML.append("\t<P3>").append(Float.toString(aFloat)).append("</P3>\n");
        }
        for (Float aFloat : p4S) {
            XML.append("\t<P4>").append(Float.toString(aFloat)).append("</P4>\n");
        }
        XML.append("</ELEVADORES>\n<MAXCAP>").append(Integer.toString(numeroPasajeros))
                .append("</MAXCAP>\n<UT>\n\t<RECORRIDO>").append(Integer.toString(utEntrePisos))
                .append("</RECORRIDO>\n\t<PUERTA>").append(Integer.toString(utPuertasAbiertas))
                .append("</PUERTA>\n</UT>\n</CONFIGURACION>");
        guardarArchivo(pathSalida, ".xml", XML.toString());

    }

    private void guardarTEXT(){
        StringBuilder texto = new StringBuilder("Numero de pisos: " + Integer.toString(numeroPisos) + "\n");
        for (Float aFloat : p1S) {
            texto.append("P1: ").append(Float.toString(aFloat)).append("\n");
        }
        for (Float aFloat : p2S) {
            texto.append("P2: ").append(Float.toString(aFloat)).append("\n");
        }
        texto.append("Numero de elevadores: ").append(Integer.toString(numeroElevadores)).append("\n");
        for (Float aFloat : p3S) {
            texto.append("P3: ").append(Float.toString(aFloat)).append("\n");
        }
        for (Float aFloat : p4S) {
            texto.append("P4: ").append(Float.toString(aFloat)).append("\n");
        }
        texto.append("Capacidad Maxima: ").append(Integer.toString(numeroPasajeros))
                .append("\nDuracion entre pisos: ").append(Integer.toString(utEntrePisos))
                .append("\nDuracion puerta abierta: ").append(Integer.toString(utPuertasAbiertas));
        guardarArchivo(pathSalida, ".txt", texto.toString());
    }

    private void guardarArchivo(String path, String extension, String contenido){
        try (Writer writer = new BufferedWriter(new OutputStreamWriter(
                new FileOutputStream(path + extension), "utf-8"))) {
            writer.write(contenido);
            invocarAlerta("Archivo guardado", Alert.AlertType.INFORMATION);
        } catch (UnsupportedEncodingException e) {
            invocarAlerta("Encoding no soportado", Alert.AlertType.ERROR);
        } catch (FileNotFoundException e) {
            invocarAlerta("Archivo no encontrado", Alert.AlertType.ERROR);
        } catch (IOException e) {
            invocarAlerta("IOException", Alert.AlertType.ERROR);
        }
    }

    private void cargarArchivo(File file){

    }
}
