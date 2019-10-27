package model.ordine.statiOrdine;

import model.ordine.Ordine;
import model.ordine.StatoOrdine;

public class InAttesaConferma extends StatoOrdine {

	public InAttesaConferma(Ordine ordine) {
		super(ordine);
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getStato() {
		// TODO Auto-generated method stub
		return "IN_ATTESA_CONFERMA";
	}

	@Override
	public void aggiornaStato() {
		ordine.setStatoOrdine(new InPreparazione(ordine));
	}

}
