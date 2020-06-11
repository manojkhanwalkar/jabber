package logger;

import util.JSONUtil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class LogManager {

    static class Holder
    {
        static LogManager logManager = new LogManager();
    }

    private LogManager()
    {
        init();
    }

    public static LogManager getInstance()
    {
        return Holder.logManager;
    }

    public void init()
    {
        try {
            File loggerPropertiesFile= new File("/home/manoj/IdeaProjects/jabber/src/main/resources/mylogger.json");

            try(BufferedReader bufferedReader = new BufferedReader(new FileReader(loggerPropertiesFile)))
            {
                String str = bufferedReader.readLine();
                LoggerProperties loggerProperties = (LoggerProperties)JSONUtil.fromJSON(str,LoggerProperties.class);
                logger = (Logger)Class.forName(loggerProperties.getLogger()).getDeclaredConstructors()[0].newInstance();

                mode = loggerProperties.getMode();
                if (mode== LoggerProperties.Mode.async)
                {
                    asycLogManager = new AsycLogManager(logger);
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    private Logger logger ;

    private LoggerProperties.Mode mode;

    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
    //SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public void log(Object payload, Logger.LogLevels level)
    {
        LocalDateTime dateTime = LocalDateTime.now();
        LogRecord logRecord = new LogRecord(Thread.currentThread().getName(),dateTime.format(formatter),level.toString(),payload.toString() );
        if (mode== LoggerProperties.Mode.sync)
        {
            logSync(logRecord);
        }
        else
        {
            asycLogManager.logAsync(logRecord);
        }

    }


    public void logSync(LogRecord logRecord)
    {
        logger.log(logRecord);
    }

    AsycLogManager asycLogManager ;



    static class AsycLogManager
    {

        Logger logger;

        LinkedBlockingQueue<LogRecord> queue = new LinkedBlockingQueue<>();

        ExecutorService service = Executors.newFixedThreadPool(1);

        public AsycLogManager(Logger logger)
        {
            this.logger = logger;

            service.submit(()->{

                while(true)

                {
                    LogRecord logRecord = queue.take();
                    logger.log(logRecord);
                }
            });
        }

        public void logAsync(LogRecord logRecord)
        {

            try {
                queue.put(logRecord);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }


}
