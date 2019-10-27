package gestioneCliente;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.Stream;

//
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import model.prodottoECarrello.Carrello;
import model.prodottoECarrello.Prodotto;
import model.prodottoECarrello.RigaCarrello;

public class CarrelloController extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private Carrello carrello;
	
	@Override
	public void init() throws ServletException {
		carrello = new Carrello();
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// MOSTRO IL CARRELLO
		mostraCarrello();
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		super.doPost(req, resp);
	}
	
	public Carrello mostraCarrello() {
		Carrello carrello = new Carrello();
		Gson gson = new Gson();
		try {
			JsonReader reader = new JsonReader(new FileReader("files/carrello.json"));
			Set<RigaCarrello> righe = gson.fromJson(reader, RigaCarrello.class);
			Double subTotale = righe.stream().mapToDouble(x -> x.getPrezzoUnitario() * x.getQuantitaScelta()).sum(); //lo calcolo
			carrello.setRighe(righe);
			carrello.setSubTotale(subTotale);		
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return carrello;
		
	}
	
	public Boolean aggiungiAlCarrello(Prodotto p) {
		Boolean result = Boolean.FALSE;
		Gson gson = new Gson();
		try {
			RigaCarrello riga = new RigaCarrello();
//			riga.setIdProdotto(p.getIdProdotto());
			riga.setNomeprodotto(p.getNome());
			riga.setPrezzoUnitario(p.getPrezzo());
			riga.setPesoTotaleProdotto(p.getPeso());
			riga.setQuantitaScelta(1); //aggiungo 1. Per modificare la quantita ci sarà la funzione a parte modificaQuantita()
			carrello.getRighe().add(riga);
			
			JsonWriter writer = new JsonWriter(new FileWriter("files/carrello.json"));
			writer.beginArray();
			for (RigaCarrello rigaC : carrello.getRighe()) {
				writer.beginObject();
				writer.name("idProdotto").value(rigaC.getIdProdotto());
				writer.name("nomeProdotto").value(rigaC.getNomeprodotto());
				writer.name("prezzoUnitario").value(rigaC.getPrezzoUnitario());
				writer.name("pesoTotale").value(rigaC.getPesoTotaleProdotto());
				writer.name("quantitaScelta").value(rigaC.getQuantitaScelta());
				writer.endObject();
			}
			writer.endArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public Boolean eliminaProdotto(Integer idP) {
		Boolean result = Boolean.FALSE;
		Gson gson = new Gson();
		try {
			JsonReader reader = new JsonReader(new FileReader("files/carrello.json"));
			Set<RigaCarrello> righe = gson.fromJson(reader, RigaCarrello.class);
			JsonWriter writer = new JsonWriter(new FileWriter("files/carrello.json"));
			writer.beginArray();		
			for (RigaCarrello riga : righe) {
				if (!riga.getIdProdotto().equals(idP)) { //scrivo tutte quelle che non hanno valore = idP
					writer.beginObject();
					writer.name("idProdotto").value(riga.getIdProdotto());
					writer.name("nomeProdotto").value(riga.getNomeprodotto());
					writer.name("prezzoUnitario").value(riga.getPrezzoUnitario());
					writer.name("pesoTotale").value(riga.getPesoTotaleProdotto());
					writer.name("quantitaScelta").value(riga.getQuantitaScelta());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	

}
