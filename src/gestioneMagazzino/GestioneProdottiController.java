package gestioneMagazzino;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Sorts;

import model.prodottoECarrello.Magazzino;
import model.prodottoECarrello.Prodotto;

public class GestioneProdottiController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		HttpSession session = req.getSession();
		Magazzino m = this.mostraMagazzino();
		session.setAttribute("prodottiMagazzino", m);
		resp.sendRedirect(req.getContextPath() + "/prodottiMagazzino.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//Switch
		String tipoOperazione = req.getParameter("tipoOperazione");
		//Che io debba modificare o salvare, mi servono lo stesso tutti i parametri della riga
		String nome = req.getParameter("nome");
		String categoria = req.getParameter("categoria");
		String marca = req.getParameter("marca");
		String provenienza = req.getParameter("provenienza");
		Double prezzo = Double.parseDouble(req.getParameter("prezzo").replace(",", "."));
		Double sconto = Double.parseDouble(req.getParameter("sconto").replace(",", "."));
		Integer unitaDisponibili = Integer.parseInt(req.getParameter("unitaDisponibili"));
		Boolean disponibile = Boolean.parseBoolean(req.getParameter("disponibile"));
		Prodotto p = new Prodotto();
		p.setImagePath(req.getParameter("imagePath"));
		p.setNome(nome);
		p.setCategoria(categoria);
		p.setMarca(marca);
		p.setProvenienza(provenienza);
		p.setPrezzo(prezzo);
		p.setSconto(sconto / 100); //se e' ad esempio il 20%, lo trasformo in 0.2 perche' viene salvato cosi su DB
		p.setUnitaDisponibili(unitaDisponibili);
		p.setDisponibile(disponibile);
		
		if (tipoOperazione.equals("Salva")) {
			String id = req.getParameter("id");
			//se esiste, vuol dire che la richiesta proviene da una riga popolata con l'id e quindi con un prodotto esistente
			//se invece non esiste, vuol dire che lato client ho inserito una nuova riga e sto cercando di inserire un nuovo prodotto
			
			//nel primo caso modifico il prodotto con le nuove informazioni
			//nel secondo eseguo un inserimento
			
			if (id != null) {
				p.set_id(new ObjectId(id));
				this.modificaProdotto(p);
			}
			else
				this.inserisciProdotto(p);
		}
		else if (tipoOperazione.equals("Elimina")) {		
			this.eliminaProdotto(p);
		}
		
		resp.sendRedirect(req.getContextPath() + "/gestioneProdottiController");
	}
	

	private void modificaProdotto(Prodotto p) {
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");			
      
      
		Document query = new Document();
		query.put("_id", p.get_id());
		Document newDocument = new Document();
		newDocument.put("imagePath", p.getImagePath());
		newDocument.put("nome", p.getNome());
		newDocument.put("marca", p.getMarca());
		newDocument.put("categoria", p.getCategoria());
		newDocument.put("provenienza", p.getProvenienza());
		newDocument.put("prezzo", p.getPrezzo());
		newDocument.put("sconto", p.getSconto());
		newDocument.put("unitaDisponibili", p.getUnitaDisponibili());
		newDocument.put("disponibile", p.getDisponibile());
		Document updateObject = new Document();
		updateObject.put("$set", newDocument);
		collection.updateOne(query, updateObject);
	}

	private void inserisciProdotto(Prodotto p) {

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

	private Magazzino mostraMagazzino() {
		Magazzino magazzino = new Magazzino();	
		List<Prodotto> result = new ArrayList<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");

		//Lettura
		Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

			@Override
			public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
					throws JsonParseException {
				// TODO Auto-generated method stub
				return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
			}
			
		}).create();
		
		MongoCursor<Document> foundData = collection.find(new Document()).sort(Sorts.ascending("nome")).cursor();
		while (foundData.hasNext()) {
			Prodotto curr = gson.fromJson(foundData.next().toJson(), Prodotto.class);
			result.add(curr);
			//DEBUG
		    System.out.println(curr.toString());
		}
		
		magazzino.setProdotti(result);
		return magazzino;
	}
	
	private void eliminaProdotto(Prodotto p) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");
		
		//Eliminazione
		Document deleteQuery = new Document();
		deleteQuery.put("_id", p.get_id());
		collection.deleteOne(deleteQuery);
	}
}
