package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

public class Vrchol {

	/**
	 * Jednoznacne identifikuje bod a je jedinecny v cele aplikaci Pokud je id
	 * ==0, pak se jedna o instanci, ktera doposud nebyla zapsana do uloziste
	 * ostatnich mist (tj. neni zaevidovana)
	 */
	private int id = 0;
	private int id2 = 0;

	// X -ova souradnice bodu na platne pocatek je v levem hornim rohu platna
	protected int x;
	protected int y;

	// Nazev bodu
	protected String nazev;

	// Popis mista
	protected String popis;
	protected BufferedImage img;
	public int stupen = 0;

	// Body pro vytvoreni hrany
	protected Vrchol prvni;
	protected Vrchol druhy;

	public Vrchol(int x, int y, String nazev, String popis, BufferedImage img) {
		this.x = x;
		this.y = y;
		this.nazev = nazev;
		this.popis = popis;
		this.img = img;
	}

	public Vrchol(int id, int x, int y, String nazev, String popis, BufferedImage img) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.nazev = nazev;
		this.popis = popis;
		this.img = img;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public String getNazev() {
		return nazev;
	}

	public void setNazev(String nazev) {
		this.nazev = nazev;
	}

	public String getPopis() {
		return popis;
	}

	public void setPopis(String popis) {
		this.popis = popis;
	}

	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public void paint(Graphics2D g, Vrchol m) {
		// throw new UnsupportedOperationException("Nutno doprogramovat");
		try {
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(new Color(0, 0, 0)); // barva bodu
			g.fillOval(x - 10, y - 10, 20, 20); // nakresli kolecko, nastaveni
												// velikosti
			g.setFont(new Font("default", Font.BOLD, 16));
			g.drawString(m.getNazev(), x - 5, y + 30);
		} catch (Exception e) {
			throw new UnsupportedOperationException("Chyba");
		}
	}

	public void prvni(Vrchol prvni) {
		this.prvni = prvni;
	}

	public void druhy(Vrchol druhy) {
		this.druhy = druhy;
	}

	public void stupen() {
		this.stupen++;
	}

	public int getStupen() {
		return stupen;
	}

	public void setStupen() {
		this.stupen--;
	}
	
	public void setStupen2(int stupen) {
		this.stupen = stupen;
	}
	
	public void saveID(){
		this.id2=getId();
	}
	
	public void setID2(){
		this.id=this.id2;
	}
}
