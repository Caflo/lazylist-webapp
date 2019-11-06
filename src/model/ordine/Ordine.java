package model.ordine;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.types.ObjectId;

public class Ordine {

	private ObjectId _id;
	private Integer idCliente;
	private String nomeCliente;
	private String cognomeCliente;
	private Date dataConsegna;
	private String indirizzoConsegna;
	private String emailCliente;
	private String CAP;
	private Double costoTotale;
	private FasciaOraria fasciaOraria;
	private TipoPagamento tipoPagamento;
	private StatoOrdine statoOrdine;
	private List<LineaOrdine> lineeOrdine;
	
	public Ordine() {
		this.lineeOrdine = new ArrayList<>();
	}
	
	public ObjectId get_id() {
		return _id;
	}
	public void set_id(ObjectId _id) {
		this._id = _id;
	}
	public Integer getIdCliente() {
		return idCliente;
	}
	public void setIdCliente(Integer idCliente) {
		this.idCliente = idCliente;
	}
	public String getNomeCliente() {
		return nomeCliente;
	}
	public void setNomeCliente(String nomeCliente) {
		this.nomeCliente = nomeCliente;
	}
	public String getCognomeCliente() {
		return cognomeCliente;
	}
	public void setCognomeCliente(String cognomeCliente) {
		this.cognomeCliente = cognomeCliente;
	}
	public Date getDataConsegna() {
		return dataConsegna;
	}
	public void setDataConsegna(Date date) {
		this.dataConsegna = date;
	}
	public String getIndirizzoConsegna() {
		return indirizzoConsegna;
	}
	public void setIndirizzoConsegna(String indirizzoConsegna) {
		this.indirizzoConsegna = indirizzoConsegna;
	}
	
	public String getEmailCliente() {
		return emailCliente;
	}

	public void setEmailCliente(String emailCliente) {
		this.emailCliente = emailCliente;
	}

	public String getCAP() {
		return CAP;
	}
	public void setCAP(String cAP) {
		CAP = cAP;
	}
	public Double getCostoTotale() {
		return costoTotale;
	}
	public void setCostoTotale(Double d) {
		this.costoTotale = d;
	}
	public FasciaOraria getFasciaOraria() {
		return fasciaOraria;
	}
	public void setFasciaOraria(FasciaOraria fasciaOraria) {
		this.fasciaOraria = fasciaOraria;
	}
	public TipoPagamento getTipoPagamento() {
		return tipoPagamento;
	}
	public void setTipoPagamento(TipoPagamento tipoPagamento) {
		this.tipoPagamento = tipoPagamento;
	}
	public StatoOrdine getStatoOrdine() {
		return statoOrdine;
	}
	public void setStatoOrdine(StatoOrdine statoOrdine) {
		this.statoOrdine = statoOrdine;
	}
	public List<LineaOrdine> getLineeOrdine() {
		return lineeOrdine;
	}
	public void setLineeOrdine(List<LineaOrdine> lineeOrdine) {
		this.lineeOrdine = lineeOrdine;
	}
	
	public void aggiornaStato() {
		statoOrdine.aggiornaStato();
	}
}
