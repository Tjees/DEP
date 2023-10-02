import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProductDAOPsql implements ProductDAO {
    private Connection con;
    private OVChipkaartDAO OVDao;

    public ProductDAOPsql(Connection con) {
        this.con = con;
    }
    public void setOVDao(OVChipkaartDAO OVDao) {
        this.OVDao = OVDao;
    }

    @Override
    public boolean save( Product product ) {
        try {
            int ID = product.getProduct_nummer();
            String naam = product.getNaam();
            String beschrijving = product.getBeschrijving();
            int prijs = product.getPrijs();

            // Maak SQL statement aan.
            String query = "INSERT INTO product (product_nummer, naam, beschrijving, prijs) VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, naam);
            preparedStatement.setString(3, beschrijving);
            preparedStatement.setInt(4, prijs);

            //Voer uit.
            preparedStatement.executeUpdate();

            if( product.getOVChipkaartList() != null ) {
                for( OVChipkaart kaart : product.getOVChipkaartList() ) {
                    OVDao.save(kaart);
                }
            }

            String koppelQuery = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?)";
            PreparedStatement preparedKoppelStatement = con.prepareStatement(koppelQuery);
            if( product.getOVChipkaartList() != null ) {
                for( OVChipkaart kaart : product.getOVChipkaartList() ) {
                    preparedKoppelStatement.setInt(1, kaart.getKaart_nummer());
                    preparedKoppelStatement.setInt(2, ID);
                    preparedKoppelStatement.executeUpdate();
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
    public boolean update( Product product ) {
        try {
            int ID = product.getProduct_nummer();
            String naam = product.getNaam();
            String beschrijving = product.getBeschrijving();
            int prijs = product.getPrijs();

            String koppelQuery = "DELETE FROM ov_chipkaart_product WHERE product_nummer=?";
            PreparedStatement preparedKoppelStatement = con.prepareStatement(koppelQuery);
            if( product.getOVChipkaartList() != null ) {
                preparedKoppelStatement.setInt(1, ID);
                preparedKoppelStatement.executeUpdate();
            }

            if( product.getOVChipkaartList() != null ) {
                for( OVChipkaart kaart : product.getOVChipkaartList() ) {
                    OVDao.update(kaart);
                }
            }

            // Maak SQL statement aan.
            String query = "UPDATE product SET product_nummer = ?, naam = ?, beschrijving = ?, prijs = ? WHERE product_nummer=?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, ID);
            preparedStatement.setString(2, naam);
            preparedStatement.setString(3, beschrijving);
            preparedStatement.setInt(4, prijs);
            preparedStatement.setInt(5, ID);

            //Voer uit.
            preparedStatement.executeUpdate();

            String koppelQuery2 = "INSERT INTO ov_chipkaart_product (kaart_nummer, product_nummer) VALUES (?, ?)";
            PreparedStatement preparedKoppelStatement2 = con.prepareStatement(koppelQuery2);
            if( product.getOVChipkaartList() != null ) {
                for( OVChipkaart kaart : product.getOVChipkaartList() ) {
                    preparedKoppelStatement2.setInt(1, kaart.getKaart_nummer());
                    preparedKoppelStatement2.setInt(2, ID);
                    preparedKoppelStatement2.executeUpdate();
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
    public boolean delete( Product product ) {
        try {
            int ID = product.getProduct_nummer();

            String koppelQuery = "DELETE FROM ov_chipkaart_product WHERE product_nummer=?";
            PreparedStatement preparedKoppelStatement = con.prepareStatement(koppelQuery);
            if( product.getOVChipkaartList() != null ) {
                preparedKoppelStatement.setInt(1, ID);
                preparedKoppelStatement.executeUpdate();
            }

            if( product.getOVChipkaartList() != null ) {
                for( OVChipkaart kaart : product.getOVChipkaartList() ) {
                    kaart.deleteProduct(product);
                    OVDao.update(kaart);
                }
            }

            // Maak SQL statement aan.
            String query = "DELETE FROM product WHERE product_nummer=?";
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
    public List<Product> findByOVChipkaart(OVChipkaart kaart) {
        List<Product> producten = new ArrayList<>();
        try {
            // Maak SQL statement aan.
            String query = "SELECT product.product_nummer, product.naam, product.beschrijving, product.prijs FROM ov_chipkaart_product INNER JOIN product ON ov_chipkaart_product.product_nummer = product.product_nummer INNER JOIN ov_chipkaart ON ov_chipkaart_product.kaart_nummer = ov_chipkaart.kaart_nummer WHERE ov_chipkaart_product.kaart_nummer = ?";
            PreparedStatement preparedStatement = con.prepareStatement(query);

            // Vul de statement in ( kan allemaal met setOject() maar ja ).
            preparedStatement.setInt(1, kaart.getKaart_nummer());

            //Voer uit.
            ResultSet Rs = preparedStatement.executeQuery();

            while (Rs.next()) {
                Product tempProduct = new Product();
                tempProduct.setProduct_nummer(Rs.getInt(1));
                tempProduct.setNaam(Rs.getString(2));
                tempProduct.setBeschrijving(Rs.getString(3));
                tempProduct.setPrijs(Rs.getInt(4));
                producten.add(tempProduct);
            }

            return producten;
        } catch (Exception excp) {
            System.out.println(excp.getMessage());
            return producten;
        }
    }
}
