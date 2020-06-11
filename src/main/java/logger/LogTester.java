package logger;

public class LogTester {


    public static void main(String[] args)throws Exception  {


        LogManager logManager = LogManager.getInstance();

        logManager.init();


        for (int i=0;i<100;i++) {
            logManager.log(i, Logger.LogLevels.INFO);

            Thread.sleep(250);
        }

    }
}
