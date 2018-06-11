package Model;

public class BotonesBuilder {
    static InterfazBotones crearBoton(int destino, Elevador e){
        return new DestinoBotones(destino, e);
    }

    static InterfazBotones crearBoton(Elevador e, Acciones a){
        return a.equals(Acciones.EMERGENCIA) ? new EmergenciaBotones(e) : new DetenerseBotones(e);
    }
}
