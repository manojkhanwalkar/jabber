package dynamodb2;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LRUEvictor<V> implements Evictor<V> {


    public static long timeToLive= 5*1000*1000*1000;
    Map<V,Long> evictionMap = new HashMap<>();

    public synchronized void put(V v)
    {
        long timeToEvict = System.nanoTime()+timeToLive;

        long currTimeToEvict = evictionMap.computeIfAbsent(v,val->timeToEvict);

        if (timeToEvict!=currTimeToEvict)
        {
            evictionMap.put(v,timeToEvict);
        }
    }

    public synchronized void remove(V v)
    {
        evictionMap.remove(v);
    }


    public synchronized List<V> getEvictionTargets()
    {
        long currTime = System.nanoTime();

        List<V> list= evictionMap.entrySet().stream().filter(e->e.getValue()<=currTime).map(e->e.getKey()).collect(Collectors.toList());

        list.stream().forEach(v->remove(v));

        return list;
    }


}
