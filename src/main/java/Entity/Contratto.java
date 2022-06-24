package Entity;

import java.util.LinkedList;

public class Contratto {

    private int IDContratto;

    private int perioditicita;

    private String ultimaConsegna;

    private int IDSede;

    private LinkedList<FarmacoContratto> listaFarmaciContratto = new LinkedList<>();


    public Contratto(int IDContratto, int perioditicita, String ultimaConsegna) {
        setIDContratto(IDContratto);
        setPerioditicita(perioditicita);
        setUltimaConsegna(ultimaConsegna);
    }

    public Contratto() {}

    public void setIDContratto(int IDContratto) { this.IDContratto = IDContratto; }
    public int getIDContratto() { return IDContratto; }

    public void setPerioditicita(int perioditicita) { this.perioditicita = perioditicita; }
    public int getPerioditicita() { return perioditicita; }

    public void setUltimaConsegna(String ultimaConsegna) { this.ultimaConsegna = ultimaConsegna; }
    public String getUltimaConsegna() { return ultimaConsegna; }

    public void setIDSede(int IDSede) { this.IDSede = IDSede; }
    public int getIDSede() { return IDSede; }

    public void addListaFarmaciContratto(FarmacoContratto fc) { listaFarmaciContratto.add(fc); }
    public LinkedList<FarmacoContratto> getListaFarmaciContratto() { return listaFarmaciContratto; }
}
