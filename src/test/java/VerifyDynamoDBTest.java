import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;

public class VerifyDynamoDBTest {

    LocalStackDynamoDB localStackDynamoDB = new LocalStackDynamoDB();

    @Before
    public void setup(){
        this.localStackDynamoDB.setDisableSSLVerification(true);

        this.localStackDynamoDB.init("https:<IP/A-Record>:4566", "us-west-2");
    }

    @Test
    public void testTableCreate(){

        Assert.assertEquals("Music", this.localStackDynamoDB.createTable(
                        "Music",
                        Arrays.asList(new KeySchemaElement("year", KeyType.HASH),
                                new KeySchemaElement("title", KeyType.RANGE)),
                        Arrays.asList(new AttributeDefinition("year", ScalarAttributeType.N),
                                new AttributeDefinition("title", ScalarAttributeType.S)),
                        new ProvisionedThroughput(10L, 10L))
                .getTable("Music").getTableName());
    }

    @Test
    public void testTableDelete(){
        Assert.assertNull(this.localStackDynamoDB.deleteTable("Music").getTable("Music"));
    }
}

