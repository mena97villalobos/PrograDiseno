package Model;

import java.util.ArrayList;

public class Piso {
    public int numero;
    public SensorProximidad sensorProximidad = new SensorProximidad();
    public ArrayList<BotonInterfaz> panel;

    public Piso(int numero, ArrayList<BotonInterfaz> panel) {
        this.numero = numero;
        this.panel = panel;
    }
}
