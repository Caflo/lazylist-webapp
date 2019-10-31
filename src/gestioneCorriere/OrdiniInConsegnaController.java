package gestioneCorriere;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
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

import model.ordine.LineaOrdine;
import model.ordine.Ordine;
import model.ordine.OrdiniInConsegna;
import model.ordine.OrdiniTotali;
import model.ordine.statiOrdine.Consegnato;
import model.ordine.statiOrdine.NonConsegnato;
import notificaEmail.EmailController;
import serializer.OrdineDeserializer;

public class OrdiniInConsegnaController extends HttpServlet {


	private static final long serialVersionUID = 1L;
	
	private Set<String> whiteList;

	@Override
	public void init() throws ServletException {

		whiteList = new HashSet<>();
		//se vuoi aggiungere il tuo indirizzo email, aggiungi una riga qui
		//non fare casini
		whiteList.add("caflo1997@gmail.com");
	}


	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		
		OrdiniInConsegna ordiniInConsegna = this.mostraOrdiniInConsegna();
		session.setAttribute("ordiniInConsegna", ordiniInConsegna);
		resp.sendRedirect(req.getContextPath() + "/ordiniInConsegna.jsp");
		
	}


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Qui viene passato l'ID dell'ordine e viene aggiornato allo stato successivo
		//(Consegnato o non consegnato)
		
		String id = req.getParameter("id");
		String tipoOperazione = req.getParameter("tipoOperazione");
		if (tipoOperazione.equals("Consegnato"))
			this.aggiornaConsegnato(id);
		else if (tipoOperazione.equals("Non consegnato"))
			this.aggiornaNonConsegnato(id);
		
		resp.sendRedirect(req.getContextPath() + "/ordiniInConsegnaController");
		
	}

	private OrdiniInConsegna mostraOrdiniInConsegna() {
		OrdiniInConsegna ordiniInConsegna = new OrdiniInConsegna();
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("ordini");

			//Lettura
		
			Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();

			
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            ja.put(output);
				Ordine curr = gson.fromJson(obj.toString(), Ordine.class);
				if (curr.getStatoOrdine().getStato().equals("IN_CONSEGNA"))
					ordiniInConsegna.getOrdini().add(curr);
	        }
	        
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
		return ordiniInConsegna;
	}


	private void aggiornaNonConsegnato(String id) {
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Lettura
			Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

				@Override
				public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
						throws JsonParseException {
					// TODO Auto-generated method stub
					return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
				}
				
			}).registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();
			
			DBCollection collection = database.getCollection("ordini");
			BasicDBObject query = new BasicDBObject();
	        query.put("_id", new ObjectId(id));
	        Ordine result = gson.fromJson(collection.findOne(query).toString(), Ordine.class);
	        result.setStatoOrdine(new NonConsegnato(result));

			//Modifica
			query = new BasicDBObject();
	        query.put("_id", new ObjectId(id));
	        BasicDBObject newDocument = new BasicDBObject();
	        newDocument.put("statoOrdine", result.getStatoOrdine().getStato());
	        BasicDBObject updateObject = new BasicDBObject();
	        updateObject.put("$set", newDocument);
	        collection.update(query, updateObject);
	        
	      //Mando email
			this.buildEmailAndSend(result, "ok");
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}


	private void aggiornaConsegnato(String id) {
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Lettura
			Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

				@Override
				public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
						throws JsonParseException {
					// TODO Auto-generated method stub
					return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
				}
				
			}).registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();
			
			DBCollection collection = database.getCollection("ordini");
			BasicDBObject query = new BasicDBObject();
	        query.put("_id", new ObjectId(id));
	        Ordine result = gson.fromJson(collection.findOne(query).toString(), Ordine.class);
	        result.setStatoOrdine(new Consegnato(result));

			//Modifica
			query = new BasicDBObject();
	        query.put("_id", new ObjectId(id));
	        BasicDBObject newDocument = new BasicDBObject();
	        newDocument.put("statoOrdine", result.getStatoOrdine().getStato());
	        BasicDBObject updateObject = new BasicDBObject();
	        updateObject.put("$set", newDocument);
	        collection.update(query, updateObject);
	        
	      //Mando email
			this.buildEmailAndSend(result, "ok");
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
	private void buildEmailAndSend(Ordine ordine, String azione) {
		DecimalFormat decF = new DecimalFormat("0.00");
		EmailController emailController = new EmailController(ordine.getEmailCliente(), whiteList);
		emailController.setEnabled(true);
		StringBuilder testo = new StringBuilder();
		if (azione.equals("ok")) {
			switch(ordine.getStatoOrdine().getStato()) {
			case "IN_ATTESA_CONFERMA":
				emailController.setEMAIL_SUBJECT("Richiesta ordine");
				testo.append("Ciao " + ordine.getNomeCliente() + "! Grazie per aver ordinato su Lazylist! L'ordine e' stato evaso. Ti faremo sapere quando sara' stato accettato.\n");
			break;
			case "IN_PREPARAZIONE":
				emailController.setEMAIL_SUBJECT("Ordine in preparazione");
				testo.append("Ciao " + ordine.getNomeCliente() + "! L'ordine e' stato accettato.\n");
				testo.append("\nRiepilogo dell'ordine:\n");
				for (LineaOrdine l : ordine.getLineeOrdine()) {
					testo.append("Prodotto: " + l.getNomeProdotto() 
						+ " QNT. " + l.getQuantitaScelta() 
						+ "    EUR: " + decF.format(l.getPrezzoUnitarioScontato()));
					testo.append("\n");
				}
				testo.append("Costo spedizione: EUR " + decF.format(ordine.getFasciaOraria().getCostoConsegna()));
				testo.append("\nTotale ordine: EUR " + decF.format(ordine.getCostoTotale()));
			break;
			case "IN_CONSEGNA":
				emailController.setEMAIL_SUBJECT("Ordine in consegna");
				testo.append("Ciao " + ordine.getNomeCliente() + "! Il fattorino ha appena lasciato il negozio, a breve verra' consegnato l'ordine.\n");
				testo.append("\nRiepilogo dell'ordine:\n");
				for (LineaOrdine l : ordine.getLineeOrdine()) {
					testo.append("Prodotto: " + l.getNomeProdotto() 
						+ " QNT. " + l.getQuantitaScelta() 
						+ "    EUR: " + decF.format(l.getPrezzoUnitarioScontato()));
					testo.append("\n");
				}
				testo.append("Costo spedizione: EUR " + decF.format(ordine.getFasciaOraria().getCostoConsegna()));
				testo.append("\nTotale ordine: EUR " + decF.format(ordine.getCostoTotale()));
			break;
			case "CONSEGNATO":
				emailController.setEMAIL_SUBJECT("Ordine consegnato");
				testo.append("Ciao " + ordine.getNomeCliente() + "! Il tuo ordine e' appena stato consegnato. Ti auguriamo buona giornata.");
			break;
			case "NON_CONSEGNATO":
				emailController.setEMAIL_SUBJECT("Ordine non consegnato");
				testo.append("Ciao " + ordine.getNomeCliente() + "! Ci sono stati dei problemi? Contattaci sul nostro indirizzo email presente sul nostro sito!");
			break;
			}
		}
		
		else if (azione.equals("rifiutato")) {
			emailController.setEMAIL_SUBJECT("Ordine rifiutato");
			testo.append("Ciao! Ti comunichiamo purtroppo che il tuo ordine e' stato rifiutato. Se hai avuto problemi con la lista della spesa, contattaci sul nostro sito!\n");			
		}
		
		emailController.setEMAIL_TEXT(testo.toString());
		emailController.mandaEmail();
		
	}

}
