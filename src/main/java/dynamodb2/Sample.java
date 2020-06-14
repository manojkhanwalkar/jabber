package dynamodb2;

//import com.amazonaws.client.builder.AwsClientBuilder;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
//import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
//import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDB;
import dynamodb2.data.Customer;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;

import java.net.URI;
import java.util.UUID;


//SDK version 2 of dynamodB being used here with the enhanced client


public class Sample {


   /* static AmazonDynamoDB getHandle()
    {
        AmazonDynamoDB ddb = AmazonDynamoDBClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration("http://localhost:8000", "us-west-2"))
                .build();

        return ddb;

    }*/


    public static void main(String[] args) throws Exception {

        Region region = Region.US_WEST_2;

        DynamoDbClient ddb = DynamoDbClient.builder().endpointOverride(new URI("http://localhost:8000")).build();

        /*DynamoDbClient ddb = DynamoDbClient.builder()
                .region(region)
                .build();*/

        //AmazonDynamoDB ddb = getHandle();

        DynamoDbEnhancedClient enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();

        DynamoDbTable<Customer> customerTable =
                enhancedClient.table("customers_table", TableSchema.fromBean(Customer.class));

     //   System.out.println(customerTable);

       // customerTable.createTable();

      /*  customerTable.getItem(customerToGet);
        customerTable.putItem(newCustomer);
        customerTable.deleteItem(customerToDelete);
        customerTable.scan();*/

      Customer customer = new Customer();
      customer.setId( UUID.randomUUID().toString());

      customerTable.putItem(customer);

      customerTable.scan().items().stream().forEach(c->System.out.println(c));

//        createDynamoDBTable(enhancedClient);


    }
}
