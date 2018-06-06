package Model;

import java.util.ArrayList;

public class PisoFactory {
    public static ConfiguracionDTO configuracion;

    public static Piso buidPiso(int numeroPiso){
        ArrayList<BotonInterfaz> aux = new ArrayList<>();
        if(numeroPiso == configuracion.getNumeroPisos()){
            aux.add(new BotonBajar());
            return new Piso(numeroPiso, aux);
        }
        else if(numeroPiso == 1){
            aux.add(new BotonSubir());
            return new Piso(numeroPiso, aux);
        }
        else{
            aux.add(new BotonBajar());
            aux.add(new BotonSubir());
            return new Piso(numeroPiso, aux);
        }
    }
}
