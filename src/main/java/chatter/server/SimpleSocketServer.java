package chatter.server;

import chatter.data.Packet;
import util.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;

public class SimpleSocketServer extends Thread
{
    private ServerSocket serverSocket;
    private int port;
    private boolean running = false;


    UserManager userManager = new UserManager();

    public SimpleSocketServer( int port )
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
                System.out.println( "Listening for a connection" );

                // Call accept() to receive the next connection
                Socket socket = serverSocket.accept();

                // Pass the socket to the RequestHandler thread for processing
                RequestHandler requestHandler = new RequestHandler( socket, userManager );

                CompletableFuture.runAsync(requestHandler);

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }


}

class RequestHandler implements Runnable
{
    private Socket socket;
    UserManager userManager;
    RequestHandler(Socket socket, UserManager userManager)
    {
        this.socket = socket;

        this.userManager = userManager;
    }

    @Override
    public void run()
    {
        try
        {

            // Get input and output streams
            BufferedReader in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );

            Sender sender = new Sender(socket);

            while(true) {
                // Echo lines back to the client until the client closes the connection or we receive an empty line
                String line = in.readLine();
                if (line != null && line.length() > 0) {
                    System.out.println("User connected " + line);
                    userManager.add(line,sender);
                    break;
                }
            }

            while(true) {
                String line = in.readLine();
                if (line != null && line.length() > 0) {
                    Packet packet = (Packet)JSONUtil.fromJSON(line,Packet.class);
                    sender = userManager.get(packet.getTo());
                    if (sender!=null)
                    {
                        sender.send(packet);   // TODO - make sending async
                    }
                    else
                    {
                        System.out.println("sender not connected " + sender);
                    }

                }
            }

        } catch( Exception e )
        {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println( "Connection closed" );

        }
    }
}