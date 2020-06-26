package vfs;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.util.HashMap;
import java.util.Map;

import static vfs.HistoryFileManager.*;

public class VelocityStatsManager {

    String attribute;  // ccv or ip

    Map<String,HistoricalCount> historicalCountMap = new HashMap<>();
    Map<String,CurrentCount> currentCountMap = new HashMap<>();

    public VelocityStatsManager(String attribute) {
        this.attribute = attribute;
        init();
    }



    public HistoricalCount getHistoricalStats(String key)
    {
        return historicalCountMap.get(key);
    }

    public CurrentCount getCurrentStats(String key)
    {
        return currentCountMap.get(key);
    }




    public void init()
    {

        CCVIPValues.getAll(attribute).forEach(str->{

            historicalCountMap.put(str, new HistoricalCount());

            currentCountMap.put(str, new CurrentCount());
        });

        populateHistoricalCount(30,"setLast30Days");
        populateHistoricalCount(1,"setLastDay");
        populateHistoricalCount(7,"setLast7Days");


    }


    private void populateHistoricalCount(int n, String methodName)
    {

        try {
            MethodHandles.Lookup publicLookup = MethodHandles.publicLookup();

            MethodType mt = MethodType.methodType(void.class, int.class);
            MethodHandle methodHandle = publicLookup.findVirtual(HistoricalCount.class, methodName, mt);


            Map<String,Integer> counts = velocityCount(lastNDays(n,attribute));

            counts.entrySet().stream().forEach(entry->{

                HistoricalCount historicalCount = historicalCountMap.get(entry.getKey());
                //historicalCount.setLast30Days(entry.getValue());

                try {
                    methodHandle.invoke(historicalCount,entry.getValue());
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }

            });
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

    }

    @Override
    public String toString() {
        return "VelocityStatsManager{" +
                "attribute='" + attribute + '\'' +
                ", historicalCountMap=" + historicalCountMap +
                '}';
    }
}
