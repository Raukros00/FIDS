package Entity;

public class Farmacia {

    private int IDFarmacia;
    private String nomeSede;
    private String indirizzoSede;
    private String citta;
    private String provincia;
    private String CAP;
    private int numConsegne;
    private Double distanza;

    public Farmacia() {}
    public Farmacia(int IDFarmacia, String nomeSede, String indirizzoSede, String citta, String provincia, String CAP, Double distanza) {
        setIDFarmacia(IDFarmacia);
        setNomeSede(nomeSede);
        setIndirizzoSede(indirizzoSede);
        setCitta(citta);
        setProvincia(provincia);
        setCAP(CAP);
        setDistanza(distanza);
    }

    public void setIDFarmacia(int IDFarmacia) { this.IDFarmacia = IDFarmacia; }
    public int getIDFarmacia() { return IDFarmacia; }
    public void setNomeSede(String nomeSede) { this.nomeSede = nomeSede; }
    public String getNomeSede() { return nomeSede; }
    public void setIndirizzoSede(String indirizzoSede) { this.indirizzoSede = indirizzoSede; }
    public String getIndirizzoSede() { return indirizzoSede; }
    public void setCitta(String citta) { this.citta = citta; }
    public String getCitta() { return citta; }
    public void setProvincia(String provincia) { this.provincia = provincia; }
    public String getProvincia() { return provincia; }
    public void setCAP(String CAP) { this.CAP = CAP; }
    public String getCAP() { return CAP; }
    public void setNumConsegne(int numConsegne) { this.numConsegne = numConsegne; }
    public int getNumConsegne() { return numConsegne; }

    public void setDistanza(Double distanza) { this.distanza = distanza; }
    public Double getDistanza() { return distanza; }




}
