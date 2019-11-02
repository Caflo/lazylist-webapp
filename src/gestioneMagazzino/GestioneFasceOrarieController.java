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

	private void eliminaFascia(String idFascia) {
		// TODO Auto-generated method stub
		
	}

	private void modificaFascia(FasciaOraria f) {
		// TODO Auto-generated method stub
		
	}

	private void inserisciFascia(FasciaOraria f) {
		// TODO Auto-generated method stub
		
	}
}

	