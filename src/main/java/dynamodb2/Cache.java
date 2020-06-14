package dynamodb2;

import dynamodb2.data.Customer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache<V> {

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

    public V get(String keyName, String key)
    {
        Map<String,V> map = uniqueKeysMap.get(keyName);
        if (map!=null)
        {
            return map.get(key);
        }
        else
        {
            System.out.println("Invalid key type specified");
            return null;
        }

    }


    public void put(V value)
    {

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


    public void init()
    {
        uniqueKeys.stream().forEach(key->{

            uniqueKeysMap.put(key,new HashMap<>());
        });
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


        EvictionPolicy policy;
        public Builder<T> evictionPolicy(EvictionPolicy policy)
        {
            this.policy=policy;
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

            cache.init();

            return cache;
        }
    }


    @Override
    public String toString() {
        return "Cache{" +
                "cacheName='" + cacheName + '\'' +
                ", uniqueKeys=" + uniqueKeys +
                ", uniqueKeysMap=" + uniqueKeysMap +
                '}';
    }
}
