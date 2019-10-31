package model.prodottoECarrello;

import java.util.HashSet;
import java.util.Set;

public class Carrello {
	
	private Set<RigaCarrello> righe;
	private Double subTotale;

	public Carrello() {
		righe = new HashSet<>();
	}
	
	public Double getSubTotale() {
		return subTotale;
	}

	public void setSubTotale(Double subTotale) {
		this.subTotale = subTotale;
	}

	public Set<RigaCarrello> getRighe() {
		return righe;
	}

	public void setRighe(Set<RigaCarrello> righe) {
		this.righe = righe;
	}
	
	
}
