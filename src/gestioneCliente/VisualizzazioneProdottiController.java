package gestioneCliente;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import model.prodottoECarrello.Catalogo;
import model.prodottoECarrello.Prodotto;

public class VisualizzazioneProdottiController extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Catalogo catalogo = this.mostraCatalogo();
		session.setAttribute("catalogo", catalogo);
		resp.sendRedirect(req.getContextPath() + "/index.jsp");
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//FILTRO
		HttpSession session = req.getSession();
		String filtro = req.getParameter("filtro");
		if (filtro.equals("Tutti")) {
			session.removeAttribute("prodottiFiltrati");
			resp.sendRedirect(req.getContextPath() + "/visualizzazioneProdottiController");
		}
		else {
			Set<Prodotto> prodottiFiltrati = this.filtraProdotti(filtro);
			session.setAttribute("prodottiFiltrati", prodottiFiltrati);
			resp.sendRedirect(req.getContextPath() + "/index.jsp");
		}
	}


	private Catalogo mostraCatalogo() {
		
		Set<Prodotto> result = new HashSet<>();
		Catalogo c = new Catalogo();
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("prodotti");

			//Lettura
			Gson gson = new Gson();
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
			BasicDBObject orderBy = new BasicDBObject();
			orderBy.put("nome", 1);
	        DBCursor cursor = collection.find(searchQuery).sort(orderBy);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            Prodotto p = gson.fromJson(output.toString(), Prodotto.class);
	            result.add(p);
	            ja.put(output);
	        }
    
	        
	        c.setProdotti(result);
	        
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
		return c;
	}
	
	private Set<Prodotto> filtraProdotti(String filtro) {

		Set<Prodotto> result = new HashSet<>();
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("prodotti");

			//Lettura
			Gson gson = new Gson();
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("categoria", filtro);
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            Prodotto p = gson.fromJson(output.toString(), Prodotto.class);
	            result.add(p);
	            ja.put(output);
	        }
    	        
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
		return result;
		
	}
}
