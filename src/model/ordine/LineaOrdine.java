package model.ordine;

public class LineaOrdine {

	private String idProdotto; //idPrdootto
	private String nomeProdotto;
	private Double prezzoUnitarioScontato;
	private Integer quantitaScelta;
	
	
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
	public Double getPrezzoUnitarioScontato() {
		return prezzoUnitarioScontato;
	}
	public void setPrezzoUnitarioScontato(Double prezzoUnitarioScontato) {
		this.prezzoUnitarioScontato = prezzoUnitarioScontato;
	}
	public Integer getQuantitaScelta() {
		return quantitaScelta;
	}
	public void setQuantitaScelta(Integer quantitaScelta) {
		this.quantitaScelta = quantitaScelta;
	}
}
