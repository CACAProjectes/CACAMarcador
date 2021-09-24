package es.xuan.cacamarcador.model;

import java.io.Serializable;
import java.util.Date;

import es.xuan.cacamarcador.altres.Utils;

public class Estadistica implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private int setLocal = 0;
    private int setVisitant = 0;
    private int puntsLocal = 0;
    private int puntsVisitant = 0;
    private int num_set = 0;
    private int num_jugador = 0;
    private boolean isLocal = true;
    private String accio = "";
    private Date temps;
    private String nom_equip;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public int getSetLocal() {
        return setLocal;
    }

    public void setSetLocal(int setLocal) {
        this.setLocal = setLocal;
    }

    public int getSetVisitant() {
        return setVisitant;
    }

    public void setSetVisitant(int setVisitant) {
        this.setVisitant = setVisitant;
    }

    public int getPuntsLocal() {
        return puntsLocal;
    }

    public void setPuntsLocal(int puntsLocal) {
        this.puntsLocal = puntsLocal;
    }

    public int getPuntsVisitant() {
        return puntsVisitant;
    }

    public void setPuntsVisitant(int puntsVisitant) {
        this.puntsVisitant = puntsVisitant;
    }

    public String getNom_equip() {
        return nom_equip;
    }

    public void setNom_equip(String nom_equip) {
        this.nom_equip = nom_equip;
    }

    public Date getTemps() {
        return temps;
    }

    public void setTemps(Date temps) {
        this.temps = temps;
    }

    public int getNum_set() {
        return num_set;
    }

    public void setNum_set(int num_set) {
        this.num_set = num_set;
    }

    public int getNum_jugador() {
        return num_jugador;
    }

    public void setNum_jugador(int num_jugador) {
        this.num_jugador = num_jugador;
    }

    public boolean isLocal() {
        return isLocal;
    }

    public void setLocal(boolean local) {
        isLocal = local;
    }

    public String getAccio() {
        return accio;
    }

    public void setAccio(String accio) {
        this.accio = accio;
    }
    @Override
    public String toString() {
        return Utils.convertDate2String(temps) + ";" +
                puntsLocal + ";" +
                puntsVisitant + ";" +
                nom_equip + ";" +
                num_jugador + ";" +
                num_set+ ";" +
                accio;
    }
}
