package com.zyifly.mongodb.connect;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.junit.Test;

/**
 * Created by zhaoyifei on 16/8/12.
 */
public class TestMongoConnect {

    @Test
    public void testMongodb(){

        MongoConnect mc = MongoConnect.getRWInstance();
        if(!mc.isExistCollection("group")){
            mc.createCollection("group");
        }
        MongoCollection<Document> userR = mc.getReadCollection("group");
        MongoCollection<Document> userW = mc.getWriteCollection("group");
        Document doc = new Document();
        doc.put("groupname", "system");
        userW.insertOne(doc);
        FindIterable<Document> iter = userR.find();
        System.out.println(iter.first().toJson());
    }
}
