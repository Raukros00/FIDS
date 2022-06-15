package Entity;
import java.util.LinkedList;

public class Farmaco {

    private int IDFarmaco;
    private String nomeFarmaco;
    private String principioAttivo;
    private String lottoS;
    private String dataScadenza;
    private String quantitaFarmaco;
    private LinkedList<Lotto> listaLotti = new LinkedList<Lotto>();
    private Lotto lotto;

    public Farmaco() {}
    public Farmaco(int IDFarmaco, String nomeFarmaco, String principioAttivo, String quantitaFarmaco) {
        setIDFarmaco(IDFarmaco);
        setNomeFarmaco(nomeFarmaco);
        setPrincipioAttivo(principioAttivo);
        setQuantitaFarmaco(quantitaFarmaco);
    }

    public Farmaco(String nomeFarmaco, String principioAttivo, String quantitaFarmaco ,String lottoS, String dataScadenza) {
        setNomeFarmaco(nomeFarmaco);
        setPrincipioAttivo(principioAttivo);
        setLotto(lottoS);
        setQuantitaFarmaco(quantitaFarmaco);
        setDataScadenza(dataScadenza);
    }


    public void setIDFarmaco(int IDFarmaco) { this.IDFarmaco = IDFarmaco; }
    public int getIDFarmaco() { return IDFarmaco; }
    public void setNomeFarmaco(String nomeFarmaco) { this.nomeFarmaco = nomeFarmaco; }
    public String getNomeFarmaco() {return nomeFarmaco; }
    public void setPrincipioAttivo(String principioAttivo) { this.principioAttivo = principioAttivo; }
    public String getPrincipioAttivo() { return principioAttivo; }
    public void setQuantitaFarmaco(String quantitaFarmaco) { this.quantitaFarmaco = quantitaFarmaco; }
    public String getQuantitaFarmaco() { return quantitaFarmaco; }

    public void setLotto(String lottoS) { this.lottoS = lottoS; }
    public String getLotto() { return lottoS;}

    public void setDataScadenza(String dataScadenza) { this.dataScadenza = dataScadenza; }
    public String getDataScadenza() { return dataScadenza; }
    public void inserisciLotto(Lotto l) {
        this.listaLotti.add(l);
    }

    public LinkedList<Lotto> getListaLotti() { return listaLotti; }

}