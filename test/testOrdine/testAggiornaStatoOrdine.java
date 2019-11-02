package testOrdine;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.ordine.Ordine;
import serializer.OrdineDeserializer;

public class testAggiornaStatoOrdine {
	
	@Test
	public void test() {
		
		String idOrdine = "5dbd506017ecbb76375db466"; //id ordine da aggiornare
		
		Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");
		
		Document query = new Document();
		query.put("_id", new ObjectId(idOrdine));
		Ordine result = gson.fromJson(collection.find(query).first().toJson(), Ordine.class);
		
		result.aggiornaStato();
			  	         
		Document newDocument = new Document();
		newDocument.put("statoOrdine", result.getStatoOrdine().getStato());
		 
		Document updateObject = new Document();
		updateObject.put("$set", newDocument);
		 
		collection.updateOne(query, updateObject);
	}
}
