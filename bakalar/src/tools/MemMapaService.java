package tools;

import java.util.ArrayList;
import java.util.List;
import entity.Hrana;
import entity.Vrchol;

public class MemMapaService implements MapaService {
	private List<Vrchol> vrchol = new ArrayList<Vrchol>();
	private int IDcko = 0;

	@Override
	public List<Vrchol> getVrchol() {
		return vrchol;
	}

	@Override
	public Vrchol getPodleId(int id) {
		for (Vrchol m : vrchol) {
			if (m.getId() == id) {
				return m;
			}
		}
		return null;
	}

	@Override
	public void pridejVrchol(Vrchol m) {
		vrchol.add(m);
		IDcko++; // narust IDcko o 1
		m.setId(IDcko);

	}

	@Override
	public void smazVrchol(int id, Hrana hrana) {
		Vrchol m = getPodleId(id);
		if (m != null) {
			vrchol.remove(m);
			hrana.deleteEdge(m);
		}
	}

	@Override
	public void smazList() {
		vrchol.clear();
		IDcko = 0;
	}

	@Override
	public Integer[] getCisla() {
		Integer[] cisla = new Integer[vrchol.size()];
		int t = 0;
		for (Vrchol v : vrchol) {
			cisla[t] = v.getStupen();
			t++;
		}
		return cisla;
	}
}
