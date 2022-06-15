package Entity;

public class Lotto extends Farmaco {

    private String IDLotto;
    private String dataScadenza;
    private String dataProduzione;
    private int quantitaLotto;
    private int FKFarmaco;

    public Lotto() {}

    public Lotto(String IDLotto, String dataScadenza, String dataProduzione, int quantitaLotto, int FKFarmaco) {
        setIDLotto(IDLotto);
        setDataScadenza(dataScadenza);
        setDataProduzione(dataProduzione);
        setQuantitaLotto(quantitaLotto);
        setFKFarmaco(FKFarmaco);
    }

    public void setIDLotto(String IDLotto) { this.IDLotto = IDLotto; }
    public String getIDLotto() { return IDLotto; }

    public void setDataScadenza(String dataScadenza) { this.dataScadenza = dataScadenza; }
    public String getDataScadenza() { return dataScadenza; }

    public void setDataProduzione(String dataProduzione) { this.dataProduzione = dataProduzione; }
    public String getDataProduzione() { return dataProduzione; }

    public void setQuantitaLotto(int quantitaLotto) { this.quantitaLotto = quantitaLotto; }
    public int getQuantitaLotto() { return quantitaLotto; }

    public void setFKFarmaco(int FKFarmaco) { this.FKFarmaco = FKFarmaco; }
    public int getFKFarmaco() { return FKFarmaco; }
}
