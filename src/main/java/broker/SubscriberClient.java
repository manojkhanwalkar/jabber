package broker;

import util.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class SubscriberClient
{

    Subscriber subscriber;
    public SubscriberClient(Subscriber subscriber) {

        this.subscriber = subscriber;
    }

    static class ReceiverHandler implements Runnable
    {

            BufferedReader in;

            Subscriber subscriber;

        public ReceiverHandler(Socket socket, Subscriber subscriber)
        {
            this.subscriber = subscriber;

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
                    BrokerMessage brokerMessage = (BrokerMessage)JSONUtil.fromJSON(line,BrokerMessage.class);
                    if (brokerMessage.action== BrokerMessage.Action.Ack)
                    {
                        System.out.println( line );

                    }
                    else
                    {
                        subscriber.recv(brokerMessage);
                    }
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
             socket = new Socket( server, 6000 );

            out = new PrintStream( socket.getOutputStream() );

            ReceiverHandler receiverHandler = new ReceiverHandler(socket,subscriber);

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