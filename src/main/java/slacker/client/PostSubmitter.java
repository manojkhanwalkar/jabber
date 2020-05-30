package slacker.client;

import data.*;
import slacker.data.PostRequest;
import util.Connection;
import util.JSONUtil;
import util.JarUtils;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PostSubmitter {

    public static void main(String[] args) throws Exception {

        PostSubmitter submitter = new PostSubmitter();

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);
        scheduledThreadPool.scheduleAtFixedRate(()-> submitter.test(),10,10, TimeUnit.SECONDS);

      //  submitter.test();


    }




    public void test()
    {



        for (int i=0;i<100;i++) {
            CompletableFuture.runAsync(new SubmitTask());
        }

    }


    static class SubmitTask implements Runnable
    {

        Random random = new Random();
        public void run()
        {
            PostRequest postRequest = new PostRequest();
            postRequest.setChannelId("C" + random.nextInt(10));
            postRequest.setUserId("U" + random.nextInt(10));
            postRequest.setPost("P" + random.nextInt(Integer.MAX_VALUE));

            submitPost(postRequest);

        }
    }


    private static void submitPost(PostRequest request)
    {
        // send to scheduler a jar file and client name and get back a job id .

        Connection app = new Connection("https://localhost:8280/");

        app.sendSimple(JSONUtil.toJSON(request),"post");

    }




}
