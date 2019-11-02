package crud;

import java.lang.reflect.Type;
import java.net.UnknownHostException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bson.Document;
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
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;

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

		Magazzino magazzino = new Magazzino();	
		Set<Prodotto> result = new HashSet<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");

		//Lettura
		Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

			@Override
			public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
					throws JsonParseException {
				// TODO Auto-generated method stub
				return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
			}
			
		}).create();
		
		MongoCursor<Document> foundData = collection.find(new Document()).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
			Prodotto curr = gson.fromJson(obj.toJson(), Prodotto.class);
			result.add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}
		
		magazzino.setProdotti(result);
		return magazzino;
	}
	
	public OrdiniTotali readOrdiniTotali() {

		OrdiniTotali ordiniTotali = new OrdiniTotali();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");

		//Lettura

		Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();

		
		MongoCursor<Document> foundData = collection.find(new Document()).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
			Ordine curr = gson.fromJson(obj.toJson(), Ordine.class);
			ordiniTotali.getOrdini().add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}
		
		return ordiniTotali;
	}
	
	
	public Set<FasciaOraria> readFasceOrarie() {
	
		Set<FasciaOraria> result = new HashSet<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("fasceOrarie");

		//Lettura
		Gson gson = new Gson();
		MongoCursor<Document> foundData = collection.find(new Document()).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
		    FasciaOraria curr = gson.fromJson(obj.toJson(), FasciaOraria.class);
			result.add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}	
		return result;
	}
	
	public Set<FasciaOraria> readFasceOrariePerGiorno(String giorno) {
		
		Set<FasciaOraria> result = new HashSet<>();
				
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("fasceOrarie");

		//Lettura
		Gson gson = new Gson();
		Document query = new Document();
		query.put("giorno", giorno);
		MongoCursor<Document> foundData = collection.find(query).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
		    FasciaOraria curr = gson.fromJson(obj.toJson(), FasciaOraria.class);
			result.add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}
		
		return result;
	}

	
	/*--------------CLIENTE-------------*/

	public Catalogo readCatalogo() {

		Catalogo catalogo = new Catalogo();	
		Set<Prodotto> result = new HashSet<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");

		//Lettura
		Gson gson = new GsonBuilder().registerTypeAdapter(ObjectId.class, new JsonDeserializer<ObjectId>() {

			@Override
			public ObjectId deserialize(JsonElement arg0, Type arg1, JsonDeserializationContext arg2)
					throws JsonParseException {
				// TODO Auto-generated method stub
				return new ObjectId(arg0.getAsJsonObject().get("$oid").getAsString());
			}
			
		}).create();
		
		MongoCursor<Document> foundData = collection.find(new Document()).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
			Prodotto curr = gson.fromJson(obj.toJson(), Prodotto.class);
			result.add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}
		
		catalogo.setProdotti(result);
		return catalogo;
	}
	
	public Carrello readCarrello() {
		Set<RigaCarrello> result = new HashSet<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("carrello");

		//Lettura
		Gson gson = new Gson();
		MongoCursor<Document> foundData = collection.find(new Document()).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
		    RigaCarrello curr = gson.fromJson(obj.toJson(), RigaCarrello.class);
			result.add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}
		
		Carrello c = new Carrello();
		c.setRighe(result);
		c.setSubTotale(result.stream().mapToDouble(x -> (x.getPrezzoUnitario() * (1 - x.getSconto())) * x.getQuantitaScelta()).sum());
		
		return c;
	}
	
	public StoricoOrdiniCliente readStorico() {

		StoricoOrdiniCliente result = new StoricoOrdiniCliente();
				
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");

		//Lettura

		Gson gson = new GsonBuilder().registerTypeAdapter(Ordine.class, new OrdineDeserializer()).create();

		
		MongoCursor<Document> foundData = collection.find(new Document()).cursor();
		while (foundData.hasNext()) {
		    Document obj = foundData.next();
			Ordine curr = gson.fromJson(obj.toJson(), Ordine.class);
			result.getOrdini().add(curr);
			//DEBUG
		    System.out.println(obj.toJson().toString());
		}
		return result;
	}
	
	/*--------------CORRIERE-------------*/
	
	public OrdiniInConsegna readOrdiniInConsegna() {
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
		    System.out.println(obj.toJson().toString());
		}		
		return ordiniInConsegna;
	}

}
