package vfs;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class VFSManager {


    //TODO - create a map of attribute and VelocityStatsManager

    Map<String,VelocityStatsManager> attrVelocityMap = new HashMap<>();

    String[] attributes = { "ccv" , "ip"};


    public void init() {

        CCVIPValues.init(attributes);

        Arrays.stream(attributes).forEach(attr->{

            attrVelocityMap.put(attr, new VelocityStatsManager(attr));
        });

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
