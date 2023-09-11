import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class OVChipkaartDAOPsql implements OVChipkaartDAO {
    // Variabelen.
    private Connection con;
    private ReizigerDAO rdao;

    public OVChipkaartDAOPsql( Connection con ){
        this.con = con;
    }

    // Functies.
    @Override
    public boolean save(OVChipkaart kaart) {
        try {
            int ID = kaart.getKaart_nummer();
            Date geldig_tot = kaart.getGeldig_tot();
            int klasse = kaart.getKlasse();
            double saldo = kaart.getSaldo();
            int reiziger_id = kaart.getReiziger_id();

            // Maak SQL statement aan.
            String query = "INSERT INTO ov_chipkaart (kaart_nummer, geldig_tot, klasse, saldo, reiziger_id) VALUES ( ?, ?, ?, ?, ? )";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, ID);
            preparedStatement.setDate(2, geldig_tot);
            preparedStatement.setInt(3, klasse);
            preparedStatement.setDouble(4, saldo);
            preparedStatement.setInt(5, reiziger_id);

            //Voer uit.
            preparedStatement.executeUpdate();

            return true;
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(OVChipkaart kaart) {
        try {
            int ID = kaart.getKaart_nummer();
            Date geldig_tot = kaart.getGeldig_tot();
            int klasse = kaart.getKlasse();
            double saldo = kaart.getSaldo();
            int reiziger_id = kaart.getReiziger_id();

            // Maak SQL statement aan.
            String query = "UPDATE ov_chipkaart SET geldig_tot = ?, klasse = ?, saldo = ?, reiziger_id = ? WHERE kaart_nummer = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setDate(1, geldig_tot);
            preparedStatement.setInt(2, klasse);
            preparedStatement.setDouble(3, saldo);
            preparedStatement.setInt(4, reiziger_id);
            preparedStatement.setInt(5, ID);

            //Voer uit.
            preparedStatement.executeUpdate();

            return true;
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(OVChipkaart kaart) {
        try {
            int ID = kaart.getKaart_nummer();

            // Maak SQL statement aan.
            String query = "DELETE FROM ov_chipkaart WHERE kaart_nummer = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, ID);

            //Voer uit.
            preparedStatement.executeUpdate();

            return true;
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            return false;
        }
    }

    @Override
    public List<OVChipkaart> findByReiziger(Reiziger reiziger) {
        List<OVChipkaart> kaarten = new ArrayList<>();
        try {
            // Maak SQL statement aan.
            String query = "SELECT * FROM ov_chipkaart WHERE reiziger_id=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, reiziger.getId());

            //Voer uit.
            ResultSet Rs = preparedStatement.executeQuery();

            while (Rs.next()) {
                OVChipkaart tempKaart = new OVChipkaart();
                tempKaart.setKaart_nummer(Rs.getInt(1));
                tempKaart.setGeldig_tot(Rs.getDate(2));
                tempKaart.setKlasse(Rs.getInt(3));
                tempKaart.setSaldo(Rs.getFloat(4));
                tempKaart.setReiziger_id(Rs.getInt(5));
                kaarten.add(tempKaart);
            }

            return kaarten;
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            return kaarten;
        }
    }

    @Override
    public List<OVChipkaart> findAll() {
        List<OVChipkaart> allKaarten = new ArrayList<>();
        try {
            // Maak SQL statement aan.
            String query = "SELECT * FROM ov_chipkaart";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            //Voer uit.
            ResultSet Rs = preparedStatement.executeQuery();

            while (Rs.next()) {
                OVChipkaart tempKaart = new OVChipkaart();
                tempKaart.setKaart_nummer(Rs.getInt(1));
                tempKaart.setGeldig_tot(Rs.getDate(2));
                tempKaart.setKlasse(Rs.getInt(3));
                tempKaart.setSaldo(Rs.getDouble(4));
                tempKaart.setReiziger_id(Rs.getInt(5));
                allKaarten.add(tempKaart);
            }
            return allKaarten;
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            return allKaarten;
        }
    }

    public void setRdao(ReizigerDAO rdao) {
        this.rdao = rdao;
    }
}