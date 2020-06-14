package dynamodb2;

import dynamodb2.data.Customer;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Cache<V> {

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

        public Cache<T> build()
        {
            Cache<T> cache = new Cache<>();
            return cache;
        }
    }

}
