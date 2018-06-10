package Model;

import java.util.ArrayList;

class ObservadorElevador {
    private ArrayList<Piso> pisos;

    ObservadorElevador(ArrayList<Piso> pisos) {
        this.pisos = pisos;
    }

    void notificarLlegada(int numeroPiso, int numeroElevador){
        Piso notificarA = pisos.get(numeroPiso);
        notificarA.abrirPuertas(numeroElevador);
    }

    void notificarSalida(int numeroPiso, int numeroElevador){
        Piso notificarA = pisos.get(numeroPiso);
        notificarA.cerrarPuertas(numeroElevador);
    }
}
