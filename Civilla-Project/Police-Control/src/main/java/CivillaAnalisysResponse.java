import java.util.Hashtable;

public class CivillaAnalisysResponse {
    public int status;
    public String response;
    public Hashtable<String, Float> content;

    CivillaAnalisysResponse(int status, String response, Hashtable<String, Float> content){
        this.status = status;
        this.response = response;
        this.content = content;
    }
}

