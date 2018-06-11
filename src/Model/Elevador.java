package Model;

import java.util.ArrayList;
import java.util.stream.IntStream;

public class Elevador {
    //Información del Elevador
    int numeroElevador;
    public int pisoActual = 1;
    public ArrayList<Boolean> lucesLlegada = new ArrayList<>();
    public ArrayList<InterfazBotones> panel = new ArrayList<>();
    public Estados estado = Estados.DETENIDO;
    private SensorPeso sensorPeso = new SensorPeso();
    ArrayList<Integer> proximosDestinos = new ArrayList<>();
    boolean detener = false;
    Calendarizador calendarizador;
    ObservadorElevador observador;
    public InterfazBotones botonEmergencia;
    public InterfazBotones botonDetenerse;

    //Probabilidades y variables de simulación
    private float probabilidadEmergencia;
    private float probabilidadDetenerse;
    int utEntrePisos;
    int utPuertasAbiertas;

    public Elevador(int numeroElevador, int numeroPisos, float emergencia, float detenerse,
                    int utEP, int utPA, Calendarizador c, ObservadorElevador observador) {
        this.botonEmergencia = BotonesBuilder.crearBoton(this, Acciones.EMERGENCIA);
        this.botonDetenerse = BotonesBuilder.crearBoton(this, Acciones.DETENERSE);

        this.numeroElevador = numeroElevador;
        lucesLlegada.add(true);
        panel.add(BotonesBuilder.crearBoton(1, this));
        IntStream.range(1, numeroPisos).forEach(i -> {
            lucesLlegada.add(false);
            panel.add(BotonesBuilder.crearBoton(i + 1, this));
        });
        this.probabilidadDetenerse = detenerse;
        this.probabilidadEmergencia = emergencia;
        this.utEntrePisos = utEP;
        this.utPuertasAbiertas = utPA;
        Thread thread = new Thread(new ElevadorThread(this));
        thread.start();
        this.calendarizador = c;
        this.observador = observador;
    }

    void annadirDestino(int destino){
        proximosDestinos.add(destino);
    }

}
