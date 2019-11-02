package testProdotti;

import java.net.UnknownHostException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class testEliminaProdotto {

	@Test
	public void test() {
		
		ObjectId id = new ObjectId("5db57f1589495b291d1ffbb5");
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");
		
		//Eliminazione
		Document deleteQuery = new Document();
		deleteQuery.put("_id", id);
		collection.deleteOne(deleteQuery);
	}
}
