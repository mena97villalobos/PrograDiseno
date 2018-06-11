package Model;

import java.util.ArrayList;

public class Piso {
    public int numeroPiso;
    private SensorProximidad sensorProximidad = new SensorProximidad();
    public ArrayList<InterfazBotones> panel;
    private float probabilidaPasajero;
    private float probabilidaPisoDestino;
    public ArrayList<Boolean> estadoPuertas = new ArrayList<>();
    Calendarizador calendarizador;


    public Piso(int numero, float pasajeros, float destino, Calendarizador c) {
        this.numeroPiso = numero;
        this.probabilidaPasajero = pasajeros;
        this.probabilidaPisoDestino = destino;
        this.calendarizador = c;
    }

    void abrirPuertas(int numeroElevador){
        estadoPuertas.set(numeroElevador-1, true);
    }

    void cerrarPuertas(int numeroElevador){
        estadoPuertas.set(numeroElevador-1, false);
    }

}
