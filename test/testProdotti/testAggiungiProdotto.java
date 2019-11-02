package testProdotti;

import java.net.UnknownHostException;

import org.bson.Document;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.prodottoECarrello.Prodotto;

public class testAggiungiProdotto {

	@Test
	public void test() {
		//Prodotto di prova
		Prodotto p = new Prodotto();
		p.setNome("Yoghurt greco AGGIUNTO 100gr");
		p.setMarca("Delta");
		p.setCategoria("Latticini");
		p.setProvenienza("Grecia");
		p.setPrezzo(1.50);
		p.setSconto(0.20);
		p.setUnitaDisponibili(11);
		p.setDisponibile(true);
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");
		
		//Inserimento
		Document document = new Document();
		document.put("nome", p.getNome());
		document.put("imagePath", p.getImagePath());
		document.put("marca", p.getMarca());
		document.put("categoria", p.getCategoria());
		document.put("provenienza", p.getProvenienza());
		document.put("prezzo", p.getPrezzo());
		document.put("sconto", p.getSconto());
		document.put("unitaDisponibili", p.getUnitaDisponibili());
		document.put("disponibile", p.getDisponibile());

		collection.insertOne(document);
	}
			
}
