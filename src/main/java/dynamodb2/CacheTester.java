package dynamodb2;

import dynamodb2.data.Customer;

public class CacheTester {


    public static void main(String[] args) {

        CacheProperties cacheProperties = new CacheProperties();
        cacheProperties.addUniqueKey("id");

        Cache.Builder<Customer> builder = Cache.builder();

        Cache<Customer> custCache = builder.properties(cacheProperties).build();



        Customer customer = new Customer();
        custCache.put(customer);


        System.out.println(custCache);
        custCache.remove(customer);
        System.out.println(custCache);


    }
}
