package logger;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LogManager {

    static class Holder
    {
        static LogManager logManager = new LogManager();
    }

    private LogManager()
    {
        init("to be implemented");
    }

    public static LogManager getInstance()
    {
        return Holder.logManager;
    }

    public void init(String propertyFileName)
    {
        //TODO - create and read logging props file
        logger = new SysOutLogger();
    }

    private Logger logger ;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public void log(Object payload, Logger.LogLevels level)
    {
        LocalDateTime dateTime = LocalDateTime.now();
        logger.log(new LogRecord(Thread.currentThread().getName(),dateTime.format(formatter),level.toString(),payload.toString() ));
    }


}
