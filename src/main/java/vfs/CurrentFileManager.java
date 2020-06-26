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
import java.util.function.Function;
import java.util.stream.Collectors;

public class CurrentFileManager {

    String currentDir = "/home/manoj/data/vfs/current/";

    String[] attribs;

 //   Map<String,BufferedReader> readers = new HashMap<>();

    Map<String,BufferedWriter> writers = new HashMap<>();

    VFSManager vfsManager ;

    public CurrentFileManager(VFSManager vfsManager , String... arrtibs)
    {
        this.attribs = arrtibs;
        this.vfsManager = vfsManager;
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

                    recover(attr,reader);

                }

                BufferedWriter writer = new BufferedWriter(new FileWriter(currentDir + attr + "_" + formattedDate,true));
                writers.put(attr,writer);
            } catch (IOException e) {
                e.printStackTrace();
            }

        });




    }

    private void recover(String attr, BufferedReader reader)
    {
        try {
            List<String> list = new ArrayList<>();

            String line=reader.readLine();
            while(line!=null)
            {
                list.add(line);
                line = reader.readLine();
            }

            // Map<Integer, Long> result = numbers.stream()
            //      .filter(val -> val > 3)
            //      .collect(Collectors.groupingBy(i -> i, Collectors.counting()));
            Map<String, Long> keyCount = list.stream().sorted().collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

            VelocityStatsManager velocityStatsManager = vfsManager.attrVelocityMap.get(attr);
            keyCount.entrySet().stream().forEach(entry->{

                try {
                    velocityStatsManager.currentCountMap.get(entry.getKey()).setPrevCount(entry.getValue().intValue());
                } catch (Exception e) {
                   // e.printStackTrace();
                }

            });

        } catch (IOException e) {
            e.printStackTrace();
        }

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
