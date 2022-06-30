package Entity;

public class Segnalazione {
    private int FKSpedizione;
    private String descrizione;
    private int FKUtente;

    public Segnalazione(){
    }

    public void setFKSpedizione(int FKSpedizione) { this.FKSpedizione = FKSpedizione; }
    public int getFKSpedizione() { return FKSpedizione; }
    public void setDescrizione(String descrizione) { this.descrizione = descrizione; }
    public String getDescrizione() { return descrizione; }
    public void setFKUtente(int FKUtente) { this.FKUtente = FKUtente; }
    public int getFKUtente() { return FKUtente; }


}

