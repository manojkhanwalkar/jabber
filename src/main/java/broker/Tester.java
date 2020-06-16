package broker;

import software.amazon.awssdk.services.rekognition.model.Sunglasses;

public class Tester {

    public static void main(String[] args) throws Exception {

        Publisher publisher = new Publisher();


        publisher.publish("topic1", "Hello World");
        publisher.publish("topic2", "Another Hello World");







        Thread.sleep(10000);
    }






}
