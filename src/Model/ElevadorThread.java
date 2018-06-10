package Model;

public class ElevadorThread implements Runnable {
    public Elevador elevador;
    private boolean detener = false;
    private boolean stop = false;

    ElevadorThread(Elevador elevador) {
        this.elevador = elevador;
    }

    @Override
    public void run() {
        while(!stop) {
            try {
                int ultimaVisita = elevador.lucesLlegada.indexOf(true) + 1;
                int visitando;
                if (detener) {
                    if (elevador.estado.equals(Estados.DETENIDO)) {
                        try {
                            Thread.sleep(elevador.utPuertasAbiertas*1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    detener = false;
                }
                visitando = elevador.proximosDestinos.get(0);
                System.out.println("Elevador " + Integer.toString(elevador.numeroElevador) + " visitando Piso " + Integer.toString(visitando));
                elevador.proximosDestinos.remove(0);
                elevador.observador.notificarSalida(elevador.pisoActual - 1, elevador.numeroElevador);
                if (elevador.estado.equals(Estados.DETENIDO)) {
                    elevador.estado = visitando < elevador.pisoActual ? Estados.BAJANDO : Estados.SUBIENDO;
                } else
                    elevador.estado = Estados.DETENIDO;
                if (!elevador.estado.equals(Estados.DETENIDO)) {
                    try {
                        Thread.sleep(elevador.utEntrePisos*1000);
                        System.out.println("Elevador " + Integer.toString(elevador.numeroElevador) + " llegando al Piso " + Integer.toString(visitando));
                        elevador.observador.notificarLlegada(visitando-1, elevador.numeroElevador);
                        elevador.lucesLlegada.set(ultimaVisita - 1, false);
                        elevador.lucesLlegada.set(visitando - 1, true);
                        elevador.pisoActual = visitando;
                        Thread.sleep(elevador.utPuertasAbiertas*1000);
                        elevador.estado = Estados.DETENIDO;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            catch (Exception ignored){}
        }
    }
}
