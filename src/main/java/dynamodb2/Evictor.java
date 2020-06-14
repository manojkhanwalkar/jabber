package dynamodb2;

import java.util.List;

public interface Evictor<V> {

     void put(V v);
     List<V> getEvictionTargets();


}
