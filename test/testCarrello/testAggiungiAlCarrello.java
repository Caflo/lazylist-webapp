package testCarrello;

import java.net.UnknownHostException;

import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import model.prodottoECarrello.Prodotto;
import model.prodottoECarrello.RigaCarrello;

public class testAggiungiAlCarrello {

	@Test
	public void test() {
		
		ObjectId idProdotto = new ObjectId("5db572398949ee2631bc1c6b");
		//Prodotto di prova
		Prodotto p = new Prodotto();
		p.set_id(idProdotto);
		p.setNome("Yoghurt greco");
		p.setMarca("Delta");
		p.setCategoria("Latticini");
		p.setProvenienza("Grecia");
		p.setPeso(0.10);
		p.setPrezzo(1.50);
		p.setSconto(0.20);
		p.setUnitaDisponibili(11);
		p.setDisponibile(true);
		
		RigaCarrello riga = new RigaCarrello();
		riga.setIdProdotto(p.get_id().toString());
		riga.setNomeprodotto(p.getNome());
		riga.setQuantitaScelta(1);
		riga.setPrezzoUnitario(p.getPrezzo());
		riga.setPesoTotaleProdotto(p.getPeso());
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Inserimento
			DBCollection collection = database.getCollection("carrello");
	        BasicDBObject document = new BasicDBObject();
	        document.put("idProdotto", riga.getIdProdotto()); //nelle righe carrello e in linea ordine lo metto come stringa
	        document.put("nomeProdotto", p.getNome());
	        document.put("quantitaScelta", 1);
	        document.put("prezzoUnitario", p.getPrezzo());
	        document.put("pesoTotaleProdotto", p.getPeso());
	        
	        //Automaticamente mongoDB inserirà anche un campo _id ma nel carrello me ne sbatto altamente, non mi serve

	        collection.insert(document);
	        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
}
