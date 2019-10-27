package model.prodottoECarrello;

import org.bson.types.ObjectId;

public class RigaCarrello {

	private String idProdotto;
	private String nomeProdotto;
	private Integer quantitaScelta;
	private Double prezzoUnitario;
	private Double sconto;
	
	public String getIdProdotto() {
		return idProdotto;
	}
	public void setIdProdotto(String idProdotto) {
		this.idProdotto = idProdotto;
	}
	public String getNomeProdotto() {
		return nomeProdotto;
	}
	public void setNomeProdotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}
	public String getNomeprodotto() {
		return nomeProdotto;
	}
	public void setNomeprodotto(String nomeProdotto) {
		this.nomeProdotto = nomeProdotto;
	}
	public Integer getQuantitaScelta() {
		return quantitaScelta;
	}
	public void setQuantitaScelta(Integer quantitaScelta) {
		this.quantitaScelta = quantitaScelta;
	}
	public Double getPrezzoUnitario() {
		return prezzoUnitario;
	}
	public void setPrezzoUnitario(Double prezzoUnitario) {
		this.prezzoUnitario = prezzoUnitario;
	}
	public Double getSconto() {
		return sconto;
	}
	public void setSconto(Double sconto) {
		this.sconto = sconto;
	}
}
