package Controller;

import Model.*;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;


import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerPiso implements Initializable {

    @FXML
    public Pane panel;
    @FXML
    public Pane elevadoresPanel;

    public Piso piso;
    private ArrayList<ImageView> puertasElevadores = new ArrayList<>();
    private Image puertaAbierta;
    private Image puertaCerrada;
    private boolean stop = false;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/Resources/puertaAbierta.png");
        puertaAbierta = new Image(file.toURI().toString());
        file = new File("src/Resources/puertaCerrada.png");
        puertaCerrada = new Image(file.toURI().toString());
    }

    void iniciar(ArrayList<Elevador> elevadores){
        for (InterfazBotones interfazBoton : piso.panel) {
            Button b = new Button();
            b.setBackground(new Background(new BackgroundFill(
                    Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            b.setOnAction(event -> interfazBoton.presionar());
            String text;
            if(interfazBoton instanceof SubirBotones){
                b.setLayoutX(18);
                b.setLayoutY(14);
                text = "SUBIR";
            }
            else{
                b.setLayoutX(18);
                b.setLayoutY(55);
                text = "BAJAR";
            }
            b.setText(text);
            interfazBoton.asociarBotonInterfaz(b);
            panel.getChildren().add(b);
        }
        int x = 14;
        ArrayList<Boolean> estadoPuertas = new ArrayList<>();
        for (Elevador elevadore : elevadores) {
            ImageView imagen = new ImageView();
            puertasElevadores.add(imagen);
            imagen.setLayoutY(14);
            imagen.setLayoutX(x);
            boolean elevadorEnPiso = elevadore.pisoActual == piso.numeroPiso;
            imagen.setImage(elevadorEnPiso ? puertaAbierta : puertaCerrada);
            estadoPuertas.add(elevadorEnPiso);
            imagen.setFitHeight(295);
            imagen.setFitWidth(200);
            elevadoresPanel.getChildren().add(imagen);
            x += 214;
        }
        piso.estadoPuertas = estadoPuertas;
        actualizarInfo();
    }

    private void actualizarInfo(){
        Task task = new Task() {
            @Override
            protected Void call(){
                while (!stop) {
                    ImageView imagen;
                    for (int i = 0; i < piso.estadoPuertas.size(); i++) {
                        imagen = puertasElevadores.get(i);
                        imagen.setImage(piso.estadoPuertas.get(i) ? puertaAbierta : puertaCerrada);
                    }
                }
                return null;
            }
        };
        Thread t = new Thread(task);
        t.start();
    }
}
