package testProdotti;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import model.prodottoECarrello.Prodotto;

public class testModificaProdotto {

	@Test
	public void test() {
		
		ObjectId id = new ObjectId("5db57f1589495b291d1ffbb5");
		Prodotto nuovoP = new Prodotto();
		nuovoP.setNome("Yoghurt greco AAA MODIFICATO");
		nuovoP.setMarca("Delta");
		nuovoP.setCategoria("Latticini");
		nuovoP.setProvenienza("Grecia");
		nuovoP.setPeso(0.10);
		nuovoP.setPrezzo(1.50);
		nuovoP.setSconto(0.20);
		nuovoP.setUnitaDisponibili(200);
		nuovoP.setDisponibile(true);
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Modifica
			DBCollection collection = database.getCollection("prodotti");
			BasicDBObject query = new BasicDBObject();
	        query.put("_id", id);
	        BasicDBObject newDocument = new BasicDBObject();
	        newDocument.put("nome", nuovoP.getNome());
	        newDocument.put("marca", nuovoP.getMarca());
	        newDocument.put("categoria", nuovoP.getCategoria());
	        newDocument.put("provenienza", nuovoP.getProvenienza());
	        newDocument.put("peso", nuovoP.getPeso());
	        newDocument.put("prezzo", nuovoP.getPrezzo());
	        newDocument.put("sconto", nuovoP.getSconto());
	        newDocument.put("unitaDisponibili", nuovoP.getUnitaDisponibili());
	        newDocument.put("disponibile", nuovoP.getDisponibile());
	        BasicDBObject updateObject = new BasicDBObject();
	        updateObject.put("$set", newDocument);
	        collection.update(query, updateObject);
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
