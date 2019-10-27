package model.ordine;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Date;

public class FasciaOraria {

	private String oraInizio; //GSON rompe e allora ho messo i due tipi String
	private String oraFine;
	private DayOfWeek giorno;
	private Double costoConsegna;
	
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
	public DayOfWeek getGiorno() {
		return giorno;
	}
	public void setGiorno(DayOfWeek giorno) {
		this.giorno = giorno;
	}
	public Double getCostoConsegna() {
		return costoConsegna;
	}
	public void setCostoConsegna(Double costoConsegna) {
		this.costoConsegna = costoConsegna;
	}
	
}
