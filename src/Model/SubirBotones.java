package Model;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class SubirBotones implements InterfazBotones, Interrupcion {
    private boolean luz = false;
    public String label = "SUBIR";
    private Button botonInterfaz;
    public Piso piso;

    SubirBotones(Piso piso) {
        this.piso = piso;
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
    public void asociarBotonInterfaz(Button botonInterfaz) {
        this.botonInterfaz = botonInterfaz;
    }

    @Override
    public void lanzarInterrupcion(Instruccion i) {
        piso.calendarizador.annadirInstruccion(i);
    }

    @Override
    public Instruccion crearInstruccion() {
        return Instruccion.construirInstruccion(Acciones.SUBIR,
                piso.numeroPiso, piso.numeroPiso, -1);
    }
}
