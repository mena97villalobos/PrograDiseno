package Model;

public class SensorPeso implements Interrupcion {
    private int pasajeros = 0;
    private int maxPasajeros;
    public Elevador elevador;

    @Override
    public void lanzarInterrupcion(Instruccion instruccion) {
        System.out.println("Cantidad máxima de pasajeros del Elevador " +
                Integer.toString(elevador.numeroElevador) + " alcanzada");
        elevador.calendarizador.annadirInstruccion(instruccion);
        elevador.lleno = true;
    }

    @Override
    public Instruccion crearInstruccion() {
        return Instruccion.construirInstruccion(Acciones.DETENERSE, elevador);
    }

    public void annadirPasajero(){
        pasajeros++;
        if(pasajeros > maxPasajeros){
            lanzarInterrupcion(crearInstruccion());
        }
    }

    public void eliminarPasajero(){
        pasajeros--;
        elevador.lleno = false;
    }

    void setMaxPasajeros(int maxPasajeros){
        this.maxPasajeros = maxPasajeros;
    }
}
