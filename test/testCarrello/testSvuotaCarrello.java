package testCarrello;

import java.net.UnknownHostException;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class testSvuotaCarrello {
	
	@Test
	public void test() {
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");
		
		//Svuota tutto
		collection.drop();
	}
}
