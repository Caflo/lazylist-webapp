package gestioneCliente;

import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
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

import model.ordine.FasciaOraria;
import model.ordine.Ordine;

public class OrdineController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Ordine ordine = new Ordine();
		session.setAttribute("ordine", ordine);
		resp.sendRedirect(req.getContextPath() + "/checkout.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		String tipoOperazione = req.getParameter("tipoOperazione");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

		//Switch
		if (tipoOperazione.equals("cercaFasce")) {
			Date data;
			try {
				data = df.parse(req.getParameter("data"));
				Set<FasciaOraria> fasceDisponibili = this.calcolaFasceDisponibiliPerData(data);

				//Invio ad ajax 
				Gson gson = new Gson();
				String output = gson.toJson(fasceDisponibili); //lo mando in json
				resp.getOutputStream().print(output); //lo restituisco alla callback ajax
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		else if (tipoOperazione.equals("calcolaCosto")) {
			String oraInizio = req.getParameter("fascia").split("-")[0];
			String oraFine = req.getParameter("fascia").split("-")[1];
			Double costoConsegna = this.getCostoConsegnaByFascia(oraInizio, oraFine);
			Gson gson = new Gson();
			String output = gson.toJson(costoConsegna); //lo mando in json
			resp.getOutputStream().print(output); //lo restituisco alla callback ajax
		}
	}

	private Double getCostoConsegnaByFascia(String oraInizio, String oraFine) {

		FasciaOraria f = null;
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("fasceOrarie");

			//Lettura
			Gson gson = new Gson();
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("oraInizio", oraInizio);
			searchQuery.put("oraFine", oraFine);
	        DBObject cursor = collection.findOne(searchQuery);
	        f = gson.fromJson(cursor.toString(), FasciaOraria.class);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return f.getCostoConsegna();
	}

	private Set<FasciaOraria> calcolaFasceDisponibiliPerData(Date data) {
		
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", Locale.ITALY);
        
		String giornoDellaData = simpleDateformat.format(data);
		
		Set<FasciaOraria> result = new HashSet<>();
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("fasceOrarie");

			//Lettura
			Gson gson = new Gson();
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
			searchQuery.put("giorno", giornoDellaData);
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            FasciaOraria fo = gson.fromJson(output.toString(), FasciaOraria.class);
	            result.add(fo);
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
