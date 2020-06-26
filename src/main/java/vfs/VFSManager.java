package vfs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class VFSManager {

    Map<String,VelocityStatsManager> attrVelocityMap = new HashMap<>();

    String[] attributes = { "ccv" , "ip"};

    transient ScheduledExecutorService service = new ScheduledThreadPoolExecutor(1);

    public void init() {

        CCVIPValues.init(attributes);

        Arrays.stream(attributes).forEach(attr->{

            attrVelocityMap.put(attr, new VelocityStatsManager(attr));
        });

        CurrentCountRecycler recycler = new CurrentCountRecycler(this);
        service.scheduleAtFixedRate(recycler,15,15, TimeUnit.SECONDS);
    }


    static class CurrentCountRecycler implements Runnable
    {

        final VFSManager vfsManager;

        public CurrentCountRecycler(VFSManager vfsManager) {
            this.vfsManager = vfsManager;
        }

        @Override
        public void run() {

            Map<String,VelocityStatsManager> attrVelocityMap = vfsManager.attrVelocityMap;

            attrVelocityMap.values().stream().forEach(vsm->{

                vsm.currentCountMap.values().stream().forEach(currentCount -> {
                    synchronized (currentCount)
                    {

                        int count = currentCount.minuteCount.removeFirst().count;
                        currentCount.prevCount += count;
                        currentCount.minuteCount.add(new CurrentCount.Counter());
                    }

                });

            });



        }
    }



    public VelocityStatsList process(TransactionData transactionData)
    {
        //TODO - use method handles to figure out the attr values

        VelocityStatsList velocityStatsList = new VelocityStatsList();
        String ccvValue = transactionData.getCcv();
        if (ccvValue!=null)
        {
            VelocityStatsManager velocityStatsManager = attrVelocityMap.get("ccv");
            if (velocityStatsManager!=null)
            {
                VelocityStats velocityStats = velocityStatsManager.add(ccvValue);
                velocityStatsList.addStats("ccv",velocityStats);
            }
        }

        String ipValue = transactionData.getIp();
        if (ipValue!=null)
        {
            VelocityStatsManager velocityStatsManager = attrVelocityMap.get("ip");
            if (velocityStatsManager!=null)
            {
                VelocityStats velocityStats = velocityStatsManager.add(ccvValue);
                velocityStatsList.addStats("ip",velocityStats);
            }
        }


        return velocityStatsList;

    }

    public static void main(String[] args) {

        VFSManager vfsManager = new VFSManager();

        vfsManager.init();

      //  System.out.println(vfsManager.attrVelocityMap);

    }
}
