package testOrdine;

import org.bson.Document;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import model.ordine.Ordine;
import model.ordine.OrdiniTotali;
import serializer.OrdineDeserializer;

public class testLetturaOrdine {

	@Test
	public void test() {
	
		OrdiniTotali ordiniTotali = new OrdiniTotali();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");

		//Lettura

		Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();
		
		MongoCursor<Document> foundData = collection.find(new Document()).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
			Ordine curr = gson.fromJson(obj.toJson(), Ordine.class);
			ordiniTotali.getOrdini().add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}
	}
}
