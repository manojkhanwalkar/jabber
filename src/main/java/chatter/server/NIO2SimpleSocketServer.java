package chatter.server;




import chatter.data.Packet;
import util.JSONUtil;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NIO2SimpleSocketServer
{
     AsynchronousServerSocketChannel listener;


    UserManager userManager = new UserManager();


    public void start()
    {
        try
        {
            // Create an AsynchronousServerSocketChannel that will listen on port 5000

            listener = AsynchronousServerSocketChannel.open().bind(new InetSocketAddress(5000));

            // Listen for a new request
            listener.accept( null, new CompletionHandler<AsynchronousSocketChannel,Void>() {

                @Override
                public void completed(AsynchronousSocketChannel channel, Void att)
                {
                    // Accept the next connection
                    listener.accept( null, this );

                    // Greet the client
                  //  ch.write( ByteBuffer.wrap( "Hello, I am Echo Server 2020, let's have an engaging conversation!\n".getBytes() ) );

                    // read user name

                    {
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1096);
                        try {
                            // Read the first line
                            int bytesRead = channel.read(byteBuffer).get(20, TimeUnit.SECONDS);
                            while (bytesRead != -1) {
                                System.out.println("bytes read: " + bytesRead);

                                // Make sure that we have data to read
                                if (byteBuffer.position() > 2) {
                                    // Make the buffer ready to read
                                    byteBuffer.flip();

                                    // Convert the buffer into a line
                                    byte[] lineBytes = new byte[bytesRead];
                                    byteBuffer.get(lineBytes, 0, bytesRead);
                                    String line = new String(lineBytes);

                                    // Debug
                                    System.out.println("User connected: " + line);
                                    Sender sender = new Sender(channel);

                                    line = line.replaceAll("[\\n\\t ]", "");
                                    userManager.add(line,sender);

                                    break;

                                }
                            }
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                    }





                    // Allocate a byte buffer (4K) to read from the client
                    ByteBuffer byteBuffer = ByteBuffer.allocate( 4096 );
                    try
                    {
                        // Read the first line
                        int bytesRead = channel.read( byteBuffer ).get( );

                        boolean running = true;
                        while( bytesRead != -1 && running )
                        {
                            System.out.println( "bytes read: " + bytesRead );

                            // Make sure that we have data to read
                            if( byteBuffer.position() > 2 )
                            {
                                // Make the buffer ready to read
                                byteBuffer.flip();

                                // Convert the buffer into a line
                                byte[] lineBytes = new byte[ bytesRead ];
                                byteBuffer.get( lineBytes, 0, bytesRead );
                                String line = new String( lineBytes );

                                Packet packet = (Packet) JSONUtil.fromJSON(line,Packet.class);

                                Sender sender = userManager.get(packet.getTo());

                                if (sender!=null)
                                {
                                    sender.niosend(packet);
                                }
                                else
                                {
                                    System.out.println("Sender not found for packet " + packet);
                                }

                                // Debug
                               // System.out.println( "Message: " + line );

                                // Echo back to the caller
                               // ch.write( ByteBuffer.wrap( line.getBytes() ) );

                                // Make the buffer ready to write
                                byteBuffer.clear();

                                // Read the next line
                                bytesRead = channel.read( byteBuffer ).get();
                            }
                            else
                            {
                                // An empty line signifies the end of the conversation in our protocol
                                running = false;
                            }
                        }
                    }
                    catch (InterruptedException e)
                    {
                        e.printStackTrace();
                    }
                    catch (ExecutionException e)
                    {
                        e.printStackTrace();
                    }


                    System.out.println( "End of conversation" );
                    try
                    {
                        // Close the connection if we need to
                        if( channel.isOpen() )
                        {
                            channel.close();
                        }
                    }
                    catch (IOException e1)
                    {
                        e1.printStackTrace();
                    }
                }

                @Override
                public void failed(Throwable exc, Void att) {
                    ///...
                }
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }


    public void stop()
    {

    }


}

