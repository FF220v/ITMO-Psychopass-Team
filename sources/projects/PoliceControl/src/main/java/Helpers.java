public class Helpers {
    public static boolean inArrayLower(String str, String[] arr){
        for (String s: arr)
            if (s.toLowerCase().equals(str.toLowerCase()))
                    return true;
        return false;
    }
}
