package testCarrello;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import model.prodottoECarrello.RigaCarrello;

public class testLetturaCarrello {

	@Test
	public void test() {
		
		Set<RigaCarrello> result = new HashSet<>();
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("carrello");

			//Lettura
			Gson gson = new Gson();
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            RigaCarrello rc = gson.fromJson(output.toString(), RigaCarrello.class);
	            result.add(rc);
	            ja.put(output);
	        }
	        
	        //manca creare entita Carrello e calcolare subtotale
	        
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
	}
}
