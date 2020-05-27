package scheduler;

import data.Status;
import data.SubmitRequest;
import data.WorkerResponse;

import java.util.HashMap;
import java.util.Map;
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

    Map<String,JobProcessor> responsesPending = new HashMap<>();

    public synchronized void submit(SubmitRequest submitRequest, String jobId)
    {
        JobProcessor jobProcessor = new JobProcessor(submitRequest,jobId,jobcache,this);

        queue.add(jobProcessor);

        if (noofjobs.tryAcquire())
        {
            jobProcessor = queue.remove();

          //  System.out.println(jobProcessor.submitRequest.getPriority());
            CompletableFuture.runAsync(jobProcessor);

            responsesPending.put(jobId,jobProcessor);
        }


    }


    public synchronized void processResponse(WorkerResponse workerResponse)
    {
        String jobId = workerResponse.getJobStatus().getId();

        JobProcessor jobProcessor = responsesPending.remove(jobId);

        SubmitRequest submitRequest = jobProcessor.submitRequest;

        jobcache.update(submitRequest.getClient(), jobId, Status.Complete,workerResponse.getJobStatus().getResult());

        workingJobTracker.remove(jobId);



        complete();
    }



    public synchronized void complete()
    {
        noofjobs.release();
        if (!queue.isEmpty() && noofjobs.tryAcquire())
        {
            JobProcessor jobProcessor = queue.remove();
         //   System.out.println(jobProcessor.submitRequest.getPriority());
            responsesPending.put(jobProcessor.jobId,jobProcessor);

            CompletableFuture.runAsync(jobProcessor);
        }

    }




    //  CompletableFuture.runAsync(new JobProcessor());
}
