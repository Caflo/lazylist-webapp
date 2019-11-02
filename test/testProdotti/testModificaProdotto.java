package testProdotti;

import java.net.UnknownHostException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.prodottoECarrello.Prodotto;

public class testModificaProdotto {

	@Test
	public void test() {
		
		ObjectId id = new ObjectId("5db9aa2c353f01338f9af28f");
		Prodotto nuovoP = new Prodotto();
		nuovoP.setNome("Yoghurt greco AAA MODIFICATO");
		nuovoP.setMarca("Delta");
		nuovoP.setCategoria("Latticini");
		nuovoP.setProvenienza("Grecia");
		nuovoP.setPrezzo(1.50);
		nuovoP.setSconto(0.20);
		nuovoP.setUnitaDisponibili(200);
		nuovoP.setDisponibile(true);
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");
		
		//Modifica
		Document query = new Document();
		query.put("_id", id);
		Document newDocument = new Document();
		newDocument.put("nome", nuovoP.getNome());
		newDocument.put("marca", nuovoP.getMarca());
		newDocument.put("categoria", nuovoP.getCategoria());
		newDocument.put("provenienza", nuovoP.getProvenienza());
		newDocument.put("prezzo", nuovoP.getPrezzo());
		newDocument.put("sconto", nuovoP.getSconto());
		newDocument.put("unitaDisponibili", nuovoP.getUnitaDisponibili());
		newDocument.put("disponibile", nuovoP.getDisponibile());
		BasicDBObject updateObject = new BasicDBObject();
		updateObject.put("$set", newDocument);
		collection.updateOne(query, updateObject);
	}
}
