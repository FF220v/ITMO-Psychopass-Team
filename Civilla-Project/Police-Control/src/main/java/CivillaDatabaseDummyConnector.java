import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.ArrayList;
import java.util.Hashtable;

public class CivillaDatabaseDummyConnector implements ICivillaDatabase {

    private static final Hashtable<String, CivillaDatabaseItem> databaseItems =
            new Hashtable<String, CivillaDatabaseItem>();

    @Override
    public CivillaDatabaseResponse get(String id) {
        if (databaseItems.getOrDefault(id, null) != null){
            ArrayList<CivillaDatabaseItem> ret = new ArrayList<CivillaDatabaseItem>();
            ret.add(databaseItems.get(id));
            return new CivillaDatabaseResponse(200, "ok", ret);
        }
        return new CivillaDatabaseResponse(404, "not found", new ArrayList<CivillaDatabaseItem>());
    }

    @Override
    public CivillaDatabaseResponse put(CivillaDatabaseItem item) {
        databaseItems.put(item.id, item);
        return new CivillaDatabaseResponse(200, "ok");
    }

    @Override
    public CivillaDatabaseResponse delete() {
        throw new NotImplementedException();
    }

    @Override
    public CivillaDatabaseResponse query(String filter) {
        throw new NotImplementedException();
    }
}
