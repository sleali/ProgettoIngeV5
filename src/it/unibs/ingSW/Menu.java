package it.unibs.ingSW;

import java.io.File;

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
			System.out.println("Menu principale [utente: configuratore]\n1) Gestione reti N \n2) Gestione reti PN \n3) Gestione reti PNP \n4) Importa una rete da file esterno\n\n0) Esci");
			scelta = InputDati.leggiIntero("Selezionare una delle voci del menu:", 0, 4);
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
				case 4:
					importazione_da_file();
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
	
	public static void importazione_da_file() throws Exception
	{
		int scelta = 0;
		String path, name;
		File f;
		InserimentoRete ins;
		do 
		{
			System.out.println("\nMenu di importazione rete da file, selezionare la tipologia di "
					+ "rete che si desidera importare:\n1) Rete di tipo N\n2) Rete di tipo PN\n"
					+ "3) Rete di tipo PNP\n\n0) Torna al menu del configuratore");			
			scelta = InputDati.leggiIntero("Selezionare una delle voci del menu:", 0, 3);
			switch (scelta)
			{
				case 1: 
					path = InputDati.leggiStringa("Inserire il percorso del file che contiene la rete N da caricare:\n");
					f = new File(path);
					if(f.exists() && !f.isDirectory()) { 
					    ins = new InserimentoRete(1);
					    name = InputDati.leggiStringa("Inserire il nome con cui si desidera salvare la rete: ");
					    if(ins.inserisci(path, name))
					    	System.out.println("Rete caricata con successo");
					    else 
					    	System.out.println("Errore nel caricamento, la rete selezionata e' gia' esistente");
					    
					}
					System.out.println("\n");
				break;
				case 2:
					path = InputDati.leggiStringa("Inserire il percorso del file che contiene la rete PN da caricare:\n");
					f = new File(path);
					if(f.exists() && !f.isDirectory()) { 
					    ins = new InserimentoRete(2);
					    name = InputDati.leggiStringa("Inserire il nome con cui si desidera salvare la rete: ");
					    if(ins.inserisci(path, name))
					    	System.out.println("Rete caricata con successo");
					    else 
					    	System.out.println("Si e' verificato un errore nel caricamento, "
					    			+ "controllare che la rete selezionata non sia gia' "
					    			+ "esistente o che la rete N alla base della rete "
					    			+ "selezionata esista");					    
					}
					System.out.println("\n");
				break;
				case 3:
					path = InputDati.leggiStringa("Inserire il percorso del file che contiene la rete PNP da caricare:\n");
					f = new File(path);
					if(f.exists() && !f.isDirectory()) { 
					    ins = new InserimentoRete(3);
					    name = InputDati.leggiStringa("Inserire il nome con cui si desidera salvare la rete: ");
					    if(ins.inserisci(path, name))
					    	System.out.println("Rete caricata con successo");
					    else 
					    	System.out.println("Si e' verificato un errore nel caricamento, "
					    			+ "controllare che la rete selezionata non sia gia' "
					    			+ "esistente o che la rete PN alla base della rete "
					    			+ "selezionata esista");					    
					}
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
