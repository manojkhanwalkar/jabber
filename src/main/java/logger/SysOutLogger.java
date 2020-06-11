package logger;

public class SysOutLogger implements Logger  {

    @Override
    public void log(LogRecord logRecord) {

        System.out.println(logRecord);
    }
}
