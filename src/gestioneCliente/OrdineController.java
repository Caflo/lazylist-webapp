package gestioneCliente;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

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

import model.ordine.Contanti;
import model.ordine.FasciaOraria;
import model.ordine.LineaOrdine;
import model.ordine.Ordine;
import model.ordine.POS;
import model.ordine.StatoOrdine;
import model.ordine.TipoPagamento;
import model.ordine.statiOrdine.Attuale;
import model.prodottoECarrello.Carrello;
import model.prodottoECarrello.RigaCarrello;

public class OrdineController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		Ordine ordine = new Ordine();
		session.setAttribute("ordine", ordine);
		resp.sendRedirect(req.getContextPath() + "/checkout.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

		HttpSession session = req.getSession();
		String tipoOperazione = req.getParameter("tipoOperazione");
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat dfStandard = new SimpleDateFormat("yyyy-MM-dd");

		//Switch
		if (tipoOperazione.equals("cercaFasce")) { //AJAX
			Date data;
			try {
				data = dfStandard.parse(req.getParameter("data"));
				List<FasciaOraria> fasceDisponibili = this.calcolaFasceDisponibiliPerData(data);

				//Invio ad ajax 
				Gson gson = new Gson();
				String output = gson.toJson(fasceDisponibili); //lo mando in json
				resp.getOutputStream().print(output); //lo restituisco alla callback ajax
				
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		else if (tipoOperazione.equals("calcolaCosto")) { //AJAX
			String oraInizio = req.getParameter("fascia").split("-")[0];
			String oraFine = req.getParameter("fascia").split("-")[1];
			Double subtotale = Double.parseDouble(req.getParameter("subtotale"));
			Double costoConsegna = this.getCostoConsegnaByFascia(oraInizio, oraFine);
			//Sono strumentopoli che ci serviranno piu' tardi
			session.setAttribute("costoConsegna", costoConsegna);
			session.setAttribute("subtotale", subtotale);
			Gson gson = new Gson();
			String output = gson.toJson(costoConsegna); //lo mando in json
			resp.getOutputStream().print(output); //lo restituisco alla callback ajax
		}
		else if (tipoOperazione.equals("Effettua ordine")) { //FORM click su effettua ordine
//			String email = req.getParameter("email");
			String nome = req.getParameter("nome");
			String cognome = req.getParameter("cognome");
			String indirizzo = req.getParameter("indirizzo");
			String CAP = req.getParameter("CAP");
			Date d = null;
			try {
				d = dfStandard.parse(req.getParameter("data"));
			} catch (ParseException e) {
				e.printStackTrace();
			}
			String fasciaOraria = req.getParameter("fascia");
			String tipoPagamento = req.getParameter("tipoPagamento");
			Carrello c = this.mostraCarrello();
			Ordine o = new Ordine();
			o.setIdCliente(1);
			o.setEmailCliente("caflo1997@gmail.com");
			o.setNomeCliente(nome);
			o.setCognomeCliente(cognome);
			o.setIndirizzoConsegna(indirizzo);
			o.setCAP(CAP);
			FasciaOraria f = new FasciaOraria();
			f.setOraInizio(fasciaOraria.split("-")[0]);
			f.setOraFine(fasciaOraria.split("-")[1]);
			//Mi riprendo il costo della consegna ricavato tramite ajax e messo in sessione
			//e anche il subtotale
			Double costoConsegna = (Double) session.getAttribute("costoConsegna"); 
			f.setCostoConsegna(costoConsegna);
			o.setFasciaOraria(f);
			o.setDataConsegna(d);
			TipoPagamento t = null;
			if (tipoPagamento.equals("Contanti")) {
				t = new Contanti();
			}
			else
				t = new POS();
			o.setTipoPagamento(t);
			//inizio con questo stato
			StatoOrdine stato = new Attuale(o);
			for (RigaCarrello riga : c.getRighe()) {
				LineaOrdine l = new LineaOrdine();
				l.setIdProdotto(riga.getIdProdotto());
				l.setNomeProdotto(riga.getNomeProdotto());
				//prende gia' quello scontato, al contrario della RigaCarrello
				l.setPrezzoUnitarioScontato(riga.getPrezzoUnitario() * (1- riga.getSconto()));
				l.setQuantitaScelta(riga.getQuantitaScelta());
				o.getLineeOrdine().add(l);
			}
			o.setStatoOrdine(new Attuale(o));
			Double subtotale = (Double) session.getAttribute("subtotale");
			o.setCostoTotale(subtotale + costoConsegna);
			session.setAttribute("ordineAttuale", o);
			//l'ordine attuale deve essere preso dal gestore del magazzino che conferma l'ordine
			resp.sendRedirect(req.getContextPath() + "/gestioneOrdiniController");
		}
		
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
		    System.out.println(d.toJson().toString());
		}
		
		Carrello c = new Carrello();
		c.setRighe(result);
		c.setSubTotale(result.stream().mapToDouble(x -> (x.getPrezzoUnitario() * (1 - x.getSconto())) * x.getQuantitaScelta()).sum());
		
		return c;
	}
	
	private Double getCostoConsegnaByFascia(String oraInizio, String oraFine) {

		FasciaOraria f = null;
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("fasceOrarie");


		//Lettura
		Gson gson = new Gson();
		Document searchQuery = new Document();
		searchQuery.put("oraInizio", oraInizio);
		searchQuery.put("oraFine", oraFine);
		MongoCursor<Document> cursor = collection.find(searchQuery).cursor();
		f = gson.fromJson(cursor.next().toJson(), FasciaOraria.class);
		return f.getCostoConsegna();
	}

	private List<FasciaOraria> calcolaFasceDisponibiliPerData(Date data) {
		
		SimpleDateFormat simpleDateformat = new SimpleDateFormat("EEEE", Locale.ITALY);
        
		String giornoDellaData = simpleDateformat.format(data);
		
		List<FasciaOraria> result = new ArrayList<>();
		
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("fasceOrarie");

		//Lettura
		Gson gson = new Gson();
		Document searchQuery = new Document();
		searchQuery.put("giorno", giornoDellaData);
		FindIterable<Document> foundData = collection.find(searchQuery);
		for (Document d : foundData) {
		    FasciaOraria curr = gson.fromJson(d.toJson(), FasciaOraria.class);
			result.add(curr);
			//DEBUG
		    System.out.println(d.toJson().toString());
		}
		
		return result;
	}
}
