package Entity;

public class LottoSpedizione implements Cloneable {

    private String nomeFarmaco;
    private String principioAttivo;
    private String codiceLotto;
    private String dataProduzione;
    private String dataScadenza;
    private int IDSpedizione;
    private int quantita;
    private int IDFarmaco;


    public LottoSpedizione(String nomeFarmaco, String codiceLotto, int IDSpedizione, int quantita){
        setNomeFarmaco(nomeFarmaco);
        setCodiceLotto(codiceLotto);
        setIDSpedizione(IDSpedizione);
        setQuantita(quantita);
    }

    public LottoSpedizione(String nomeFarmaco, String codiceLotto, int quantita, String dataProduzione, String dataScadenza, int IDFarmaco){
        setNomeFarmaco(nomeFarmaco);
        setCodiceLotto(codiceLotto);
        setQuantita(quantita);
        setDataProduzione(dataProduzione);
        setDataScadenza(dataScadenza);
        setIDFarmaco(IDFarmaco);
    }

    public LottoSpedizione(int IDSpedizione, String nomeFarmaco, String principioAttivo, String codiceLotto, String dataProduzione, String dataScadenza, int quantita, int IDFarmaco){

        setIDSpedizione(IDSpedizione);
        setNomeFarmaco(nomeFarmaco);
        setPrincipioAttivo(principioAttivo);
        setCodiceLotto(codiceLotto);
        setDataProduzione(dataProduzione);
        setDataScadenza(dataScadenza);
        setQuantita(quantita);
        setIDFarmaco(IDFarmaco);

    }

    public LottoSpedizione(){}

    public void setNomeFarmaco(String nomeFarmaco) { this.nomeFarmaco = nomeFarmaco;}
    public String getNomeFarmaco() { return nomeFarmaco; }
    public void setPrincipioAttivo(String principioAttivo) { this.principioAttivo = principioAttivo; }
    public String getPrincipioAttivo() { return principioAttivo; }
    public void setCodiceLotto(String codiceLotto) { this.codiceLotto = codiceLotto; }
    public String getCodiceLotto() { return codiceLotto; }
    public void setDataProduzione(String dataProduzione) { this.dataProduzione = dataProduzione; }
    public String getDataProduzione() { return dataProduzione; }
    public void setDataScadenza(String dataScadenza) { this.dataScadenza = dataScadenza; }
    public String getDataScadenza() { return dataScadenza; }
    public void setIDSpedizione(int IDSpedizione) { this.IDSpedizione = IDSpedizione; }
    public int getIDSpedizione() { return IDSpedizione; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
    public int getQuantita() { return quantita; }

    public void setIDFarmaco(int IDFarmaco) {this.IDFarmaco = IDFarmaco; }
    public int getIDFarmaco() { return IDFarmaco; }
}
