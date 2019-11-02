package testOrdine;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.junit.Test;


import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import model.ordine.Contanti;
import model.ordine.FasciaOraria;
import model.ordine.LineaOrdine;
import model.ordine.Ordine;
import model.ordine.TipoPagamento;
import model.ordine.statiOrdine.InPreparazione;

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

				
		MongoClient mongoClient = MongoClients.create();
		MongoDatabase database = mongoClient.getDatabase("testDB");
		MongoCollection<Document> collection = database.getCollection("ordini");
		
		//Inserimento
		Document ordine = new Document();
		ordine.put("idCliente", o.getIdCliente());
		ordine.put("emailCliente", o.getEmailCliente());
		ordine.put("nomeCliente", o.getNomeCliente());
		ordine.put("cognomeCliente", o.getCognomeCliente());
		ordine.put("dataConsegna", formatData.format(o.getDataConsegna()));
		ordine.put("indirizzoConsegna", o.getIndirizzoConsegna());
		ordine.put("CAP", o.getCAP());
		Document fascia = new Document()
				.append("oraInizio", o.getFasciaOraria().getOraInizio())
				.append("oraFine", o.getFasciaOraria().getOraFine())
				.append("costoConsegna", o.getFasciaOraria().getCostoConsegna());
		ordine.put("fasciaOraria", fascia);
		ordine.put("tipoPagamento", o.getTipoPagamento().getTipoPagamento());
		ordine.put("statoOrdine", o.getStatoOrdine().getStato());
		ordine.put("costoTotale", o.getCostoTotale());
		List<Document> linee = new ArrayList<>();
		for (LineaOrdine linea : o.getLineeOrdine()) {
			linee.add(new Document("idProdotto", linea.getIdProdotto())
					.append("nomeProdotto", linea.getNomeProdotto())
					.append("prezzoUnitarioScontato", linea.getPrezzoUnitarioScontato())
					.append("quantitaScelta", linea.getQuantitaScelta()));
		}
		ordine.put("lineeOrdine", linee);

		collection.insertOne(ordine);
		
		System.out.println(ordine.toString());
	}		
}
