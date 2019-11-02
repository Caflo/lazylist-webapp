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

import org.bson.Document;
import org.bson.types.ObjectId;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

import model.ordine.LineaOrdine;
import model.ordine.Ordine;
import model.ordine.OrdiniInConsegna;
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
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");

		//Lettura

		Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();

		
		MongoCursor<Document> foundData = collection.find(new Document()).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
			Ordine curr = gson.fromJson(obj.toJson(), Ordine.class);
			ordiniInConsegna.getOrdini().add(curr);
			//DEBUG
		    System.out.println(curr.toString());
		}	
		return ordiniInConsegna;
	}


	private void aggiornaNonConsegnato(String id) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");
		
		//Lettura
		Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

			@Override
			public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
					throws JsonParseException {
				// TODO Auto-generated method stub
				return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
			}
			
		}).registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();
		
		Document query = new Document();
		query.put("_id", new ObjectId(id));
		Ordine result = gson.fromJson(collection.find(query).cursor().next().toJson(), Ordine.class);
		result.setStatoOrdine(new NonConsegnato(result));

		//Modifica
		Document newDocument = new Document();
		newDocument.put("statoOrdine", result.getStatoOrdine().getStato());
		 
		Document updateObject = new Document();
		updateObject.put("$set", newDocument);
		 
		collection.updateOne(query, updateObject);
		
     //Mando email
		this.buildEmailAndSend(result, "ok");
		
	}


	private void aggiornaConsegnato(String id) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");
		
		//Lettura
		Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

			@Override
			public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
					throws JsonParseException {
				// TODO Auto-generated method stub
				return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
			}
			
		}).registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();
		
		Document query = new Document();
		query.put("_id", new ObjectId(id));
		Ordine result = gson.fromJson(collection.find(query).cursor().next().toJson(), Ordine.class);
		result.setStatoOrdine(new Consegnato(result));

		//Modifica
		Document newDocument = new Document();
		newDocument.put("statoOrdine", result.getStatoOrdine().getStato());
		 
		Document updateObject = new Document();
		updateObject.put("$set", newDocument);
		 
		collection.updateOne(query, updateObject);
		
     //Mando email
		this.buildEmailAndSend(result, "ok");
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
