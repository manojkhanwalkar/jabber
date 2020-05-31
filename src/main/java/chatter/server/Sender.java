package chatter.server;

import chatter.data.Packet;
import util.JSONUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class Sender {

   PrintWriter out;



    public Sender(Socket socket) {

        try {
            out = new PrintWriter( socket.getOutputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    AsynchronousSocketChannel channel;

    public Sender(AsynchronousSocketChannel channel) {

        this.channel = channel;
    }


    public synchronized void send(Packet packet)
    {
        out.println(JSONUtil.toJSON(packet));
        out.flush();
    }

    public synchronized  void niosend(Packet packet)
    {
        String str = JSONUtil.toJSON(packet) + "\n";

        channel.write( ByteBuffer.wrap( str.getBytes() ) );

        System.out.println("Completed writing");



    }








}
