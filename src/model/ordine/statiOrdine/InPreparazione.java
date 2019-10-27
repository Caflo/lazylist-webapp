package model.ordine.statiOrdine;

import model.ordine.Ordine;
import model.ordine.StatoOrdine;

public class InPreparazione extends StatoOrdine {

	public InPreparazione(Ordine ordine) {
		super(ordine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getStato() {
		// TODO Auto-generated method stub
		return "IN_PREPARAZIONE";
	}

	@Override
	public void aggiornaStato() {
		ordine.setStatoOrdine(new InConsegna(ordine));
	}

}
