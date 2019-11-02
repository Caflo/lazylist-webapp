package gestioneCliente;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

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
			//AJAX
			String nomeProdotto = req.getParameter("nomeProdotto");
			this.aggiungiAlCarrello(nomeProdotto);
			Integer totCarrello = (Integer) session.getAttribute("totCarrello");
			session.setAttribute("totCarrello", ++totCarrello);
			//Non rispondo con niente, non mi serve niente
		}
		else if (tipoOperazione.equals("eliminaDalCarrello")) {
			String nomeProdotto = req.getParameter("nomeProdotto");
			Integer qnt = this.readQuantita(nomeProdotto);
			this.eliminaDalCarrello(nomeProdotto);
			Integer totCarrello = (Integer) session.getAttribute("totCarrello");
			session.setAttribute("totCarrello", totCarrello - qnt);
		}
		else if (tipoOperazione.equals("modificaQuantita")) {
			String nomeProdotto = req.getParameter("nomeProdotto");
			Integer nuovaQuantita = Integer.parseInt(req.getParameter("quantity"));
			Integer vecchiaQuantita = this.readQuantita(nomeProdotto);
			this.modificaQuantita(nomeProdotto, nuovaQuantita);
			Integer totCarrello = (Integer) session.getAttribute("totCarrello");
			if (nuovaQuantita > vecchiaQuantita) {
				Integer amount = Math.abs(nuovaQuantita - vecchiaQuantita);
				session.setAttribute("totCarrello", totCarrello + amount);
			}
			else if (nuovaQuantita < vecchiaQuantita) {
				Integer amount = Math.abs(nuovaQuantita - vecchiaQuantita);
				session.setAttribute("totCarrello", totCarrello - amount);
			}
		}	
		else if (tipoOperazione.equals("Svuota carrello")) {
			this.svuotaCarrello();
			session.setAttribute("totCarrello", 0);
		}
		
		// Passo di nuovo alla doGet che reimpostera' il carrello
		resp.sendRedirect(req.getContextPath() + "/carrelloController");
		
	}
	
	private void svuotaCarrello() {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");
		
		//Svuota tutto
		collection.drop();	
	}

	private Integer readQuantita(String nomeProdotto) {
		Integer result = null;
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");

		//Lettura
		Gson gson = new Gson();
		Document searchQuery = new Document();
		searchQuery.put("nomeProdotto", nomeProdotto);
		Integer quantita = (Integer) collection.find(searchQuery).first().get("quantitaScelta");
		result = quantita;
			
		return result;
	}

	private void eliminaDalCarrello(String nomeProdotto) {	
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");
		
		//Eliminazione
		Document deleteQuery = new Document();
		deleteQuery.put("nomeProdotto", nomeProdotto);
		collection.deleteOne(deleteQuery);	
	}

	public Carrello mostraCarrello() {
		List<RigaCarrello> result = new ArrayList<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");

		//Lettura
		Gson gson = new Gson();
		FindIterable<Document> foundData = collection.find(new Document());
		for (Document d : foundData) {
		    RigaCarrello curr = gson.fromJson(d.toJson(), RigaCarrello.class);
			result.add(curr);
			//DEBUG
		    System.out.println(curr.toString());
		}
		
		Carrello c = new Carrello();
		c.setRighe(result);
		c.setSubTotale(result.stream().mapToDouble(x -> (x.getPrezzoUnitario() * (1 - x.getSconto())) * x.getQuantitaScelta()).sum());
		
		return c;
	}
	
	public void aggiungiAlCarrello(String nomeProdotto) {
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");
		
		//Trovo il prodotto dal nome inviato dal client
		
		Document searchQuery = new Document();
		searchQuery.put("nome", nomeProdotto);
		MongoCursor<Document> cursor = collection.find(searchQuery).cursor();
		Gson gson = new Gson();
		Prodotto p = gson.fromJson(cursor.next().toJson(), Prodotto.class);
		
		
		//Verifico se esiste gia nel carrello il prodotto che sto aggiungendo

		collection = database.getCollection("carrello");
		searchQuery = new Document();
		searchQuery.put("nomeProdotto", nomeProdotto);
		cursor = collection.find(searchQuery).cursor();
		gson = new Gson();
		if (cursor.hasNext()) { //esiste, allora non ne salvo uno nuovo ma aggiorno solo la quantita'
		    RigaCarrello rigaTrovata = gson.fromJson(cursor.next().toJson(), RigaCarrello.class);
			Integer quantitaTrovata = rigaTrovata.getQuantitaScelta();
			this.modificaQuantita(nomeProdotto, ++quantitaTrovata);
		}
		
		//Altrimenti lo aggiungo semplicemente con quantita inizializzata a 1
		
		else {
		    Document document = new Document();
		    document.put("idProdotto", p.get_id().toString()); //nelle righe carrello e in linea ordine lo metto come stringa
		    document.put("imagePath", p.getImagePath());
		    document.put("nomeProdotto", nomeProdotto);
		    document.put("quantitaScelta", 1);
		    document.put("prezzoUnitario", p.getPrezzo());
		    document.put("sconto", p.getSconto());
		    collection.insertOne(document);
		    
		    //Automaticamente mongoDB inserira' anche un campo _id ma nel carrello me ne sbatto altamente, non mi serve
		}
		
	}
		
	public void modificaQuantita(String nomeProdotto, Integer nuovaQuantita) {
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");
		
		//Modifica
		Document query = new Document();
		query.put("nomeProdotto", nomeProdotto);
		Document newDocument = new Document();
		newDocument.put("quantitaScelta", nuovaQuantita);
		Document updateObject = new Document();
		updateObject.put("$set", newDocument);
		collection.updateOne(query, updateObject);
	}

}
