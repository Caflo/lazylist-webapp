package gestioneMagazzino;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.types.ObjectId;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import model.ordine.OrdiniTotali;
import model.prodottoECarrello.Magazzino;
import model.prodottoECarrello.Prodotto;
import model.prodottoECarrello.RigaCarrello;

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
		
		//Non uso gli ID di mongoDB anche perche' senno' si dovrebbe aggiungere la cella alla tabella relativa all'id
		
		//Vedo se trovo almeno una tupla, se la trovo la aggiorno, altrimenti la inserisco
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
				
			DBCollection collection = database.getCollection("prodotti");
			
	       
	       
			BasicDBObject query = new BasicDBObject();
        	query.put("_id", p.get_id());
	        BasicDBObject newDocument = new BasicDBObject();
	        newDocument.put("nome", p.getNome());
	        newDocument.put("marca", p.getMarca());
	        newDocument.put("categoria", p.getCategoria());
	        newDocument.put("provenienza", p.getProvenienza());
	        newDocument.put("prezzo", p.getPrezzo());
	        newDocument.put("sconto", p.getSconto());
	        newDocument.put("unitaDisponibili", p.getUnitaDisponibili());
	        newDocument.put("disponibile", p.getDisponibile());
	        BasicDBObject updateObject = new BasicDBObject();
	        updateObject.put("$set", newDocument);
	        collection.update(query, updateObject);
		        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private void inserisciProdotto(Prodotto p) {

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
	        document.put("prezzo", p.getPrezzo());
	        document.put("sconto", p.getSconto());
	        document.put("unitaDisponibili", p.getUnitaDisponibili());
	        document.put("disponibile", p.getDisponibile());

	        collection.insert(document);
	        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}

	private Magazzino mostraMagazzino() {
		Magazzino result = new Magazzino();	
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("prodotti");

			//Lettura
			
			//Necessario registrare la callback siccome Gson non traduce correttamente gli ObjectID di mongoDB
	        Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

				@Override
				public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
						throws JsonParseException {
					// TODO Auto-generated method stub
					return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
				}
				
			}).create();
	        
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            Prodotto p = gson.fromJson(obj.toString(), Prodotto.class);
	            result.getProdotti().add(p);
	            ja.put(output);
	        }
    
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	private void eliminaProdotto(Prodotto p) {
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Eliminazione
			DBCollection collection = database.getCollection("prodotti");
			BasicDBObject deleteQuery = new BasicDBObject();
	        deleteQuery.put("nome", p.getNome());
	        collection.remove(deleteQuery);
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}
}
