package Model;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class BotonEmergencia implements BotonInterfaz, Interrupcion {
    private boolean luz = false;
    private Button botonInterfaz;
    private Instruccion instruccion;
    public Elevador elevador;

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
        instruccion = new InstruccionEmergenciaDetenerse(elevador, Acciones.EMERGENCIA);
    }
}
