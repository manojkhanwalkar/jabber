package broker;

import util.JSONUtil;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConsumerConnection {

    ConsumerProxy consumerProxy;


    Socket socket;
    PrintWriter out;

    public ConsumerConnection(Socket socket)
    {
        this.socket = socket;
    }

    public void send(BrokerMessage brokerMessage)
    {

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
                    if (brokerMessage.action== BrokerMessage.Action.Subscribe)
                    {
                        consumerProxy.subscribe(brokerMessage);
                    }
                    else if (brokerMessage.action==BrokerMessage.Action.Ack)
                    {
                        consumerProxy.processAck(brokerMessage);
                    } else
                    {
                        System.out.println("Invalid action type " + brokerMessage);
                    }
                }
            }

        } catch (Exception e )
        {
            e.printStackTrace();
        }




    }


    public void receive(BrokerMessage brokerMessage)
    {

    }


}
