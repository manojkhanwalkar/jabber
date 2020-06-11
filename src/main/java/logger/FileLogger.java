package logger;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class FileLogger implements Logger {


    String dir;
    String fileName;
    int size;

    BufferedWriter writer;

    public void init(LoggerProperties loggerProperties)
    {
        this.dir = loggerProperties.getLogDir();
        this.fileName = loggerProperties.getFileName();
        this.size = loggerProperties.getFileSize();

        makeFile();

    }

    private void makeFile()
    {
        try {
            LocalDateTime dateTime = LocalDateTime.now();
            File file = new File(dir+fileName+"_"+dateTime.format(formatter));
             writer = new BufferedWriter(new FileWriter(file));


        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    int currSize = 0;

    @Override
    public synchronized void log(LogRecord logRecord) {

        String str = logRecord.toString();
        if (currSize>size) {
            rollover();
            currSize=0;
        }
        else
        {
            currSize+=str.length();

        }

        try {
            writer.write(str);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss:SSS");



    void rollover()
    {

        try {
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        makeFile();

    }
}
