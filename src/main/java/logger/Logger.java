package logger;

public interface Logger {

    public void log(LogRecord logRecord);

    public enum LogLevels {INFO, DEBUG, WARN}
}
