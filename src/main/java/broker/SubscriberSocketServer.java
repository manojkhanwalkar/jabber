package broker;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SubscriberSocketServer extends Thread
{
    private ServerSocket serverSocket;
    private int port;
    private boolean running = false;




    public SubscriberSocketServer(int port )
    {
        this.port = port;
    }

    public void startServer()
    {
        try
        {
            serverSocket = new ServerSocket( port );
            this.start();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void stopServer()
    {
        running = false;
        this.interrupt();
    }



    @Override
    public void run()
    {
        running = true;
        while( running )
        {
            try
            {
                System.out.println( "Listening for a subscriber connection" );

                // Call accept() to receive the next connection
                Socket socket = serverSocket.accept();


                ConsumerConnection consumerConnection = new ConsumerConnection(socket);

                ConsumerProxy consumerProxy = new ConsumerProxy(consumerConnection);

                consumerConnection.consumerProxy=consumerProxy;

                ExecutorService executorService = Executors.newFixedThreadPool(1);

                executorService.submit(()-> {

                    consumerConnection.receive();

                });


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


}
