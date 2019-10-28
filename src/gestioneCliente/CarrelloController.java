package gestioneCliente;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

//
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
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import model.prodottoECarrello.Carrello;
import model.prodottoECarrello.Prodotto;
import model.prodottoECarrello.RigaCarrello;

public class CarrelloController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// LA USO PER
		// *) Mostrare il carrello
		HttpSession session = req.getSession();
		Carrello c = mostraCarrello();
		session.setAttribute("carrello", c);
		resp.sendRedirect(req.getContextPath() + "/cart.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// LA USO PER 
		// *) Aggiungere i prodotto del carrello dal catalogo
		// *) Modificare quantita dal carrello
		// *) Eliminare un prodotto dal carrello
	
		HttpSession session = req.getSession();
	
		String tipoOperazione = req.getParameter("tipoOperazione");
		
		//Switch
		if (tipoOperazione.equals("aggiungiAlCarrello")) {
			String nomeProdotto = req.getParameter("nomeProdotto");
			this.aggiungiAlCarrello(nomeProdotto);
			resp.sendRedirect(req.getContextPath() + "/index.jsp");
		}
		else if (tipoOperazione.equals("eliminaDalCarrello")) {
			String nomeProdotto = req.getParameter("nomeProdotto");
			this.eliminaDalCarrello(nomeProdotto);
			// Passo di nuovo alla doGet che reimpostera' il carrello
			resp.sendRedirect(req.getContextPath() + "/carrelloController");
		}
		else if (tipoOperazione.equals("modificaQuantita")) {
			String nomeProdotto = req.getParameter("nomeProdotto");
			Integer nuovaQuantita = Integer.parseInt(req.getParameter("quantity"));
			this.modificaQuantita(nomeProdotto, nuovaQuantita);
			// Passo di nuovo alla doGet che reimpostera' il carrello
			resp.sendRedirect(req.getContextPath() + "/carrelloController");
		}	
		
	}
	
	private void eliminaDalCarrello(String nomeProdotto) {	
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Eliminazione
			DBCollection collection = database.getCollection("carrello");
			BasicDBObject deleteQuery = new BasicDBObject();
	        deleteQuery.put("nomeProdotto", nomeProdotto);
	        collection.remove(deleteQuery);
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}	
	}

	public Carrello mostraCarrello() {
		Set<RigaCarrello> result = new HashSet<>();
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("carrello");

			//Lettura
			Gson gson = new Gson();
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            RigaCarrello rc = gson.fromJson(output.toString(), RigaCarrello.class);
	            result.add(rc);
	            ja.put(output);
	        }
	        
	        //manca creare entita Carrello e calcolare subtotale
	        
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
		
		Carrello c = new Carrello();
		c.setRighe(result);
		c.setSubTotale(result.stream().mapToDouble(x -> (x.getPrezzoUnitario() * (1 - x.getSconto())) * x.getQuantitaScelta()).sum());
		
		return c;
	}
	
	public void aggiungiAlCarrello(String nomeProdotto) {
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Trovo il prodotto dal nome inviato dal client
			
			DBCollection collectionP = database.getCollection("prodotti");
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("nome", nomeProdotto);
	        DBObject cursor = collectionP.findOne(searchQuery);
	        Gson gson = new Gson();
	        Prodotto p = gson.fromJson(cursor.toString(), Prodotto.class);
	        
			
			//Verifico se esiste gia nel carrello il prodotto che sto aggiungendo

			DBCollection collectionC = database.getCollection("carrello");
			searchQuery = new BasicDBObject();
			searchQuery.put("nomeProdotto", nomeProdotto);
	        cursor = collectionC.findOne(searchQuery);
	        gson = new Gson();
	        if (cursor != null) { //esiste, allora non ne salvo uno nuovo ma aggiorno solo la quantita'
		        RigaCarrello rigaTrovata = gson.fromJson(cursor.toString(), RigaCarrello.class);
	        	Integer quantitaTrovata = rigaTrovata.getQuantitaScelta();
	        	this.modificaQuantita(nomeProdotto, ++quantitaTrovata);
	        }
	        
	        //Altrimenti lo aggiungo semplicemente con quantita inizializzata a 1
	        
	        else {
		        BasicDBObject document = new BasicDBObject();
		        document.put("idProdotto", p.get_id().toString()); //nelle righe carrello e in linea ordine lo metto come stringa
		        document.put("nomeProdotto", nomeProdotto);
		        document.put("quantitaScelta", 1);
		        document.put("prezzoUnitario", p.getPrezzo());
		        document.put("sconto", p.getSconto());
		        collectionC.insert(document);
		        
		        //Automaticamente mongoDB inserira' anche un campo _id ma nel carrello me ne sbatto altamente, non mi serve
	        }
	 
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
		
	public void modificaQuantita(String nomeProdotto, Integer nuovaQuantita) {
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Modifica
			DBCollection collection = database.getCollection("carrello");
			BasicDBObject query = new BasicDBObject();
	        query.put("nomeProdotto", nomeProdotto.toString());
	        BasicDBObject newDocument = new BasicDBObject();
	        newDocument.put("quantitaScelta", nuovaQuantita);
	        BasicDBObject updateObject = new BasicDBObject();
	        updateObject.put("$set", newDocument);
	        collection.update(query, updateObject);
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

}
