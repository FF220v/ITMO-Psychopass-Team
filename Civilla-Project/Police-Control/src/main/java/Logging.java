import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.lang.invoke.MethodHandles.Lookup;

public class Logging {
    public static Logger log = Logging.initialize_logger();

    public static Logger initialize_logger(){
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        return Logger.getLogger(Logging.class.getName());
    }
}
