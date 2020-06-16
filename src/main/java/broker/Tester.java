package broker;

import software.amazon.awssdk.services.rekognition.model.Sunglasses;

public class Tester {

    public static void main(String[] args) throws Exception {

        Publisher publisher = new Publisher();


        Subscriber subscriber = new Subscriber();

        subscriber.subscribe("topic1", (bm)->{ System.out.println(bm); });
        subscriber.subscribe("topic2", (bm)->{ System.out.println(bm); });

        Thread.sleep(1000);


        for (int i=0;i<100;i++) {

            publisher.publish("topic1", "Hello World");
            publisher.publish("topic2", "Another Hello World");

            Thread.sleep(1000);

        }




        Thread.sleep(10000);
    }






}
