package model.prodottoECarrello;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class Carrello {
	
	private List<RigaCarrello> righe;
	private Double subTotale;

	public Carrello() {
		righe = new ArrayList<>();
	}
	
	public Double getSubTotale() {
		return subTotale;
	}

	public void setSubTotale(Double subTotale) {
		this.subTotale = subTotale;
	}

	public List<RigaCarrello> getRighe() {
		return righe;
	}

	public void setRighe(List<RigaCarrello> result) {
		this.righe = result;
	}
	
	
}
