package Entity;
import java.util.LinkedList;

public class Farmaco {

    private int IDFarmaco;
    private String nomeFarmaco;
    private String principioAttivo;
    private String lottoS;
    private String dataScadenza;
    private String quantitaFarmaco="0";
    private String tipologia;
    private int quantitaFarmacoInt;
    private int periodicitaProduzione;
    private int quantitaProduzione;
    private String dataUltimaProduzione;
    private int validita;
    private LinkedList<Lotto> listaLotti = new LinkedList<Lotto>();

    public Farmaco() {}
    public Farmaco(int IDFarmaco, String nomeFarmaco, String principioAttivo, String quantitaFarmaco ,String lottoS, String dataScadenza,int periodicitaProduzione, int quantitaProduzione) {
        setIDFarmaco(IDFarmaco);
        setNomeFarmaco(nomeFarmaco);
        setPrincipioAttivo(principioAttivo);
        setQuantitaFarmaco(quantitaFarmaco);
        setLottoS(lottoS);
        setDataScadenza(dataScadenza);
        setQuantitaProduzione(quantitaProduzione);
        setPeriodicitaProduzione(periodicitaProduzione);
    }

    public Farmaco(int IDFarmaco, String nomeFarmaco, String principioAttivo, String quantitaFarmaco) {
        setIDFarmaco(IDFarmaco);
        setNomeFarmaco(nomeFarmaco);
        setPrincipioAttivo(principioAttivo);
        setQuantitaFarmaco(quantitaFarmaco);
    }

    public Farmaco(String nomeFarmaco, String principioAttivo, String tipologia, String quantitaFarmaco ,String lottoS, String dataScadenza) {
        setNomeFarmaco(nomeFarmaco);
        setPrincipioAttivo(principioAttivo);
        setTipologia(tipologia);
        setLottoS(lottoS);
        setQuantitaFarmaco(quantitaFarmaco);
        setDataScadenza(dataScadenza);
    }

    public Farmaco(String nomeFarmaco, String lottoS, int quantitaFarmacoInt) {
        setNomeFarmaco(nomeFarmaco);
        setLottoS(lottoS);
        setQuantitaFarmacoInt(quantitaFarmacoInt);
    }

    public Farmaco(String nomeFarmaco, int quantita) {
        setNomeFarmaco(nomeFarmaco);
        setQuantitaFarmacoInt(quantita);
    }


    public void setIDFarmaco(int IDFarmaco) { this.IDFarmaco = IDFarmaco; }
    public int getIDFarmaco() { return IDFarmaco; }
    public void setNomeFarmaco(String nomeFarmaco) { this.nomeFarmaco = nomeFarmaco; }
    public String getNomeFarmaco() {return nomeFarmaco; }
    public void setPrincipioAttivo(String principioAttivo) { this.principioAttivo = principioAttivo; }
    public String getPrincipioAttivo() { return principioAttivo; }
    public void setQuantitaFarmaco(String quantitaFarmaco) { this.quantitaFarmaco = quantitaFarmaco; }
    public String getQuantitaFarmaco() { return quantitaFarmaco; }

    public void setLottoS(String lottoS) { this.lottoS = lottoS; }
    public String getLottoS() { return lottoS;}

    public void setTipologia(String tipologia) { this.tipologia = tipologia; }
    public String getTipologia() { return tipologia; }

    public void setDataScadenza(String dataScadenza) { this.dataScadenza = dataScadenza; }
    public String getDataScadenza() { return dataScadenza; }
    public void inserisciLotto(Lotto l) {
        this.listaLotti.add(l);
    }

    public LinkedList<Lotto> getListaLotti() {return listaLotti; }

    public void setPeriodicitaProduzione(int periodicitaProduzione) { this.periodicitaProduzione = periodicitaProduzione; }
    public int getPeriodicitaProduzione(){ return periodicitaProduzione;}
    public int getQuantitaProduzione(){ return quantitaProduzione; }
    public void setQuantitaProduzione(int quantitaProduzione) { this.quantitaProduzione = quantitaProduzione; }
    public void setQuantitaFarmacoInt(int quantitaFarmacoInt){ this.quantitaFarmacoInt = quantitaFarmacoInt; }
    public int getQuantitaFarmacoInt() { return quantitaFarmacoInt; }
    public void setValidita(int validita ){ this.validita=validita; }
    public int getValidita(){ return validita;}

}
