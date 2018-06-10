package Model;

import java.util.ArrayList;

public class Elevador {
    public ArrayList<Boolean> lucesLlegada = new ArrayList<>();
    public ArrayList<BotonInterfaz> panel = new ArrayList<>();
    int numeroElevador;
    public int pisoActual = 1;
    private float probabilidadEmergencia;
    private float probabilidadDetenerse;
    public Estados estado = Estados.DETENIDO;
    int utEntrePisos;
    int utPuertasAbiertas;
    ArrayList<Integer> proximosDestinos = new ArrayList<>();
    boolean detener = false;
    private Thread thread;
    Calendarizador calendarizador;
    ObservadorElevador observador;

    public Elevador(int numeroElevador, int numeroPisos, float emergencia, float detenerse,
                    int utEP, int utPA, Calendarizador c, ObservadorElevador observador) {
        BotonEmergencia botonEmergencia = new BotonEmergencia();
        botonEmergencia.elevador = this;
        BotonDetenerse botonDetenerse = new BotonDetenerse();
        botonDetenerse.elevador = this;
        this.numeroElevador = numeroElevador;
        lucesLlegada.add(true);
        panel.add(new BotonDestino(numeroElevador, 1, this));
        for(int i = 1; i < numeroPisos; i++) {
            lucesLlegada.add(false);
            panel.add(new BotonDestino(numeroElevador, i+1, this));
        }
        this.probabilidadDetenerse = detenerse;
        this.probabilidadEmergencia = emergencia;
        this.utEntrePisos = utEP;
        this.utPuertasAbiertas = utPA;
        this.thread = new Thread(new ElevadorThread(this));
        this.thread.start();
        this.calendarizador = c;
        this.observador = observador;
    }

    void annadirDestino(int destino){
        proximosDestinos.add(destino);
    }

}
