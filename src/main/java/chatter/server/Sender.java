package chatter.server;

import chatter.data.Packet;
import util.JSONUtil;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Sender {

    PrintWriter out;

    public Sender(Socket socket) {

        try {
            out = new PrintWriter( socket.getOutputStream() );
        } catch (IOException e) {
            e.printStackTrace();
        }

       /* // Write out our header to the client
        out.println( "Echo Server 1.0" );
        out.flush();*/
    }


    public synchronized void send(Packet packet)
    {
        out.println(JSONUtil.toJSON(packet));
        out.flush();
    }
}
