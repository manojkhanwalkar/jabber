package chatter.server;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ChatServer {


   /* public static void main( String[] args )
    {

        int port = 9000;

        System.out.println( "Start server on port: " + port );

        SimpleSocketServer server = new SimpleSocketServer( port );
        server.startServer();


        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            server.stopServer();

            System.out.println("Server stopped");
        }));


    }*/


    public static void main(String[] args) throws Exception {

        NIO2SimpleSocketServer server = new NIO2SimpleSocketServer();

        server.start();



        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            server.stop();

            System.out.println("Server stopped");
        }));


        while(true)
        {
            Thread.sleep(1000);
        }

    }
}
