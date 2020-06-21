public class CivillaDatabaseItem {
    public String id;
    public String firstName;
    public String secondName;
    public float coefficient;
    public boolean likesBeer;

    CivillaDatabaseItem(String id, String firstName, String secondName, float coefficient, boolean likesBeer){
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.likesBeer = likesBeer;
        this.coefficient = coefficient;
    }

}
