package Entity;

import java.util.LinkedList;

public class Spedizione {

    private int IDSpedizione;
    private String dataConsegna;
    private int statoConsegna; //può assumere i valori 1,2,3. 1:confermata  2:consegnata  3:caricata
    private int IDSede;
    private String nomeFarmacia;
    private String citta;
    private int distanza;
    private String indirizzoFarmacia;


    private LinkedList<LottoSpedizione> listaLotti = new LinkedList<>();

    public Spedizione() {}
    public Spedizione(int IDSpedizione, String dataConsegna, int statoConsegna){
        setIDSpedizione(IDSpedizione);
        setDataConsegna(dataConsegna);
        setStatoConsegna(statoConsegna);
    }

    public void setIDSpedizione(int IDSpedizione) { this.IDSpedizione = IDSpedizione; }
    public int getIDSpedizione() { return IDSpedizione; }

    public void setDataConsegna(String dataConsegna) { this.dataConsegna = dataConsegna; }
    public String getDataConsegna() { return dataConsegna; }

    public void setStatoConsegna(int statoConsegna) { this.statoConsegna = statoConsegna; }
    public int getStatoConsegna() { return statoConsegna; }

    public void setIDSede(int IDSede) { this.IDSede = IDSede; }
    public int getIDSede() {return IDSede;}

    public void setIndirizzoFarmacia(String indirizzoFarmacia) { this.indirizzoFarmacia = indirizzoFarmacia; }
    public String getIndirizzoFarmacia() { return indirizzoFarmacia; }

    public String getNomeFarmacia() {return nomeFarmacia;}
    public void setNomeFarmacia(String nomeFarmacia){this.nomeFarmacia=nomeFarmacia; }
    public String getCitta() {return citta;}
    public void setCitta(String citta){this.citta=citta;}
    public int getDistanza(){return distanza;}
    public void setDistanza(int distanza){this.distanza=distanza; }

    public void addLotto(LottoSpedizione ls) { listaLotti.add(ls); }
    public LinkedList<LottoSpedizione> getListaLottiSpedizione() { return listaLotti; }


}
