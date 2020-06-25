package vfs;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class HistoryFileGenerator {

    static         Random random = new Random();

    private static void write(List<String> list, File file) throws Exception
    {
        BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file));
        list.stream().forEach(ccv->{

            try {
                bufferedWriter.write(ccv + " " + random.nextInt(100));
                bufferedWriter.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        bufferedWriter.flush();
        bufferedWriter.close();

    }

    public static void main(String[] args) throws Exception {


        // generate files with CCV and IP values to use
        // generate history file summary for 35 days
        //

        CCVIPValues.init();

        Instant now = Instant.now();
        for (int i=1;i<35;i++)
        {
            Instant first = now.minus(i, ChronoUnit.DAYS);
            SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
            Date firstDate = Date.from(first);

            String formattedDate = formatter.format(firstDate);

            File file = new File(historyDir +"ccv_"+formattedDate);
            write(CCVIPValues.getAllCCVs(), file);
            File file1= new File(historyDir +"ip_"+formattedDate);
            write(CCVIPValues.getAllIps(), file1);



        }



/*
        Instant second = now.minus(25, ChronoUnit.DAYS);


        Instant cutoff = now.minus(30, ChronoUnit.DAYS);


        Date cutoffDate = Date.from(cutoff);


        Date secondDate = Date.from(second);

        System.out.println(firstDate.before(cutoffDate));


        System.out.println(secondDate.before(cutoffDate));


        System.out.println(formattedDate);

        Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(formattedDate); */



       // System.out.println(date1);


    }

   static  String historyDir = "/home/manoj/data/vfs/history/";
    String currentDir = "/home/manoj/data/vfs/current/";



}
