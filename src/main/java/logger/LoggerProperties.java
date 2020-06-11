package logger;

import util.JSONUtil;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class LoggerProperties {

    public enum Mode {sync, async}


    Mode mode;

    String logger ;

    String logDir ;

    String fileName;

    int fileSize;

    public Mode getMode() {
        return mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public String getLogger() {
        return logger;
    }

    public void setLogger(String logger) {
        this.logger = logger;
    }

    public String getLogDir() {
        return logDir;
    }

    public void setLogDir(String logDir) {
        this.logDir = logDir;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public int getFileSize() {
        return fileSize;
    }

    public void setFileSize(int fileSize) {
        this.fileSize = fileSize;
    }


    public static void createsampleProperty()
    {
        LoggerProperties loggerProperties = new LoggerProperties();
        loggerProperties.setFileName("log");
        loggerProperties.setFileSize(10000);
        loggerProperties.setLogDir("/home/manoj/data/epi/");
        loggerProperties.setMode(Mode.async);
        loggerProperties.setLogger("logger.SysOutLogger");

        String str = JSONUtil.toJSON(loggerProperties);

        File loggerPropertiesFile= new File("/home/manoj/IdeaProjects/jabber/src/main/resources/mylogger.json");

        try {
            try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(loggerPropertiesFile)))
            {
                bufferedWriter.write(str);
                bufferedWriter.newLine();
                bufferedWriter.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public static void main(String[] args) {


     createsampleProperty();
    }
}
