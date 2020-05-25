import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    public static void main(String[] args) {
        Logger LOGGER = LoggerFactory.getLogger(Main.class);

        LOGGER.trace("This is a TRACE log");
        LOGGER.debug("This is a debug log");
//        LOGGER.info("This is an INFO log");
        LOGGER.warn("This is a WARN log");
        LOGGER.error("This is an ERROR log");
    }
}
