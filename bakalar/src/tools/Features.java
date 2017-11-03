package tools;

public class Features {

	boolean rovinny;
	boolean strom;
	boolean euler;
	boolean souvisly;
	int komponent = 0;

	public Features() {
		// TODO Auto-generated constructor stub
	}

	public void main(Integer[] cisla) {
		int podil = podil(cisla);
		rovinny = jeRovinny(cisla, podil);
		strom = jeStrom(cisla, podil);
		euler = jeEuler(cisla);
		souvisly = jeSouvisly(cisla, podil);
		pocetKomponent(cisla, podil);
	}

	// ------ Rovinnost -------
	public boolean jeRovinny(Integer[] cisla, int podil) {
		return rovinny;
	}

	// ------ Souvislost -------
	public boolean jeSouvisly(Integer[] cisla, int podil) {

		if (cisla.length <= podil + 1)
			return true;
		else
			return false;
	}

	// ------ Eulerovský graf -------
	public boolean jeEuler(Integer[] cisla) {
		return euler;
	}

	// ------ Strom -------
	public boolean jeStrom(Integer[] cisla, int podil) {

		if (jeSouvisly(cisla, podil)) {
			if (podil == cisla.length - 1)
				return true;
			else
				return false;
		}
		return false;
	}

	// ------ Komponenty -------
	public int pocetKomponent(Integer[] cisla, int podil) {
		komponent = cisla.length - podil;
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
}
