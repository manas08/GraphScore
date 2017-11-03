package tools;

import java.awt.Image;
import java.util.List;

import entity.Hrana;
import entity.Vrchol;
import gui.DelEdge;
import gui.Main;


/**
 * 
 * Rozhrani sluzby pro posyktnuti mapy a mmist v ni vyznacenych
 *
 */

public interface MapaService {
	
	
	/**
	 * pridani mista m do evidence
	 * @param m instance, kterou chceme pridat
	 */
	
	public void pridejVrchol(Vrchol m);
	
	/**
	 * smaze z evidence misto se zadanym id
	 * @param id identifikator mista, ktere chcem smazat z evidence
	 */
	
	public void smazVrchol(int id, Hrana hrana);
	
	
	
	/**
	 * vrati misto podle zadaneho id
	 * @param id hledane id
	 * @return misto
	 */
	
	public Vrchol getPodleId (int id);
	
	/**
	 * vrati seznam vsech evidovanych mist
	 * @return seznam mist
	 */
	
	public List<Vrchol> getVrchol();

	void smazList();
	public Integer[] getCisla();
	

	
}
