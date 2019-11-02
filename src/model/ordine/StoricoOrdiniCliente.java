package model.ordine;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StoricoOrdiniCliente {

	private List<Ordine> ordini;

	public StoricoOrdiniCliente() {
		this.ordini = new ArrayList<>();
	}
	
	public List<Ordine> getOrdini() {
		return ordini;
	}

	public void setOrdini(List<Ordine> ordini) {
		this.ordini = ordini;
	}
	
}
