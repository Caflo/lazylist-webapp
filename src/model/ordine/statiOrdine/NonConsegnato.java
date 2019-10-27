package model.ordine.statiOrdine;

import model.ordine.Ordine;
import model.ordine.StatoOrdine;

public class NonConsegnato extends StatoOrdine {

	public NonConsegnato(Ordine ordine) {
		super(ordine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getStato() {
		// TODO Auto-generated method stub
		return "NON_CONSEGNATO";
	}

	@Override
	public void aggiornaStato() {
		return;
	}

}
