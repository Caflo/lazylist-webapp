package testFasceOrarie;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

import com.google.gson.Gson;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.util.JSON;

import model.ordine.FasciaOraria;
import model.prodottoECarrello.RigaCarrello;

public class testLetturaFasceoOrarieDisponibili {
	
	@Test
	public void test() {
		
		String giorno = "giovedì";
		Set<FasciaOraria> result = new HashSet<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("fasceOrarie");

		//Lettura
		Gson gson = new Gson();
		Document myDoc = collection.find().first();
		System.out.println(myDoc.toJson());
		Document query = new Document();
		query.put("giorno", giorno);
		MongoCursor<Document> foundData = collection.find(query).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
		    FasciaOraria curr = gson.fromJson(obj.toJson(), FasciaOraria.class);
			result.add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}
		
	}
}
