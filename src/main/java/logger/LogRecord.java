package logger;

public class LogRecord {

    String threadName;
    String date;
    String level;
    String payload;

    public LogRecord(String threadName, String date, String level, String payload) {
        this.threadName = threadName;
        this.date = date;
        this.level = level;
        this.payload = payload;
    }

    @Override
    public String toString() {
        return level + " " +"[" + date + "]" + " " + threadName + " "  + payload;

    }
}
