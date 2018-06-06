package Controller;

import Model.BotonDestino;
import Model.BotonInterfaz;
import Model.Elevador;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
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
import java.util.ResourceBundle;

public class ControllerElevador implements Initializable {
    @FXML
    public Pane panel;
    @FXML
    public ImageView puerta;
    @FXML
    public Pane lucesLlegadaPane;

    public Elevador elevador;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/Resources/puertaCerrada.png");
        Image image = new Image(file.toURI().toString());
        puerta.setImage(image);
    }

    public void iniciar(){
        int x = 14;
        for (Boolean aBoolean : elevador.lucesLlegada) {
            Rectangle r = new Rectangle();
            r.setWidth(37);
            r.setHeight(30);
            r.setX(x);
            r.setY(17);
            r.setFill(aBoolean ? Color.LIME : Color.YELLOW);
            lucesLlegadaPane.getChildren().add(r);
            x += 44;
        }
        x = 14;
        int y = 14;
        int j = 1;
        int maximo = 6;
        int iteracion = 1;
        for (BotonInterfaz botonInterfaz : elevador.panel) {
            Button b = new Button();
            b.setBackground(new Background(new BackgroundFill(
                    Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            b.setOnAction(event -> botonInterfaz.presionar());
            b.setLayoutX(x);
            b.setLayoutY(y);
            b.setText(Integer.toString(j));
            b.setPrefHeight(30);
            b.setPrefWidth(37);
            ((BotonDestino) botonInterfaz).botonInterfaz = b;
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
    }
}
