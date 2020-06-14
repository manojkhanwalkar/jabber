package dynamodb2;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class LFUEvictor<V> implements Evictor<V> {



    Map<V,Integer> evictionMap = new HashMap<>();

    public synchronized void put(V v)
    {


        int freq = evictionMap.computeIfAbsent(v,val->1);

        if (freq!=1)
        {
            evictionMap.put(v,freq+1);
        }
    }

    public synchronized void remove(V v)
    {
        evictionMap.remove(v);
    }



    // get the least recently used 10% of the map to evict.
    public synchronized List<V> getEvictionTargets()
    {

        int limit = (int)(evictionMap.size()*0.1)+1;

        List<V> list =  evictionMap.entrySet().stream().sorted(Map.Entry.comparingByValue()).limit(limit).map(e->e.getKey()).collect(Collectors.toList());
//        return evictionMap.entrySet().stream().sorted((e1,e2)->Integer.compare(e1.getValue(),e2.getValue())).limit(limit).map(e->e.getKey()).collect(Collectors.toList());

        list.stream().forEach(v->remove(v));
        return list;
    }


}
