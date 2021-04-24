//Per problemi con le lettere accentate in Eclipse andare in "Project->Properties->Resource" e settare la codifica su UTF-8
package it.unibs.ingSW;

import java.io.File;
import java.io.FilenameFilter;
import java.util.HashMap;

import it.unibs.fp.mylib.InputDati;

public class AggiungiPNP {
	private static final String DIRECTORYPN = "./salvataggi/retiPN/";
	private static final String DIRECTORYPNP = "./salvataggi/retiPNP/";
	
	public AggiungiPNP() {}
	
	public void toPNP() throws Exception {
		int scelta = 0;
		do {
			System.out.println("\n\nMenu di visualizzazione delle reti PN:\n"
					+ "1) Visualizza e converti la descrizione di una rete PN in una rete PNP\n"
					+ "\n0) Torna al menu principale");
			scelta = InputDati.leggiIntero("Selezionare una delle voci del menu:", 0, 1);
			switch (scelta)
			{
				case 1:
					File dir = new File(DIRECTORYPN);
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
						Rete retePN = new Rete();
						System.out.println("\n\nElenco delle descrizioni disponibili:");
						for(int i = 1; i <= names.length; i++)
						{
							System.out.println(i + ") " + names[i - 1]);
						}
						descr = InputDati.leggiIntero("Inserire il numero della descrizione "
								+ "che si desidera convertire: ", 1, names.length);
						retePN.carica(DIRECTORYPN + names[descr - 1], ElementoPN.class);
						creaPNP(retePN); //Metodo per convertire la rete PN in rete PNP
					}
					else 
						System.out.println("Non e' possibile visualizzare alcuna descrizione, nessuna rete e' ancora stata salvata");		
					break;
			}
		} while (scelta != 0);
		System.out.println("Uscita dal menù delle reti PN avvenuta");
	}
	
	private void creaPNP(Rete retePN) {
		Rete pnp = new Rete();
		HashMap<Integer, Integer> priorites = new HashMap<Integer, Integer>();
		
		for (Elemento el : retePN.getElementi()) {
			int priorità = priorites.containsKey(el.getIdTransizione()) ? priorites.get(el.getIdTransizione()) : InputDati.leggiIntero("Inserire la priorità della transizione con ID "+el.getIdTransizione() + "\n", 1, Integer.MAX_VALUE);
			
			ElementoPNP elPNP = new ElementoPNP(new PostoPN(el.getIdPosto(), ((ElementoPN)el).getPosto().getMarcatura()), new TransizionePNP(el.getIdTransizione(), priorità), el.getVerso(), ((ElementoPN)el).getPeso());
			
			priorites.put(el.getIdTransizione(), priorità);
			
			pnp.add(elPNP);
		}
		int salva = InputDati.leggiIntero("\n\nVuoi salvare?\n1) SI\n2) NO\n", 1, 2);
		if(salva==1) {
			String filename;
			boolean exist, esito;
			if(pnp.size() > 0)
			{
				exist = checkDuplicate(pnp);
				if(exist)
					System.out.println("Errore, la rete che si desidera salvare e' il duplicato di una rete gia' esistente ");
				else 
				{
					filename = InputDati.leggiStringa("Inserire il nome con cui si desidera salvare la descrizione: ");
					esito = pnp.salva(DIRECTORYPNP + filename);
					if(esito)
						System.out.println("Salvataggio avvenuto con successo!");
					else 
						System.out.println("Si e' verificato un errore nel salvataggio");
				}
			}
			else 
				System.out.println("Errore, non e' possibile salvare una rete priva di elementi");
		}
			
	}
	
	private boolean checkDuplicate(Rete rete)
	{
		boolean find = false;
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
		for (int i = 0; i < names.length && !find; i++) 
		{
			Rete reteEsistente = new Rete();
			reteEsistente.carica(DIRECTORYPNP + names[i], ElementoPNP.class);
			if (rete.isEqual(reteEsistente)) 
				find = true;
		}
		return find;
	}
}
