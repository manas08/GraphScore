package entity;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import tools.ColorChooser;

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
	public int komponenta;
	public boolean navstiveno;
	public boolean prozkoumano;

	// Body pro vytvoreni hrany
	protected Vrchol prvni;
	protected Vrchol druhy;
	public List<Vrchol> sousedi = new ArrayList<Vrchol>();

	Color color;
	int thic;
	static int thickness = 10;
	

	public Vrchol(int x, int y, String nazev, String popis, BufferedImage img, Color color) {
		this.x = x;
		this.y = y;
		this.nazev = nazev;
		this.popis = popis;
		this.img = img;
		this.komponenta = 0;
		this.navstiveno = false;
		this.color = color;
		this.thic = thickness;
	}

	public Vrchol(int id, int x, int y, String nazev, String popis, BufferedImage img, Color color) {
		this.id = id;
		this.x = x;
		this.y = y;
		this.nazev = nazev;
		this.popis = popis;
		this.img = img;
		this.color = color;
		this.thic = thickness;
	}

	public List<Vrchol> getSousedi() {
		return sousedi;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean getNavstiveno() {
		return navstiveno;
	}

	public void setNavstiveno(boolean navstiveno) {
		this.navstiveno = navstiveno;
	}

	public boolean getProzkoumano() {
		return prozkoumano;
	}

	public void setProzkoumano(boolean prozkoumano) {
		this.prozkoumano = prozkoumano;
	}
	
	public int getKomponent() {
		return komponenta;
	}

	public void setKomponent(int komponenta) {
		this.komponenta = komponenta;
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

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public int getThickness() {
		return thic;
	}

	public void setThickness(int thic) {
		this.thic = thic;
	}

	public static int getThicknessAll() {
		return thickness;
	}

	public static void setThicknessAll(int thic) {
		thickness = thic;
	}

	public void paint(Graphics2D g, Vrchol m) {
		// throw new UnsupportedOperationException("Nutno doprogramovat");
		try {
			//System.out.println(m.getNazev() + " " + m.getColor().getGreen() + " " + m.getColor().getRed() + " " + m.getColor().getBlue());
			g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(m.getColor()); // barva bodu
			g.fillOval(x - thic, y - thic, 2 * thic, 2 * thic); // nakresli kolecko, nastaveni
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
