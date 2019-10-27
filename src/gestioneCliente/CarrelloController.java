package gestioneCliente;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

//
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import model.prodottoECarrello.Carrello;
import model.prodottoECarrello.Prodotto;
import model.prodottoECarrello.RigaCarrello;

public class CarrelloController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Carrello carrello;
	
	@Override
	public void init() throws ServletException {
		carrello = new Carrello();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// MOSTRO IL CARRELLO
		mostraCarrello();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// LA USO PER 
		// *) Aggiungere i prodotto del carrello dal catalogo
	
		HttpSession session = req.getSession();
		String nomeProdotto = req.getParameter("nomeProdotto");
		this.aggiungiAlCarrello(nomeProdotto);
		resp.sendRedirect(req.getContextPath() + "/index.html");
		
	}
	
	public Carrello mostraCarrello() {
		Carrello carrello = new Carrello();
		Gson gson = new Gson();
		try {
			JsonReader reader = new JsonReader(new FileReader("files/carrello.json"));
			Set<RigaCarrello> righe = gson.fromJson(reader, RigaCarrello.class);
			Double subTotale = righe.stream().mapToDouble(x -> x.getPrezzoUnitario() * x.getQuantitaScelta()).sum(); //lo calcolo
			carrello.setRighe(righe);
			carrello.setSubTotale(subTotale);		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return carrello;
		
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
			
			//Inserimento
			DBCollection collectionC = database.getCollection("carrello");
	        BasicDBObject document = new BasicDBObject();
	        document.put("idProdotto", p.get_id().toString()); //nelle righe carrello e in linea ordine lo metto come stringa
	        document.put("nomeProdotto", nomeProdotto);
	        document.put("quantitaScelta", 1);
	        document.put("prezzoUnitario", p.getPrezzo());
	        document.put("pesoTotaleProdotto", p.getPeso());
	        
	        //Automaticamente mongoDB inserira' anche un campo _id ma nel carrello me ne sbatto altamente, non mi serve

	        collectionC.insert(document);
	        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
		

}
