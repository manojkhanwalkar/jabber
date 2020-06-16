package broker;

import chatter.data.Packet;
import util.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;

public class PublisherClient
{
    public PublisherClient() {

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



    Socket socket;

    PrintStream out;

    public void send(BrokerMessage brokerMessage)
    {

        String str = JSONUtil.toJSON(brokerMessage);
        System.out.println(str);
        out.println(str);
        out.flush();
    }


    public void start()
    {

        String server = "localhost";



        try
        {
            // Connect to the server
             socket = new Socket( server, 5000 );

            out = new PrintStream( socket.getOutputStream() );

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