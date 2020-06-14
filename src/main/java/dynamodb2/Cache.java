package dynamodb2;

import dynamodb2.data.Customer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache<V> {

    String tableName;

    public void clean() {

        DBManager.delete(tableName);

    }

    public enum EvictionPolicy { LRU , LFU};

    EvictionPolicy policy = EvictionPolicy.LRU;

    public EvictionPolicy getPolicy() {
        return policy;
    }

    public void setPolicy(EvictionPolicy policy) {
        this.policy = policy;
    }

    String cacheName;


    List<String> uniqueKeys= new ArrayList<>();

    private Cache() {}

    public V get(String keyName, String key, V dummy)
    {
        Map<String,V> map = uniqueKeysMap.get(keyName);
        if (map!=null)
        {
            V v =  map.get(key);
            if (v==null)
            {
               // System.out.println("Getting from data base ");

                v= dbManager.getItem(dummy);

                recover(v);
            }

            return  v;
        }
        else
        {
            System.out.println("Invalid key type specified");
            return null;
        }

    }


    public void printDBItems()
    {
        dbManager.printAllItems();
    }


    public static final int HighWaterMark = 10;

    Evictor<V> evictor ;

    int entries = 0;

    public void put(V value)
    {

        if (entries>=HighWaterMark)
        {
            evictor.getEvictionTargets().stream().forEach(v->remove(v));
        }
        entries++;


        Class<?> clazz = value.getClass();


        uniqueKeys.stream().forEach(key->{

            try {
                Map<String,V> map = uniqueKeysMap.get(key);
                if (map!=null)
                {
                    Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);

                    String keyValue = (String)field.get(value);

                    map.put(keyValue,value);

                    evictor.put(value);

                    dbManager.putItem(value);
                }
                else
                {
                    System.out.println("Invalid key type specified");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }


    public void recover(V value)
    {


        entries++;


        Class<?> clazz = value.getClass();


        uniqueKeys.stream().forEach(key->{

            try {
                Map<String,V> map = uniqueKeysMap.get(key);
                if (map!=null)
                {
                    Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);

                    String keyValue = (String)field.get(value);

                    map.put(keyValue,value);

                    evictor.put(value);


                }
                else
                {
                    System.out.println("Invalid key type specified");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }


    public void remove(V value)
    {

        entries--;
        Class<?> clazz = value.getClass();


        uniqueKeys.stream().forEach(key->{

            try {
                Map<String,V> map = uniqueKeysMap.get(key);
                if (map!=null)
                {
                    Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);

                    String keyValue = (String)field.get(value);

                    map.remove(keyValue,value);

                    //dbManager.deleteItem(value);
                }
                else
                {
                    System.out.println("Invalid key type specified");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });


    }


    public void delete(V value)
    {

        entries--;
        Class<?> clazz = value.getClass();


        uniqueKeys.stream().forEach(key->{

            try {
                Map<String,V> map = uniqueKeysMap.get(key);
                if (map!=null)
                {
                    Field field = clazz.getDeclaredField(key);
                    field.setAccessible(true);

                    String keyValue = (String)field.get(value);

                    map.remove(keyValue,value);

                    dbManager.deleteItem(value);
                }
                else
                {
                    System.out.println("Invalid key type specified");
                }
            } catch (NoSuchFieldException | IllegalAccessException e) {
                e.printStackTrace();
            }
        });

    }



    Map<String,Map<String,V>> uniqueKeysMap = new HashMap<>();

    DBManager<V> dbManager;

    Class<V> table;

    public void init()
    {
        uniqueKeys.stream().forEach(key->{

            uniqueKeysMap.put(key,new HashMap<>());
        });

        if (tableName!=null)
        {
            DBManager.create(tableName,table);
            dbManager= new DBManager<>(tableName,table);

        }
    }

    public static<T>  Builder<T> builder()
    {
        return new Builder<>();
    }


    static class Builder<T>
    {

        CacheProperties cacheProperties;

        public Builder<T> properties(CacheProperties cacheProperties)
        {
            this.cacheProperties = cacheProperties;
            return this;
        }

        Evictor evictor ;

        EvictionPolicy policy;
        public Builder<T> evictionPolicy(EvictionPolicy policy)
        {
            this.policy=policy;
            if (policy==EvictionPolicy.LRU)
            {
                evictor = new LRUEvictor();
            }
            else
            {
                evictor = new LFUEvictor();
            }

            return this;
        }

        String tableName;
        Class<T> table;
        public Builder<T> persist(String tableName, Class<T> c)
        {
            this.tableName = tableName;
            this.table = c;
            return this;
        }

        public Cache<T> build()
        {
            Cache<T> cache = new Cache<>();
            cache.cacheName=cacheProperties.cacheName;
            cacheProperties.getUniqueKeys().stream().forEach(key->{
                cache.uniqueKeys.add(key);


            });

            cache.policy = policy;
            cache.evictor=evictor;

            cache.tableName=tableName;
            cache.table = table;


            cache.init();

            return cache;
        }
    }


    @Override
    public String toString() {
        return "Cache{" +
                "policy=" + policy +
                ", cacheName='" + cacheName + '\'' +
                ", uniqueKeys=" + uniqueKeys +
                ", evictor=" + evictor +
                ", entries=" + entries +
                ", uniqueKeysMap=" + uniqueKeysMap +
                '}';
    }
}
