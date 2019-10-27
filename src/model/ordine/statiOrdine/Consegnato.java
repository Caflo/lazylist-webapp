package model.ordine.statiOrdine;

import model.ordine.Ordine;
import model.ordine.StatoOrdine;

public class Consegnato extends StatoOrdine {

	public Consegnato(Ordine ordine) {
		super(ordine);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getStato() {
		// TODO Auto-generated method stub
		return "CONSEGNATO";
	}

	@Override
	public void aggiornaStato() {
		// TODO Auto-generated method stub
		return;
	}

}
