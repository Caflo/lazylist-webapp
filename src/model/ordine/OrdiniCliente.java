package model.ordine;

import java.util.HashSet;
import java.util.Set;

public class OrdiniCliente {

	private Set<Ordine> ordini;
	
	public OrdiniCliente() {
		this.ordini = new HashSet<>();
	}

	public Set<Ordine> getOrdini() {
		return ordini;
	}

	public void setOrdini(Set<Ordine> ordini) {
		this.ordini = ordini;
	}
	
	
	
}
