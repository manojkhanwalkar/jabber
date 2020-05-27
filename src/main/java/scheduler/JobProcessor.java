package scheduler;

import data.*;
import util.Connection;
import util.JSONUtil;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.PriorityQueue;
import java.util.UUID;

import static data.Priority.High;
import static data.Priority.Low;
import static util.JarUtils.getJarFromBase64EncodedString;
import static util.JarUtils.loadClassFromJar;

public class JobProcessor implements Runnable, Comparable<JobProcessor>
{

    SubmitRequest submitRequest;
    String jobId;
    JobCache jobcache;
    JobManager jobManager;

    public JobProcessor(SubmitRequest submitRequest, String jobId, JobCache jobcache, JobManager jobManager) {
        this.submitRequest = submitRequest;
        this.jobId = jobId;
        this.jobcache = jobcache;
        this.jobManager = jobManager;
    }



     public int compareTo(JobProcessor jobProcessor)
    {
        Priority curr = submitRequest.getPriority();

        Priority next = jobProcessor.submitRequest.getPriority();

        switch(curr)
        {
            case High:
                return -1;

            case Med :
                if (next==Priority.Med || next==Priority.Low)
                    return -1;
                else
                    return 1;
            case Low :
                if (next==Priority.Low)
                    return -1;
                else
                    return 1;

                default:
                    return 0 ;
        }
    }


    private void process()
    {
        try {


            jobcache.update(submitRequest.getClient(), jobId, Status.Working);

            jobManager.workingJobTracker.add(submitRequest,jobId);



            WorkerRequest workerRequest = new WorkerRequest(submitRequest.getBase64Jar(),jobId,submitRequest.getJobClassName());

            submitJob(workerRequest);




        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //jobManager.complete();
        }



    }


    private void submitJob(WorkerRequest request)
    {
        // send to scheduler a jar file and client name and get back a job id .


        Connection app=WorkerLoadBalancer.getInstance().getNextWorker();

        while(app==null)
        {
            try {
                System.out.println("No worker found");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            app = WorkerLoadBalancer.getInstance().getNextWorker();
        }

        String respStr =  app.sendSimple(JSONUtil.toJSON(request),"work");

      /*  WorkerResponse response = (WorkerResponse)JSONUtil.fromJSON(respStr,WorkerResponse.class);

        return response;*/


    }


    public void run()
    {
            process();
    }


    /*

     private void process()
    {
        try {

            Thread.sleep(5000);

            String jarName = "/tmp/"+ UUID.randomUUID().toString()+"*.jar";
            getJarFromBase64EncodedString(jarName, submitRequest.getBase64Jar());

            String jobClassName = submitRequest.getJobClassName();
            Object obj = loadClassFromJar(jarName, jobClassName);
            Class c = obj.getClass();
            Method method = c.getMethod("compute");

            Result result = new Result();
            String res= (String)method.invoke(obj);

            result.setResult(res);
            jobcache.update(submitRequest.getClient(), jobId, Status.Complete,result);


        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jobManager.complete();
        }



    }

     */



}
