package decision.client;

import data.*;
import decision.data.DecisionRequest;
import decision.data.DecisionResponse;
import util.Connection;
import util.JSONUtil;
import util.JarUtils;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class EventSubmitter {

    public static void main(String[] args) {

        EventSubmitter submitter = new EventSubmitter();
        submitter.test();

    }




    public void test()
    {


    }


    private DecisionResponse submitJob(DecisionRequest request)
    {
        // send to scheduler a jar file and client name and get back a job id .

        Connection app = new Connection("https://localhost:8180/");

        String respStr =  app.sendSimple(JSONUtil.toJSON(request),"submit");

        if (respStr!=null) {

            DecisionResponse response = (DecisionResponse) JSONUtil.fromJSON(respStr, DecisionResponse.class);

            return response;
        }
        else {

            return null;
        }
    }



}
