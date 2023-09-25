import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Reiziger {
    // Variabelen.
    private int id;
    private String voorletters;
    private String tussenvoegsel;
    private String achternaam;
    private Date geboortedatum;
    private Adres adres;
    private List<OVChipkaart> OVChipkaartList = new ArrayList<>();

    public List<OVChipkaart> getOVChipkaartList() {
        return OVChipkaartList;
    }

    public void addOVChipkaart(OVChipkaart kaart) {
        this.OVChipkaartList.add(kaart);
    }

    public Adres getAdres() {
        return adres;
    }
    public void setAdres(Adres adres) {
        this.adres = adres;
    }

    // Functies.
    public int getId() {
        return this.id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getVoorletters() {
        return this.voorletters;
    }
    public void setVoorletters( String voorletters ) {
        this.voorletters = voorletters;
    }

    public String getTussenvoegsel() {
        return this.tussenvoegsel;
    }
    public void setTussenvoegsel( String tussenvoegsel ) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return this.achternaam;
    }
    public void setAchternaam( String achternaam ) {
        this.achternaam = achternaam;
    }

    public String getNaam() {
        return this.voorletters + " " + this.tussenvoegsel + " " + this.achternaam;
    }

    public Date getDate() {
        return this.geboortedatum;
    }
    public void setDate( Date geboortedatum) {
        this.geboortedatum = geboortedatum;
    }

    @Override
    public String toString() {
        if( this.adres != null ) {
            return "Reiziger {" + "#" + id + ", " + voorletters + " " + tussenvoegsel + " " + achternaam + ", " + geboortedatum.toString() +
                    ", " + adres.toString()+ "}";
        }
        else {
            return "Reiziger {" + "#" + id + ", " + voorletters + " " + tussenvoegsel + " " + achternaam + ", " + geboortedatum.toString() + "}";
        }
    }

    // Constructor.
    public Reiziger() {
        id = -1;
        voorletters = "NULL";
        tussenvoegsel = "NULL";
        achternaam = "NULL";
        geboortedatum = Date.valueOf("1990-12-23");
    }

    // Constructor Overloaded.
    public Reiziger(int id, String voorletters, String tussenvoegsel, String achternaam, Date geboortedatum) {
        this.id = id;
        this.voorletters = voorletters;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.geboortedatum = geboortedatum;
    }
}
