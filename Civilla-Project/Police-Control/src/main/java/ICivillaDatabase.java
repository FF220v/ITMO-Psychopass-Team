public interface ICivillaDatabase {
    public CivillaDatabaseResponse get(String id);
    public CivillaDatabaseResponse query(String filter);
    public CivillaDatabaseResponse put(CivillaDatabaseItem id);
    public CivillaDatabaseResponse delete();
}
