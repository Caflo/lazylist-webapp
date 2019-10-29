package testOrdine;

import static org.junit.Assert.fail;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import org.junit.Test;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import model.ordine.Contanti;
import model.ordine.FasciaOraria;
import model.ordine.LineaOrdine;
import model.ordine.Ordine;
import model.ordine.POS;
import model.ordine.StatoOrdine;
import model.ordine.TipoPagamento;
import model.ordine.statiOrdine.Attuale;
import model.ordine.statiOrdine.Consegnato;
import model.ordine.statiOrdine.InConsegna;
import model.ordine.statiOrdine.InPreparazione;
import model.ordine.statiOrdine.NonConsegnato;
import model.prodottoECarrello.RigaCarrello;

public class testInserisciOrdine {
	
	@Test
	public void test() throws ParseException {
		
		DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat formatOrario = new SimpleDateFormat("HH:mm"); //uso sempre quello di Date perché Gson ha problemi con la serializzazione di java.time

		//Cose che servono per costruire l'ordine
		FasciaOraria f = new FasciaOraria();
		f.setGiorno("giovedi");
		f.setOraInizio("16:00");
		f.setOraFine("16:30");
		f.setCostoConsegna(2.50);
		
		TipoPagamento tipoPagamento = new Contanti();
		
		List<LineaOrdine> lineeOrdine = new ArrayList<>();
		LineaOrdine l1 = new LineaOrdine();
		l1.setIdProdotto("5db576278949af9ff39abbf2");
		l1.setNomeProdotto("Rigatoni");
		l1.setPrezzoUnitarioScontato(2.50);
		l1.setQuantitaScelta(2);
		LineaOrdine l2 = new LineaOrdine();
		l2.setIdProdotto("5db576278949af9questoefalso");
		l2.setNomeProdotto("Mozzarella Santa Lucia");
		l2.setPrezzoUnitarioScontato(3.00);
		l2.setQuantitaScelta(1);
		lineeOrdine.add(l1);
		lineeOrdine.add(l2);
				
		//Creo l'ordine di prova
		Ordine o = new Ordine();
		o.setIdCliente(1);
		o.setEmailCliente("caflo1997@gmail.com");
		o.setNomeCliente("Mario");
		o.setCognomeCliente("Rossi");
		o.setDataConsegna(formatData.parse("22/09/2019"));
		o.setIndirizzoConsegna("Via degli acaci, 13");
		o.setCAP("40100");
		o.setFasciaOraria(f);
		o.setLineeOrdine(lineeOrdine);
		o.setTipoPagamento(tipoPagamento);
		o.setStatoOrdine(new InPreparazione(o));
		o.setCostoTotale(lineeOrdine.stream().mapToDouble(x -> x.getPrezzoUnitarioScontato() * x.getQuantitaScelta()).sum() + f.getCostoConsegna());

				
		try {
			MongoClient mongoClient = new MongoClient("localhost" , 27017);
			DB database = mongoClient.getDB("testDB");
			
			//Inserimento
			DBCollection collection = database.getCollection("ordini");
	        BasicDBObject document = new BasicDBObject();
	        document.put("idCliente", o.getIdCliente());
	        document.put("emailCliente", o.getEmailCliente());
	        document.put("nomeCliente", o.getNomeCliente());
	        document.put("cognomeCliente", o.getCognomeCliente());
	        document.put("dataConsegna", formatData.format(o.getDataConsegna()));
	        document.put("indirizzoConsegna", o.getIndirizzoConsegna());
	        document.put("CAP", o.getCAP());
	        BasicDBObject fascia = new BasicDBObject()
	        		.append("oraInizio", o.getFasciaOraria().getOraInizio())
	        		.append("oraFine", o.getFasciaOraria().getOraFine())
	        		.append("costoConsegna", o.getFasciaOraria().getCostoConsegna());
	        document.put("fasciaOraria", fascia);
	        document.put("tipoPagamento", o.getTipoPagamento().getTipoPagamento());
	        document.put("statoOrdine", o.getStatoOrdine().getStato());
	        document.put("costoTotale", o.getCostoTotale());
	        BasicDBList linee = new BasicDBList();
	        for (LineaOrdine linea : o.getLineeOrdine()) {
	        	linee.add(new BasicDBObject("idProdotto", linea.getIdProdotto())
	        			.append("nomeProdotto", linea.getNomeProdotto())
	        			.append("prezzoUnitarioScontato", linea.getPrezzoUnitarioScontato())
	        			.append("quantitaScelta", linea.getQuantitaScelta()));
	        }
	        document.put("lineeOrdine", linee);

	        collection.insert(document);
	        
	        System.out.println(document.toString());
	        
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}		
}
