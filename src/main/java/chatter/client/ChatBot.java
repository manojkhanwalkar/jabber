package chatter.client;

public class ChatBot {


    public static void main(String args[]) throws Exception {
        // Using Scanner for Getting Input from User

        ChatBotHelper chatBotHelper = new ChatBotHelper();

        chatBotHelper.start();

        Runtime.getRuntime().addShutdownHook(new Thread(()->{


            chatBotHelper.stop();

        }));

        while(true)
        {
            Thread.sleep(1000);
        }


    }



}
