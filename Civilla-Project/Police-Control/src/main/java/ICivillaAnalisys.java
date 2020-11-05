import java.util.Hashtable;

public interface ICivillaAnalisys {
     public CivillaAnalisysResponse analyse(String id, String data);
     public CivillaAnalisysResponse analyseBatch(Hashtable<String, String> data);
}
