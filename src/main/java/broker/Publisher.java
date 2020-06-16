package broker;

import javax.annotation.processing.Processor;
import java.util.UUID;

public class Publisher {

    int count=1;

    PublisherClient publisherClient = new PublisherClient();

    public Publisher()
    {
        publisherClient.start();
    }

    public void publish(String topicName , String payload)
    {

        BrokerMessage brokerMessage = new BrokerMessage();

        brokerMessage.action= BrokerMessage.Action.Publish;
        brokerMessage.id= UUID.randomUUID().toString();
        brokerMessage.seqNum=count++;
        brokerMessage.topic=topicName;
        brokerMessage.payload=payload;

        publisherClient.send(brokerMessage);
    }

    //TODO - socket client to be added here , for now just use the proxy directly
}
