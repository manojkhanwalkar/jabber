package broker;

public class ConsumerConnection {

    ConsumerProxy consumerProxy;

    public void send(BrokerMessage brokerMessage)
    {

        System.out.println(brokerMessage);


    }

    public void receive(BrokerMessage brokerMessage)
    {
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
