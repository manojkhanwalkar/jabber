package vfs;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CurrentCount {

    static class Counter
    {
        int count=0;

    }

    LinkedList<Counter> minuteCount;

    int prevCount=0;


    public CurrentCount()
    {
        minuteCount = new LinkedList<>();

        for (int i=0;i<5;i++)
        {
            minuteCount.add(new Counter());
        }

        CurrentCountRecycler recycler = new CurrentCountRecycler(this);
        service.scheduleAtFixedRate(recycler,1,1, TimeUnit.MINUTES);
    }

    ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);


    public synchronized void increment()
    {
        minuteCount.getLast().count++;
    }

    public synchronized int getDayCount()
    {
        int totCount=prevCount + getLast5MinCount();


        return totCount;
    }


    public synchronized int getLast5MinCount()
    {
        int totCount=0;

        Iterator<Counter> iterator = minuteCount.iterator();
        while(iterator.hasNext())
        {
            Counter counter = iterator.next();
            totCount+=counter.count;
        }

        return totCount;
    }


    static class CurrentCountRecycler implements Runnable
    {

        final CurrentCount currentCount;

        public CurrentCountRecycler(CurrentCount currentCount) {
            this.currentCount = currentCount;
        }

        @Override
        public void run() {

            synchronized (currentCount)
            {

                int count = currentCount.minuteCount.removeFirst().count;
                currentCount.prevCount += count;
                currentCount.minuteCount.add(new Counter());
            }

        }
    }


    public static void main(String[] args) throws Exception  {

        CurrentCount currentCount = new CurrentCount();
        for (int i=0;i<100;i++)
        {
            currentCount.increment();

            System.out.println(currentCount.getDayCount()  + "  " + currentCount.getLast5MinCount());

            Thread.sleep(30000);
        }




    }

}
