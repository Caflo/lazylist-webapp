package gestioneMagazzino;

import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

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
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");

		//Lettura

		Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();

		
		FindIterable<Document> foundData = collection.find(new Document());
		for (Document d : foundData) {
			Ordine curr = gson.fromJson(d.toJson(), Ordine.class);
			ordiniTotali.getOrdini().add(curr);
			//DEBUG
		    System.out.println(d.toJson().toString());
		}
		
		return ordiniTotali;
	}
	
	private void inserisciOrdine(Ordine o) {

		DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat formatOrario = new SimpleDateFormat("HH:mm"); //uso sempre quello di Date perche' Gson ha problemi con la serializzazione di java.time

		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");
		
		//Inserimento
		Document ordine = new Document();
		ordine.put("idCliente", o.getIdCliente());
		ordine.put("emailCliente", o.getEmailCliente());
		ordine.put("nomeCliente", o.getNomeCliente());
		ordine.put("cognomeCliente", o.getCognomeCliente());
		ordine.put("dataConsegna", formatData.format(o.getDataConsegna()));
		ordine.put("indirizzoConsegna", o.getIndirizzoConsegna());
		ordine.put("CAP", o.getCAP());
		Document fascia = new Document()
				.append("oraInizio", o.getFasciaOraria().getOraInizio())
				.append("oraFine", o.getFasciaOraria().getOraFine())
				.append("costoConsegna", o.getFasciaOraria().getCostoConsegna());
		ordine.put("fasciaOraria", fascia);
		ordine.put("tipoPagamento", o.getTipoPagamento().getTipoPagamento());
		ordine.put("statoOrdine", o.getStatoOrdine().getStato());
		ordine.put("costoTotale", o.getCostoTotale());
		List<Document> linee = new ArrayList<>();
		for (LineaOrdine linea : o.getLineeOrdine()) {
			linee.add(new Document("idProdotto", linea.getIdProdotto())
					.append("nomeProdotto", linea.getNomeProdotto())
					.append("prezzoUnitarioScontato", linea.getPrezzoUnitarioScontato())
					.append("quantitaScelta", linea.getQuantitaScelta()));
		}
		ordine.put("lineeOrdine", linee);

		collection.insertOne(ordine);
		
		System.out.println(ordine.toString());
	
	}
	
	private void aggiornaStatoOrdine(String id) {

		Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");
		
		Document query = new Document();
		query.put("_id", new ObjectId(id));
		Ordine result = gson.fromJson(collection.find(query).first().toJson(), Ordine.class);
		
		result.aggiornaStato();
			  	         
		Document newDocument = new Document();
		newDocument.put("statoOrdine", result.getStatoOrdine().getStato());
		 
		Document updateObject = new Document();
		updateObject.put("$set", newDocument);
		 
		collection.updateOne(query, updateObject);
		
		//Mando email
		this.buildEmailAndSend(result, "ok");
	}
	

	private void rifiutaOrdine(String id) { //si elimina l'ordine
		
		Ordine daRifiutare = null;
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");
				
		//Leggo l'ordine in questione (in particolare mi serve la mail)
		daRifiutare = this.mostraOrdiniTotali().getOrdini().stream().filter(x -> x.get_id().equals(new ObjectId(id))).findFirst().get();
		
		//Eliminazione
		collection.deleteOne(new Document("_id", new ObjectId(id)));
		
		//Mando email
		this.buildEmailAndSend(daRifiutare, "rifiutato");
	}
	
	private void svuotaCarrello() {

		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");
		
		//Svuota tutto
		collection.drop();		
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
