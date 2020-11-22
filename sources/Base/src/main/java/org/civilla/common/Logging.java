package org.civilla.common;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Logging {
    public static Logger log = Logging.initialize_logger();

    public static Logger initialize_logger(){
        System.setProperty("java.util.logging.SimpleFormatter.format", "[%1$tF %1$tT] [%4$-7s] %5$s %n");
        Logger logger = Logger.getLogger(Logging.class.getName());
        logger.setLevel(Level.INFO);
        return logger;
    }
}
