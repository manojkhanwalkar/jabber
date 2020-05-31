package chatter.server;

public class ChatServer {


    public static void main( String[] args )
    {

        int port = 9000;

        System.out.println( "Start server on port: " + port );

        SimpleSocketServer server = new SimpleSocketServer( port );
        server.startServer();


        Runtime.getRuntime().addShutdownHook(new Thread(()->{
            server.stopServer();

            System.out.println("Server stopped");
        }));


    }
}
