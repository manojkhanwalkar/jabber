package broker;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class ConsumerProxy {

    BlockingQueue<BrokerMessage> sendingQueue = new LinkedBlockingQueue<>();

    ConsumerConnection consumerConnection ;

    PubSubManager pubSubManager = PubSubManager.getInstance();

    ExecutorService service = Executors.newFixedThreadPool(1);

    public ConsumerProxy(ConsumerConnection consumerConnection)
    {
        this.consumerConnection = consumerConnection;



        service.submit(new ConsumerSender(sendingQueue,consumerConnection));
    }

    public void call(BrokerMessage brokerMessage)
    {

        try {
            sendingQueue.put(brokerMessage);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public void subscribe(BrokerMessage brokerMessage)
    {
        pubSubManager.subscribe(brokerMessage.getTopic(),this);
    }

    public void processAck(BrokerMessage brokerMessage) {

        //TODO - implement ack processing, also the subscriber needs to send an ack after receiving the message.
    }


    static class ConsumerSender implements Runnable
    {
        BlockingQueue<BrokerMessage> sendingQueue ;
        ConsumerConnection consumerConnection;
        public ConsumerSender(BlockingQueue<BrokerMessage> sendingQueue, ConsumerConnection consumerConnection) {
            this.sendingQueue = sendingQueue;
            this.consumerConnection = consumerConnection;
        }

        public void run()
        {

            int count=0;

            while(true)
            {
                try {
                    BrokerMessage brokerMessage = sendingQueue.take();
                    BrokerMessage msgToFroward = new BrokerMessage();
                    msgToFroward.action= BrokerMessage.Action.Consume;
                    msgToFroward.id= UUID.randomUUID().toString();
                    msgToFroward.payload=brokerMessage.payload;
                    msgToFroward.seqNum=++count;
                    msgToFroward.topic=brokerMessage.getTopic();

                    consumerConnection.send(msgToFroward);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }



            }


        }


    }


}
