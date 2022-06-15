package Entity;

public class Spedizione {

    private int IDSpedizione;

    private String dataConsegna;

    private int statoConsegna; //può assumere i valori 1,2,3. 1:confermata  2:consegnata  3:caricata

    private int IDSede;


    public Spedizione(int IDSpedizione, String dataConsegna, int statoConsegna){

        setIDSpedizione(IDSpedizione);
        setDataConsegna(dataConsegna);
        setStatoConsegna(statoConsegna);
    }

    public void setIDSpedizione(IDSpedizione) { this.IDSpedizione = IDSpedizione; }
    public int getIDSpedizione() { return IDSpedizione; }

    public void setDataConsegna(dataConsegna) { this.dataConsegna = dataConsegna; }
    public String getDataConsegna() { return dataConsegna; }

    public void setStatoConsegna(statoConsegna) { this.statoConsegna = statoConsegna; }
    public int getStatoConsegna() { return statoConsegna; }

    public void setIDSede(IDSede) { this.IDSede = IDSede; }
    public int getIDSede() {return IDSede;}
}
