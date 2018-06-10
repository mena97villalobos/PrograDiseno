package Model;

public class InstruccionSubirBajar extends Instruccion{
    int pisoDestino;
    int pisoOrigen;
    int elevador;

    InstruccionSubirBajar(int pisoDestino, int pisoOrigen, Acciones a, int elevadorFijo) {
        this.pisoDestino = pisoDestino;
        this.pisoOrigen = pisoOrigen;
        this.elevador = elevadorFijo;
        super.accion = a;
    }

    @Override
    public String toString(){
        return "****\nPiso Origen: " + Integer.toString(pisoOrigen) + " Piso Destino: " + Integer.toString(pisoDestino)
                + " Acción: " + accion.toString() + "\n****\n";
    }
}
