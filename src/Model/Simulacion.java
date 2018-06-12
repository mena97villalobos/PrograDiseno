package Model;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.scene.control.Alert;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Simulacion implements Runnable {
    private ConfiguracionDTO c;
    public ArrayList<Piso> pisos = new ArrayList<>();
    public ArrayList<Elevador> elevadores = new ArrayList<>();
    private boolean stop = false;
    private Random random = new Random();

    public Simulacion(ConfiguracionDTO c) {
        this.c = c;
        PisoFactory.configuracion = c;
    }

    public void iniciar(){
        Calendarizador calendarizador = new Calendarizador(elevadores);
        Thread thread = new Thread(calendarizador);
        thread.start();
        for(int i = 0; i < c.getNumeroPisos(); i++)
            pisos.add(PisoFactory.buidPiso(i+1, c.getP1S(), c.getP2S(), calendarizador));
        int numeroPisos = c.getNumeroPisos();
        for(int i = 0; i < c.getNumeroElevadores(); i++) {
            Elevador e = new Elevador(i + 1, numeroPisos, c.getP3S().get(i), c.getP4S().get(i),
                    c.getUtEntrePisos(), c.getUtPuertasAbiertas(), calendarizador, new ObservadorElevador(pisos));
            e.setMaxPasajeros(c.getNumeroPasajeros());
            elevadores.add(e);
        }
    }

    private void generarPasajero(int destino, int origen){
        Piso pisoOrigen = pisos.get(origen-1);
        boolean solicitado = false;
        for (int i = 0; i < pisoOrigen.estadoPuertas.size(); i++) {
            if(pisoOrigen.estadoPuertas.get(i)){
                Elevador elevador = elevadores.get(i);
                elevador.panel.get(destino-1).presionar();
                solicitado = true;
                break;
            }
        }
        if(!solicitado){
            hiloEspera(pisoOrigen, destino);
        }
    }

    private void hiloEspera(Piso pisoOrigen, int destino){
        Task task = new Task() {
            @Override
            protected Void call() {
                boolean esperando = true;
                int elevadorDisponible = 0;
                while(esperando){
                    for (int i = 0; i < pisoOrigen.estadoPuertas.size(); i++) {
                        if (pisoOrigen.estadoPuertas.get(i)){
                            esperando = false;
                            elevadorDisponible = i;
                            break;
                        }
                    }
                }
                elevadores.get(elevadorDisponible)
                        .panel.get(destino-1).presionar();
                return null;
            }
        };
        Thread t = new Thread(task);
        t.start();
    }

    @Override
    public void run() {
        while(!stop){
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            int pisoDestino = pisos.size();
            int pisoOrigen = 1;
            for (Piso piso : pisos) {
                float probabilidad = random.nextFloat();
                if(probabilidad <= piso.probabilidaPasajero){
                    pisoOrigen = piso.numeroPiso;
                    for (Piso piso1 : pisos) {
                        if(!piso1.equals(piso)){
                            float probabilidad2 = random.nextFloat();
                            if(probabilidad2 <= piso1.probabilidaPisoDestino){
                                pisoDestino = piso1.numeroPiso;
                            }
                        }
                    }
                }
            }
            generarPasajero(pisoDestino, pisoOrigen);
        }
    }
}
