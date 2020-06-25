package vfs;

import org.apache.commons.io.filefilter.NameFileFilter;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalUnit;
import java.util.Collections;
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

    static class MyFileFilter implements  FilenameFilter
    {
        String pattern;

        Date cutoffDate ;

        public MyFileFilter(String pattern, int n)
        {
            this.pattern = pattern;

            cutoff(n+1);
        }


        private void cutoff(int n)
        {

            Instant cutoff = Instant.now().minus(n, ChronoUnit.DAYS);
             cutoffDate = Date.from(cutoff);

        }


        @Override
        public boolean accept(File file, String fileName) {

            try {

                if (fileName.startsWith(pattern)) {
                    String[] split = fileName.split("_");
                    Date fileDate = new SimpleDateFormat("dd-MM-yyyy").parse(split[1]);
                    if (fileDate.after(cutoffDate))
                        return true;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }

            return false;
        }
    }

    private static List<File> lastNDays(int n, String prefix)
    {

        File file = new File(historyDir);

        FilenameFilter filenameFilter = new MyFileFilter(prefix,n);

        File[] files = file.listFiles(filenameFilter);



        return List.of(files);

    }

    public static void main(String[] args) throws Exception {


        lastNDays(30,"ccv_");
        // generate files with CCV and IP values to use
        // generate history file summary for 35 days
        //

     /*   CCVIPValues.init();

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



        }*/



/*
        Instant second = now.minus(25, ChronoUnit.DAYS);




        Date secondDate = Date.from(second);

        System.out.println(firstDate.before(cutoffDate));


        System.out.println(secondDate.before(cutoffDate));


        System.out.println(formattedDate);

      */



       // System.out.println(date1);


    }

   static  String historyDir = "/home/manoj/data/vfs/history/";
    String currentDir = "/home/manoj/data/vfs/current/";



}
