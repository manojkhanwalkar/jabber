package client;

import com.amazonaws.services.batch.model.SubmitJobRequest;
import data.*;
import org.apache.commons.codec.binary.Base64;
import util.Connection;
import util.JSONUtil;
import util.JarUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class JobSubmitter {

    public static void main(String[] args) {

        JobSubmitter submitter = new JobSubmitter();
        submitter.test();

    }




    public void test()
    {
        ArrayList<String> jobids = new ArrayList<>();


        for (int i=0;i<10;i++) {
            String jar = JarUtils.getBase64EncodedStringFromJar("/home/manoj/data/jabber/epi.jar");
            SubmitRequest request = new SubmitRequest(jar, "client1", "tmp.Test");
            SubmitResponse response = submitJob(request);

            System.out.println(response);
            jobids.add(response.getId());

        }

        for (int i=0;i<10;i++) {
            String jar = JarUtils.getBase64EncodedStringFromJar("/home/manoj/data/jabber/epi.jar");
            SubmitRequest request = new SubmitRequest(jar, "client1", "tmp.AnotherTest");
            SubmitResponse response = submitJob(request);

            System.out.println(response);
            jobids.add(response.getId());

        }



        Lock lock = new ReentrantLock();

        Condition responseRecd = lock.newCondition();

        CompletableFuture.runAsync(new StatusTracker(jobids, responseRecd,lock));

        try {
            lock.lock();
            responseRecd.await();
            lock.unlock();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    private SubmitResponse submitJob(SubmitRequest request)
    {
        // send to scheduler a jar file and client name and get back a job id .

        Connection app = new Connection("https://localhost:8480/");

        String respStr =  app.sendSimple(JSONUtil.toJSON(request),"submit");

        SubmitResponse response = (SubmitResponse)JSONUtil.fromJSON(respStr,SubmitResponse.class);

        return response;

        //return null;

    }




    static class StatusTracker implements Runnable
    {
        volatile ArrayList<String> jobids;
        Condition responseRecd;
        Lock lock;

        public StatusTracker( ArrayList<String> jobids, Condition responseRecd, Lock lock)
        {
            this.jobids = jobids;
            this.responseRecd= responseRecd;
            this.lock = lock;
        }

        private StatusResponse getStatus(StatusRequest statusRequest)
        {
            Connection app = new Connection("https://localhost:8480/");

            String respStr =  app.sendSimple(JSONUtil.toJSON(statusRequest),"status");

            StatusResponse response = (StatusResponse)JSONUtil.fromJSON(respStr,StatusResponse.class);

            return response;
        }

        private void processResponse(StatusResponse statusResponse)
        {
            statusResponse.getJobStatus().stream().forEach(st->{

                Result result = st.getResult();
                if (result!=null)
                {
                    jobids.remove(st.getId());
                }
            });

            if (!jobids.isEmpty())
            {
                Future<Void> future = CompletableFuture.runAsync(new StatusTracker(jobids,responseRecd,lock));

            }
            else
            {
                lock.lock();
                responseRecd.signalAll();
                lock.unlock();

            }

        }

        public void run()
        {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            StatusRequest statusRequest = new StatusRequest(jobids,"client1");
            StatusResponse statusResponse = getStatus(statusRequest);
            System.out.println(statusResponse);

            processResponse(statusResponse);

        }
    }

}
