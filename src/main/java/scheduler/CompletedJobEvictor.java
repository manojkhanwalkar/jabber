package scheduler;

import data.StatusTuple;

import java.util.ArrayDeque;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;

public class CompletedJobEvictor {

    public static final int TimeOut = 10000;   // 10 secs after completion.

    static class TimedStatusTuple
    {
        long timeToEvict;
        StatusTuple statusTuple;
        String clientId;

        public TimedStatusTuple(long timeToEvict, StatusTuple statusTuple,String clientId) {
            this.timeToEvict = timeToEvict;
            this.statusTuple = statusTuple;
            this.clientId=clientId;
        }
    }


    static class CompletedJobEvictorTask implements Runnable
    {

        CompletedJobEvictor completedJobEvictor;
        public CompletedJobEvictorTask(CompletedJobEvictor completedJobEvictor)
        {
            this.completedJobEvictor = completedJobEvictor;
        }

        public void run()
        {
            System.out.println("Started completed job evictor thread");

            while(true)
            {
                long time = System.currentTimeMillis();
                long timetoEvict= time+1000;

                synchronized (completedJobEvictor) {
                    if (!completedJobEvictor.evictionQueue.isEmpty()) {


                        timetoEvict = completedJobEvictor.evictionQueue.peek().timeToEvict;
                        if (time >= timetoEvict) {
                            TimedStatusTuple tst = completedJobEvictor.evictionQueue.remove();
                            completedJobEvictor.jobCache.remove(tst.statusTuple, tst.clientId);

                            System.out.println("Evicted completed job " + tst.statusTuple);
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

    Queue<TimedStatusTuple> evictionQueue = new ArrayDeque<>();

    JobCache jobCache;

    public  CompletedJobEvictor(JobCache jobCache)
    {
        this.jobCache = jobCache;

        CompletableFuture.runAsync(new CompletedJobEvictorTask(this));
    }


    public synchronized void add(StatusTuple statusTuple, String clientId)
    {
        evictionQueue.add(new TimedStatusTuple(System.currentTimeMillis()+TimeOut,statusTuple,clientId));

    }





}
