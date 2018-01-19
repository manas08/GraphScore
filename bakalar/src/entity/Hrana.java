package entity;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;

public class Hrana {
	private Vrchol prvni;
	private Vrchol druhy;
	public Graphics2D gr;
	public List<Hrana> list = new ArrayList<Hrana>();
	int stroke;
	Color color;

	public Hrana() {
	}

	public Hrana(Vrchol prvni, Vrchol druhy) {
		this.prvni = prvni;
		this.druhy = druhy;
		prvni.stupen();
		druhy.stupen();
		this.stroke = 4;
		this.color = new Color(165, 49, 68);
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

	public void setDruhy(Vrchol druhy) {
		this.druhy = druhy;
		Hrana hran = new Hrana(prvni, this.druhy);
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

	public void draw(Graphics2D gr) {
		this.gr = gr;
		gr.setStroke(new BasicStroke(stroke));
		gr.setColor(color);
		gr.drawLine(prvni.getX(), prvni.getY(), druhy.getX(), druhy.getY());
		gr.setStroke(new BasicStroke(1));
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
