package model.ordine;


public abstract class StatoOrdine {

	protected Ordine ordine;
	
	public StatoOrdine() {
	}
	
	public StatoOrdine(Ordine ordine) {
		this.ordine = ordine;
	}
	
	public abstract String getStato();
	
	public abstract void aggiornaStato();
	
}
