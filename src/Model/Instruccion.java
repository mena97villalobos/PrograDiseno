package Model;

abstract class Instruccion {
    Acciones accion;

    static Instruccion construirInstruccion(Acciones a, int destino, int origen, int numeroElevador){
        return new InstruccionSubirBajar(origen, destino, a, numeroElevador);
    }

    static Instruccion construirInstruccion(Acciones a, Elevador e){
        return new InstruccionEmergenciaDetenerse(e, a);
    }
}
