package com.hyperiongray.ocrmemex.db;

/**
 * Created by mark on 11/20/14.
 */

import com.hyperiongray.ocrmemex.index.Indexer;
import com.mongodb.*;
import org.bson.types.ObjectId;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.Set;

public class MongoDbOp {
    private DB db;
    private DBCollection collection;

    public static void main(String[] args) throws Exception {
        // test the connnection
        MongoDbOp instance = new MongoDbOp();
        instance.openConnection();
        instance.listCollections();
        instance.iterateThroughCollection();
    }

    public void openConnection() throws java.net.UnknownHostException {

// To directly connect to a single MongoDB server (note that this will not auto-discover the primary even
// if it's a member of a replica set:
        MongoClient mongoClient = new MongoClient();
// or
//        MongoClient mongoClient = new MongoClient( "localhost" );
// or
//        MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
// or, to connect to a replica set, with auto-discovery of the primary, supply a seed list of members
//        MongoClient mongoClient = new MongoClient(Arrays.asList(new ServerAddress("localhost", 27017),
//                new ServerAddress("localhost", 27018),
//                new ServerAddress("localhost", 27019)));

        db = mongoClient.getDB("MemexHack");
        System.out.println("Connection to MongoDB established");
        collection = db.getCollection("urlinfo");
    }

    private void listCollections() {
        System.out.println("List collections:");
        // get a list of the collections in this database and print them out
        Set<String> collectionNames = db.getCollectionNames();
        for (final String s : collectionNames) {
            System.out.println(s);
        }
    }

    private void iterateThroughCollection() {
        Indexer indexer = new Indexer();
        indexer.setIndexLocation("output/index");
        DBObject myDoc = collection.findOne();
        System.out.println("First document, print just as a sample");
        System.out.println(myDoc);

        DBCursor cursor = collection.find();
        int count = 0;
        try {
            indexer.openIndexForWrite();
            while (cursor.hasNext()) {
                ++count;
                DBObject object = cursor.next();
                Set<String> keys = object.keySet();
                ObjectId oid = (ObjectId) object.get("_id");
                String id = oid.toString();
                String html = (String) object.get("html");
                if (html != null) {
                    Document doc = Jsoup.parse(html);
                    String text = doc.text();
                    indexer.addTextToIndex(text, id);
                }
            }
            indexer.closeIndex();
        } catch (Exception e) {
            e.printStackTrace();

        } finally {
            cursor.close();
        }
        System.out.println(count + " entries in MongoDB indexed to Lucene");
    }

    public void addScore(String mongoId, String keyPhrase, float score) {
//        System.out.println("Updating MongoDB for mongoId=" + mongoId + ", keyPhrase=" + keyPhrase + ", score=" + score);
        BasicDBObject newDocument = new BasicDBObject();
        newDocument.append(keyPhrase, score);
        BasicDBObject searchQuery = new BasicDBObject();
        try {
            searchQuery.put("_id", new ObjectId(mongoId));
            DBObject dbObj = collection.findOne(searchQuery);
//            System.out.println("before update");
//            System.out.println(dbObj.toString());
            dbObj.put("keyPhrase " + keyPhrase, score);
            collection.update(searchQuery, dbObj);
            // debugging - verify
            dbObj = collection.findOne(searchQuery);
//            System.out.println("after update");
//            System.out.println(dbObj.toString());
        } catch (Exception e) {
            // TODO better error handling
            // but this error should not normally happen
            e.printStackTrace();
        }
    }
}