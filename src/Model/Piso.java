package Model;

import java.util.ArrayList;

public class Piso {
    int numeroPiso;
    private SensorProximidad sensorProximidad = new SensorProximidad();
    public ArrayList<BotonInterfaz> panel;
    private float probabilidaPasajero;
    private float probabilidaPisoDestino;
    Calendarizador calendarizador;

    public Piso(int numero, float pasajeros, float destino, Calendarizador c) {
        this.numeroPiso = numero;
        this.probabilidaPasajero = pasajeros;
        this.probabilidaPisoDestino = destino;
        this.calendarizador = c;
    }

}
