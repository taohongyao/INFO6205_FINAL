package info6205.virus.simulation.logger;

import java.util.logging.Level;

public class Log {

    static {
        DEBUG = new Debug();
        System.out.println("Hello");
    }
    public static Level DEBUG;
    public static Level APP_LEVEL=Level.INFO;
}
