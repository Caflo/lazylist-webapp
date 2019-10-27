package model.prodottoECarrello;

import org.bson.types.ObjectId;

public class Prodotto {

	private ObjectId _id;
	private String nome;
	private String categoria;
	private String marca;
	private String provenienza;
	private Double prezzo;
	private Double sconto; //0.70 --> nella view = 70%
	private Integer unitaDisponibili;
	private Boolean disponibile;
	
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getMarca() {
		return marca;
	}
	public void setMarca(String marca) {
		this.marca = marca;
	}
	public String getProvenienza() {
		return provenienza;
	}
	public void setProvenienza(String provenienza) {
		this.provenienza = provenienza;
	}
	
	public Double getPrezzo() {
		return prezzo;
	}
	public void setPrezzo(Double prezzo) {
		this.prezzo = prezzo;
	}
	public Double getSconto() {
		return sconto;
	}
	public void setSconto(Double sconto) {
		this.sconto = sconto;
	}
	public Integer getUnitaDisponibili() {
		return unitaDisponibili;
	}
	public void setUnitaDisponibili(Integer unitaDisponibili) {
		this.unitaDisponibili = unitaDisponibili;
	}
	public Boolean getDisponibile() {
		return disponibile;
	}
	public void setDisponibile(Boolean disponibile) {
		this.disponibile = disponibile;
	}
}
