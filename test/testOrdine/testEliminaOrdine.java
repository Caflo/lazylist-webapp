package testOrdine;

import java.net.UnknownHostException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;



public class testEliminaOrdine {
	@Test
	public void test() {
		
		String idOrdine = "5dbd4f78b75c376bca33920b";
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");
		
		//Eliminazione
		collection.deleteOne(new Document("_id", new ObjectId(idOrdine)));
	}
}
