package testCarrello;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;

import org.junit.Test;

import com.google.gson.stream.JsonWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import model.prodottoECarrello.RigaCarrello;

public class testSvuotaCarrello {
	
	@Test
	public void test() {
		
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Svuota tutto
			DBCollection collection = database.getCollection("carrello");
			collection.drop();    
			
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
