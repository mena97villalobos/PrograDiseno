package Model;

import java.util.ArrayList;

public class Calendarizador implements Runnable{
    private ArrayList<Elevador> elevadores;
    private ArrayList<Instruccion> instrucciones = new ArrayList<>();
    private static boolean stop = false;

    Calendarizador(ArrayList<Elevador> elevadores) {
        this.elevadores = elevadores;
    }

    void annadirInstruccion(Instruccion i){
        System.out.println("Entra Instrucción: \n" + i.toString());
        instrucciones.add(i);
    }

    @Override
    public void run() {
        while (!stop) {
            try {
                realizarInstruccion(instrucciones.get(0));
                instrucciones.remove(0);
            }
            catch (IndexOutOfBoundsException ignored) {}
        }
    }


    private void realizarInstruccion(Instruccion i){
        boolean realizada = false;
        switch (i.accion){
            case BAJAR:
                InstruccionSubirBajar iBajar = (InstruccionSubirBajar) i;
                if(iBajar.elevador == -1) {
                    for (Elevador elevador : elevadores) {
                        if (elevador.estado == Estados.BAJANDO && !elevador.lleno) {
                            if (elevador.pisoActual >= iBajar.pisoOrigen) {
                                annadirDestinos(iBajar.pisoOrigen, iBajar.pisoDestino, elevador);
                                realizada = true;
                                break;
                            }
                        }
                    }
                    if (!realizada) {
                        for (Elevador elevador : elevadores) {
                            if (elevador.estado == Estados.DETENIDO && !elevador.lleno) {
                                annadirDestinos(iBajar.pisoOrigen, iBajar.pisoDestino, elevador);
                                realizada = true;
                                break;
                            }
                        }
                    }
                    if (!realizada) {
                        annadirDestinos(iBajar.pisoOrigen, iBajar.pisoDestino, elevadores.get(0));
                    }
                }
                else{
                    annadirDestinos(iBajar.pisoOrigen, iBajar.pisoDestino, elevadores.get(iBajar.elevador-1));
                }
                break;
            case SUBIR:
                InstruccionSubirBajar iSubir = (InstruccionSubirBajar) i;
                if(iSubir.elevador == -1) {
                    realizada = false;
                    for (Elevador elevador : elevadores) {
                        if (elevador.estado == Estados.SUBIENDO && !elevador.lleno) {
                            if (elevador.pisoActual >= iSubir.pisoOrigen) {
                                annadirDestinos(iSubir.pisoOrigen, iSubir.pisoDestino, elevador);
                                realizada = true;
                                break;
                            }
                        }
                    }
                    if (!realizada) {
                        for (Elevador elevador : elevadores)
                            if (elevador.estado == Estados.DETENIDO && !elevador.lleno) {
                                annadirDestinos(iSubir.pisoOrigen, iSubir.pisoDestino, elevador);
                                realizada = true;
                                break;
                            }
                    }
                    if (!realizada) {
                        annadirDestinos(iSubir.pisoOrigen, iSubir.pisoDestino, elevadores.get(0));
                    }
                }
                else{
                    annadirDestinos(iSubir.pisoOrigen, iSubir.pisoDestino, elevadores.get(iSubir.elevador-1));
                }
                break;
            case DETENERSE:
                InstruccionEmergenciaDetenerse iDetenerse = (InstruccionEmergenciaDetenerse) i;
                iDetenerse.elevador.detener = true;
                break;
            case EMERGENCIA:
                InstruccionEmergenciaDetenerse iEmergencia = (InstruccionEmergenciaDetenerse) i;
                iEmergencia.elevador.detener = true;
                break;
        }
    }

    private void annadirDestinos(int origen, int destino, Elevador elevador){
        if(origen == destino)
            elevador.annadirDestino(destino);
        else{
            elevador.annadirDestino(destino);
            elevador.annadirDestino(origen);
        }
    }

}
