package com.moodic.db;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbEnhancedClient;
import software.amazon.awssdk.enhanced.dynamodb.DynamoDbTable;
import software.amazon.awssdk.enhanced.dynamodb.Key;
import software.amazon.awssdk.enhanced.dynamodb.TableSchema;

@Repository
public class MoodicRequestRepository {

    @Autowired
    private DynamoDbEnhancedClient dynamoDbenhancedClient ;

    public void save(final MoodicRequest request) {
        DynamoDbTable<MoodicRequest> requestTable = getTable();
        requestTable.putItem(request);
    }

    public MoodicRequest getRequest(final String requestId) {
        DynamoDbTable<MoodicRequest> requestTable = getTable();
        Key key = Key.builder().partitionValue(requestId).build();
        return requestTable.getItem(key);
    }

    private DynamoDbTable<MoodicRequest> getTable() {
        return dynamoDbenhancedClient.table("MoodicRequest", TableSchema.fromBean(MoodicRequest.class));
    }

}


