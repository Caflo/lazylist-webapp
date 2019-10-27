package testOrdine;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class testEliminaOrdine {
	@Test
	public void test() {
		
		String idOrdine = "5db57e568949ce5c2beba47d";
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Eliminazione
			DBCollection collection = database.getCollection("ordini");
	        collection.remove(new BasicDBObject("_id", new ObjectId(idOrdine)));
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
