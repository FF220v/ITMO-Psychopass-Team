import java.util.ArrayList;
import java.util.Hashtable;

public class CivillaDatabaseDummyConnector implements ICivillaDatabase {

    private static final Hashtable<String, CivillaDatabaseItem> databaseItems =
            new Hashtable<>();

    @Override
    public CivillaDatabaseResponse get(String id) {
        if (databaseItems.getOrDefault(id, null) != null){
            ArrayList<CivillaDatabaseItem> ret = new ArrayList<>();
            ret.add(databaseItems.get(id));
            return new CivillaDatabaseResponse(200, "ok", ret);
        }
        return new CivillaDatabaseResponse(404, "not found", new ArrayList<>());
    }

    @Override
    public CivillaDatabaseResponse put(CivillaDatabaseItem item) {
        databaseItems.put(item.id, item);
        return new CivillaDatabaseResponse(200, "ok");
    }

    @Override
    public CivillaDatabaseResponse delete(String id) throws NotImplementedException {
        throw new NotImplementedException();
    }

    @Override
    public CivillaDatabaseResponse query(String filter) {
        if (databaseItems.isEmpty())
            return new CivillaDatabaseResponse(404, "not_found", new ArrayList<CivillaDatabaseItem>(databaseItems.values()));
        return new CivillaDatabaseResponse(200, "ok", new ArrayList<CivillaDatabaseItem>(databaseItems.values()));
    }
}
