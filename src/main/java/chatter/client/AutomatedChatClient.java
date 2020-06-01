package chatter.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AutomatedChatClient {


    public static void main(String args[]) throws Exception {
        // Using Scanner for Getting Input from User

        List<Future<Void>> futures = new ArrayList<>();

        List<AutomatedSocketClient> clients = new ArrayList<>();

        for (int i=0;i<10;i++) {


                AutomatedSocketClient socketClient = new AutomatedSocketClient("Usr" + i);

                Future future = socketClient.start();

                futures.add(future);



        }


        System.out.println("Waiting");


        futures.stream().forEach(future-> {

            try {
                future.get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }

        });

   //Thread.sleep(5000);



        clients.stream().forEach(client->client.stop());


        System.out.println("Done Waiting");

        System.exit(0);


    }



}
