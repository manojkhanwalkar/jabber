package broker;

import java.util.UUID;

public class ProducerProxy {

PubSubManager pubSubManager = PubSubManager.getInstance();

ProducerConnection producerConnection;

    public ProducerProxy(ProducerConnection producerConnection) {

        this.producerConnection = producerConnection;
    }

    int count=0;

    public void processMessage(BrokerMessage brokerMessage)
    {

        pubSubManager.process(brokerMessage);

        BrokerMessage ackBrokerMessage = new BrokerMessage();

        ackBrokerMessage.action= BrokerMessage.Action.Ack;
        ackBrokerMessage.seqNum=++count;
        ackBrokerMessage.id= UUID.randomUUID().toString();
        ackBrokerMessage.coorelationId=brokerMessage.id;

        producerConnection.send(ackBrokerMessage);


    }
}
