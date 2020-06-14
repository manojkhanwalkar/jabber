package dynamodb2;

import dynamodb2.data.Customer;

public class CacheTester {


    public static void main(String[] args) throws Exception {

        CacheProperties cacheProperties = new CacheProperties();
        cacheProperties.addUniqueKey("id");

        Cache.Builder<Customer> builder = Cache.builder();

        Cache<Customer> custCache = builder.properties(cacheProperties).evictionPolicy(Cache.EvictionPolicy.LRU).build();


        for (int i=0;i<100;i++) {

            Customer customer = new Customer();
            customer.setName(String.valueOf(i));
            custCache.put(customer);

           Thread.sleep(100);

        }


        System.out.println(custCache);


      //  custCache.remove(customer);
      //  System.out.println(custCache);


    }
}
