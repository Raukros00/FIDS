package Entity;

public class FarmacoContratto {

    private int IDFarmaco;

    private int IDContratto;

    private int quantitaRichiesta;


    public FarmacoContratto(int IDFarmaco, int quantitaRichiesta){
        setIDFarmaco(IDFarmaco);
        setQuantitaRichiesta(quantitaRichiesta);
    }

    public FarmacoContratto() {}

    public void setIDFarmaco(int IDFarmaco) { this.IDFarmaco = IDFarmaco; }
    public int getIDFarmaco() { return IDFarmaco; }

    public void setIDContratto(int IDContratto) { this.IDContratto = IDContratto; }
    public int getIDContratto() { return IDContratto; }

    public void setQuantitaRichiesta(int quantitaRichiesta) { this.quantitaRichiesta = quantitaRichiesta; }
    public int getQuantitaRichiesta() { return quantitaRichiesta; }

}
