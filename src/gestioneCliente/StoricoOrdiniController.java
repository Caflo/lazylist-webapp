package gestioneCliente;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.ordine.Ordine;
import model.ordine.StoricoOrdiniCliente;
import serializer.OrdineDeserializer;

public class StoricoOrdiniController extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		HttpSession session = req.getSession();
		StoricoOrdiniCliente storico = mostraStorico();
		session.setAttribute("storicoOrdini", storico);
		resp.sendRedirect(req.getContextPath() + "/storico.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	private StoricoOrdiniCliente mostraStorico() {
		
		StoricoOrdiniCliente result = new StoricoOrdiniCliente();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");

		//Lettura

		Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();

		FindIterable<Document> foundData = collection.find(new Document());
		for (Document d : foundData) {
			Ordine curr = gson.fromJson(d.toJson(), Ordine.class);
			result.getOrdini().add(curr);
			//DEBUG
		    System.out.println(d.toJson().toString());
		}
		return result;
	}
}
