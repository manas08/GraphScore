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

	public Hrana() {
	}

	public Hrana(Vrchol prvni, Vrchol druhy, Color color) {
		this.prvni = prvni;
		this.druhy = druhy;
		prvni.stupen();
		druhy.stupen();
		this.stroke = 4;
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
}
