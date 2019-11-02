package testCarrello;

import java.net.UnknownHostException;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class testModificaQuantita {

	@Test
	public void test() {
		
		String id = "5db572398949ee2631bc1c6b";
		Integer nuovaQuantita = 3;
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");
		
		//Modifica
		Document query = new Document();
		query.put("idProdotto", id);
		Document newDocument = new Document();
		newDocument.put("quantitaScelta", nuovaQuantita);
		Document updateObject = new Document();
		updateObject.put("$set", newDocument);
		collection.updateOne(query, updateObject);
	}
}
