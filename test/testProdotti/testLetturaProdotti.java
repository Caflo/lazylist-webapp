package testProdotti;

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

import model.prodottoECarrello.Prodotto;

public class testLetturaProdotti {

	@Test
	public void test() {
		
		Set<Prodotto> result = new HashSet<>();
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("prodotti");

			//Lettura
			Gson gson = new Gson();
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            Prodotto p = gson.fromJson(output.toString(), Prodotto.class);
	            result.add(p);
	            ja.put(output);
	        }
    
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
	}
	
}
