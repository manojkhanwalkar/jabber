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
    public FileLogger(String dir, String fileName, int size) {
        this.dir = dir;
        this.fileName = fileName;
        this.size = size;


    }

    private BufferedWriter makeFile()
    {
        try {
            LocalDateTime dateTime = LocalDateTime.now();
            File file = new File(dir+fileName+"_"+dateTime.format(formatter));
            BufferedWriter writer = new BufferedWriter(new FileWriter(file));

            return writer;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public void log(LogRecord logRecord) {


    }
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");



    BufferedWriter rollover(BufferedWriter curr)
    {

        try {
            curr.flush();
            curr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return makeFile();

    }
}
