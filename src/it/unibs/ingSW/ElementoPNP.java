package it.unibs.ingSW;

public class ElementoPNP implements Elemento<ElementoPNP>{
	private PostoPN posto;
	private TransizionePNP transizione;
	private boolean verso;
	private int peso;
	
	public ElementoPNP(PostoPN posto, TransizionePNP transizione, boolean verso, int peso) {
		this.verso = verso;
		this.posto = posto;
		this.transizione = transizione;
		this.peso = (peso<1) ? 1 : peso;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public void setTransizione(TransizionePNP transizione) {
		this.transizione = transizione;
	}

	public void setVerso(boolean verso) {
		this.verso = verso;
	}
	
	public PostoPN getPosto() {
		return posto;
	}
	
	public void setPosto(PostoPN posto) {
		this.posto = posto;
	}
	
	public TransizionePNP getTransizione() {
		return transizione;
	}
	
	public int getIdPosto()
	{
		return posto.getID();
	}
	
	public int getIdTransizione()
	{
		return transizione.getID();
	}
	
	public boolean getVerso() {
		return verso;
	}
	
	public boolean isEqual(ElementoPNP ePar)
	{
		return (ePar.getPosto().isEqual(posto) && ePar.getTransizione().isEqual(transizione) && (ePar.getVerso() == verso));
	}
	
	public String print(){
		String out = "ID posto: " + posto.getID() + "\nMarcatura: " + posto.getMarcatura() + "\nID transizione: " + transizione.getID() + "\nPeso: " + this.peso + "\nPriorità: " + transizione.getPriorità();
		if(verso)
			out += "\nVerso: da posto a transizione";
		else 
			out += "\nVerso: da transizione a posto";
		return out;
	}
}
