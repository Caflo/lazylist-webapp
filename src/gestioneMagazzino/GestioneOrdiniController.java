package gestioneMagazzino;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jabsorb.client.Session;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import com.sun.mail.smtp.SMTPTransport;

import model.ordine.LineaOrdine;
import model.ordine.Ordine;
import model.ordine.OrdiniTotali;
import notificaEmail.EmailController;
import serializer.OrdineDeserializer;

public class GestioneOrdiniController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		//Se sono qua perche' devo inserire un ordine
		Ordine ordine = (Ordine) session.getAttribute("ordineAttuale");
		if (ordine != null) { //lo inserisco e reindirizzo a pagina di successo
			//Aggiorno stato da Attuale a InAttesaConferma
			ordine.aggiornaStato();
			
			//Inserisco
			this.inserisciOrdine(ordine);
			
			//Mando email
			this.buildEmailAndSend(ordine);
			
			//Rimuovo attributo senno' ogni volta che vengo chiamato inserisco un ordine
			session.removeAttribute("ordine");
			
			//Rimango alla pagina di successo
			resp.sendRedirect(req.getContextPath() + "/orderSuccess.jsp");
		}
		else {
			//Altrimenti visualizzo i prodotti del magazzino
			OrdiniTotali ordiniMagazzino = this.mostraOrdiniTotali();
			session.setAttribute("ordini", ordiniMagazzino);
			resp.sendRedirect(req.getContextPath() + "/ordini.jsp");
		}
	}

	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
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
	
	private void buildEmailAndSend(Ordine ordine) {
		EmailController emailController = new EmailController(ordine.getEmailCliente(), "Attesa conferma");
		StringBuilder testo = new StringBuilder();
		testo.append("Ciao! L'ordine e' stato evaso. Ti faremo sapere quando sara' stato accettato.\n");
		testo.append("Intanto ti allego il riepilogo dell'ordine:\n");
		for (LineaOrdine l : ordine.getLineeOrdine()) {
			testo.append("Prodotto: " + l.getNomeProdotto() 
				+ " x " + l.getQuantitaScelta() 
				+ " Prezzo: " + l.getPrezzoUnitarioScontato());
			testo.append("\n");
		}
		testo.append("Totale ordine: &euro;" + ordine.getCostoTotale());
		emailController.setEMAIL_TEXT(testo.toString());
		emailController.mandaEmail();
		
	}
}
