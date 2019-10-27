package testCarrello;

import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import model.prodottoECarrello.Carrello;
import model.prodottoECarrello.RigaCarrello;

public class testEliminaDalCarrello {

	@Test
	public void test() {
		
		String id = "5db572398949ee2631bc1c6b";
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Eliminazione
			DBCollection collection = database.getCollection("carrello");
			BasicDBObject deleteQuery = new BasicDBObject();
	        deleteQuery.put("idProdotto", id);
	        collection.remove(deleteQuery);
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
