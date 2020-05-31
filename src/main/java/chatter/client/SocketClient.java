package chatter.client;

import chatter.data.Packet;
import util.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;

public class SocketClient
{
    public SocketClient() {

    }

    static class ReceiverHandler implements Runnable
    {

            BufferedReader in;

        public ReceiverHandler(Socket socket)
        {
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
                    e.printStackTrace();
                }
                if( line != null )
                {
                    System.out.println( line );
                   // line = in.readLine();
                }
            }
        }
    }


    static class ConsoleHandler implements Runnable
    {

        PrintStream out;

        String user; String other;

        public ConsoleHandler(Socket socket)
        {
            // Create input and output streams to read from and write to the server
            try {
                out = new PrintStream( socket.getOutputStream() );
            } catch (IOException e) {
                e.printStackTrace();
            }

            user = System.getProperty("user");
            other = System.getProperty("other");

            out.println(user);
            out.flush();



        }

        public void run()
        {
            Scanner in = new Scanner(System.in);
            while(true)
            {

                System.out.println("Enter Message to send , Exit to end the session");

                String value = in.nextLine();

                if (value.equalsIgnoreCase("exit"))
                {
                    break;
                }
                else
                {

                    Packet packet = new Packet();
                    packet.setFrom(user);
                    packet.setTo(other);
                    packet.setMessage(value);

                    out.println(JSONUtil.toJSON(packet));

                    out.flush();
                    // send the message .
                   // System.out.println(value);


                }
            }
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

            ConsoleHandler consoleHandler = new ConsoleHandler(socket);

            CompletableFuture.runAsync(consoleHandler);

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