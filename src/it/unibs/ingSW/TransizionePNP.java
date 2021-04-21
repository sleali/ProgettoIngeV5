package it.unibs.ingSW;

public class TransizionePNP extends TransizioneN {
	private int priorità;
	
	public TransizionePNP(int ID, int priorità){
		super(ID);
		this.priorità = (priorità<1) ? 1 : priorità;
	}
	
	public TransizionePNP(int ID) {
		super(ID);
		this.priorità = 1;
	}
	
	public int getPriorità(){
		return priorità;
	}
	
	public void setPriorità(int priorità) {
		this.priorità = priorità;
	}
}
