import java.util.Hashtable;
import java.util.Random;

public class CivillaAnalisysDummyConnector implements ICivillaAnalisys {

    @Override
    public CivillaAnalisysResponse analyse(String id, String data) {
        Hashtable<String, Float> ret = new Hashtable<>();
        ret.put(id, new Random().nextFloat());
        return new CivillaAnalisysResponse(200, "ok", ret);
    }

    @Override
    public CivillaAnalisysResponse analyseBatch(Hashtable<String, String> data) throws NotImplementedException {
        throw new NotImplementedException();
    }
}
