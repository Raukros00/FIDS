package Entity;

public class LottoSpedizione {

    private String nomeFarmaco;
    private String codiceLotto;
    private int IDSpedizione;
    private int quantita;


    public LottoSpedizione(String nomeFarmaco, String codiceLotto, int IDSpedizione, int quantita){
        setNomeFarmaco(nomeFarmaco);
        setCodiceLotto(codiceLotto);
        setIDSpedizione(IDSpedizione);
        setQuantita(quantita);
    }

    public LottoSpedizione(){}

    public void setNomeFarmaco(String nomeFarmaco) { this.nomeFarmaco = nomeFarmaco;}
    public String getNomeFarmaco() { return nomeFarmaco; }
    public void setCodiceLotto(String codiceLotto) { this.codiceLotto = codiceLotto; }
    public String getCodiceLotto() { return codiceLotto; }

    public void setIDSpedizione(int IDSpedizione) { this.IDSpedizione = IDSpedizione; }
    public int getIDSpedizione() { return IDSpedizione; }
    public void setQuantita(int quantita) { this.quantita = quantita; }
    public int getQuantita() { return quantita; }
}
