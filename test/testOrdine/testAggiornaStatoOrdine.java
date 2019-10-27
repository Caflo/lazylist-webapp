package testOrdine;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import org.bson.types.ObjectId;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import model.ordine.LineaOrdine;
import model.ordine.Ordine;

public class testAggiornaStatoOrdine {
	
	@Test
	public void test() {
		
		String idOrdine = "5db57e568949ce5c2beba47d"; //id ordine da aggiornare
		
		try {
			Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			DBCollection collection = database.getCollection("ordini");
			BasicDBObject query = new BasicDBObject();
	        query.put("_id", new ObjectId(idOrdine));
	        Ordine result = gson.fromJson(collection.findOne(query).toString(), Ordine.class);
	        
	        result.aggiornaStato();
	        	  	         
	        BasicDBObject newDocument = new BasicDBObject();
	        newDocument.put("statoOrdine", result.getStatoOrdine().getStato());
	         
	        BasicDBObject updateObject = new BasicDBObject();
	        updateObject.put("$set", newDocument);
	         
	        collection.update(query, updateObject);
	           
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
