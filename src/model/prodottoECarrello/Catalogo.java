package model.prodottoECarrello;

import java.util.ArrayList;
import java.util.List;

public class Catalogo {
	
	private List<Prodotto> prodotti;

	public Catalogo() {
		this.prodotti = new ArrayList<>();
	}

	public List<Prodotto> getProdotti() {
		return prodotti;
	}

	public void setProdotti(List<Prodotto> prodotti) {
		this.prodotti = prodotti;
	}

}
