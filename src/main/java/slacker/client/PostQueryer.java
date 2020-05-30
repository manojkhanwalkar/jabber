package slacker.client;

import data.*;
import slacker.data.QueryRequest;
import slacker.data.QueryResponse;
import util.Connection;
import util.JSONUtil;
import util.JarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PostQueryer {

    public static void main(String[] args) throws Exception  {

        PostQueryer submitter = new PostQueryer();
        submitter.test();

        Thread.sleep(10000);

    }




    public void test()
    {

        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(5);

            QueryTask task = new QueryTask(this);

            scheduledThreadPool.scheduleWithFixedDelay(task,5,5, TimeUnit.SECONDS);




    }

    volatile String[] nextMessageIds = new String[10];


    static class QueryTask implements Runnable
    {


        PostQueryer parent;
        public QueryTask(PostQueryer parent)
        {
            this.parent = parent;

        }
        public void run()
        {

            try {
                for (int i=0;i<10;i++) {
                    QueryRequest queryRequest = new QueryRequest();

                    queryRequest.setChannelId("C" + i);
                    queryRequest.setLastMessageId(parent.nextMessageIds[i]);

                    QueryResponse response = submitJob(queryRequest);

                    System.out.println(response);

                    parent.nextMessageIds[i]= response.getLastMessageId();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    private static QueryResponse submitJob(QueryRequest request)
    {
        // send to scheduler a jar file and client name and get back a job id .

        Connection app = new Connection("https://localhost:8280/");

        String respStr =  app.sendSimple(JSONUtil.toJSON(request),"query");

        if (respStr!=null) {

            QueryResponse response = (QueryResponse) JSONUtil.fromJSON(respStr, QueryResponse.class);

            return response;
        }
        else {

            return null;
        }
    }




}
