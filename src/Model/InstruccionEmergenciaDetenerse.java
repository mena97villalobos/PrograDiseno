package Model;

public class InstruccionEmergenciaDetenerse extends Instruccion {
    public Elevador elevador;

    InstruccionEmergenciaDetenerse(Elevador elevador, Acciones a) {
        this.elevador = elevador;
        super.accion = a;
    }

    @Override
    public String toString(){
        return "****\nElevador: " + Integer.toString(elevador.numeroElevador) + " Accion: " + accion.toString() + "\n****\n";
    }
}
