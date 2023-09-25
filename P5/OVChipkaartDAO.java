import java.util.List;

public interface OVChipkaartDAO {
    public boolean save( OVChipkaart kaart );
    public boolean update( OVChipkaart kaart );
    public boolean delete( OVChipkaart kaart );
    public List<OVChipkaart> findByReiziger( Reiziger reiziger );
    public List<OVChipkaart> findAll();
}
