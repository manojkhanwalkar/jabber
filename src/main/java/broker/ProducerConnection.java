package broker;

import chatter.data.Packet;
import util.JSONUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;

public class ProducerConnection {

    ProducerProxy producerProxy;
    Socket socket;
    PrintWriter out;

    public ProducerConnection(Socket socket)
    {
        this.socket = socket;
    }


    public void send(BrokerMessage brokerMessage)
    {

       // System.out.println(brokerMessage);
        out.println(JSONUtil.toJSON(brokerMessage));
        out.flush();


    }


    public void receive()
    {

        try {

            // TOO - loop to receive messages from client
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            out = new PrintWriter(socket.getOutputStream());
            while (true) {

                //  Thread.sleep(100);
                String line = in.readLine();
                if (line != null && line.length() > 0) {
                    BrokerMessage brokerMessage = (BrokerMessage) JSONUtil.fromJSON(line, BrokerMessage.class);

                    producerProxy.processMessage(brokerMessage);


                }
            }

        } catch (Exception e )
        {
            e.printStackTrace();
        }




    }

}
