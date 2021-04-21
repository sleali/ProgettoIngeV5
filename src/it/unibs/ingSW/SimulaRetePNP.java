package it.unibs.ingSW;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;

import it.unibs.fp.mylib.InputDati;

public class SimulaRetePNP {
	private static final String DIRECTORYPNP = "./salvataggi/retiPNP/";
	private Rete rete;
	ArrayList<PostoPN> posti;
	ArrayList<TransizionePNP> transizioni;
	
	public SimulaRetePNP() {
		rete = new Rete();
	}
	
	public void scegli() {
		File dir = new File(DIRECTORYPNP);
		String names[] = dir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String name) {
				boolean value;
				// return files only that begins with test
				if(name.startsWith(".")){
					value=false;
				}
				else{
					value=true;
				}
				return value;
			}
		});
		if(names.length > 0)
		{
			int descr;
			System.out.println("\n\nElenco delle descrizioni disponibili:");
			for(int i = 1; i <= names.length; i++)
			{
				System.out.println(i + ") " + names[i - 1]);
			}
			descr = InputDati.leggiIntero("Inserire il numero della descrizione che si desidera simualare: ", 1, names.length);
			rete.carica(DIRECTORYPNP + names[descr - 1], ElementoPNP.class);
			updateRete();
		}
		else 
			System.out.println("Non e' possibile visualizzare alcuna descrizione, nessuna rete e' ancora stata salvata");
	}
	
	private void updateRete() {
		posti = new ArrayList<>();
		transizioni = new ArrayList<>();
		for(Elemento el : rete.getElementi()) {
			boolean equalPosto=false;
			boolean equalTransizione=false;
			for(PostoPN postoPN : posti) {
				if(postoPN.isEqual(((ElementoPNP)el).getPosto()))
					equalPosto=true;
			}
			for(TransizionePNP transizionePNP : transizioni) {
				if(transizionePNP.isEqual(((ElementoPNP)el).getTransizione()))
					equalTransizione=true;
			}
			if(!equalPosto)
				posti.add(((ElementoPNP)el).getPosto());
			if(!equalTransizione)
				transizioni.add(((ElementoPNP)el).getTransizione());
		}
	}
	
	public void simula() {
		int prosegui=1;
		ArrayList<TransizioneN> abil;
		
		do {
			abil = transizioniAbilitatePriority();
			switch(abil.size()) {
				case 0:
					System.out.println("DEADLOCK\n");
					break;
				case 1:
					scattaTransizione(abil.get(0));
					statoRete();
					break;
				default:
					scattaTransizione(scegliTransizione(abil));
					statoRete();
					break;
			}
			if(abil.size() > 0)
				prosegui = InputDati.leggiIntero("Vuoi proseguire con la simulazione?\n\b1) SI\n\n0) NO\n", 0, 1);
		}while(prosegui==1 && abil.size()!=0);
	}
	
	public ArrayList<TransizionePNP> transizioniAbilitate() {
		ArrayList<TransizionePNP> transizioniAbil = new ArrayList<>();
		for(TransizionePNP trans : transizioni) {
			boolean abil=true;
			for(Elemento el : rete.getElementi()) {
				if(el.getVerso() && trans.getID()==el.getIdTransizione()){
					int marcatura = ((ElementoPNP)el).getPosto().getMarcatura();
					int peso = ((ElementoPNP)el).getPeso();
					if(marcatura-peso<0) {
						abil=false;
					}
				}
			}
			if(abil)
				transizioniAbil.add(trans);
		}
		return transizioniAbil;
	}
	
	public ArrayList<TransizioneN> transizioniAbilitatePriority() {
		ArrayList<TransizioneN> transizioniAbil = new ArrayList<>();
		int max = 0;
		for(TransizionePNP trans : transizioniAbilitate()) {
			max = trans.getPriorità() > max ? trans.getPriorità() : max;
		}
		
		for(TransizionePNP trans : transizioniAbilitate()) {
			if(trans.getPriorità() == max)
				transizioniAbil.add(trans);
		}
		return transizioniAbil;
	}
	
	// Metodo per avere i predecessori della transizione trans
	public ArrayList<PostoPN> predecessori(TransizioneN trans){
		ArrayList<PostoPN> pred = new ArrayList<>();
		for(Elemento elem : rete.getElementi()) {
			if(elem.getIdTransizione() == trans.getID() && elem.getVerso()) {
				pred.add(((ElementoPNP)elem).getPosto());
			}
		}
		return pred;
	}
	
	// Metodo per avere i successori della transizione trans
	public ArrayList<PostoPN> successori(TransizioneN trans){
		ArrayList<PostoPN> succ = new ArrayList<>();
		for(Elemento elem : rete.getElementi()) {
			if(elem.getIdTransizione() == trans.getID() && !elem.getVerso()) {
				succ.add(((ElementoPNP)elem).getPosto());
			}
		}
		return succ;
	}
	
	public TransizioneN scegliTransizione(ArrayList<TransizioneN> list) {
		System.out.println("Scegli una transizione tra quelle proposte qui sotto:\n\n");
		for(int i=0 ; i<list.size() ; i++) {
			System.out.println((i+1)+") Transizione ID="+list.get(i).getID());
		}
		System.out.println("\n\n");
		return list.get((InputDati.leggiIntero("Inserisci il numero della transizione che vuoi far scattare (1, "+list.size()+")", 1, list.size()))-1);
	}
	
	public void scattaTransizione(TransizioneN trans) {
		int marcaturaNew;
		ArrayList<PostoPN> scatto1 = getScatto1(predecessori(trans), successori(trans));
		ArrayList<PostoPN> scatto2 = getScatto2(predecessori(trans), successori(trans));
		ArrayList<PostoPN> scatto3 = getScatto3(predecessori(trans), successori(trans));
		
		
		for(PostoPN posto : scatto1) {
			marcaturaNew = posto.getMarcatura() - getPeso(posto, trans);
			for(Elemento el : rete.getElementi()) {
				ElementoPNP elNew = (ElementoPNP)el;
				if(elNew.getIdPosto()==posto.getID()) {
					elNew.getPosto().setMarcatura(marcaturaNew);
				}
			}
		}
		
		for(PostoPN posto : scatto2) {
			marcaturaNew = posto.getMarcatura() + getPeso(trans, posto);
			
			for(Elemento el : rete.getElementi()) {
				ElementoPNP elNew = (ElementoPNP)el;
				if(elNew.getIdPosto()==posto.getID()) {
					elNew.getPosto().setMarcatura(marcaturaNew);
				}
			}
		}
		
		for(PostoPN posto : scatto3) {
			marcaturaNew = posto.getMarcatura() - getPeso(posto, trans) + getPeso(trans, posto);
			
			for(Elemento el : rete.getElementi()) {
				ElementoPNP elNew = (ElementoPNP)el;
				if(elNew.getIdPosto()==posto.getID()) {
					elNew.getPosto().setMarcatura(marcaturaNew);
				}
			}
		}
		
		updateRete();
		
		System.out.println("E' scattata la transizione con ID "+trans.getID());
	}
	
	// Metodo per ritornare solo i posti che fanno parte dei predecessori e non dei successori
	public ArrayList<PostoPN> getScatto1(ArrayList<PostoPN> prec, ArrayList<PostoPN> succ){
		ArrayList<PostoPN> scatto = prec;
		for(PostoPN pre : scatto) {
			for(PostoPN suc : succ) {
				if(pre.isEqual(suc))
					scatto.remove(suc);
			}
		}
		return scatto;
	}
	
	// Metodo per ritornare solo i posti che fanno parte dei successori e non dei predecessori
	public ArrayList<PostoPN> getScatto2(ArrayList<PostoPN> prec, ArrayList<PostoPN> succ){
		ArrayList<PostoPN> scatto = succ;
		for(PostoPN suc : scatto) {
			for(PostoPN pre : prec) {
				if(suc.isEqual(pre))
					scatto.remove(pre);
			}
		}
		return scatto;
	}

	// Metodo per ritornare solo i posti che fanno parte sia dei predecessori che dei successori
	public ArrayList<PostoPN> getScatto3(ArrayList<PostoPN> prec, ArrayList<PostoPN> succ){
		ArrayList<PostoPN> scatto = new ArrayList<>();
		for(PostoPN suc : succ) {
			for(PostoPN pre : prec) {
				if(suc.isEqual(pre))
					scatto.add(pre);
			}
		}
		return scatto;
	}
	
	public void statoRete() {
		for(PostoPN posto : posti) {
			System.out.println("ID Posto: "+posto.getID()+"\nMarcatura Posto: "+posto.getMarcatura()+"\n\n");
		}
	}
	
	public int getPeso(TransizioneN trans, PostoPN posto) {
		for(Elemento el : rete.getElementi()) {
			ElementoPNP elNew = (ElementoPNP)el;
			if(elNew.getIdPosto()==posto.getID() && elNew.getIdTransizione()==trans.getID() && !elNew.getVerso())
				return elNew.getPeso();
		}
		return 0;
	}
	
	public int getPeso(PostoPN posto, TransizioneN trans) {
		for(Elemento el : rete.getElementi()) {
			ElementoPNP elNew = (ElementoPNP)el;
			if(elNew.getIdPosto()==posto.getID() && elNew.getIdTransizione()==trans.getID() && elNew.getVerso())
				return elNew.getPeso();
		}
		return 0;
	}
}
