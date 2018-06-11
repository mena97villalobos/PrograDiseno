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
import javafx.scene.shape.Rectangle;

import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class ControllerElevador implements Initializable {
    @FXML
    public Pane panel;
    @FXML
    public ImageView puerta;
    @FXML
    public Pane lucesLlegadaPane;
    @FXML
    public Button detener;
    @FXML
    public Button emergencia;

    public Elevador elevador;
    private boolean stop = false;
    private ArrayList<Rectangle> lucesLlegada = new ArrayList<>();
    private Image puertaCerrada;
    private Image puertaAbierta;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/Resources/puertaAbierta.png");
        puertaAbierta = new Image(file.toURI().toString());
        file = new File("src/Resources/puertaCerrada.png");
        puertaCerrada = new Image(file.toURI().toString());
        puerta.setImage(puertaAbierta);
        detener.setBackground(new Background(new BackgroundFill(
                Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        emergencia.setBackground(new Background(new BackgroundFill(
                Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    void iniciar(){
        int x = 14;
        for (Boolean aBoolean : elevador.lucesLlegada) {
            Rectangle r = new Rectangle();
            r.setWidth(37);
            r.setHeight(30);
            r.setX(x);
            r.setY(17);
            r.setFill(aBoolean ? Color.LIME : Color.YELLOW);
            lucesLlegadaPane.getChildren().add(r);
            lucesLlegada.add(r);
            x += 44;
        }
        x = 14;
        int y = 14;
        int j = 1;
        int maximo = 6;
        int iteracion = 1;
        for (InterfazBotones botonInterfaz : elevador.panel) {
            Button b = new Button();
            b.setBackground(new Background(new BackgroundFill(
                    Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            b.setOnAction(event -> botonInterfaz.presionar());
            b.setLayoutX(x);
            b.setLayoutY(y);
            b.setText(Integer.toString(j));
            b.setPrefHeight(30);
            b.setPrefWidth(37);
            ((DestinoBotones) botonInterfaz).botonInterfaz = b;
            panel.getChildren().add(b);
            x += 44;
            j++;
            maximo--;
            if(maximo == 0){
                y = (30 * iteracion) + (14 * (iteracion+1));
                x = 14;
                maximo = 6;
                iteracion++;
            }
        }
        elevador.botonEmergencia.asociarBotonInterfaz(this.emergencia);
        elevador.botonDetenerse.asociarBotonInterfaz(this.detener);
        this.emergencia.setOnAction(event -> elevador.botonEmergencia.presionar());
        this.detener.setOnAction(event -> elevador.botonDetenerse.presionar());
        actualizarInfo();
    }

    private void actualizarInfo(){
        Task task = new Task() {
            @Override
            protected Void call(){
            while (!stop) {
                if (elevador.estado.equals(Estados.DETENIDO))
                    puerta.setImage(puertaAbierta);
                else
                    puerta.setImage(puertaCerrada);
                for (int i = 0; i < elevador.lucesLlegada.size(); i++) {
                    if (elevador.lucesLlegada.get(i)) {
                        Rectangle r = lucesLlegada.get(i);
                        r.setFill(Color.LIME);
                    } else {
                        Rectangle r = lucesLlegada.get(i);
                        r.setFill(Color.YELLOW);
                    }
                }
            }
            return null;
            }
        };
        Thread t = new Thread(task);
        t.start();
    }
}
