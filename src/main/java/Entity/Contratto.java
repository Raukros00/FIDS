package Entity;

public class Contratto {

    private int IDContratto;

    private int perioditicita;

    private String ultimaConsegna;

    private int IDSede;


    public Contratto(int IDContratto, int perioditicita, String ultimaConsegna) {

        setIDContratto(IDContratto);
        setPerioditicita(perioditicita);
        setUltimaConsegna(ultimaConsegna);

    }

    public void setIDContratto(int IDContratto) { this.IDContratto = IDContratto; }
    public int getIDContratto() { return IDContratto; }

    public void setPerioditicita(int perioditicita) { this.perioditicita = perioditicita; }
    public int getPerioditicita() { return perioditicita; }

    public void setUltimaConsegna(String ultimaConsegna) { this.ultimaConsegna = ultimaConsegna; }
    public String getUltimaConsegna() { return ultimaConsegna; }

    public void setIDSede(int IDSede) { this.IDSede = IDSede; }
    public int getIDSede() { return IDSede; }
}
