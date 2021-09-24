package es.xuan.cacamarcador.model;

import java.io.Serializable;
import java.util.ArrayList;

public class PartitMarcador implements Serializable, Cloneable {
    private static final long serialVersionUID = 1L;

    private String textCapcalera;
        /*
            idTorneig
            divisio
            fase
            grup
            categoria
            jornada
            federacio
            tipus
            temporada
         */
    private String equipLocal;
    private String equipLocalEscut;
    private String equipVisitant;
    private String equipVisitantEscut;
    private int resultatSetLocal;
    private int resultatSetVisitant;
    private int numSetActual;
    private int[][] puntsSets = {{0,0},{0,0},{0,0},{0,0},{0,0}};
    private int resultatPuntsLocal;
    private int resultatPuntsVisitant;
    private int tempsMortLocal;
    private int tempsMortVisitant;
    private int[] jugadorsLocal = {0,0,0,0,0,0};
    private int[] jugadorsVisitant = {0,0,0,0,0,0};
    private ArrayList<Jugador> jugadorsEquipLocal = null;
    private ArrayList<Jugador> jugadorsEquipVisitant = null;
    private boolean pilotaSaqueLocal = true;

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
    public boolean isPilotaSaqueLocal() {
        return pilotaSaqueLocal;
    }

    public void setPilotaSaqueLocal(boolean pPilotaSaqueLocal) {
        this.pilotaSaqueLocal = pPilotaSaqueLocal;
    }

    public ArrayList<Jugador> getJugadorsEquipLocal() {
        return jugadorsEquipLocal;
    }

    public void setJugadorsEquipLocal(ArrayList<Jugador> jugadorsEquipLocal) {
        this.jugadorsEquipLocal = jugadorsEquipLocal;
    }

    public ArrayList<Jugador> getJugadorsEquipVisitant() {
        return jugadorsEquipVisitant;
    }

    public void setJugadorsEquipVisitant(ArrayList<Jugador> jugadorsEquipVisitant) {
        this.jugadorsEquipVisitant = jugadorsEquipVisitant;
    }

    public String getTextCapcalera() {
        return textCapcalera;
    }

    public void setTextCapcalera(String textCapcalera) {
        this.textCapcalera = textCapcalera;
    }

    public String getEquipLocal() {
        return equipLocal;
    }

    public void setEquipLocal(String equipLocal) {
        this.equipLocal = equipLocal;
    }

    public String getEquipLocalEscut() {
        return equipLocalEscut;
    }

    public void setEquipLocalEscut(String equipLocalEscut) {
        this.equipLocalEscut = equipLocalEscut;
    }

    public String getEquipVisitant() {
        return equipVisitant;
    }

    public void setEquipVisitant(String equipVisitant) {
        this.equipVisitant = equipVisitant;
    }

    public String getEquipVisitantEscut() {
        return equipVisitantEscut;
    }

    public void setEquipVisitantEscut(String equipVisitantEscut) {
        this.equipVisitantEscut = equipVisitantEscut;
    }

    public int getResultatSetLocal() {
        return resultatSetLocal;
    }

    public void setResultatSetLocal(int resultatSetLocal) {
        this.resultatSetLocal = resultatSetLocal;
    }

    public int getResultatSetVisitant() {
        return resultatSetVisitant;
    }

    public void setResultatSetVisitant(int resultatSetVisitant) {
        this.resultatSetVisitant = resultatSetVisitant;
    }

    public int getNumSetActual() {
        return numSetActual;
    }

    public void setNumSetActual(int numSetActual) {
        this.numSetActual = numSetActual;
    }

    public int[][] getPuntsSets() {
        return puntsSets;
    }

    public void setPuntsSets(int[][] puntsSets) {
        this.puntsSets = puntsSets;
    }

    public int getResultatPuntsLocal() {
        return resultatPuntsLocal;
    }

    public void setResultatPuntsLocal(int resultatPuntsLocal) {
        this.resultatPuntsLocal = resultatPuntsLocal;
    }

    public int getResultatPuntsVisitant() {
        return resultatPuntsVisitant;
    }

    public void setResultatPuntsVisitant(int resultatPuntsVisitant) {
        this.resultatPuntsVisitant = resultatPuntsVisitant;
    }

    public int getTempsMortLocal() {
        return tempsMortLocal;
    }

    public void setTempsMortLocal(int tempsMortLocal) {
        this.tempsMortLocal = tempsMortLocal;
    }

    public int getTempsMortVisitant() {
        return tempsMortVisitant;
    }

    public void setTempsMortVisitant(int tempsMortVisitant) {
        this.tempsMortVisitant = tempsMortVisitant;
    }

    public int[] getJugadorsLocal() {
        return jugadorsLocal;
    }

    public void setJugadorsLocal(int[] jugadorsLocal) {
        this.jugadorsLocal = jugadorsLocal;
    }

    public int[] getJugadorsVisitant() {
        return jugadorsVisitant;
    }

    public void setJugadorsVisitant(int[] jugadorsVisitant) {
        this.jugadorsVisitant = jugadorsVisitant;
    }
}
