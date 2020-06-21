import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import java.util.Hashtable;
import java.util.Random;

public class CivillaAnalisysDummyConnector implements ICivillaAnalisys {

    @Override
    public CivillaAnalisysResponse analyse(String id, String data) {
        Hashtable<String, Float> ret = new Hashtable<String, Float>();
        ret.put(id, new Random().nextFloat());
        return new CivillaAnalisysResponse(200, "ok", new Hashtable<String, Float>());
    }

    @Override
    public CivillaAnalisysResponse analyseBatch(Hashtable<String, String> data) {
        throw new NotImplementedException();
    }
}
