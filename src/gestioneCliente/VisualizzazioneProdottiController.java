package gestioneCliente;

import static com.mongodb.client.model.Sorts.descending;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

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
import com.mongodb.client.model.Sorts;

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
			List<Prodotto> prodottiFiltrati = this.filtraProdotti(filtro);
			session.setAttribute("prodottiFiltrati", prodottiFiltrati);
			resp.sendRedirect(req.getContextPath() + "/index.jsp");
		}
	}


	private Catalogo mostraCatalogo() {
		
		Catalogo catalogo = new Catalogo();	
		List<Prodotto> result = new ArrayList<>();
		
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
		
		Bson sort = descending("nome");
		MongoCursor<Document> foundData = collection.find(new Document()).sort(Sorts.ascending("nome")).cursor();
		while (foundData.hasNext()) {
			Prodotto curr = gson.fromJson(foundData.next().toJson(), Prodotto.class);
			result.add(curr);
			//DEBUG
		    System.out.println(curr.toString());
		}
		
		catalogo.setProdotti(result);
		return catalogo;

	}
	
	private List<Prodotto> filtraProdotti(String filtro) {

		List<Prodotto> result = new ArrayList<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("prodotti");

		//Lettura
		Gson gson = new Gson();
		Document filter = new Document();
		filter.put("categoria", filtro);
		MongoCursor<Document> foundData = collection.find(filter).sort(Sorts.ascending("nome")).cursor();
		while (foundData.hasNext()) {
			Prodotto curr = gson.fromJson(foundData.next().toJson(), Prodotto.class);
			result.add(curr);
		    System.out.println(curr.toString());
		}
		return result;
		
	}
}
