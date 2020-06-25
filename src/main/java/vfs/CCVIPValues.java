package vfs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.time.LocalDate;
import java.util.Date;
import java.util.Random;

public class CCVIPValues {


    public static void main(String[] args) throws Exception {

        //generateDataFile("ccv");
       // generateDataFile("ip");

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
                  bufferedWriter.write(getCCV());
              else
                  bufferedWriter.write(getIP());

              bufferedWriter.newLine();
          }

          bufferedWriter.flush();
      }



    }

    static Random random = new Random();

    private static String getCCV()
    {

        StringBuilder builder = new StringBuilder(16);
        for (int i=0;i<16;i++)
        {
            int next = random.nextInt(10);
            builder.append(next);
        }

        return builder.toString();
    }

    private static String getIP()
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
