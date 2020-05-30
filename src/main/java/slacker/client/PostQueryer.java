package slacker.client;

import data.*;
import slacker.data.QueryRequest;
import slacker.data.QueryResponse;
import util.Connection;
import util.JSONUtil;
import util.JarUtils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PostQueryer {

    public static void main(String[] args) {

        PostQueryer submitter = new PostQueryer();
        submitter.test();

    }




    public void test()
    {


        QueryRequest queryRequest = new QueryRequest();

        queryRequest.setChannelId("C1");

            QueryResponse response = submitJob(queryRequest);

            System.out.println(response);


    }


    private QueryResponse submitJob(QueryRequest request)
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
