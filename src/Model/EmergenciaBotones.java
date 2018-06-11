package Model;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;

import java.io.File;

public class EmergenciaBotones implements InterfazBotones, Interrupcion {
    private boolean luz = false;
    private Button botonInterfaz;
    public Elevador elevador;

    public EmergenciaBotones(Elevador elevador) {
        this.elevador = elevador;
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
            encenderAlarma();
        }
    }

    @Override
    public void asociarBotonInterfaz(Button botonInterfaz) {
        this.botonInterfaz = botonInterfaz;
    }

    @Override
    public void lanzarInterrupcion(Instruccion instruccion) {
        elevador.calendarizador.annadirInstruccion(instruccion);
    }

    @Override
    public Instruccion crearInstruccion() {
        return Instruccion.construirInstruccion(Acciones.EMERGENCIA, elevador);
    }

    private void encenderAlarma(){
        Media sound = new Media(new File("src/Resources/alarma.mp3").toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
