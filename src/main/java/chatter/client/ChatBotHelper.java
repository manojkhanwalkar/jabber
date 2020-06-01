package chatter.client;

import chatter.data.Packet;
import util.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatBotHelper
{
    public ChatBotHelper() {

    }

    static class ReceiverHandler implements Runnable
    {

            BufferedReader in;

            QueryProcessor queryProcessor;

        public ReceiverHandler(Socket socket)
        {
            try {
                in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            } catch (IOException e) {
                e.printStackTrace();
            }

            queryProcessor = new QueryProcessor(socket);
        }

        public void run()
        {
            while(true)
            {
                String line = null;
                try {
                    line = in.readLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if( line != null )
                {
                    System.out.println( "Query Received " + line );

                    queryProcessor.process(line);
                   // line = in.readLine();
                }
            }
        }
    }



    static class QueryProcessor {

        PrintStream out;

        String user;

        public QueryProcessor(Socket socket) {
            // Create input and output streams to read from and write to the server
            try {
                out = new PrintStream(socket.getOutputStream());


                user = System.getProperty("user");


                out.println(user);
                out.flush();

                init();

            } catch (IOException e) {
                e.printStackTrace();

            }


        }


        Map<String, String> answers = new HashMap<>();

        private void init() {

            for (int i = 0; i < 10; i++) {
                answers.put("Q" + i, "A" + i);

            }

        }


        ExecutorService pool = Executors.newFixedThreadPool(10);
        public void process(String query) {

            CompletableFuture.runAsync(() -> {

                Packet input = (Packet) JSONUtil.fromJSON(query, Packet.class);

                String answer = answers.getOrDefault(input.getMessage(), "Unable t answer");

                Packet packet = new Packet();
                packet.setFrom(user);
                packet.setTo(input.getFrom());
                packet.setMessage(answer);

                synchronized (out) {
                    out.println(JSONUtil.toJSON(packet));

                    out.flush();
                }
            }, pool);


        }

    }









    Socket socket;

    public void start()
    {

        String server = "localhost";



        try
        {
            // Connect to the server
             socket = new Socket( server, 5000 );


            ReceiverHandler receiverHandler = new ReceiverHandler(socket);

            CompletableFuture.runAsync(receiverHandler);



        }
        catch( Exception e )
        {
            e.printStackTrace();
        }
    }


    public void stop()
    {
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}