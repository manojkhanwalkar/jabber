package scheduler.persistence;


import org.apache.commons.io.comparator.LastModifiedFileComparator;
import util.JSONUtil;

import java.io.*;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

public class FilePersistenceManager {

//TODO - restore state and init new file on start
    //- write after add and complete operation
    // close file on stop.


    String persistenceDir;


    public FilePersistenceManager(String persistenceDir) {
        this.persistenceDir = persistenceDir;

    }

    BufferedWriter writer = null;

    public void init() {
        File file = new File(persistenceDir + System.currentTimeMillis() + ".data");
        try {
            writer = new BufferedWriter(new FileWriter(file));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void stop() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // new file everytime we persist = bc + timestamp

    // restore one file at a time , sorted by time .

    public void persist(Record record) {

        try {
            String str = JSONUtil.toJSON(record);
            try {
                writer.write(str);
                writer.newLine();
            } catch (IOException e) {
                e.printStackTrace();
            }

            writer.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    static class RecordIterable implements Iterable<Record> {

        FilePersistenceManager persistenceManager;

        public RecordIterable(FilePersistenceManager persistenceManager) {
            this.persistenceManager = persistenceManager;
        }

        @Override
        public Iterator<Record> iterator() {
            return new RecordIterator(persistenceManager.persistenceDir);
        }

        @Override
        public void forEach(Consumer<? super Record> action) {
            throw new RuntimeException("Not implemented");
        }

        @Override
        public Spliterator<Record> spliterator() {
            return null;
        }


    }

    static class RecordIterator implements Iterator<Record> {
        String persistenceDir;

        public RecordIterator(String persistenceDir) {
            this.persistenceDir = persistenceDir;

            init();
        }


        boolean more = true;

        @Override
        public boolean hasNext() {
            return more;
        }

        @Override
        public Record next() {
            Record record= nextRecord();
            if (record==null)
            {
                more = false;
            }

            return record;
        }


        File[] files;

        int count = 0;

        BufferedReader reader = null;

        private Record nextRecord() {

            while(true) {
                try {
                    if (reader == null) {
                         reader = new BufferedReader(new FileReader(files[count]));
                    }

                    String s = reader.readLine();
                    if (s != null) {
                        Record block = (Record) JSONUtil.fromJSON(s, Record.class);
                        return block;
                    } else {

                        count++;
                        reader = null;
                        if (count>=files.length)  // all files have been processed
                            return null;

                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }


        private void init() {
            File dir = new File(persistenceDir);
            files = dir.listFiles();
            Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_COMPARATOR);


            if (files.length == 0) {

                more = false;
                return;

            }
        }


    }





    public Iterator<Record> restore() {

        return new RecordIterable(this).iterator();


    }


}








