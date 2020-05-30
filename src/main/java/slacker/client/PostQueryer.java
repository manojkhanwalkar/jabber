package slacker.client;

import data.*;
import slacker.data.QueryRequest;
import slacker.data.QueryResponse;
import util.Connection;
import util.JSONUtil;
import util.JarUtils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
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

            QueryTask task = new QueryTask();

            for (int i=0;i<10;i++)
            {
                CompletableFuture.runAsync(task);
            }



    }


    static class QueryTask implements Runnable
    {

        AtomicInteger channelCount= new AtomicInteger(0);

        public QueryTask()
        {

        }
        public void run()
        {
            QueryRequest queryRequest = new QueryRequest();

            queryRequest.setChannelId("C" + channelCount.getAndIncrement()%10);

            QueryResponse response = submitJob(queryRequest);

            System.out.println(response);

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
