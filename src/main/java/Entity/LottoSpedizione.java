package Entity;

public class LottoSpedizione {

    private int IDLotto;

    private int IDSpedizione;

    private int quantita;


    public LottoSpedizione(int quantita){

        setQuantita(quantita);

    }

    public LottoSpedizione(){}

    public void setIDLotto(IDLotto) { this.IDLotto = IDLotto; }
    public int getIDLotto() { return IDLotto; }

    public void setIDLotto(IDLotto) { this.IDLotto = IDLotto; }
    public int getIDSpedizione() { return IDSpedizione; }

    public void setQuantita(quantita) { this.quantita = quantita; }
    public int getQuantita() { return quantita; }
}
