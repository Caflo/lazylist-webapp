package testOrdine;

import java.net.UnknownHostException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import model.ordine.Ordine;
import model.ordine.OrdiniTotali;

public class testLetturaOrdine {

	@Test
	public void test() {
		
		//può essere anche OrdiniInConsegna o OrdiniCliente, è un esempio
		OrdiniTotali ordiniTotali = new OrdiniTotali();
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("ordini");

			//Lettura
		
			Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();

			
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            ja.put(output);
				Ordine curr = gson.fromJson(obj.toString(), Ordine.class);
				ordiniTotali.getOrdini().add(curr);
	        }
	        
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
	}
}
