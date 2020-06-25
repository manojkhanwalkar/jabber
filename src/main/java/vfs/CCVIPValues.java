package vfs;

import java.io.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class CCVIPValues {

    static List<String> ips = new ArrayList<>();
    static List<String> ccvs = new ArrayList<>();



    public static void init()
    {
        try {
            readDataFile("ccv");
            readDataFile("ip");
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    public static String getIP()
    {
        int index = random.nextInt(ips.size()) ;

        return ips.get(index);
    }

    public static String getCCV()
    {
        int index = random.nextInt(ccvs.size()) ;

        return ips.get(index);
    }

    private static void readDataFile(String prefix)  throws Exception {
        File file = new File(dataDir+prefix);

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
        {
            String line = bufferedReader.readLine();

            while(line!=null)
            {
                if (prefix.startsWith("ccv"))
                    ccvs.add(line);
                else
                    ips.add(line);


                line = bufferedReader.readLine();
            }

        }
    }


    static String dataDir = "/home/manoj/data/vfs/data/";
    private static void generateDataFile(String prefix) throws Exception
    {
     // LocalDate date = LocalDate.now();

      File file = new File(dataDir+prefix);

      try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file)))
      {
          for (int i=0;i<10;i++) {
              if (prefix.startsWith("ccv"))
                  bufferedWriter.write(generateCCV()());
              else
                  bufferedWriter.write(generateIP());

              bufferedWriter.newLine();
          }

          bufferedWriter.flush();
      }



    }

    static Random random = new Random();

    private static String generateCCV()
    {

        StringBuilder builder = new StringBuilder(16);
        for (int i=0;i<16;i++)
        {
            int next = random.nextInt(10);
            builder.append(next);
        }

        return builder.toString();
    }

    private static String generateIP()
    {

        //100.200.255.255
        StringBuilder builder = new StringBuilder();
        for (int i=0;i<4;i++)
        {
            int next = random.nextInt(256);
            builder.append(next);
            if (i!=3)
                builder.append(".");

        }

        return builder.toString();
    }

}
