package Model;

import java.util.ArrayList;

public class Elevador {
    public ArrayList<Boolean> lucesLlegada = new ArrayList<>();
    public ArrayList<BotonInterfaz> panel = new ArrayList<>();
    public BotonInterfaz botonEmergencia = new BotonEmergencia();
    public BotonInterfaz botonDetenerse = new BotonDetenerse();
    public int numeroElevador;

    public Elevador(int numeroElevador, int numeroPisos) {
        this.numeroElevador = numeroElevador;
        lucesLlegada.add(true); //Los Elevadores inician en el piso 1, luz del piso 1 siempre esta prendida
        panel.add(new BotonDestino(numeroElevador, 1));
        for(int i = 1; i < numeroPisos; i++) {
            lucesLlegada.add(false);
            panel.add(new BotonDestino(numeroElevador, i+1));
        }
    }
}
