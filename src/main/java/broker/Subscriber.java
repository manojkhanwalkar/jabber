package broker;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class Subscriber {

    SubscriberClient subscriberClient ;

    public Subscriber()
    {
        subscriberClient= new SubscriberClient(this);
        subscriberClient.start();
    }


    ConcurrentMap<String,Listener> listeners = new ConcurrentHashMap<>();

    public void subscribe(String topicName ,Listener listener)
    {

        listeners.put(topicName,listener);

        BrokerMessage subsribeMessage = new BrokerMessage();
        subsribeMessage.action= BrokerMessage.Action.Subscribe;
        subsribeMessage.id= UUID.randomUUID().toString();
        subsribeMessage.topic=topicName;

        subscriberClient.send(subsribeMessage);
    }

    public void recv(BrokerMessage brokerMessage)
    {

        Listener listener = listeners.get(brokerMessage.getTopic());

        CompletableFuture.runAsync(()->listener.onMesage(brokerMessage));


    }

}
