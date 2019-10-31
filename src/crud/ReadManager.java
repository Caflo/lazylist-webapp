package crud;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.Set;

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
import model.ordine.Ordine;
import model.ordine.OrdiniInConsegna;
import model.ordine.OrdiniTotali;
import model.ordine.StoricoOrdiniCliente;
import model.prodottoECarrello.Carrello;
import model.prodottoECarrello.Catalogo;
import model.prodottoECarrello.Magazzino;
import model.prodottoECarrello.Prodotto;
import model.prodottoECarrello.RigaCarrello;
import serializer.OrdineDeserializer;

public class ReadManager {

	
	/*--------------MAGAZZINO-------------*/
	
	public Magazzino readProdotti() {

		Magazzino result = new Magazzino();	
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("prodotti");

			//Lettura
			
			//Necessario registrare la callback siccome Gson non traduce correttamente gli ObjectID di mongoDB
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
	            Prodotto p = gson.fromJson(obj.toString(), Prodotto.class);
	            result.getProdotti().add(p);
	            ja.put(output);
	        }
    
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public OrdiniTotali readOrdiniTotali() {

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
	
	
	public Set<FasciaOraria> readFasceOrarie() {
		
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
	
	public Set<FasciaOraria> readFasceOrariePerGiorno(String giorno) {
		
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
			searchQuery.put("giorno", giorno);
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

	
	/*--------------CLIENTE-------------*/

	public Catalogo readCatalogo() {

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
	        DBCursor cursor = collection.find(searchQuery);
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
	
	public Carrello readCarrello() {
		Set<RigaCarrello> result = new HashSet<>();
		
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			DBCollection collection = database.getCollection("carrello");

			//Lettura
			Gson gson = new Gson();
			JSONArray ja = new JSONArray();
			BasicDBObject searchQuery = new BasicDBObject();
	        DBCursor cursor = collection.find(searchQuery);
	        while (cursor.hasNext()) {
	            DBObject obj = cursor.next();
	            JSONObject output = new JSONObject(JSON.serialize(obj));
	            RigaCarrello rc = gson.fromJson(output.toString(), RigaCarrello.class);
	            result.add(rc);
	            ja.put(output);
	        }
	        
	        //manca creare entita Carrello e calcolare subtotale
	        
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
		
		Carrello c = new Carrello();
		c.setRighe(result);
		c.setSubTotale(result.stream().mapToDouble(x -> (x.getPrezzoUnitario() * (1 - x.getSconto())) * x.getQuantitaScelta()).sum());
		
		return c;
	}
	
	public StoricoOrdiniCliente readStorico() {

		StoricoOrdiniCliente result = new StoricoOrdiniCliente();
		
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
				result.getOrdini().add(curr);
	        }
	        
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
		
		return result;
	}
	
	/*--------------CORRIERE-------------*/
	
	public OrdiniInConsegna readOrdiniInConsegna() {
		OrdiniInConsegna ordiniInConsegna = new OrdiniInConsegna();
		
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
				if (curr.getStatoOrdine().getStato().equals("IN_CONSEGNA"))
					ordiniInConsegna.getOrdini().add(curr);
	        }
	        
	        //DEBUG
	        System.out.println(ja.toString());
		} catch (UnknownHostException | JSONException e) {
			e.printStackTrace();
		}
		return ordiniInConsegna;
	}

}
