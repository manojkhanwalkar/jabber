package vfs;

import java.io.*;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class CCVIPValues {

  //  static List<String> ips = new ArrayList<>();
   // static List<String> ccvs = new ArrayList<>();

    static Map<String,List<String>>  attributeValues = new HashMap<>();



    public static void init(String... attributes)  // ccv , ip
    {

            Arrays.stream(attributes).forEach(attr->{
                try {

                    attributeValues.put(attr,new ArrayList<>());
                    readDataFile(attr);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });



    }


    public static String get(String attr)
    {
        List<String> list = attributeValues.get(attr);

        int index = random.nextInt(list.size()) ;

        return list.get(index);
    }

    public static List<String> getAll(String attr)
    {
        List<String> list = attributeValues.get(attr);
        return  list.stream().collect(Collectors.toList());
    }





    private static void readDataFile(String prefix)  throws Exception {
        File file = new File(dataDir+prefix);
        List<String> list = attributeValues.get(prefix);

        try(BufferedReader bufferedReader = new BufferedReader(new FileReader(file)))
        {
            String line = bufferedReader.readLine();

            while(line!=null)
            {
                list.add(line);
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
                  bufferedWriter.write(generateCCV());
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
