public class Adres {
    // Variabelen.
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    private int reiziger_id;

    // Functies.
    public int getID() {
        return id;
    }
    public void setID( int id ) {
        this.id = id;
    }

    public String getPostcode() {
        return postcode;
    }
    public void setPostcode( String pc ) {
        this.postcode = pc;
    }

    public String getHuisnummer() {
        return huisnummer;
    }
    public void setHuisnummer( String hn ) {
        this.huisnummer = hn;
    }

    public String getStraat() {
        return straat;
    }
    public void setStraat( String st ) {
        this.straat = st;
    }

    public String getWoonplaats() {
        return woonplaats;
    }
    public void setWoonplaats( String wp ) {
        this.woonplaats = wp;
    }

    public int getReizigerID() {
        return reiziger_id;
    }
    public void setReizigerID( int id ) {
        this.reiziger_id = id;
    }

    @Override
    public String toString() {
        return "Adres {" + "#" + id + ", " + postcode + ", " + huisnummer + ", " + straat + ", " + woonplaats + "}";
    }

    // Constructor.
    public Adres(){}
    public Adres( int id, String postcode, String huisnummer, String straat, String woonplaats, int reiziger_id){
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger_id = reiziger_id;
    }

}
