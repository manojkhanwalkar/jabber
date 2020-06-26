package vfs;

import java.io.*;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class CurrentFileManager {

    String currentDir = "/home/manoj/data/vfs/current/";

    String[] attribs;

 //   Map<String,BufferedReader> readers = new HashMap<>();

    Map<String,BufferedWriter> writers = new HashMap<>();

    public CurrentFileManager(String... arrtibs)
    {
        this.attribs = arrtibs;


    }




    public void recover()
    {
        // read , sort , count per key using group by and then put in the current day count

        // if no file exists , just open one file per attribute type .

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date firstDate = Date.from(Instant.now());

        String formattedDate = formatter.format(firstDate);

        Arrays.stream(attribs).forEach(attr->{

            try {
                File file = new File(currentDir +"ccv_"+formattedDate);
                if (file.exists()) {
                    BufferedReader reader = new BufferedReader(new FileReader(currentDir + "ccv_" + formattedDate));

                    // recovery code here

                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(currentDir + attr + "_" + formattedDate,true));
                writers.put(attr,writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });




    }

    public synchronized void write(String attr, String key)
    {
        try {
            BufferedWriter writer = writers.get(attr);
            writer.write(key);
            writer.newLine();
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }






}
