public interface ICivillaDatabase {
    CivillaDatabaseResponse get(String id);
    CivillaDatabaseResponse query(String filter);
    CivillaDatabaseResponse put(CivillaDatabaseItem id);
    CivillaDatabaseResponse delete(String id) throws NotImplementedException;
}
