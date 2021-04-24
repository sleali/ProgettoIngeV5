package it.unibs.ingSW;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class InserimentoRete {
	private Rete rete;
	private int type;
	
	public InserimentoRete(int type) {
		/*
		 * type == 1 => rete parametro di tipo N
		 * type == 2 => rete parametro di tipo PN
		 * type == 3 => rete parametro di tipo PNP
		 */
		rete = new Rete();
		this.type = type;
	}
	
	private ElementoN getElementoNformPN(ElementoPN elementoPN)
	{
		PostoPN p = elementoPN.getPosto();
		TransizioneN t = elementoPN.getTransizione();
		
		PostoN newP = new PostoN(p.getID());
		
		return new ElementoN(newP, t, elementoPN.getVerso());
		
	}
	
	private ElementoPN getElementoPNfromPNP(ElementoPNP el)
	{
		PostoPN p = el.getPosto();
		TransizionePNP t = el.getTransizione();
		
		TransizioneN newT = new TransizioneN(t.getID());
		
		return new ElementoPN(p, newT, el.getVerso(), el.getPeso());
	}
	
	private Rete getReteNfromPN(Rete retePar)
	{
		Rete rete = new Rete();
		ElementoN elN;
		for (Elemento elemento : retePar.getElementi()) {
			elN = getElementoNformPN((ElementoPN)elemento);
			rete.add(elN);
		}
		return rete;
	}
	
	private Rete getRetePNformPNP(Rete retePar)
	{
		Rete rete = new Rete();
		ElementoPN elN;
		for (Elemento elemento : retePar.getElementi()) {
			elN = getElementoPNfromPNP((ElementoPNP)elemento);
			rete.add(elN);
		}
		return rete;
	}
	
	public boolean inserisci(String path, String name)
	{
		String directoryN = "./salvataggi/retiN/";
		String directoryPN = "./salvataggi/retiPN/";
		String directoryPNP = "./salvataggi/retiPNP/";
		Rete rete = new Rete();
		
		if(this.type == 1)
		{
			rete.carica(path, ElementoN.class);
			if(checkDuplicate(rete, directoryN, ElementoN.class))
				return false;
			else 
			{
				rete.salva(directoryN + name);
				return true;
			}
		}
		else if(this.type == 2)
		{
			rete.carica(path, ElementoPN.class);
			if(checkDuplicate(rete, directoryPN, ElementoPN.class))
				return false;
			else
			{
				if(checkDuplicate(getReteNfromPN(rete), directoryN, ElementoN.class))
				{
					rete.salva(directoryPN + name);
					return true;
				}
				else 
					return false;
			}
		}
		else
		{
			rete.carica(path, ElementoPNP.class);
			if(checkDuplicate(rete, directoryPNP, ElementoPNP.class))
				return false;
			else
			{
				if(checkDuplicate(getRetePNformPNP(rete), directoryPN, ElementoPN.class))
				{
					rete.salva(directoryPNP + name);
					return true;
				}
				else 
					return false;
			}
		}
	}
	
	private boolean checkDuplicate(Rete rete, String directory, Class<?> classe)
	{
		boolean find = false;
		File dir = new File(directory);
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
		for (int i = 0; i < names.length && !find; i++) 
		{
			Rete reteEsistente = new Rete();
			reteEsistente.carica(directory + names[i], classe);
			if (rete.isEqual(reteEsistente)) 
				find = true;
		}
		return find;
	}
	
}
