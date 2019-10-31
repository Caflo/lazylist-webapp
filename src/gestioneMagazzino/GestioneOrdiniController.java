package gestioneMagazzino;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
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
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;

import model.ordine.LineaOrdine;
import model.ordine.Ordine;
import model.ordine.OrdiniTotali;
import notificaEmail.EmailController;
import serializer.OrdineDeserializer;

public class GestioneOrdiniController extends HttpServlet {
	
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
		
		//Se devo inserire un ordine attuale
		Ordine ordine = (Ordine) session.getAttribute("ordineAttuale");
		if (ordine != null) { //lo inserisco e reindirizzo a pagina di successo
			//Aggiorno stato da Attuale a InAttesaConferma
			ordine.aggiornaStato();
		
			//Inserisco
			this.inserisciOrdine(ordine);
			this.svuotaCarrello();
			
			//Mando email
			this.buildEmailAndSend(ordine, "ok");
			
			//Rimuovo attributo senno' ogni volta che vengo chiamato inserisco un ordine
			session.removeAttribute("ordineAttuale");
			
			//Rimango alla pagina di successo
			resp.sendRedirect(req.getContextPath() + "/orderSuccess.jsp");
		}
		else {
			//Altrimenti visualizzo i prodotti del magazzino
			OrdiniTotali ordiniMagazzino = this.mostraOrdiniTotali();
			session.setAttribute("ordini", ordiniMagazzino);
			resp.sendRedirect(req.getContextPath() + "/ordiniMagazzino.jsp");
		}		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// Qui viene passato l'ID dell'ordine e viene aggiornato allo stato successivo
		// Oppure il gestore del magazzino vuole rifiutare una richiesta di ordine
		
		String id = req.getParameter("id");
		String tipoOperazione = req.getParameter("tipoOperazione");
		if (tipoOperazione.equals("Aggiorna stato ordine") || tipoOperazione.equals("Accetta ordine"))
			this.aggiornaStatoOrdine(id);
		else
			this.rifiutaOrdine(id);
		
		resp.sendRedirect(req.getContextPath() + "/gestioneOrdiniController");
		
	}

	private OrdiniTotali mostraOrdiniTotali() {

		OrdiniTotali ordiniTotali = new OrdiniTotali();
		
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
				ordiniTotali.getOrdini().add(curr);
	        }
	        
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
		return ordiniTotali;
	}
	
	private void inserisciOrdine(Ordine o) {

		DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat formatOrario = new SimpleDateFormat("HH:mm"); //uso sempre quello di Date perche' Gson ha problemi con la serializzazione di java.time

		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Inserimento
			DBCollection collection = database.getCollection("ordini");
	        BasicDBObject document = new BasicDBObject();
	        document.put("idCliente", o.getIdCliente());
	        document.put("emailCliente", o.getEmailCliente());
	        document.put("nomeCliente", o.getNomeCliente());
	        document.put("cognomeCliente", o.getCognomeCliente());
	        document.put("dataConsegna", formatData.format(o.getDataConsegna()));
	        document.put("indirizzoConsegna", o.getIndirizzoConsegna());
	        document.put("CAP", o.getCAP());
	        BasicDBObject fascia = new BasicDBObject()
	        		.append("oraInizio", o.getFasciaOraria().getOraInizio())
	        		.append("oraFine", o.getFasciaOraria().getOraFine())
	        		.append("costoConsegna", o.getFasciaOraria().getCostoConsegna());
	        document.put("fasciaOraria", fascia);
	        document.put("tipoPagamento", o.getTipoPagamento().getTipoPagamento());
	        document.put("statoOrdine", o.getStatoOrdine().getStato());
	        document.put("costoTotale", o.getCostoTotale());
	        BasicDBList linee = new BasicDBList();
	        for (LineaOrdine linea : o.getLineeOrdine()) {
	        	linee.add(new BasicDBObject("idProdotto", linea.getIdProdotto())
	        			.append("nomeProdotto", linea.getNomeProdotto())
	        			.append("prezzoUnitarioScontato", linea.getPrezzoUnitarioScontato())
	        			.append("quantitaScelta", linea.getQuantitaScelta()));
	        }
	        document.put("lineeOrdine", linee);

	        collection.insert(document);
	        
	        System.out.println(document.toString());
	        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	
	}
	
	private void aggiornaStatoOrdine(String id) {

		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Lettura ordine
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
	        result.aggiornaStato();

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
	

	private void rifiutaOrdine(String id) { //si elimina l'ordine
		
		Ordine daRifiutare = null;
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Leggo l'ordine in questione (in particolare mi serve la mail)
			daRifiutare = this.mostraOrdiniTotali().getOrdini().stream().filter(x -> x.get_id().equals(new ObjectId(id))).findFirst().get();
			
			//Eliminazione
			DBCollection collection = database.getCollection("ordini");
	        collection.remove(new BasicDBObject("_id", new ObjectId(id)));
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}

		//Mando email
		this.buildEmailAndSend(daRifiutare, "rifiutato");
	}
	
	private void svuotaCarrello() {

		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
						
			//Svuoto il carrello
			DBCollection collection = database.getCollection("carrello");
			BasicDBObject document = new BasicDBObject();
			collection.remove(document);
    
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
