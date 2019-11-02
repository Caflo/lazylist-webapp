package model.ordine;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class OrdiniInConsegna {
	
	private List<Ordine> ordini;

	public OrdiniInConsegna() {
		this.ordini = new ArrayList<>();
	}
	
	public List<Ordine> getOrdini() {
		return ordini;
	}

	public void setOrdini(List<Ordine> ordini) {
		this.ordini = ordini;
	}
	
	

}
