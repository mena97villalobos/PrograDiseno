package Model;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class DestinoBotones implements InterfazBotones, Interrupcion {
    private boolean luz = false;
    private int destino;
    public Button botonInterfaz;
    public Elevador elevador;

    DestinoBotones(int destino, Elevador e){
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
            Instruccion i = crearInstruccion();
            lanzarInterrupcion(i);
        }
    }

    @Override
    public void lanzarInterrupcion(Instruccion instruccion) {
        elevador.calendarizador.annadirInstruccion(instruccion);
    }

    @Override
    public void asociarBotonInterfaz(Button botonInterfaz) {
        this.botonInterfaz = botonInterfaz;
    }

    @Override
    public Instruccion crearInstruccion() {
        int pisoOrigen = elevador.pisoActual;
        return Instruccion.construirInstruccion(destino < pisoOrigen ? Acciones.BAJAR : Acciones.SUBIR,
                pisoOrigen, destino, elevador.numeroElevador);
    }
}
