package Controller;

import Model.*;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerPiso implements Initializable {

    @FXML
    public Pane panel;

    public Piso piso;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void iniciar(){
        for (BotonInterfaz botonInterfaz : piso.panel) {
            Button b = new Button();
            b.setBackground(new Background(new BackgroundFill(
                    Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
            b.setOnAction(event -> botonInterfaz.presionar());
            String text = "";
            try{
                ((BotonSubir) botonInterfaz).botonInterfaz = b;
                b.setLayoutX(18);
                b.setLayoutY(14);
                text = ((BotonSubir) botonInterfaz).label;
            }
            catch (Exception e){
                try {
                    ((BotonBajar) botonInterfaz).botonInterfaz = b;
                    b.setLayoutX(18);
                    b.setLayoutY(55);
                    text = ((BotonBajar) botonInterfaz).label;
                }
                catch (ClassCastException cce){

                }
            }
            b.setText(text);
            panel.getChildren().add(b);
        }
    }
}
