package com.zyifly.mongodb.connect;

import com.mongodb.*;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.MongoIterable;
import com.mongodb.client.model.CreateCollectionOptions;
import org.apache.log4j.Logger;
import org.bson.Document;

import java.util.Map;


/**
 * Created by zhaoyifei on 16/8/1.
 */
public class MongoConnect {
    private  MongoClient mongoClient = null;
    private static Logger log = Logger.getLogger(MongoConnect.class);
    private  MongoDatabase db = null;

    private  String HOST = "101.200,211.135";
    private  int PORT = 27017;
    private  String DBNAME = "";

    private static String READ_URI = "mongodb://101.200.211.135:27021,101.200.211.135:27022";

    private static String WRITE_URI = "mongodb://101.200.211.135:27020";

    private static String DB_NAME = "heartonline";

    private final static MongoConnect instance = new MongoConnect();

    private final static MongoConnect rwInstance = new MongoConnect(READ_URI, WRITE_URI, DB_NAME);
    // "mongodb://localhost:27017,localhost:27018,localhost:27019"
    private MongoClient readClient = null;

    private MongoClient writeClient = null;

    private MongoDatabase readDB = null;

    private MongoDatabase writeDB = null;

    public MongoConnect(String HOST, int PORT, String DBNAME) {
        this.HOST = HOST;
        this.PORT = PORT;
        this.DBNAME = DBNAME;
        mongoClient = new MongoClient( HOST , PORT );
        db=mongoClient.getDatabase(DBNAME);
    }

    private MongoConnect(String readUri, String writeUri, String dbName) {

        MongoClientURI readconn = new MongoClientURI(readUri);
        readClient = new MongoClient(readconn);
        MongoClientURI writeconn = new MongoClientURI(writeUri);
        writeClient = new MongoClient(writeconn);
        this.readDB = this.readClient.getDatabase(dbName);
        this.writeDB = this.writeClient.getDatabase(dbName);
    }

    private MongoConnect() {

        MongoClientURI readconn = new MongoClientURI(READ_URI);
        readClient = new MongoClient(readconn);
        MongoClientURI writeconn = new MongoClientURI(WRITE_URI);
        writeClient = new MongoClient(writeconn);
        this.readDB = this.readClient.getDatabase(DBNAME);
        this.writeDB = this.writeClient.getDatabase(DBNAME);
    }

    public static MongoConnect getInstance(){
        return instance;
    }

    public static MongoConnect getRWInstance() {
        return rwInstance;

    }

    /**
     * 获取集合（表）
     * @param collectionName
     */
    public MongoCollection<Document> getCollection(String collectionName) {
        return this.db.getCollection(collectionName);
    }

    /**
     * 插入
     * @param collectionName
     * @param map
     */
    public void insert(String collectionName , Map<String, Object> map) {
        Document doc = new Document();
        doc.putAll(map);
        this.getCollection(collectionName).insertOne(doc);
    }

    public MongoCollection<Document> getReadCollection(String collectionName) {
        return this.readDB.getCollection(collectionName);
    }

    public boolean isExistCollection(String collectionName){
        MongoIterable<String> iterable = this.readDB.listCollectionNames();
        MongoCursor<String> iter = iterable.iterator();
        while(iter.hasNext()){
            if(iter.next().equals(collectionName)){
                return true;
            }
        }
        return false;
    }

    public void createCollection(String collectionName){
        this.writeDB.createCollection(collectionName,new CreateCollectionOptions().capped(true).sizeInBytes(0x100000));
    }

    public MongoCollection<Document> getWriteCollection(String collectionName) {
        return this.writeDB.getCollection(collectionName);
    }



}
