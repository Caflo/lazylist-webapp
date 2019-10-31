package testFasceOrarie;

import java.net.UnknownHostException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.TextStyle;
import java.util.Locale;

import org.junit.Test;

import com.mongodb.BasicDBObject;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.MongoClient;

import model.ordine.FasciaOraria;

public class testInserisciFasciaOraria {

	@Test
	public void test() {
		
		DateFormat formatData = new SimpleDateFormat("dd/MM/yyyy");
		DateFormat formatOrario = new SimpleDateFormat("HH:mm"); //uso sempre quello di Date perché Gson ha problemi con la serializzazione di java.time
		final DateTimeFormatter dtf = new DateTimeFormatterBuilder() //mi serve per avere Giovedi al posto di Thursday
		        .appendOptional(DateTimeFormatter.ofPattern("EEEE"))
		        .toFormatter(Locale.ITALIAN);
		
		//Fascia oraria di prova
		FasciaOraria f = new FasciaOraria();
		f.setGiorno(DayOfWeek.FRIDAY.getDisplayName(TextStyle.FULL, Locale.ITALY));
		f.setOraInizio("18:00");
		f.setOraFine("19:00");
		f.setCostoConsegna(2.00);
		
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
}
