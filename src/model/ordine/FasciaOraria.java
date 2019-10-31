package model.ordine;

import org.bson.types.ObjectId;

public class FasciaOraria {

	private ObjectId _id;
	private String oraInizio; //GSON rompe e allora ho messo i due tipi String
	private String oraFine;
	private String giorno;
	private Double costoConsegna;
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public String getOraInizio() {
		return oraInizio;
	}
	public void setOraInizio(String oraInizio) {
		this.oraInizio = oraInizio;
	}
	public String getOraFine() {
		return oraFine;
	}
	public void setOraFine(String oraFine) {
		this.oraFine = oraFine;
	}
	public Double getCostoConsegna() {
		return costoConsegna;
	}
	public void setCostoConsegna(Double costoConsegna) {
		this.costoConsegna = costoConsegna;
	}
	public String getGiorno() {
		return giorno;
	}
	public void setGiorno(String giorno) {
		this.giorno = giorno;
	}
	
}
