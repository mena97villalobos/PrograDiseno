package Model;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;

public class BotonBajar implements BotonInterfaz, Interrupcion{
    private boolean luz = false;
    public String label = "BAJAR";
    public Button botonInterfaz;
    private Instruccion instruccion;
    public Piso piso;

    BotonBajar(Piso piso) {
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
            crearInstruccion();
            lanzarInterrupcion();
        }
    }

    @Override
    public void lanzarInterrupcion() {
        piso.calendarizador.annadirInstruccion(instruccion);
    }

    @Override
    public void crearInstruccion() {
        instruccion = new InstruccionSubirBajar(piso.numeroPiso, piso.numeroPiso, Acciones.BAJAR, -1);
    }
}
