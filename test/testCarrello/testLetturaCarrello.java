package testCarrello;

import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import org.bson.Document;
import org.json.JSONException;
import org.junit.Test;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import model.prodottoECarrello.RigaCarrello;

public class testLetturaCarrello {

	@Test
	public void test() {
		
		Set<RigaCarrello> result = new HashSet<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");

		//Lettura
		Gson gson = new Gson();
		MongoCursor<Document> foundData = collection.find(new Document()).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
		    RigaCarrello curr = gson.fromJson(obj.toJson(), RigaCarrello.class);
			result.add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}
	}
}
