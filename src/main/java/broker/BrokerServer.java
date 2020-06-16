package broker;

public class BrokerServer {




    public static void main(String[] args) throws Exception {

        PublisherSocketServer publisherSocketServer = new PublisherSocketServer(5000);

        publisherSocketServer.startServer();

        SubscriberSocketServer subscriberSocketServer = new SubscriberSocketServer(6000);

        subscriberSocketServer.startServer();



        while (true)
        {
            Thread.sleep(1000);
        }

    }


}
