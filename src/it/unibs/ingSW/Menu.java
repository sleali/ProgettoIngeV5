package it.unibs.ingSW;

import it.unibs.fp.mylib.*;

public class Menu 
{
	public static void main(String[] args) throws Exception{
		int scelta = 0;
		do 
		{
			System.out.println("V5\n\nMenu principale\n1) Configuratore \n2) Fruitore \n\n0) Esci");
			scelta = InputDati.leggiIntero("Selezionare una delle voci del menu:", 0, 2);
			switch (scelta)
			{
				case 1: 
					System.out.println("\n");
					configuratore();
					break;
				case 2:
					System.out.println("\n");
					fruitore();
					break;
				default:
					System.out.println("\n");
					System.out.println("Uscita in corso...");
					break;
			}
		} 
		while (scelta != 0);
	}
	
	public static void configuratore() throws Exception {
		int scelta = 0;
		do 
		{
			System.out.println("Menu principale [utente: configuratore]\n1) Gestione reti N \n2) Gestione reti PN \n3) Gestione reti PNP \n\n0) Esci");
			scelta = InputDati.leggiIntero("Selezionare una delle voci del menu:", 0, 3);
			switch (scelta)
			{
				case 1: 
					System.out.println("\n");
					reti();
					break;
				case 2:
					reti_petri();
					break;
				case 3:
					reti_petri_pnp();
					break;
				default:
					System.out.println("Uscita in corso...");
					break;
			}
		} 
		while (scelta != 0);
	}
	
	public static void fruitore(){
		int scelta = 0;
		do 
		{
			System.out.println("Menu principale [utente: fruitore]\n1) Simula Reti PN \n2) Simula Reti PNP\n\n0) Esci");
			scelta = InputDati.leggiIntero("Selezionare una delle voci del menu:", 0, 2);
			switch (scelta)
			{
				case 1: 
					SimulaRetePN retePN = new SimulaRetePN();
					retePN.scegli();
					retePN.simula();
					System.out.println("\n");
					break;
				case 2:
					SimulaRetePNP retePNP = new SimulaRetePNP();
					retePNP.scegli();
					retePNP.simula();
					System.out.println("\n");
					break;
				default:
					System.out.println("Uscita in corso...");
					break;
			}
		} 
		while (scelta != 0);
	}
	
	public static void reti(){
		int scelta = 0;
		do 
		{
			System.out.println("Menu principale Reti N [utente: configuratore]\n1) Inserisci descrizione \n2) Visualizza descrizione \n\n0) Esci");
			scelta = InputDati.leggiIntero("Selezionare una delle voci del menu:", 0, 2);
			switch (scelta)
			{
				case 1: 
					AggiungiN aggiungi = new AggiungiN();
					aggiungi.add();
					System.out.println("\n");
				break;
				case 2:
					VisualizzaN visualizza = new VisualizzaN();
					visualizza.print();
					System.out.println("\n");
				break;
				default:
					System.out.println("\n");
				break;
			}
		} 
		while (scelta != 0);
	}
	
	public static void reti_petri() throws Exception{
		int scelta = 0;
		do 
		{
			System.out.println("Menu principale Reti PN [utente: configuratore]\n1) Inserisci descrizione \n2) Visualizza descrizione \n\n0) Esci");
			scelta = InputDati.leggiIntero("Selezionare una delle voci del menu:", 0, 2);
			switch (scelta)
			{
				case 1: 
					AggiungiPN aggiungi = new AggiungiPN();
					aggiungi.toPN();
					System.out.println("\n");
				break;
				case 2:
					VisualizzaPN visualizza = new VisualizzaPN();
					visualizza.print();
					System.out.println("\n");
				break;
				default:
					System.out.println("\n");
				break;
			}
		} 
		while (scelta != 0);
	}
	
	public static void reti_petri_pnp() throws Exception{
		int scelta = 0;
		do 
		{
			System.out.println("Menu principale Reti PNP [utente: configuratore]\n1) Inserisci descrizione \n2) Visualizza descrizione \n\n0) Esci");
			scelta = InputDati.leggiIntero("Selezionare una delle voci del menu:", 0, 2);
			switch (scelta)
			{
				case 1: 
					AggiungiPNP aggiungi = new AggiungiPNP();
					aggiungi.toPNP();
					System.out.println("\n");
				break;
				case 2:
					VisualizzaPNP visualizza = new VisualizzaPNP();
					visualizza.print();
					System.out.println("\n");
				break;
				default:
					System.out.println("\n");
				break;
			}
		} 
		while (scelta != 0);
	}
}
