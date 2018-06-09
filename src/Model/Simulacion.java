package Model;

import java.util.ArrayList;

public class Simulacion {
    private ConfiguracionDTO c;
    public ArrayList<Piso> pisos = new ArrayList<>();
    public ArrayList<Elevador> elevadores = new ArrayList<>();

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
        for(int i = 0; i < c.getNumeroElevadores(); i++)
            elevadores.add(new Elevador(i+1, numeroPisos, c.getP3S().get(i), c.getP4S().get(i),
                    c.getUtEntrePisos(), c.getUtPuertasAbiertas(), calendarizador));


    }
}
