package broker;

import chatter.data.Packet;
import chatter.server.Sender;
import chatter.server.UserManager;
import util.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class PublisherSocketServer extends Thread
{
    private ServerSocket serverSocket;
    private int port;
    private boolean running = false;




    public PublisherSocketServer( int port )
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
                System.out.println( "Listening for a publisher connection" );

                // Call accept() to receive the next connection
                Socket socket = serverSocket.accept();


                ProducerConnection producerConnection = new ProducerConnection(socket);

                ProducerProxy producerProxy = new ProducerProxy(producerConnection);

                producerConnection.producerProxy=producerProxy;

                ExecutorService executorService = Executors.newFixedThreadPool(1);

                executorService.submit(()-> {

                    producerConnection.receive();

                });


            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


}
