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
    private Instruccion instruccion;
    public Elevador elevador;

    public BotonDestino(int numeroElevador, int destino, Elevador e){
        this.label = Integer.toString(destino);
        this.numeroElevador = numeroElevador;
        this.destino = destino;
        this.elevador = e;
    }

    @Override
    public void prenderLuz() {
        botonInterfaz.setBackground(new Background(new BackgroundFill(
                Color.LIME, CornerRadii.EMPTY, Insets.EMPTY)));
        luz = true;
    }

    @Override
    public void apagarLuz() {
        botonInterfaz.setBackground(new Background(new BackgroundFill(
                Color.YELLOW, CornerRadii.EMPTY, Insets.EMPTY)));
        luz = false;
    }

    @Override
    public void presionar() {
        if(luz)
            apagarLuz();
        else{
            prenderLuz();
            crearInstruccion();
            lanzarInterrupcion();
        }
    }

    @Override
    public void lanzarInterrupcion() {
        elevador.calendarizador.annadirInstruccion(instruccion);
    }

    @Override
    public void crearInstruccion() {
        int pisoOrigen = elevador.pisoActual;
        int pisoDestino = Integer.parseInt(label);
        instruccion = new InstruccionSubirBajar(pisoOrigen, pisoDestino,
                pisoDestino > pisoOrigen ? Acciones.BAJAR : Acciones.SUBIR, elevador.numeroElevador);
    }
}
