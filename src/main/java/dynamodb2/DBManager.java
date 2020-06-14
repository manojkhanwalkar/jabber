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
import software.amazon.awssdk.services.dynamodb.model.DeleteTableRequest;
import software.amazon.awssdk.services.glue.model.Table;

import java.net.URI;
import java.util.UUID;

import static software.amazon.awssdk.enhanced.dynamodb.TableSchema.fromBean;


//SDK version 2 of dynamodB being used here with the enhanced client


public class DBManager<T> {

    static DynamoDbEnhancedClient enhancedClient;

    static DynamoDbClient ddb;
    public static void init() throws Exception
    {
        Region region = Region.US_WEST_2;
         ddb = DynamoDbClient.builder().endpointOverride(new URI("http://localhost:8000")).build();
        enhancedClient = DynamoDbEnhancedClient.builder()
                .dynamoDbClient(ddb)
                .build();


    }


    //static <T> BeanTableSchema<T> fromBean(Class<T> beanClass) {
    public static<T> void create(String tableName, Class<T> c)
    {
        DynamoDbTable<T> customerTable = enhancedClient.table(tableName, TableSchema.fromBean(c));

        try {
            customerTable.createTable();

        } catch(Exception e) {  }



}




    public static void delete(String tableName)
    {
        DeleteTableRequest deleteTableRequest =  DeleteTableRequest.builder().tableName(tableName).build();
        ddb.deleteTable(deleteTableRequest);

    }

    DynamoDbTable<T> beanTable ;

    Class<T> c;

    public DBManager(String tableName, Class<T> c)
    {
            beanTable = enhancedClient.table(tableName, TableSchema.fromBean(c));

    }

    public void putItem(T t )
    {
        beanTable.putItem(t);

    }


    public T getItem(T t )
    {
        return beanTable.getItem(t);

    }

    public void deleteItem(T t )
    {
        beanTable.deleteItem(t);

    }


    public void printAllItems()
    {
        beanTable.scan().items().stream().forEach(c->System.out.println(c));
    }





}
