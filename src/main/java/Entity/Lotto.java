package Entity;

public class Lotto {

    private String codiceLotto;
    private String dataScadenza;
    private String dataProduzione;
    private String quantitaLotto;
    private int FKFarmaco;

    public Lotto() {}

    public Lotto(String codiceLotto, String dataScadenza, String dataProduzione, String quantitaLotto, int FKFarmaco) {
        setCodiceLotto(codiceLotto);
        setDataScadenza(dataScadenza);
        setDataProduzione(dataProduzione);
        setQuantitaLotto(quantitaLotto);
        setFKFarmaco(FKFarmaco);
    }

    public void setCodiceLotto(String codiceLotto) { this.codiceLotto = codiceLotto; }
    public String getCodiceLotto() { return codiceLotto; }

    public void setDataScadenza(String dataScadenza) { this.dataScadenza = dataScadenza; }
    public String getDataScadenza() { return dataScadenza; }

    public void setDataProduzione(String dataProduzione) { this.dataProduzione = dataProduzione; }
    public String getDataProduzione() { return dataProduzione; }

    public void setQuantitaLotto(String quantitaLotto) { this.quantitaLotto = quantitaLotto; }
    public String getQuantitaLotto() { return quantitaLotto; }

    public void setFKFarmaco(int FKFarmaco) { this.FKFarmaco = FKFarmaco; }
    public int getFKFarmaco() { return FKFarmaco; }
}
