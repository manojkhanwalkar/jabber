package chatter.client;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class ChatClient {


    public static void main(String args[]) throws Exception {
        // Using Scanner for Getting Input from User

        SocketClient socketClient = new SocketClient();

        socketClient.start();

        Runtime.getRuntime().addShutdownHook(new Thread(()->{


            socketClient.stop();

        }));

        while(true)
        {
            Thread.sleep(1000);
        }


    }



}
