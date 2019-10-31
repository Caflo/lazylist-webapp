package gestioneMagazzino;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.HashSet;
import java.util.Locale;
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

import model.ordine.FasciaOraria;

public class GestioneFasceOrarieController extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		//ancora in fase di sviluppo
		resp.sendRedirect(req.getContextPath() + "/comingSoon.jsp");
		
		//codice da sostituire appena si concretizza:
//		HttpSession session = req.getSession();
//		Set<FasciaOraria> fasce = this.mostraFasceOrarie();
//		session.setAttribute("fasce", fasce);
//		
//		resp.sendRedirect(req.getContextPath() + "/fasceOrarie.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		String tipoOperazione = req.getParameter("tipoOperazione");
		String idFascia = req.getParameter("id");
		String oraInizio = req.getParameter("oraInizio");
		String oraFine = req.getParameter("oraFine");
		Double costoConsegna = Double.parseDouble(req.getParameter("costoConsegna"));
		String giorno = req.getParameter("giorno");
		
		FasciaOraria f = new FasciaOraria();
		f.set_id(new ObjectId(idFascia));
		f.setOraInizio(oraInizio);
		f.setOraFine(oraFine);
		f.setGiorno(giorno);
		f.setCostoConsegna(costoConsegna);
		
		//Switch
		if (tipoOperazione.equals("Salva"))
			this.inserisciFascia(f);
		else if (tipoOperazione.equals("Modifica"))
			this.modificaFascia(f);
		else if (tipoOperazione.equals("Elimina"))
			this.eliminaFascia(idFascia);
		
		resp.sendRedirect(req.getContextPath() + "gestioneFasceOrarieController");
		
	}

	private void inserisciFascia(FasciaOraria f) {
		
		DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat formatOrario = new SimpleDateFormat("HH:mm"); //uso sempre quello di Date perche' Gson ha problemi con la serializzazione di java.time
		final DateTimeFormatter dtf = new DateTimeFormatterBuilder() //mi serve per avere Giovedi al posto di Thursday
		        .appendOptional(DateTimeFormatter.ofPattern("EEEE"))
		        .toFormatter(Locale.ITALIAN);
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Inserimento
			DBCollection collection = database.getCollection("fasceOrarie");
	        BasicDBObject document = new BasicDBObject();
		    document.put("oraInizio", f.getOraInizio());
		    document.put("oraFine", f.getOraFine());
		    document.put("giorno", f.getGiorno());
		    document.put("costoConsegna", f.getCostoConsegna());

		    collection.insert(document);
	        
	        System.out.println(document.toString());
	        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	private Set<FasciaOraria> mostraFasceOrarie() {

		Set<FasciaOraria> result = new HashSet<>();
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("fasceOrarie");

			//Lettura (anche qui, siccome c'e' l'id, serve deserializzarlo correttamente
			Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

				@Override
				public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
						throws JsonParseException {
					// TODO Auto-generated method stub
					return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
				}
				
			}).create();
			
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            FasciaOraria rc = gson.fromJson(output.toString(), FasciaOraria.class);
	            result.add(rc);
	            ja.put(output);
	        }
	             
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}		
		return result;
	}
	
	private void eliminaFascia(String id) {

		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Eliminazione
			DBCollection collection = database.getCollection("fasceOrarie");
			BasicDBObject deleteQuery = new BasicDBObject();
	        deleteQuery.put("_id", new ObjectId(id));
	        collection.remove(deleteQuery);
    
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}

	private void modificaFascia(FasciaOraria f) {
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
				
			DBCollection collection = database.getCollection("prodotti");
			
	       
	       
			BasicDBObject query = new BasicDBObject();
        	query.put("_id", f.get_id());
        	 BasicDBObject document = new BasicDBObject();
 		    document.put("oraInizio", f.getOraInizio());
 		    document.put("oraFine", f.getOraFine());
 		    document.put("giorno", f.getGiorno());
 		    document.put("costoConsegna", f.getCostoConsegna());
	        BasicDBObject updateObject = new BasicDBObject();
	        updateObject.put("$set", document);
	        collection.update(query, updateObject);
		        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
	}
	
}
