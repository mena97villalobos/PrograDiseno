package Model;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class ConfiguracionDTO {
    private ArrayList<Float> p1S;
    private ArrayList<Float> p2S;
    private ArrayList<Float> p3S;
    private ArrayList<Float> p4S;
    private int numeroPisos;
    private int numeroElevadores;
    private int numeroPasajeros;
    private int utEntrePisos;
    private int utPuertasAbiertas;
    private String pathSalida;
    private TiposArchivo tipoSalida;

    public ConfiguracionDTO() {
        ArrayList<Float> probabilidades = new ArrayList<>();
        probabilidades.add(0.5f);
        probabilidades.add(0.5f);
        p1S = p2S = p3S = p4S = probabilidades;
        numeroPisos = numeroElevadores = 2;
        numeroPasajeros = utPuertasAbiertas = 15;
        utEntrePisos = 35;
        pathSalida = "C:\\Users\\mena9\\Desktop\\salida";
        tipoSalida = TiposArchivo.JSON;
    }

    public ConfiguracionDTO(ArrayList<Float> p1S, ArrayList<Float> p2S, ArrayList<Float> p3S, ArrayList<Float> p4S,
                            int numeroPisos, int numeroElevadores, int numeroPasajeros, int utEntrePisos,
                            int utPuertasAbiertas, String pathSalida, TiposArchivo tipoSalida) {
        this.p1S = p1S;
        this.p2S = p2S;
        this.p3S = p3S;
        this.p4S = p4S;
        this.numeroPisos = numeroPisos;
        this.numeroElevadores = numeroElevadores;
        this.numeroPasajeros = numeroPasajeros;
        this.utEntrePisos = utEntrePisos;
        this.utPuertasAbiertas = utPuertasAbiertas;
        this.pathSalida = pathSalida;
        this.tipoSalida = tipoSalida;
    }

    public ArrayList<Float> getP1S() {
        return p1S;
    }

    public void setP1S(ArrayList<Float> p1S) {
        this.p1S = p1S;
    }

    public ArrayList<Float> getP2S() {
        return p2S;
    }

    public void setP2S(ArrayList<Float> p2S) {
        this.p2S = p2S;
    }

    public ArrayList<Float> getP3S() {
        return p3S;
    }

    public void setP3S(ArrayList<Float> p3S) {
        this.p3S = p3S;
    }

    public ArrayList<Float> getP4S() {
        return p4S;
    }

    public void setP4S(ArrayList<Float> p4S) {
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

    public String getPathSalida() {
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
