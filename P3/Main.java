import java.sql.*;
import java.util.List;

public class Main {
    private static Connection con;

    public static void main(String[] args) {
        // System.out.println("Hello World!");
        try {
            // Verbinden met database.
            con = getConnection();

            // Nieuwe reiziger.
            // Reiziger reiziger1 = new Reiziger(9, "PP", "van", "Achteren", Date.valueOf("1904-02-20"));

             ReizigerDAOPsql reiziger1sql = new ReizigerDAOPsql(con);
             AdresDAOPsql adres1sql = new AdresDAOPsql(con);
             reiziger1sql.setAdao(adres1sql);
             adres1sql.setRdao(reiziger1sql);
            // reiziger1sql.save(reiziger1);

            // Nieuwe reiziger.
            Reiziger reiziger1 = new Reiziger(9, "AA", "van", "Achteren", Date.valueOf("1904-02-20"));
            Adres adres1 = new Adres(10, "1234AB", "12", "Sesamstraat", "UTRECHT", 9);
            reiziger1.setAdres(adres1);

            for( Reiziger r: reiziger1sql.findAll()){
                System.out.println(r.toString());
            }

            // testReizigerDAO(reiziger1sql);
            // testAdresDAO(adres1sql);
        }
        catch ( Exception excp ) {
            System.out.println(excp.getMessage());
        }
    }

    private static Connection getConnection() throws SQLException {
        // Verbinden met database.
        Connection con;
        con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DP_OV-Chipkaart", "postgres", "1357" );
        return con;
    }

    private static void closeConnection() throws SQLException {
        if(con != null){
            con.close();
        }
    }

    private static void testReizigerDAO(ReizigerDAO rdao) throws SQLException {
        System.out.println("\n---------- Test ReizigerDAO -------------");

        // Haal alle reizigers op uit de database
        List<Reiziger> reizigers = rdao.findAll();
        System.out.println("[Test] ReizigerDAO.findAll() geeft de volgende reizigers:");
        for (Reiziger r : reizigers) {
            System.out.println(r);
        }
        System.out.print("\n");

        // Maak een nieuwe reiziger aan en persisteer deze in de database
        String gbdatum = "1981-03-14";
        Reiziger sietske = new Reiziger(77, "S", "", "Boers", java.sql.Date.valueOf(gbdatum));
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.save() ");
        rdao.save(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        // Zoek een reiziger op uit database.
        System.out.println("Reiziger met ID 8 is: " + rdao.findById(8).toString());
        System.out.print("\n");

        // Zoek reizigers op geboortedatum op uit de database.
        List<Reiziger> reizigersgb = rdao.findByGbdatum("1904-09-10");
        System.out.println("[Test] ReizigerDAO.findByGbdatum() geeft de volgende reizigers:");
        for (Reiziger r : reizigersgb) {
            System.out.println(r);
        }
        System.out.print("\n");

        // Update een reiziger uit de database.
        Reiziger tempReiziger = rdao.findById(8);
        System.out.println("[Test] Voor de update was de achternaam: " + tempReiziger.getAchternaam());
        tempReiziger.setAchternaam("Pieters");
        rdao.update(tempReiziger);
        System.out.println("[Test] Na de update was de achternaam: " + tempReiziger.getAchternaam());
        System.out.print("\n");

        // Verwijder een gebruiker uit de databse.
        System.out.print("[Test] Eerst " + reizigers.size() + " reizigers, na ReizigerDAO.delete() ");
        rdao.delete(sietske);
        reizigers = rdao.findAll();
        System.out.println(reizigers.size() + " reizigers\n");
    }

    private static void testAdresDAO(AdresDAO adao) throws SQLException {
        System.out.println("\n---------- Test AdresDAO -------------");

        // Haal alle adressen op uit de database
        List<Adres> adressen = adao.findAll();
        System.out.println("[Test] adao.findAll() geeft de volgende adressen:");
        for (Adres a : adressen) {
            System.out.println(a);
        }
        System.out.print("\n");

        // Maak een nieuw adres aan en persisteer deze in de database
        Adres adres1 = new Adres(77, "1234AB", "12", "Sesamstraat", "UTRECHT", 8);
        System.out.print("[Test] Eerst " + adressen.size() + " reizigers, na ReizigerDAO.save() ");
        adao.save(adres1);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");

        // Voeg aanvullende tests van de ontbrekende CRUD-operaties in.

        // Zoek adres op reiziger op uit de database.
        // Nieuwe reiziger.
        Reiziger reiziger1 = new Reiziger(8, "PP", "van", "Achteren", Date.valueOf("1904-02-20"));
        Adres tempAdres = adao.findByReiziger(reiziger1);
        System.out.println("[Test] adao.findByReiziger() geeft de volgende adressen:");
        System.out.println(tempAdres.toString());
        System.out.print("\n");

        // Update een reiziger uit de database.
        System.out.println("[Test] Voor de update was de postcode: " + tempAdres.getPostcode());
        tempAdres.setPostcode("4567BC");
        adao.update(tempAdres);
        System.out.println("[Test] Na de update was de postcode: " + tempAdres.getPostcode());
        System.out.print("\n");

        // Verwijder een adres uit de databse.
        System.out.print("[Test] Eerst " + adressen.size() + " adressen, na adao.delete() ");
        adao.delete(adres1);
        adressen = adao.findAll();
        System.out.println(adressen.size() + " adressen\n");
    }

    private static void leesAlleReizigers(Connection con) throws SQLException {
        // Maak SQL statement aan.
        Statement stmnt = con.createStatement();

        // Maak SQL query aan ( die waarschijnlijk ook de resultaten ontvangt ) en voer deze uit.
        ResultSet Rs = stmnt.executeQuery("SELECT * FROM reiziger");

        // Lees de resultaten uit ( heb even snel aan chatgpt gevraagd hoe ik door alle elementen van een row kon lopen want wilde niet voor elke column een print doen.
        // Hierdoor werkt het hopelijk ook voor andere rows en niet alleen voor reiziger.)
        System.out.println("Alle reizigers: ");
        while( Rs.next() ) {
            ResultSetMetaData metaData = Rs.getMetaData(); // Data over de row.
            int columnCount = metaData.getColumnCount(); // Aantal elementen in row.

            for (int i = 1; i < columnCount + 1; i++) {
                String columnValue = Rs.getString(i);

                // Heb gecheckt met chatgpt en normaal geef ik lege strings terug als iets null is maar hier werkt
                // het iets anders. Dit geeft een null waarde terug in plaats van een lege string. ( een string is een reference
                // naar een array klasse en dus kan er een null reference terug worden gegeven bij ints bijvoorbeeld niet.)
                if( columnValue != null ) {
                    System.out.print(columnValue);
                    System.out.print(" ");
                }
            }
            System.out.print('\n');
        }
    }
}