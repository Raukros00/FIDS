package Entity;

public class FarmacoContratto {

    private int IDFarmaco;

    private int IDContratto;

    private int quantitaRichiesta;


    public FarmacoContratto(int quantitaRichiesta){

        setQuantitaRichiesta(quantitaRichiesta);

    }

    public FarmacoContratto() {}

    public void setIDFarmaco(IDFarmaco) { this.IDFarmaco = IDFarmaco; }
    public int getIDFarmaco() { return IDFarmaco; }

    public void setIDContratto(IDContratto) { this.IDContratto = IDContratto; }
    public int getIDContratto() { return IDContratto; }

    public void setQuantitaRichiesta(quantitaRichiesta) { this.quantitaRichiesta = quantitaRichiesta; }
    public int getQuantitaRichiesta() { return quantitaRichiesta; }

}
