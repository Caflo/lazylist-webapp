package model.prodottoECarrello;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Magazzino {

	private List<Prodotto> prodotti;

	public Magazzino() {
		prodotti = new ArrayList<>();
	}
	
	public List<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(List<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}
	
	
}
