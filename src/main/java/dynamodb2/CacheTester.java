package dynamodb2;

import dynamodb2.data.Customer;

public class CacheTester {


    public static void main(String[] args) throws Exception {

        CacheProperties cacheProperties = new CacheProperties();
        cacheProperties.addUniqueKey("id");

        Cache.Builder<Customer> builder = Cache.builder();

        DBManager.init();

        Cache<Customer> custCache = builder.properties(cacheProperties).evictionPolicy(Cache.EvictionPolicy.LFU).persist("Customer", Customer.class).build();



        for (int i=0;i<100;i++) {

            Customer customer = new Customer();
            customer.setName(String.valueOf(i));
            customer.setId(String.valueOf(i));
            custCache.put(customer);
           // dbManager.putItem(customer);



          //  Thread.sleep(100);

        }


        System.out.println(custCache);


       // custCache.printDBItems();

      for (int i=0;i<100;i++) {

            Customer customer = new Customer();
            customer.setId(String.valueOf(i));
            //customer.setName(String.valueOf(i));


            Customer result = custCache.get("id" , customer.getId(),customer);
            // dbManager.putItem(customer);

            //System.out.println(result);


            //  Thread.sleep(100);

        }

          System.out.println(custCache);





        //    dbManager.printAllItems();

        custCache.clean();




      //  custCache.remove(customer);


    }
}
