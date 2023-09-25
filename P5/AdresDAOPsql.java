import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class AdresDAOPsql implements AdresDAO {
    // Variabelen.
    private Connection con;
    private ReizigerDAO rdao;

    // Functies.
    @Override
    public boolean save( Adres adres ) {
        try {
            int ID = adres.getID();
            String postcode = adres.getPostcode();
            String huisnummer = adres.getHuisnummer();
            String straat = adres.getStraat();
            String woonplaats = adres.getWoonplaats();
            int reiziger_id = adres.getReizigerID();

            // Maak SQL statement aan.
            String query = "INSERT INTO adres (adres_id, postcode, huisnummer, straat, woonplaats, reiziger_id) VALUES ( ?, ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, postcode);
            preparedStatement.setString(3, huisnummer);
            preparedStatement.setString(4, straat);
            preparedStatement.setString(5, woonplaats);
            preparedStatement.setInt(6, reiziger_id);

            //Voer uit.
            preparedStatement.executeUpdate();

            return true;
        }
        catch ( Exception excp ) {
            System.out.println(excp.getMessage());
            return false;
        }
    }
    @Override
    public boolean update( Adres adres ) {
        try {
            int ID = adres.getID();
            String postcode = adres.getPostcode();
            String huisnummer = adres.getHuisnummer();
            String straat = adres.getStraat();
            String woonplaats = adres.getWoonplaats();
            int reiziger_id = adres.getReizigerID();

            // Maak SQL statement aan.
            String query = "UPDATE adres SET postcode = ?, huisnummer = ?, straat = ?, woonplaats = ?, reiziger_id = ? WHERE adres_id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setString(1, postcode);
            preparedStatement.setString(2, huisnummer);
            preparedStatement.setString(3, straat);
            preparedStatement.setString(4, woonplaats);
            preparedStatement.setInt(5, reiziger_id);
            preparedStatement.setInt(6, ID);

            //Voer uit.
            preparedStatement.executeUpdate();

            return true;
        }
        catch ( Exception excp ) {
            System.out.println(excp.getMessage());
            return false;
        }
    }
    @Override
    public boolean delete( Adres adres ) {
        try {
            int ID = adres.getID();

            // Maak SQL statement aan.
            String query = "DELETE FROM adres WHERE adres_id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, ID);

            //Voer uit.
            preparedStatement.executeUpdate();

            return true;
        }
        catch ( Exception excp ) {
            System.out.println(excp.getMessage());
            return false;
        }
    }
    @Override
    public Adres findByReiziger( Reiziger reiziger ) {
        Adres foundAdres = new Adres();
        try {
            // Maak SQL statement aan.
            String query = "SELECT * FROM adres WHERE reiziger_id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, reiziger.getId());

            //Voer uit.
            ResultSet Rs = preparedStatement.executeQuery();

            while(Rs.next()) {
                foundAdres.setID(Rs.getInt(1));
                foundAdres.setPostcode(Rs.getString(2));
                foundAdres.setHuisnummer(Rs.getString(3));
                foundAdres.setStraat(Rs.getString(4));
                foundAdres.setWoonplaats(Rs.getString(5));
                foundAdres.setReizigerID(Rs.getInt(6));
            }

            return foundAdres;
        }
        catch ( Exception excp ) {
            System.out.println(excp.getMessage());
            return foundAdres;
        }
    }
    @Override
    public List<Adres> findAll() {
        ArrayList<Adres> allAdressen = new ArrayList<>();
        try {
            // Maak SQL statement aan.
            String query = "SELECT * FROM adres";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            //Voer uit.
            ResultSet Rs = preparedStatement.executeQuery();

            while(Rs.next()) {
                Adres tempFoundAdres = new Adres(); // Adres object moet in de loop en kan niet erboven, hierdoor append je dezelfde reference steeds naar hetzelfde object en heb je dus in principe maar een object in je lijst.
                tempFoundAdres.setID(Rs.getInt(1));
                tempFoundAdres.setPostcode(Rs.getString(2));
                tempFoundAdres.setHuisnummer(Rs.getString(3));
                tempFoundAdres.setStraat(Rs.getString(4));
                tempFoundAdres.setWoonplaats(Rs.getString(5));
                tempFoundAdres.setReizigerID(Rs.getInt(6));
                allAdressen.add(tempFoundAdres);
            }
            return allAdressen;
        }
        catch ( Exception excp ) {
            System.out.println(excp.getMessage());
            return allAdressen;
        }
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }

    // Constructor.
    public AdresDAOPsql( Connection con ) {
        this.con = con;
    }
}
