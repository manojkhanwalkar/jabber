package scheduler;

import data.StatusTuple;
import data.SubmitRequest;

import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

public class WorkingJobTracker {

    public static final int TimeOut = 20000;   // 10 secs after submission , if no result then resubmit the job.

    static class TimedStatusTuple
    {
        long timetoReprocess;
        SubmitRequest submitRequest;
        String jobId;

        public TimedStatusTuple(String jobId)
        {
            this.jobId=jobId;
        }

        public TimedStatusTuple(long timetoReprocess, SubmitRequest submitRequest, String jobId) {
            this.timetoReprocess = timetoReprocess;
            this.submitRequest = submitRequest;
            this.jobId = jobId;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TimedStatusTuple that = (TimedStatusTuple) o;
            return jobId.equals(that.jobId);
        }

        @Override
        public int hashCode() {
            return Objects.hash(jobId);
        }
    }


    static class WorkingJobTrackerTask implements Runnable
    {

        WorkingJobTracker completedJobEvictor;
        public WorkingJobTrackerTask(WorkingJobTracker completedJobEvictor)
        {
            this.completedJobEvictor = completedJobEvictor;
        }

        public void run()
        {
            System.out.println("Started Working job resubmitter thread");

            while(true)
            {
                long time = System.currentTimeMillis();
                long timetoEvict= time+1000;

                synchronized (completedJobEvictor) {
                    if (!completedJobEvictor.resubmissionQueue.isEmpty()) {


                        timetoEvict = completedJobEvictor.resubmissionQueue.peek().timetoReprocess;
                        if (time >= timetoEvict) {
                            TimedStatusTuple tst = completedJobEvictor.resubmissionQueue.remove();


                            completedJobEvictor.jobManager.submit(tst.submitRequest,tst.jobId);

                            System.out.println("Resubmiited job " + tst);
                            continue;
                        }

                    }
                }



                {
                    try {
                        Thread.sleep(timetoEvict-time);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

    }

    Queue<TimedStatusTuple> resubmissionQueue = new ArrayDeque<>();

   JobManager jobManager;

    public WorkingJobTracker(JobManager jobManager)
    {
        this.jobManager = jobManager;

        CompletableFuture.runAsync(new WorkingJobTrackerTask(this));
    }


    public synchronized void add(SubmitRequest submitRequest, String jobId)
    {
        resubmissionQueue.add(new TimedStatusTuple(System.currentTimeMillis()+TimeOut,submitRequest,jobId));

    }


    public synchronized void remove( String jobId)
    {
        resubmissionQueue.remove(new TimedStatusTuple(jobId));
    }





}
