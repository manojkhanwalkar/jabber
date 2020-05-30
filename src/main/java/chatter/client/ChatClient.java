package chatter.client;

import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import java.util.function.Supplier;

public class ChatClient {


    public static void main(String args[]) throws Exception {
        // Using Scanner for Getting Input from User

      while(true)
      {

          Future<String> future = CompletableFuture.supplyAsync(new InputProcessor());

          String value = future.get();

          if (value.equalsIgnoreCase("exit"))
          {
              break;
          }
          else
          {
              // send the message .
              System.out.println(value);
          }
      }


    }



    static class InputProcessor implements Supplier<String> {

        @Override
        public String get() {
            Scanner in = new Scanner(System.in);

            System.out.println("Enter Message to send , Exit to end the session");

            String s = in.nextLine();

            return s;
        }



    }


}
