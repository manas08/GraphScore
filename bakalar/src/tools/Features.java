package tools;

import java.util.Collections;
import java.util.Comparator;
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
	Hrana hrana;

	public Features() {
		// TODO Auto-generated constructor stub
	}

	public void main(Integer[] cisla, MapaService mapaservice, Hrana hrana) {
		this.mapaservice = mapaservice;
		this.hrana = hrana;
		int podil = podil(cisla);

		if (mapaservice.getVrchol().size() != 0) {
			rovinny = jeRovinny(cisla, podil);
			euler = jeEuler(cisla);
			komponent = pocetKomponent(cisla);
			souvisly = jeSouvisly();
			strom = jeStrom(cisla, podil);
		} else {
			rovinny = false;
			euler = false;
			komponent = 0;
			souvisly = false;
			strom = false;
		}
	}

	// ------ Rovinnost -------
	public boolean jeRovinny(Integer[] cisla, int podil) {
		if (podil > (cisla.length * 3) - 6) {
			return false;
		}else
			return true;
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

		if (count == 2 || count == 0)
			return true;
		else 
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
		for (Vrchol vrchol : mapaservice.getVrchol()) {
			vrchol.saveID();
		}
		int[][] soused = new int[druhy.length][druhy.length];
		int i = 0;
		while (podm == false) {
			komponent = 0;
			for (int j1 = 0; j1 < druhy.length - 1; j1++) {
				for (int j2 = j1 + 1; j2 < druhy.length; j2++) {
					if (druhy[j1] < druhy[j2]) {
						Vrchol pomocny;
						prehoz = druhy[j1];
						druhy[j1] = druhy[j2];
						druhy[j2] = prehoz;
						pomocny = mapaservice.getVrchol().get(j1);

						vrch.set(j1, vrch.get(j2));
						vrch.set(j2, pomocny);
					}
				}
			}

			// nastaveni komponenty pro prvni vrchol
			vybrane = druhy[i];

			if (vybrane != 0) {
				for (Vrchol vrchol : mapaservice.getVrchol()) {
					if (vrchol.getKomponent() > komponent)
						komponent = vrchol.getKomponent();
				}
			}

			if (mapaservice.getVrchol().get(i).getKomponent() == 0) {
				if (i > 0)
					mapaservice.getVrchol().get(i).setKomponent(komponent + 1);
				else if (i <= 0)
					mapaservice.getVrchol().get(i).setKomponent(1);
			}

			// kdyz nasledujici body budou 0 tak to jsou taky komponenty
			if (vybrane == 0) {

				// aby body spojené hranami mìly stejné komponenty
				for (int j1 = 0; j1 < druhy.length - 1; j1++) {
					for (int j2 = 1; j2 < druhy.length; j2++) {
						for (int j3 = 0; j3 < hrana.getList().size(); j3++) {
							if ((mapaservice.getVrchol().get(j1).getNazev() == hrana.getList().get(j3).getPrvni()
									.getNazev()
									&& mapaservice.getVrchol().get(j2).getNazev() == hrana.getList().get(j3).getDruhy()
											.getNazev())
									|| (mapaservice.getVrchol().get(j1).getNazev() == hrana.getList().get(j3).getDruhy()
											.getNazev()
											&& mapaservice.getVrchol().get(j2).getNazev() == hrana.getList().get(j3)
													.getPrvni().getNazev())) {
								if (mapaservice.getVrchol().get(j1).getKomponent() > mapaservice.getVrchol().get(j2)
										.getKomponent()) {
									mapaservice.getVrchol().get(j1)
											.setKomponent(mapaservice.getVrchol().get(j2).getKomponent());
								} else if (mapaservice.getVrchol().get(j2).getKomponent() > mapaservice.getVrchol()
										.get(j1).getKomponent()) {
									mapaservice.getVrchol().get(j2)
											.setKomponent(mapaservice.getVrchol().get(j1).getKomponent());
								}
							}
						}
					}
				}

				for (int w = i; w < druhy.length; w++) {
					if (druhy[w] == 0 && mapaservice.getVrchol().get(w).getKomponent() == 0) {
						for (Vrchol vrchol : mapaservice.getVrchol()) {
							if (vrchol.getKomponent() > komponent)
								komponent = vrchol.getKomponent();
						}
						mapaservice.getVrchol().get(w).setKomponent(komponent + 1);
					}
				}
				podm = true;
				for (Vrchol vrchol : mapaservice.getVrchol()) {
					if (vrchol.getKomponent() > komponent)
						komponent = vrchol.getKomponent();
				}

				for (int j = 0; j < mapaservice.getVrchol().size(); j++) {
					mapaservice.getVrchol().get(j).setID2();
				}

				// seøazení listu pro korektní další poèítání
				Collections.sort(mapaservice.getVrchol(), new Comparator<Vrchol>() {

					@Override
					public int compare(Vrchol v1, Vrchol v2) {
						return v1.getNazev().compareToIgnoreCase(v2.getNazev());
					}
				});

				return komponent;
			}

			// nastaveni komponenty pro vsechny vrcholy spojene s prvnim vrcholem
			for (int j = 1 + i; j <= i + vybrane; j++) {
				if (mapaservice.getVrchol().get(j).getKomponent() == 0) {
					for (int j2 = 0; j2 < hrana.getList().size(); j2++) {
						if ((mapaservice.getVrchol().get(j).getNazev() == hrana.getList().get(j2).getPrvni().getNazev()
								&& mapaservice.getVrchol().get(i).getNazev() == hrana.getList().get(j2).getDruhy()
										.getNazev())
								|| (mapaservice.getVrchol().get(j).getNazev() == hrana.getList().get(j2).getDruhy()
										.getNazev()
										&& mapaservice.getVrchol().get(i).getNazev() == hrana.getList().get(j2)
												.getPrvni().getNazev())) {
							mapaservice.getVrchol().get(j).setKomponent(mapaservice.getVrchol().get(i).getKomponent());
							// uložíme si sousedy; pokud pozdeji zmenime komponentu, tak aby se zmenila pro
							// všechny sousedy tohoto bodu
							soused[i][j] = 1;
							druhy[j] -= 1;
						}
					}
				} else if (mapaservice.getVrchol().get(j).getKomponent() != mapaservice.getVrchol().get(i)
						.getKomponent()) {
					for (int j2 = 0; j2 < hrana.getList().size(); j2++) {
						if (mapaservice.getVrchol().get(j).getKomponent() < mapaservice.getVrchol().get(i)
								.getKomponent()) {
							if ((mapaservice.getVrchol().get(j).getNazev() == hrana.getList().get(j2).getPrvni()
									.getNazev()
									&& mapaservice.getVrchol().get(i).getNazev() == hrana.getList().get(j2).getDruhy()
											.getNazev())
									|| (mapaservice.getVrchol().get(j).getNazev() == hrana.getList().get(j2).getDruhy()
											.getNazev()
											&& mapaservice.getVrchol().get(i).getNazev() == hrana.getList().get(j2)
													.getPrvni().getNazev())) {
								mapaservice.getVrchol().get(i)
										.setKomponent(mapaservice.getVrchol().get(j).getKomponent());
								druhy[j] -= 1;

								// sousedùm bodu dáme stejnou komponentu
								for (int k2 = 0; k2 < druhy.length; k2++) {
									if (soused[i][k2] == 1) {
										mapaservice.getVrchol().get(k2)
												.setKomponent(mapaservice.getVrchol().get(i).getKomponent());
									}
								}
							}
						} else if (mapaservice.getVrchol().get(j).getKomponent() > mapaservice.getVrchol().get(i)
								.getKomponent())
							if ((mapaservice.getVrchol().get(j).getNazev() == hrana.getList().get(j2).getPrvni()
									.getNazev()
									&& mapaservice.getVrchol().get(i).getNazev() == hrana.getList().get(j2).getDruhy()
											.getNazev())
									|| (mapaservice.getVrchol().get(j).getNazev() == hrana.getList().get(j2).getDruhy()
											.getNazev()
											&& mapaservice.getVrchol().get(i).getNazev() == hrana.getList().get(j2)
													.getPrvni().getNazev())) {
								mapaservice.getVrchol().get(j)
										.setKomponent(mapaservice.getVrchol().get(i).getKomponent());
								druhy[j] -= 1;

								// sousedùm bodu dáme stejnou komponentu
								for (int k2 = 0; k2 < druhy.length; k2++) {
									if (soused[i][k2] == 1) {
										mapaservice.getVrchol().get(k2)
												.setKomponent(mapaservice.getVrchol().get(i).getKomponent());
									}
								}
							}
					}
				} else
					druhy[j] -= 1;
			}

			i++;
			if (i == druhy.length - 1)
				podm = true;
		}

		// aby body spojené hranami mìly stejné komponenty
		for (int j1 = 0; j1 < druhy.length - 1; j1++) {
			for (int j2 = 1; j2 < druhy.length; j2++) {
				for (int j3 = 0; j3 < hrana.getList().size(); j3++) {
					if ((mapaservice.getVrchol().get(j1).getNazev() == hrana.getList().get(j3).getPrvni().getNazev()
							&& mapaservice.getVrchol().get(j2).getNazev() == hrana.getList().get(j3).getDruhy()
									.getNazev())
							|| (mapaservice.getVrchol().get(j1).getNazev() == hrana.getList().get(j3).getDruhy()
									.getNazev()
									&& mapaservice.getVrchol().get(j2).getNazev() == hrana.getList().get(j3).getPrvni()
											.getNazev())) {
						if (mapaservice.getVrchol().get(j1).getKomponent() > mapaservice.getVrchol().get(j2)
								.getKomponent()) {
							mapaservice.getVrchol().get(j1)
									.setKomponent(mapaservice.getVrchol().get(j2).getKomponent());
						} else if (mapaservice.getVrchol().get(j2).getKomponent() > mapaservice.getVrchol().get(j1)
								.getKomponent()) {
							mapaservice.getVrchol().get(j2)
									.setKomponent(mapaservice.getVrchol().get(j1).getKomponent());
						}
					}
				}
			}
		}

		// nezávislé zjištìní komponent
		komponent = 0;
		for (Vrchol vrchol : mapaservice.getVrchol()) {
			if (vrchol.getKomponent() > komponent)
				komponent = vrchol.getKomponent();
		}

		for (int j = 0; j < mapaservice.getVrchol().size(); j++) {
			mapaservice.getVrchol().get(j).setID2();
		}

		// seøazení listu pro korektní další poèítání
		Collections.sort(mapaservice.getVrchol(), new Comparator<Vrchol>() {

			@Override
			public int compare(Vrchol v1, Vrchol v2) {
				return v1.getNazev().compareToIgnoreCase(v2.getNazev());
			}
		});

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
