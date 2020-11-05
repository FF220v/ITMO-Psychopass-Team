import java.util.ArrayList;

public class CivillaDatabaseResponse {
    public int status;
    public String response;
    public ArrayList<CivillaDatabaseItem> content = null;

    CivillaDatabaseResponse(int status, String response, ArrayList<CivillaDatabaseItem> item){
        this.status = status;
        this.response = response;
        this.content = item;
    }

    CivillaDatabaseResponse(int status, String response){
        this.status = status;
        this.response = response;
    }
}
