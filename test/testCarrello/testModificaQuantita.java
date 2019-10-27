package testCarrello;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class testModificaQuantita {

	@Test
	public void test() {
		
		String id = "5db572398949ee2631bc1c6b";
		Integer nuovaQuantita = 3;
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Modifica
			DBCollection collection = database.getCollection("carrello");
			BasicDBObject query = new BasicDBObject();
	        query.put("idProdotto", id);
	        BasicDBObject newDocument = new BasicDBObject();
	        newDocument.put("quantitaScelta", nuovaQuantita);
	        BasicDBObject updateObject = new BasicDBObject();
	        updateObject.put("$set", newDocument);
	        collection.update(query, updateObject);
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
