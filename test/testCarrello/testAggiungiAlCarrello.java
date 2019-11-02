package testCarrello;

import java.net.UnknownHostException;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.junit.Test;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.prodottoECarrello.Prodotto;
import model.prodottoECarrello.RigaCarrello;

public class testAggiungiAlCarrello {

	@Test
	public void test() {
		
		ObjectId idProdotto = new ObjectId("5db572398949ee2631bc1c6b");
		//Prodotto di prova
		Prodotto p = new Prodotto();
		p.set_id(idProdotto);
		p.setNome("Yoghurt greco 100gr");
		p.setMarca("Delta");
		p.setCategoria("Latticini");
		p.setProvenienza("Grecia");
		p.setPrezzo(1.50);
		p.setSconto(0.20);
		p.setUnitaDisponibili(11);
		p.setDisponibile(true);
		
		RigaCarrello riga = new RigaCarrello();
		riga.setIdProdotto(p.get_id().toString());
		riga.setNomeProdotto(p.getNome());
		riga.setQuantitaScelta(1);
		riga.setPrezzoUnitario(p.getPrezzo());
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");
		
		//Inserimento
		Document document = new Document();
		document.put("idProdotto", riga.getIdProdotto()); //nelle righe carrello e in linea ordine lo metto come stringa
		document.put("imagePath", p.getImagePath());
		document.put("nomeProdotto", p.getNome());
		document.put("quantitaScelta", 1);
		document.put("prezzoUnitario", p.getPrezzo());
		document.put("sconto", p.getSconto());
		
		//Automaticamente mongoDB inserirà anche un campo _id ma nel carrello me ne sbatto altamente, non mi serve

		collection.insertOne(document);
		
	}
}
