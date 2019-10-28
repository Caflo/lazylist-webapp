package serializer;

import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.bson.types.ObjectId;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;

import model.ordine.Contanti;
import model.ordine.FasciaOraria;
import model.ordine.LineaOrdine;
import model.ordine.Ordine;
import model.ordine.POS;
import model.ordine.statiOrdine.Attuale;
import model.ordine.statiOrdine.Consegnato;
import model.ordine.statiOrdine.InAttesaConferma;
import model.ordine.statiOrdine.InConsegna;
import model.ordine.statiOrdine.InPreparazione;
import model.ordine.statiOrdine.NonConsegnato;

public class OrdineDeserializer implements JsonDeserializer<Ordine> {

	//Siccome Gson ha problemi con le classi astratte alla deserializzazione devo farne uno custom

	@Override
	public Ordine deserialize(JsonElement json, Type type, JsonDeserializationContext context) throws JsonParseException { 
	
		Ordine result = new Ordine();
		
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		
		JsonObject jo = json.getAsJsonObject();
		
		//
		String idOrdine = jo.get("_id").getAsJsonObject().get("$oid").getAsString();
		ObjectId _id = new ObjectId(idOrdine);
		result.set_id(_id);
		Integer idCliente = jo.get("idCliente").getAsInt();
		result.setIdCliente(idCliente);
		String emailCliente = jo.get("emailCliente").getAsString();
		result.setEmailCliente(emailCliente);
		String nomeCliente = jo.get("nomeCliente").getAsString();
		result.setNomeCliente(nomeCliente);
		String cognomeCliente = jo.get("cognomeCliente").getAsString();
		result.setCognomeCliente(cognomeCliente);
		Date dataConsegna = null;
		try {
			dataConsegna = df.parse(jo.get("dataConsegna").getAsString());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		result.setDataConsegna(dataConsegna);
		String indirizzoConsegna = jo.get("indirizzoConsegna").getAsString();
		result.setIndirizzoConsegna(indirizzoConsegna);
		String CAP = jo.get("CAP").getAsString();
		result.setCAP(CAP);
		FasciaOraria f = new FasciaOraria();
		JsonObject fasciaObj = jo.get("fasciaOraria").getAsJsonObject();
		//Qui nell'ordine per la fascia oraria non considero gli altri due campi
		// (costoConsegna e giorno) perche' il giorno lo ricavo dalla data dell'ordine
		f.setOraInizio(fasciaObj.get("oraInizio").getAsString());
		f.setOraFine(fasciaObj.get("oraFine").getAsString());
		result.setFasciaOraria(f);
		if (jo.get("tipoPagamento").getAsString().equals("CONTANTI"))
			result.setTipoPagamento(new Contanti());
		else
			result.setTipoPagamento(new POS());
		if (jo.get("statoOrdine").getAsString().equals("ATTUALE"))
			result.setStatoOrdine(new Attuale(result));
		else if (jo.get("statoOrdine").getAsString().equals("IN_ATTESA_CONFERMA"))
			result.setStatoOrdine(new InAttesaConferma(result));
		else if (jo.get("statoOrdine").getAsString().equals("IN_PREPARAZIONE"))
			result.setStatoOrdine(new InPreparazione(result));
		else if (jo.get("statoOrdine").getAsString().equals("IN_CONSEGNA"))
			result.setStatoOrdine(new InConsegna(result));
		else if (jo.get("statoOrdine").getAsString().equals("CONSEGNATO"))
			result.setStatoOrdine(new Consegnato(result));
		else if (jo.get("statoOrdine").getAsString().equals("NON_CONSEGNATO"))
			result.setStatoOrdine(new NonConsegnato(result));

		
		Set<LineaOrdine> lineeOrdine = new HashSet<>();
		JsonArray lineeObj = jo.get("lineeOrdine").getAsJsonArray();
		for (int i = 0; i < lineeObj.size(); i++) {
			JsonObject lineaObj = lineeObj.get(i).getAsJsonObject();
			String idProdotto = lineaObj.get("idProdotto").getAsString();
			String nomeProdotto = lineaObj.get("nomeProdotto").getAsString();
			Double prezzoUnitarioScontato = lineaObj.get("prezzoUnitarioScontato").getAsDouble();
			Integer quantitaScelta = lineaObj.get("quantitaScelta").getAsInt();
			LineaOrdine l = new LineaOrdine();
			l.setIdProdotto(idProdotto);
			l.setNomeProdotto(nomeProdotto);
			l.setPrezzoUnitarioScontato(prezzoUnitarioScontato);
			l.setQuantitaScelta(quantitaScelta);
			lineeOrdine.add(l);
		}
		
		Double costoTotale = jo.get("costoTotale").getAsDouble();
		result.setCostoTotale(costoTotale);
		return result;
		
		
	}
	
}
