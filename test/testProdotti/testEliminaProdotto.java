package testProdotti;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

public class testEliminaProdotto {

	@Test
	public void test() {
		
		ObjectId id = new ObjectId("5db57f1589495b291d1ffbb5");
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Eliminazione
			DBCollection collection = database.getCollection("prodotti");
			BasicDBObject deleteQuery = new BasicDBObject();
	        deleteQuery.put("_id", id);
	        collection.remove(deleteQuery);
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
