package Model;

public class InstruccionSubirBajar extends Instruccion{
    int pisoDestino;
    int pisoOrigen;

    InstruccionSubirBajar(int pisoDestino, int pisoOrigen, Acciones a) {
        this.pisoDestino = pisoDestino;
        this.pisoOrigen = pisoOrigen;
        super.accion = a;
    }

    @Override
    public String toString(){
        return "****\nPiso Origen: " + Integer.toString(pisoOrigen) + " Piso Destino: " + Integer.toString(pisoDestino)
                + " Acción: " + accion.toString() + "\n****\n";
    }
}
