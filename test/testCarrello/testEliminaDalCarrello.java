package testCarrello;

import java.net.UnknownHostException;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class testEliminaDalCarrello {

	@Test
	public void test() {
		
		String id = "5db572398949ee2631bc1c6b";
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");
		
		//Eliminazione
		Document deleteQuery = new Document();
		deleteQuery.put("idProdotto", id);
		collection.deleteOne(deleteQuery);
	}
}
