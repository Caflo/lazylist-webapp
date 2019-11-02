package testProdotti;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.json.JSONException;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import model.prodottoECarrello.Prodotto;

public class testLetturaProdotti {

	@Test
	public void test() {
		 		
		Set<Prodotto> result = new HashSet<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");

		//Lettura
		Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

			@Override
			public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
					throws JsonParseException {
				// TODO Auto-generated method stub
				return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
			}
			
		}).create();
		
		Document myDoc = collection.find().first();
		System.out.println(myDoc.toJson());
		MongoCursor<Document> foundData = collection.find(new Document()).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
			Prodotto curr = gson.fromJson(obj.toJson(), Prodotto.class);
			result.add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}
	}
	
}
