package model.prodottoECarrello;

import java.util.HashSet;
import java.util.Set;

public class Magazzino {

	private Set<Prodotto> prodotti;

	public Magazzino() {
		prodotti = new HashSet<>();
	}
	
	public Set<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(Set<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}
	
	
}
