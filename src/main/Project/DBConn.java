package Project;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoDatabase;

public class DBConn {
    MongoClient mongoClient = new MongoClient("localhost", 27017);
    MongoDatabase database;
    DBConn(String name){
        database= mongoClient.getDatabase(name);
    }

    public DBConn() {

    }
}
