import java.sql.*;
import java.util.ArrayList;
import java.util.List;

// Notes.
// Als er een reiziger mee wordt gegeven bevat deze ook een adres object die gekoppeld is aan de reiziger.
// Dit adres object kan gebruikt worden om de adao aan te roepen zodat bij bijvoorbeeld een delete de delete van de adao ook kan worden aangeroepen.
// Hierbij kan je dan het adres object opvragen en meegeven om ook het bijbehorende adres te verwijderen.

// Later kan dit bij reiziger in plaats van een enkel object te zijn een lijst van objecten zijn die worden meegegeven.
// Je kan dan dus meerdere adressen doorgeven en deze dus een voor een verwijderen.
// Voor nu is het waarschijnlijk een adres per reiziger.

// Vragen.
// Moet ik de adao aanroepen om vanuit deze klasse het adres te verwijderen.
// Moet ik een adres object meegeven aan de reiziger?
// Zou het ook enkel met een adres_id kunnen?
// Moet ik dan elke keer een adres object aanmaken?

public class ReizigerDAOPsql implements ReizigerDAO {
    // Variabelen.
    private Connection con;
    private AdresDAO adao;
    private OVChipkaartDAO OVDao;

    // Functies.
    @Override
    public boolean save( Reiziger reiziger ) {
        try {
            int ID = reiziger.getId();
            String voorletters = reiziger.getVoorletters();
            String tussenvoegsel = reiziger.getTussenvoegsel();
            String achternaam = reiziger.getAchternaam();
            Date geboortedatum = reiziger.getDate();

            // Maak SQL statement aan.
            String query = "INSERT INTO reiziger (reiziger_id, voorletters, tussenvoegsel, achternaam, geboortedatum) VALUES ( ?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, voorletters);
            preparedStatement.setString(3, tussenvoegsel);
            preparedStatement.setString(4, achternaam);
            preparedStatement.setDate(5, geboortedatum);

            //Voer uit.
            preparedStatement.executeUpdate();

            // Voeg adres toe als die bestaat.
            if( reiziger.getAdres() != null ) {
                adao.save(reiziger.getAdres());
            }

            if( reiziger.getOVChipkaartList() != null ) {
                for( OVChipkaart kaart : reiziger.getOVChipkaartList() ) {
                    OVDao.save(kaart);
                }
            }

            return true;
        }
        catch ( Exception excp ) {
            System.out.println(excp.getMessage());
            return false;
        }
    }
    @Override
    public boolean update( Reiziger reiziger ) {
        try {
            int ID = reiziger.getId();
            String voorletters = reiziger.getVoorletters();
            String tussenvoegsel = reiziger.getTussenvoegsel();
            String achternaam = reiziger.getAchternaam();
            Date geboortedatum = reiziger.getDate();

            // Update adres van reiziger.
            if( reiziger.getAdres() != null ) {
                // reiziger.getAdres().setReizigerID(reiziger.getId());
                adao.update(reiziger.getAdres());
            }

            if( reiziger.getOVChipkaartList() != null ) {
                for( OVChipkaart kaart : reiziger.getOVChipkaartList() ) {
                    OVDao.update(kaart);
                }
            }

            // Maak SQL statement aan.
            String query = "UPDATE reiziger SET voorletters = ?, tussenvoegsel = ?, achternaam = ?, geboortedatum = ? WHERE reiziger_id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setString(1, voorletters);
            preparedStatement.setString(2, tussenvoegsel);
            preparedStatement.setString(3, achternaam);
            preparedStatement.setDate(4, geboortedatum);
            preparedStatement.setInt(5, ID);

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
    public boolean delete( Reiziger reiziger ) {
        try {
            int ID = reiziger.getId();

            // Delete adres van reiziger.
            if( reiziger.getAdres() != null ) {
                adao.delete(reiziger.getAdres());
            }

            if( reiziger.getOVChipkaartList() != null ) {
                for( OVChipkaart kaart : reiziger.getOVChipkaartList() ) {
                    OVDao.delete(kaart);
                }
            }

            // Maak SQL statement aan.
            String query = "DELETE FROM reiziger WHERE reiziger_id=?";
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
    public Reiziger findById( int id ) {
        Reiziger foundReiziger = new Reiziger();
        try {
            // Maak SQL statement aan.
            String query = "SELECT * FROM reiziger WHERE reiziger_id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, id);

            //Voer uit.
            ResultSet Rs = preparedStatement.executeQuery();

            while(Rs.next()) {
                foundReiziger.setId(Rs.getInt(1));
                foundReiziger.setVoorletters(Rs.getString(2));
                foundReiziger.setTussenvoegsel(Rs.getString(3));
                foundReiziger.setAchternaam(Rs.getString(4));
                foundReiziger.setDate(Rs.getDate(5));
            }

            return foundReiziger;
        }
        catch ( Exception excp ) {
            System.out.println(excp.getMessage());
            return foundReiziger;
        }
    }
    @Override
    public List<Reiziger> findByGbdatum(String datum ) {
        ArrayList<Reiziger> reizigersGb = new ArrayList<>();
        try {
            // Maak SQL statement aan.
            String query = "SELECT * FROM reiziger WHERE geboortedatum=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setDate(1, Date.valueOf(datum));

            //Voer uit.
            ResultSet Rs = preparedStatement.executeQuery();

            while(Rs.next()) {
                Reiziger tempFoundReiziger = new Reiziger(); // Reiziger object moet in de loop en kan niet erboven, hierdoor append je dezelfde reference steeds naar hetzelfde object en heb je dus in principe maar een object in je lijst.
                tempFoundReiziger.setId(Rs.getInt(1));
                tempFoundReiziger.setVoorletters(Rs.getString(2));
                tempFoundReiziger.setTussenvoegsel(Rs.getString(3));
                tempFoundReiziger.setAchternaam(Rs.getString(4));
                tempFoundReiziger.setDate(Rs.getDate(5));
                reizigersGb.add(tempFoundReiziger);
            }
            return reizigersGb;
        }
        catch ( Exception excp ) {
            System.out.println(excp.getMessage());
            return reizigersGb;
        }
    }
    @Override
    public List<Reiziger> findAll() {
        ArrayList<Reiziger> allReizigers = new ArrayList<>();
        try {
            // Maak SQL statement aan.
            String query = "SELECT * FROM reiziger";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            //Voer uit.
            ResultSet Rs = preparedStatement.executeQuery();

            while(Rs.next()) {
                Reiziger tempFoundReiziger = new Reiziger(); // Reiziger object moet in de loop en kan niet erboven, hierdoor append je dezelfde reference steeds naar hetzelfde object en heb je dus in principe maar een object in je lijst.

                tempFoundReiziger.setId(Rs.getInt(1));
                tempFoundReiziger.setVoorletters(Rs.getString(2));
                tempFoundReiziger.setTussenvoegsel(Rs.getString(3));
                tempFoundReiziger.setAchternaam(Rs.getString(4));
                tempFoundReiziger.setDate(Rs.getDate(5));

                tempFoundReiziger.setAdres(adao.findByReiziger(tempFoundReiziger));

                allReizigers.add(tempFoundReiziger);
            }
            return allReizigers;
        }
        catch ( Exception excp ) {
            System.out.println(excp.getMessage());
            return allReizigers;
        }
    }

    public void setAdao(AdresDAO adao) {
        this.adao = adao;
    }

    public void setOVDao(OVChipkaartDAO OVDao) {
        this.OVDao = OVDao;
    }

    // Constructor.
    public ReizigerDAOPsql(Connection con ){
        this.con = con;
    }
}
