package scheduler;

import data.SubmitRequest;

import java.util.PriorityQueue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Semaphore;

public class JobManager {

   Semaphore noofjobs = new Semaphore(2);

   PriorityQueue<JobProcessor> queue = new PriorityQueue<>();

    WorkingJobTracker workingJobTracker ;

    JobCache jobcache;

    public JobManager(JobCache jobcache)
    {
        this.jobcache = jobcache;

        workingJobTracker = new WorkingJobTracker(this);
    }


    public synchronized void submit(SubmitRequest submitRequest, String jobId)
    {
        JobProcessor jobProcessor = new JobProcessor(submitRequest,jobId,jobcache,this);

        queue.add(jobProcessor);

        if (noofjobs.tryAcquire())
        {
            jobProcessor = queue.remove();

          //  System.out.println(jobProcessor.submitRequest.getPriority());
            CompletableFuture.runAsync(jobProcessor);
        }


    }

    public synchronized void complete()
    {
        noofjobs.release();
        if (!queue.isEmpty() && noofjobs.tryAcquire())
        {
            JobProcessor jobProcessor = queue.remove();
         //   System.out.println(jobProcessor.submitRequest.getPriority());
            CompletableFuture.runAsync(jobProcessor);
        }

    }




    //  CompletableFuture.runAsync(new JobProcessor());
}
