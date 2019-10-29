package testProdotti;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
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
			Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

				@Override
				public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
						throws JsonParseException {
					// TODO Auto-generated method stub
					return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
				}
				
			}).create();
			
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            Prodotto p = gson.fromJson(obj.toString(), Prodotto.class);
	            result.add(p);
	            ja.put(output);
	        }
    
	        //DEBUG
	        System.out.println(ja.toString());
	        for (Prodotto p : result) {
	        	System.out.println(p.get_id().toString());
	        }
		} catch (JSONException | UnknownHostException e) {
			e.printStackTrace();
		}
	}
	
}
