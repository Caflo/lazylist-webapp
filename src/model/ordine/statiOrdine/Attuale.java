package model.ordine.statiOrdine;

import model.ordine.Ordine;
import model.ordine.StatoOrdine;

public class Attuale extends StatoOrdine {

	public Attuale(Ordine ordine) {
		super(ordine);
		// TODO Auto-generated constructor stub
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
