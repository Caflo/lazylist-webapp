package testProdotti;

import java.net.UnknownHostException;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import model.prodottoECarrello.Prodotto;

public class testAggiungiProdotto {

	@Test
	public void test() {
		//Prodotto di prova
		Prodotto p = new Prodotto();
		p.setNome("Yoghurt greco");
		p.setMarca("Delta");
		p.setCategoria("Latticini");
		p.setProvenienza("Grecia");
		p.setPeso(0.10);
		p.setPrezzo(1.50);
		p.setSconto(0.20);
		p.setUnitaDisponibili(11);
		p.setDisponibile(true);
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Inserimento
			DBCollection collection = database.getCollection("prodotti");
	        BasicDBObject document = new BasicDBObject();
	        document.put("nome", p.getNome());
	        document.put("marca", p.getMarca());
	        document.put("categoria", p.getCategoria());
	        document.put("provenienza", p.getProvenienza());
	        document.put("peso", p.getPeso());
	        document.put("prezzo", p.getPrezzo());
	        document.put("sconto", p.getSconto());
	        document.put("unitaDisponibili", p.getUnitaDisponibili());
	        document.put("disponibile", p.getDisponibile());

	        collection.insert(document);
	        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
			
}
