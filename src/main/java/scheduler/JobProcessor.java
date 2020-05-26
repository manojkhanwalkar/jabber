package scheduler;

import data.Priority;
import data.Result;
import data.Status;
import data.SubmitRequest;

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


/*    Priority priority;

    public JobProcessor(Priority priority)
    {
        this.priority = priority;
    }*/

    @Override
 /*   public int compareTo(JobProcessor jobProcessor) {

        if (this.priority== High)
        {
            return -1;
        }

        if (jobProcessor.priority==High)
        {
            return 1;
        }

        return 0;



    }*/

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

 /*    public int compareTo(JobProcessor jobProcessor)
    {
        Priority curr = this.priority;

        Priority next = jobProcessor.priority;

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
    }*/

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


    public void run()
    {
            process();
    }


   /* public static void main(String[] args) {

        PriorityQueue<JobProcessor> queue = new PriorityQueue<>();

        for (int i=0;i<10;i++)
        {
            queue.add(new JobProcessor(Low));
        }

        System.out.println(queue.remove().priority);

        System.out.println(queue.remove().priority);


        for (int i=0;i<10;i++)
        {
            queue.add(new JobProcessor(High));
        }



        while(!queue.isEmpty())
        {
            System.out.println(queue.remove().priority);
        }



    }*/


}
