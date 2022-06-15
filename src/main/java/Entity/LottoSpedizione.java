package Entity;

public class LottoSpedizione {

    private String codiceLotto;

    private int IDSpedizione;

    private int quantita;


    public LottoSpedizione(int quantita){

        setQuantita(quantita);

    }

    public LottoSpedizione(){}

    public void setCodiceLotto(String codiceLotto) { this.codiceLotto = codiceLotto; }
    public String getCodiceLotto() { return codiceLotto; }

    public void setIDSpedizione(int IDSpedizione) { this.IDSpedizione = IDSpedizione; }
    public int getIDSpedizione() { return IDSpedizione; }

    public void setQuantita(int quantita) { this.quantita = quantita; }
    public int getQuantita() { return quantita; }
}
