package Model;

import java.util.ArrayList;

public class Simulacion {
    public ConfiguracionDTO c;
    public ArrayList<Piso> pisos = new ArrayList<>();
    public ArrayList<Elevador> elevadores = new ArrayList<>();

    public Simulacion(ConfiguracionDTO c) {
        this.c = c;
        PisoFactory.configuracion = c;
    }

    public void iniciar(){
        for(int i = 0; i < c.getNumeroPisos(); i++)
            pisos.add(PisoFactory.buidPiso(i+1));
        int numeroPisos = c.getNumeroPisos();
        for(int i = 0; i < c.getNumeroElevadores(); i++)
            elevadores.add(new Elevador(i+1, numeroPisos));
    }
}
