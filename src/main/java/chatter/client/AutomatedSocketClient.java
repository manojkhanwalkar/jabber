package chatter.client;

import chatter.data.Packet;
import util.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class AutomatedSocketClient
{

    String user;

    String other = "ChatBot";

    public AutomatedSocketClient(String user) {

        this.user = user;
    }

    static class ReceiverHandler implements Runnable
    {

            BufferedReader in;

            QueryGenerator queryGenerator;

        public ReceiverHandler(Socket socket, QueryGenerator queryGenerator)
        {
            this.queryGenerator=queryGenerator;
            try {
                in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void run()
        {
            while(true)
            {
                String line = null;
                try {
                    line = in.readLine();
                } catch (IOException e) {
                   break;
                }
                if( line != null )
                {
                    //System.out.println( line );
                    Packet packet = (Packet)JSONUtil.fromJSON(line,Packet.class);

                    queryGenerator.complete(packet);
                   // line = in.readLine();
                }
            }
        }
    }


    static class QueryGenerator implements Runnable {

        PrintStream out;

        String user;
        String other;

        public QueryGenerator(Socket socket, String user, String other) {
            this.user = user;
            this.other = other;

            // Create input and output streams to read from and write to the server
            try {
                out = new PrintStream(socket.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }


            out.println(user);
            out.flush();


        }


        volatile CompletableFuture<Packet> completableFuture;


        public void complete(Packet packet) {

            completableFuture.complete(packet);
        }

        static AtomicInteger totCount = new AtomicInteger(0);

        public void run() {
            for (int i = 0; i < 1000; i++) {

                completableFuture = new CompletableFuture<>();

                Packet packet = new Packet();
                packet.setFrom(user);
                packet.setTo(other);
                packet.setMessage("Q" + i);

                out.println(JSONUtil.toJSON(packet));

                out.flush();

                Packet response = null;
                try {
                    response = completableFuture.get();

                    //Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }

                System.out.println(response);
                System.out.println(totCount.getAndIncrement());
                // send the message .
                // System.out.println(value);
            }


        }
    }




    ExecutorService pool = Executors.newFixedThreadPool(10);



    Socket socket;

    public Future start()
    {

        String server = "localhost";



        try
        {
            // Connect to the server
             socket = new Socket( server, 5000 );

            QueryGenerator queryGenerator = new QueryGenerator(socket,user,other);

            Future future = CompletableFuture.runAsync(queryGenerator,pool);

            ReceiverHandler receiverHandler = new ReceiverHandler(socket,queryGenerator);

            CompletableFuture.runAsync(receiverHandler,pool);

            return future;



        }
        catch( Exception e )
        {
            e.printStackTrace();
            return null;
        }
    }


    public void stop()
    {
        try {
            System.out.println("Closing socket");
            socket.getInputStream().close();
            socket.getOutputStream().close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}