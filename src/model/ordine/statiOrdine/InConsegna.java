package model.ordine.statiOrdine;

import model.ordine.Ordine;
import model.ordine.StatoOrdine;

public class InConsegna extends StatoOrdine {

	private Boolean consegnato = Boolean.FALSE;
	
	public InConsegna(Ordine ordine) {
		super(ordine);
	}
	
	public Boolean isConsegnato() {
		return consegnato;
	}

	public void setConsegnato(Boolean consegnato) {
		this.consegnato = consegnato;
	}

	@Override
	public String getStato() {
		// TODO Auto-generated method stub
		return "IN_CONSEGNA";
	}

	@Override
	public void aggiornaStato() {
		// TODO Auto-generated method stub
		if (consegnato.booleanValue() == true) {
			ordine.setStatoOrdine(new Consegnato(ordine));
		}
		else
			ordine.setStatoOrdine(new NonConsegnato(ordine));
	}

}
