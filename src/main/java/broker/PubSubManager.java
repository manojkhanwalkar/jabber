package broker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class PubSubManager {

    static class Holder
    {
        static PubSubManager manager = new PubSubManager();


    }

    ConcurrentMap<String,List<ConsumerProxy>> topicConsumers  = new ConcurrentHashMap<>();

    public static PubSubManager getInstance()
    {
        return Holder.manager;
    }


public void subscribe(String topic, ConsumerProxy proxy)
{
    List<ConsumerProxy> consumers = topicConsumers.computeIfAbsent(topic, t->new CopyOnWriteArrayList<>());

    consumers.add(proxy);
}


public void process(BrokerMessage brokerMessage)
{

    String topic = brokerMessage.getTopic();

    List<ConsumerProxy> consumers = topicConsumers.get(topic);

    if (consumers!=null)
        consumers.stream().forEach(consumer-> consumer.call(brokerMessage));

}


}
