package Model;

import java.util.ArrayList;

public class Piso {
    public int numero;
    public SensorProximidad sensorProximidad = new SensorProximidad();
    public ArrayList<BotonInterfaz> panel = new ArrayList<>();
}
