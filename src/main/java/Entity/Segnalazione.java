package Entity;

public class Segnalazione {

    private int IDSegnalazione;

    private String descrizione;

    private int IDUtente;

    public Segnalazione(int IDSegnalazione, String descrizione){

        setIDSegnalazione(IDSegnalazione);
        setdescrizione(descrizione);

    }

    public Segnalazione(){}

    public void setIDSegnalazione(IDSegnalazione) { this.IDSegnalazione = IDSegnalazione; }
    public int getIDSegnalazione() { return IDSegnalazione; }

    public void setdescrizione(descrizione) { this.descrizione = descrizione; }
    public String getdescrizione() { return descrizione; }

    public void setIDUtente(IDUtente) { this.IDUtente = IDUtente; }
    public int getIDUtente() { return IDUtente; }
}
