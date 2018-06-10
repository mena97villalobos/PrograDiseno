package Model;

import java.util.ArrayList;

class PisoFactory {
    static ConfiguracionDTO configuracion;

    static Piso buidPiso(int numeroPiso, ArrayList<Float> p1, ArrayList<Float> p2, Calendarizador c){
        ArrayList<BotonInterfaz> aux = new ArrayList<>();
        Piso p = new Piso(numeroPiso, p1.get(numeroPiso-1), p2.get(numeroPiso-1), c);
        if(numeroPiso == configuracion.getNumeroPisos()){
            aux.add(new BotonBajar(p));
            p.panel = aux;
            return p;
        }
        else if(numeroPiso == 1){
            aux.add(new BotonSubir(p));
            p.panel = aux;
            return p;
        }
        else{
            aux.add(new BotonSubir(p));
            aux.add(new BotonBajar(p));
            p.panel = aux;
            return p;
        }
    }
}
