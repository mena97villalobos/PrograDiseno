package Model;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class BotonDestino implements BotonInterfaz, Interrupcion {
    boolean luz = false;
    String label;
    int numeroElevador;
    int destino;
    public Button botonInterfaz;

    public BotonDestino(int numeroElevador, int destino){
        this.label = "Piso: " + Integer.toString(destino);
        this.numeroElevador = numeroElevador;
        this.destino = destino;
    }

    @Override
    public void prenderLuz() {

    }

    @Override
    public void apagarLuz() {

    }

    @Override
    public void presionar() {
        botonInterfaz.setBackground(new Background(new BackgroundFill(
                Color.LIME, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    @Override
    public void lanzarInterrupcion() {

    }

    @Override
    public void crearInstruccion() {

    }
}
