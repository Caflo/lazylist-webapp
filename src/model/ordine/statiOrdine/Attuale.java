package model.ordine.statiOrdine;

import model.ordine.Ordine;
import model.ordine.StatoOrdine;
import model.prodottoECarrello.Carrello;

public class Attuale extends StatoOrdine {

	private Carrello carrello;
	
	public Attuale(Ordine ordine) {
		super(ordine);
	}

	@Override
	public String getStato() {
		// TODO Auto-generated method stub
		return "ATTUALE";
	}

	@Override
	public void aggiornaStato() {
		// TODO Auto-generated method stub
		ordine.setStatoOrdine(new InAttesaConferma(ordine));
		return;
	}
}
