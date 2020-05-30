package slacker.client;

import data.*;
import slacker.data.PostRequest;
import util.Connection;
import util.JSONUtil;
import util.JarUtils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PostSubmitter {

    public static void main(String[] args) {

        PostSubmitter submitter = new PostSubmitter();
        submitter.test();

    }




    public void test()
    {

        PostRequest postRequest = new PostRequest();
        postRequest.setChannelId("C1");
        postRequest.setUserId("U1");
        postRequest.setPost("P1");

        submitPost(postRequest);


    }


    private void submitPost(PostRequest request)
    {
        // send to scheduler a jar file and client name and get back a job id .

        Connection app = new Connection("https://localhost:8280/");

        app.sendSimple(JSONUtil.toJSON(request),"post");

    }




}
