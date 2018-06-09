package Model;

import java.util.ArrayList;

public class ConfiguracionDTO {
    private ArrayList<Float> p1S = new ArrayList<>();
    private ArrayList<Float> p2S = new ArrayList<>();
    private ArrayList<Float> p3S = new ArrayList<>();
    private ArrayList<Float> p4S = new ArrayList<>();
    private int numeroPisos;
    private int numeroElevadores;
    private int numeroPasajeros;
    private int utEntrePisos;
    private int utPuertasAbiertas;
    private String pathSalida;
    private TiposArchivo tipoSalida;

    public ConfiguracionDTO() {
        ArrayList<ArrayList<Float>> aux = new ArrayList<>();
        aux.add(p1S);
        aux.add(p2S);
        aux.add(p3S);
        aux.add(p4S);
        for (ArrayList<Float> floats : aux) {
            floats.add(0.5f);
            floats.add(0.5f);
        }
        numeroPisos = numeroElevadores = 2;
        numeroPasajeros = utPuertasAbiertas = 15;
        utEntrePisos = 35;
        pathSalida = "C:\\Users\\mena9\\Desktop\\salida";
        tipoSalida = TiposArchivo.JSON;
    }

    public ArrayList<Float> getP1S() {
        return p1S;
    }

    void setP1S(ArrayList<Float> p1S) {
        this.p1S = p1S;
    }

    public ArrayList<Float> getP2S() {
        return p2S;
    }

    void setP2S(ArrayList<Float> p2S) {
        this.p2S = p2S;
    }

    public ArrayList<Float> getP3S() {
        return p3S;
    }

    void setP3S(ArrayList<Float> p3S) {
        this.p3S = p3S;
    }

    public ArrayList<Float> getP4S() {
        return p4S;
    }

    void setP4S(ArrayList<Float> p4S) {
        this.p4S = p4S;
    }

    public int getNumeroPisos() {
        return numeroPisos;
    }

    public void setNumeroPisos(int numeroPisos) {
        this.numeroPisos = numeroPisos;
    }

    public int getNumeroElevadores() {
        return numeroElevadores;
    }

    public void setNumeroElevadores(int numeroElevadores) {
        this.numeroElevadores = numeroElevadores;
    }

    public int getNumeroPasajeros() {
        return numeroPasajeros;
    }

    public void setNumeroPasajeros(int numeroPasajeros) {
        this.numeroPasajeros = numeroPasajeros;
    }

    public int getUtEntrePisos() {
        return utEntrePisos;
    }

    public void setUtEntrePisos(int utEntrePisos) {
        this.utEntrePisos = utEntrePisos;
    }

    public int getUtPuertasAbiertas() {
        return utPuertasAbiertas;
    }

    public void setUtPuertasAbiertas(int utPuertasAbiertas) {
        this.utPuertasAbiertas = utPuertasAbiertas;
    }

    String getPathSalida() {
        return pathSalida;
    }

    public void setPathSalida(String pathSalida) {
        this.pathSalida = pathSalida;
    }

    public TiposArchivo getTipoSalida() {
        return tipoSalida;
    }

    public void setTipoSalida(TiposArchivo tipoSalida) {
        this.tipoSalida = tipoSalida;
    }
}
