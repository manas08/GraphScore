package tools;

import java.util.ArrayList;
import java.util.List;

import entity.Hrana;
import entity.Vrchol;

public class Features {

	boolean rovinny;
	boolean strom;
	boolean euler;
	boolean souvisly;
	int komponent = 0;
	Integer[] cisla;
	private MapaService mapaservice;

	public Features() {
		// TODO Auto-generated constructor stub
	}

	public void main(Integer[] cisla, MapaService mapaservice) {
		this.mapaservice = mapaservice;
		int podil = podil(cisla);
		rovinny = jeRovinny(cisla, podil);
		euler = jeEuler(cisla);
		komponent = pocetKomponent(cisla);
		souvisly = jeSouvisly();
		strom = jeStrom(cisla, podil);
	}

	// ------ Rovinnost -------
	public boolean jeRovinny(Integer[] cisla, int podil) {
		return rovinny;
	}

	// ------ Souvislost -------
	public boolean jeSouvisly() {

		if (komponent == 1)
			return true;
		else
			return false;
	}

	// ------ Eulerovský graf -------
	public boolean jeEuler(Integer[] cisla) {
		int pom;
		int count = 0;
		
		for (int i = 0; i < cisla.length; i++) {
			pom = cisla[i];
			if (pom % 2 == 1) {
				count++;
			}
		}
		
		System.out.println(count + " " + cisla.length/2);
		if (count > cisla.length / 2)
			return false;
		else if (count <= cisla.length / 2)
			return true;
		
		return false;
	}

	// ------ Strom -------
	public boolean jeStrom(Integer[] cisla, int podil) {

		if (jeSouvisly()) {
			if (podil == cisla.length - 1)
				return true;
			else
				return false;
		}
		return false;
	}

	// ------ Komponenty -------
	public int pocetKomponent(Integer[] cisla) {
		komponent = 0;
		resetKomponent();
		this.cisla = cisla;
		Integer[] druhy = new Integer[cisla.length];
		for (int i = 0; i < cisla.length; i++) {
			druhy[i] = cisla[i];
		}

		int vybrane;
		int prehoz;
		boolean podm = false;
		List<Vrchol> vrch = mapaservice.getVrchol();
		for (Vrchol vrchol : vrch) {
			vrchol.saveID();
		}
		int i = 0;
		while (podm == false) {
			for (int j = 0; j < druhy.length - 1; j++) {
				for (int j2 = j + 1; j2 < druhy.length; j2++) {
					if (druhy[j] < druhy[j2]) {
						Vrchol pomocny;
						prehoz = druhy[j];
						druhy[j] = druhy[j2];
						druhy[j2] = prehoz;
						pomocny = mapaservice.getVrchol().get(j);

						vrch.set(j, vrch.get(j2));
						vrch.get(j2).setId(j + 1);
						vrch.set(j2, pomocny);
						pomocny.setId(j2 + 1);
					}
				}
			}

			/*
			 * System.out.println("---------------------------------------"); for (int j =
			 * 0; j < druhy.length; j++) { System.out.println(druhy[j] + " "
			 * +mapaservice.getVrchol().get(j).getKomponent());
			 * 
			 * } System.out.println("---------------------------------------");
			 */

			for (Vrchol vrchol : mapaservice.getVrchol()) {
				if (vrchol.getKomponent() > komponent)
					komponent = vrchol.getKomponent();
			}
			// nastaveni komponenty pro prvni vrchol
			vybrane = druhy[i];
			if (mapaservice.getVrchol().get(i).getKomponent() == 0) {
				if (i > 0)
					mapaservice.getVrchol().get(i).setKomponent(komponent + 1);
				else if (i <= 0)
					mapaservice.getVrchol().get(i).setKomponent(1);
			}

			// kdyz nasledujici body budou 0 tak to jsou taky komponenty
			if (vybrane == 0) {
				for (int w = i; w < druhy.length; w++) {
					// System.out.println(w + " " + mapaservice.getVrchol().get(w).getNazev() + " "+
					// mapaservice.getVrchol().get(w).getKomponent());
					if (druhy[w] == 0 && mapaservice.getVrchol().get(w).getKomponent() == 0) {
						// System.out.println("a");
						for (Vrchol vrchol : mapaservice.getVrchol()) {
							if (vrchol.getKomponent() > komponent)
								komponent = vrchol.getKomponent();
						}
						mapaservice.getVrchol().get(w).setKomponent(komponent + 1);
						// System.out.println(mapaservice.getVrchol().get(w).getKomponent() + " toto");
					}
				}
				podm = true;
				for (Vrchol vrchol : mapaservice.getVrchol()) {
					if (vrchol.getKomponent() > komponent)
						komponent = vrchol.getKomponent();
				}
				System.out.println(komponent);
				return komponent;
			}

			// nastaveni komponenty pro vsechny vrcholy spojene s prvnim vrcholem
			for (int j = 1 + i; j <= vybrane + i; j++) {
				if (mapaservice.getVrchol().get(j).getKomponent() == 0) {
					mapaservice.getVrchol().get(j).setKomponent(mapaservice.getVrchol().get(i).getKomponent());
				}
				if (mapaservice.getVrchol().get(j).getKomponent() != mapaservice.getVrchol().get(i).getKomponent()) {
					if (mapaservice.getVrchol().get(j).getKomponent() < mapaservice.getVrchol().get(i).getKomponent()) {
						mapaservice.getVrchol().get(i).setKomponent(mapaservice.getVrchol().get(j).getKomponent());
					} else if (mapaservice.getVrchol().get(j).getKomponent() > mapaservice.getVrchol().get(i)
							.getKomponent())
						mapaservice.getVrchol().get(j).setKomponent(mapaservice.getVrchol().get(i).getKomponent());
				}
				druhy[j] -= 1;
			}

			i++;
			if (i == druhy.length)
				podm = true;
		}

		for (Vrchol vrchol : mapaservice.getVrchol()) {
			if (vrchol.getKomponent() > komponent)
				komponent = vrchol.getKomponent();
		}
		return komponent;
	}

	// ------Vypoèítání N -------
	public int podil(Integer[] cisla) {

		int soucet = 0;
		for (int i = 0; i < cisla.length; i++) {
			soucet += cisla[i];
		}
		return soucet / 2;
	}

	// ------Gettry-------
	public boolean isRovinny() {
		return rovinny;
	}

	public boolean isStrom() {
		return strom;
	}

	public boolean isEuler() {
		return euler;
	}

	public boolean isSouvisly() {
		return souvisly;
	}

	public int getKomponent() {
		return komponent;
	}

	public void setCisla(Integer[] cisla) {
		this.cisla = cisla;
	}

	public void resetKomponent() {
		for (Vrchol vrch : this.mapaservice.getVrchol()) {
			vrch.setKomponent(0);
		}
	}
}
