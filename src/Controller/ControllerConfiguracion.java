package Controller;

import Model.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    private TextField nPisos;
    @FXML
    private Button saveNPisos;
    @FXML
    private TitledPane p1Pane;
    @FXML
    private TextField p1;
    @FXML
    private Button saveP1;
    @FXML
    private ComboBox<String> p1s;
    @FXML
    private TitledPane p2Pane;
    @FXML
    private TextField p2;
    @FXML
    private Button saveP2;
    @FXML
    private ComboBox<String> p2s;
    @FXML
    private TextField maxPeople;
    @FXML
    private TextField utPisos;
    @FXML
    private TextField utAbiertas;
    @FXML
    private TextField cantElevadores;
    @FXML
    private Button saveCantElev;
    @FXML
    private TitledPane p3Pane;
    @FXML
    private TextField p3;
    @FXML
    private Button saveP3;
    @FXML
    private ComboBox<String> p3s;
    @FXML
    private TitledPane p4Pane;
    @FXML
    private TextField p4;
    @FXML
    private Button saveP4;
    @FXML
    private ComboBox<String> p4s;
    @FXML
    private ComboBox<TiposArchivo> saveType;
    @FXML
    private TextField savePath;
    @FXML
    private Button saveAll;
    @FXML
    private Button cargar;
    @FXML
    private Button cDefault;
    @FXML
    private Button simular;

    private ConfiguracionDTO configuracion = new ConfiguracionDTO();
    private final FileChooser fileChooser = new FileChooser();


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setTiposArchivos();
        saveNPisos.setOnAction(event -> {
            int numeroPisos = 0;
            try {
                numeroPisos = Integer.parseInt(nPisos.getText());
            }
            catch (Exception e){
                Alertas.invocarAlerta("Debe indicar un número entero", Alert.AlertType.ERROR);
            }
            hiloAtributos(numeroPisos, p1s, configuracion.getP1S(), saveP1);
            hiloAtributos(numeroPisos, p2s, configuracion.getP2S(), saveP2);
            p1Pane.setDisable(false);
            p2Pane.setDisable(false);
            nPisos.setDisable(true);
            saveNPisos.setDisable(true);
            configuracion.setNumeroPisos(numeroPisos);
        });
        saveP1.setOnAction(event -> manejarAnnadir(p1, p1s));
        saveP2.setOnAction(event -> manejarAnnadir(p2, p2s));

        saveCantElev.setOnAction(event -> {
            int numeroElevadores = 0;
            try {
                 numeroElevadores = Integer.parseInt(cantElevadores.getText());
            }
            catch (Exception e){
                Alertas.invocarAlerta("Debe indicar un número entero", Alert.AlertType.ERROR);
            }
            hiloAtributos(numeroElevadores, p3s, configuracion.getP3S(), saveP3);
            hiloAtributos(numeroElevadores, p4s, configuracion.getP4S(), saveP4);
            p3Pane.setDisable(false);
            p4Pane.setDisable(false);
            cantElevadores.setDisable(true);
            saveCantElev.setDisable(true);
            configuracion.setNumeroElevadores(numeroElevadores);
        });
        saveP3.setOnAction(event -> manejarAnnadir(p3, p3s));
        saveP4.setOnAction(event -> manejarAnnadir(p4, p4s));

        saveAll.setOnAction(event -> {
            try {
                configuracion.setNumeroPasajeros(Integer.parseInt(maxPeople.getText()));
                configuracion.setUtEntrePisos(Integer.parseInt(utPisos.getText()));
                configuracion.setUtPuertasAbiertas(Integer.parseInt(utAbiertas.getText()));
                configuracion.setTipoSalida(saveType.getSelectionModel().getSelectedItem());
                configuracion.setPathSalida(savePath.getText());
            }
            catch (Exception e) {
                Alertas.invocarAlerta("Error de tipo de datos", Alert.AlertType.ERROR);
            }
            switch (configuracion.getTipoSalida()){
                case JSON:
                    FileHandle.guardarJSON(configuracion);
                    break;
                case XML:
                    FileHandle.guardarXML(configuracion);
                    break;
                case TEXT:
                    FileHandle.guardarTEXT(configuracion);
                    break;
                default:
                    Alertas.invocarAlerta("Error de tipo de archivo", Alert.AlertType.ERROR);
                    break;

            }
        });
        cargar.setOnAction(event -> {
            File file = fileChooser.showOpenDialog(cargar.getScene().getWindow());
            if (file != null) {
                configuracion = FileHandle.cargarArchivo(file);
                cargarConfiguraciones(configuracion);
            }
            else
                Alertas.invocarAlerta("Seleccione algún archivo", Alert.AlertType.ERROR);
        });
        cDefault.setOnAction(event -> cargarConfiguraciones(new ConfiguracionDTO()));

        simular.setOnAction(event -> {
            Simulacion s = new Simulacion(configuracion);
            s.iniciar();
            abrir_pantallas(s.elevadores, s.pisos);
        });
    }

    private void hiloAtributos(int cantidadDeseada, ComboBox<String> revisar, ArrayList<Float> guardar, Button agregar){
        Task task = new Task() {
            @Override
            protected Void call() {
                int count = 0;
                revisar.setItems(FXCollections.observableArrayList(new ArrayList<>()));
                while(count != cantidadDeseada)
                    count = new ArrayList<>(revisar.getItems()).size();

                String floatPattern = "^(0\\.\\d+).*";
                Pattern p = Pattern.compile(floatPattern);
                ArrayList<Float> aux = new ArrayList<>();
                for(String s : new ArrayList<>(revisar.getItems())){
                    Matcher m = p.matcher(s);
                    if(m.matches()) {
                        String numero = m.group(1);
                        float current = Float.parseFloat(numero);
                        aux.add(current);
                    }
                }
                float probabilidad = 0;
                for (Float aFloat : aux) {
                    probabilidad += aFloat;
                }
                if(Math.abs(probabilidad - 1.0) > 0.005) {
                    Platform.runLater(() -> {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setContentText("La probabilidad debe ser 1.0");
                        alert.show();
                    });
                    revisar.setItems(FXCollections.observableArrayList());
                    hiloAtributos(cantidadDeseada, revisar, guardar, agregar);
                    return null;
                }
                else{
                    agregar.setDisable(true);
                    guardar.clear();
                    guardar.addAll(aux);
                    return null;
                }
            }
        };
        Thread t = new Thread(task);
        t.start();
    }

    private void setTiposArchivos(){
        List<TiposArchivo> aux = Arrays.asList(TiposArchivo.values());
        saveType.setItems(FXCollections.observableArrayList(aux));
    }

    private void manejarAnnadir(TextField manejarTexto, ComboBox<String> manejarItems){
        try{
            ArrayList<String> aux = new ArrayList<>(manejarItems.getItems());
            String s = manejarTexto.getText() + ": Para piso/elevador #" + Integer.toString(aux.size()+1);
            aux.add(s);
            manejarItems.setItems(FXCollections.observableArrayList(aux));
        }
        catch (Exception e){
            Alertas.invocarAlerta("Error al recuperar datos", Alert.AlertType.ERROR);
        }
    }

    private void cargarConfiguraciones(ConfiguracionDTO c){
        if(c == null)
            c = new ConfiguracionDTO();
        p1Pane.setDisable(false);
        p2Pane.setDisable(false);
        p3Pane.setDisable(false);
        p4Pane.setDisable(false);
        saveP1.setDisable(true);
        saveP2.setDisable(true);
        saveP3.setDisable(true);
        saveP4.setDisable(true);
        nPisos.setText(Integer.toString(c.getNumeroPisos()));
        p1s.setItems(FXCollections.observableArrayList(convertirFlaotaString(c.getP1S())));
        p2s.setItems(FXCollections.observableArrayList(convertirFlaotaString(c.getP2S())));
        cantElevadores.setText(Integer.toString(c.getNumeroElevadores()));
        p3s.setItems(FXCollections.observableArrayList(convertirFlaotaString(c.getP3S())));
        p4s.setItems(FXCollections.observableArrayList(convertirFlaotaString(c.getP4S())));
        maxPeople.setText(Integer.toString(c.getNumeroPasajeros()));
        utPisos.setText(Integer.toString(c.getUtEntrePisos()));
        utAbiertas.setText(Integer.toString(c.getUtPuertasAbiertas()));
    }

    private ArrayList<String> convertirFlaotaString(ArrayList<Float> array){
        ArrayList<String> aux = new ArrayList<>();
        for (Float aFloat : array) {
            aux.add(Float.toString(aFloat) + ": Para piso/elevador #" + Integer.toString(aux.size()+1));
        }
        return aux;
    }

    public void abrir_pantallas(ArrayList<Elevador> e, ArrayList<Piso> p){
        int i = 1;
        for (Elevador elevador : e) {
            try {
                FXMLLoader loader = new FXMLLoader();
                Parent root = loader.load(getClass().getResource("../View/Elevador.fxml").openStream());
                ControllerElevador c = loader.getController();
                c.elevador = elevador;
                c.iniciar();
                Stage escenario = new Stage();
                escenario.setTitle("Elevador " + Integer.toString(i));
                escenario.setScene(new Scene(root, 920, 728));
                escenario.show();
            } catch (IOException exception) {
                exception.printStackTrace();
            }
            i++;
        }
        Stage actual = (Stage) simular.getScene().getWindow();
        actual.close();
    }

}
