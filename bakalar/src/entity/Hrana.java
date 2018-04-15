package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

import gui.Main;

public class Hrana {
	private Vrchol prvni;
	private Vrchol druhy;
	public Graphics2D gr;
	public List<Hrana> list = new ArrayList<Hrana>();
	int stroke;
	Color color;
	static int strokeall = 4;

	public Hrana() {
	}

	public Hrana(Vrchol prvni, Vrchol druhy, Color color) {
		this.prvni = prvni;
		this.druhy = druhy;
		prvni.stupen();
		druhy.stupen();
		this.stroke = strokeall;
		this.color = color;
	}

	public Vrchol getPrvni() {
		return prvni;
	}

	public Vrchol getDruhy() {
		return druhy;
	}

	public void setPrvni(Vrchol prvni) {
		this.prvni = prvni;
	}

	public void setDruhy(Vrchol druhy, Color color) {
		this.druhy = druhy;
		Hrana hran = new Hrana(prvni, this.druhy, color);
		list.add(hran);
		boolean pokracovani = true;
		for (int i = 0; i < prvni.sousedi.size(); i++) {
			if (prvni.sousedi.get(i).getNazev() == prvni.nazev) {
				pokracovani = false;
			}
		}
		if(pokracovani)
			prvni.sousedi.add(druhy);
		pokracovani = true;
		for (int i = 0; i < druhy.sousedi.size(); i++) {
			if (druhy.sousedi.get(i).getNazev() == druhy.nazev) {
				pokracovani = false;
			}
		}
		if(pokracovani)
			druhy.sousedi.add(prvni);
	}

	public List<Hrana> getList() {
		return list;
	}

	public void setList(List<Hrana> list) {
		this.list = list;
	}
	
	public int getStroke() {
		return stroke;
	}

	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

	public static int getStrokeAll() {
		return strokeall;
	}

	public static void setStrokeAll(int stroke) {
		strokeall = stroke;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public void delete() {
		for (int w = 0; w < list.size(); w++) {
			list.get(w).getPrvni().stupen = 0;
			list.get(w).getDruhy().stupen = 0;
		}
		list.clear();
		prvni = null;
		druhy = null;
	}

	public void deleteEdge(Vrchol v) {
		List<Hrana> l = new ArrayList<>();

		for (int w = 0; w < list.size(); w++) {
			if (list.get(w).prvni == v || list.get(w).druhy == v) {
				list.get(w).getPrvni().stupen--;
				list.get(w).getDruhy().stupen--;
			} else {
				l.add(list.get(w));
			}
		}
		list.clear();
		list = l;
	}
	
	public void deleteHranu(Hrana h) {
		List<Hrana> l = new ArrayList<>();

		for (int w = 0; w < list.size(); w++) {
			if (list.get(w).prvni.nazev != h.prvni.nazev && list.get(w).druhy.nazev != h.druhy.nazev) {
				l.add(h);
			} 
			list.clear();
			list = l;
		}
	}
}
