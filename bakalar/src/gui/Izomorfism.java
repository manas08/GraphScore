package gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import entity.Hrana;
import entity.Vrchol;
import tools.MapaService;
import tools.MemMapaService;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;

public class Izomorfism extends JPanel  {



	BufferedImage image1;
	BufferedImage image2;
	JPanel p1;
	JPanel p2;
	JPanel p3;
	JButton btNewScore;
	JPanel pnlTlacitka;
	public Hrana hrana = new Hrana();
	private MapaService mapaservice;
	Vrchol vrchol;
	Vrchol v1;
	Main hlavni;
	ToolBar tb;
	private int sirka = 1136;
	private int vyska = 856;
	int hran = 0;
	int vrcholu = 0;
	int poradi = 0;
	int citlivost = 9;
	boolean pomoc = false;
	boolean opak = false;
	boolean def = false;
	boolean prvni = true;
	Integer[] cisla;
	List<Vrchol> puvod = new ArrayList<Vrchol>();

	public Izomorfism(Main main, JButton izom) {

		setSize(800,400);	
		
		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();
		
		add(p1,"West");
		add(p2,"East");
		setVisible(true);

		
		mapaservice = new MemMapaService();
		image1 = new BufferedImage(563, 866, BufferedImage.TYPE_INT_RGB);
		image2 = new BufferedImage(563, 866, BufferedImage.TYPE_INT_RGB);
		btNewScore = new JButton("Zadat sk�re");
		
		// prvotn� p�ekreslen� kdy� pohneme my��
		izom.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (pomoc == true) {
					present();
					pomoc = false;
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub
			}
		});

		// t�hnut� bod�
		MouseAdapter mouse = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && pomoc == true) {
					clear();
					v1 = mapaservice.getPodleId(poradi);
					v1.setX(e.getX());
					v1.setY(e.getY());
					present();
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		};

		MouseListener ml = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				pomoc = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e)) {
					for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
						vrchol = mapaservice.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY())) && (((vrchol.getX() - citlivost) <= e.getX()) && (((vrchol.getX() + citlivost) >= e.getX())))) {
							poradi = vrchol.getId();
							pomoc = true;
						}
					}
				}

				else if (e.getButton() == MouseEvent.BUTTON3) {
					int citlivost = 9;
					for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
						Vrchol m = mapaservice.getVrchol().get(i);
						int tbPolohaX = MouseInfo.getPointerInfo().getLocation().x;
						int tbPolohaY = MouseInfo.getPointerInfo().getLocation().y;

						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY())) && (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							tb = new ToolBar(m, main, hrana, 1);
							//tb.score(getScore());
							tb.setMapaService(mapaservice);
							tb.setVisible(true);
							tb.setLocation(tbPolohaX + 15, tbPolohaY + 15);

						}
					}
				}
			}

			@Override
			public void mouseExited(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseClicked(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		};

		p1.addMouseMotionListener(mouse);
		p1.addMouseListener(ml);
		p2.addMouseMotionListener(mouse);
		p2.addMouseListener(ml);

		// -------Tla��tka---------
		btNewScore.setPreferredSize(new Dimension(170, 25));
		btNewScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});

		// ------Um�st�n� tla��tek--------
		btNewScore.setBounds(40, 400, 120, 25);

		mapaservice.pridejVrchol(new Vrchol(100, 235, "A", "Budova PDF A", null));
		mapaservice.pridejVrchol(new Vrchol(392, 304, "B", "Budova B", null));
		mapaservice.pridejVrchol(new Vrchol(600, 320, "C", "Budova C", null));
		mapaservice.pridejVrchol(new Vrchol(10, 507, "D", "Budova FIM J", null));
		p3.add(p1, "East");
		p3.add(p2, "West");
	}

	public void hideshowBTN(Main main, boolean b, int i) {
		for (JButton btn : main.getBTN()) {
			if(i == 1 && btn.getText()=="Zobrazit vlastnosti"){
				
			}else
				btn.setVisible(b);
		}
	}
	
	public void aplly(Main main) {
		hlavni = main;
		hlavni.add(p3, "Center");
		pomoc = true;
		this.pnlTlacitka = hlavni.getPanel();
		btNewScore.setVisible(true);

		if (prvni == true) {
			pnlTlacitka.add(btNewScore);
			prvni = false;
		}

		clear();
	}
	


	

	// samotn� vykreslen� bod� a hran
	public void vykresliHranu() {
		Graphics2D gr = image1.createGraphics();

		for (int i = 0; i < hrana.getList().size(); i++) {
			Hrana h = hrana.getList().get(i);
			if (h != null) {
				gr.setStroke(new BasicStroke(4));
				gr.setColor(new Color(165, 49, 68));
				gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				gr.drawLine(h.getPrvni().getX(), h.getPrvni().getY(), h.getDruhy().getX(), h.getDruhy().getY());
			}
		}
		System.out.println("erger");
		for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
			Vrchol m = mapaservice.getVrchol().get(i);
			System.out.println(mapaservice.getVrchol().get(i).getNazev());
			m.paint(gr, m);
		}

		hran = hrana.getList().size();
		vrcholu = mapaservice.getVrchol().size();
		hlavni.setCounts(hran, vrcholu);
	}

	// metoda pro vykreslen�
	public void present() {
		vykresliHranu();
		if (p1.getGraphics() != null)
			p1.getGraphics().drawImage(image1, 0, 0, null);
		vykresliHranu();
		if (p2.getGraphics() != null)
			p2.getGraphics().drawImage(image2, 0, 0, null);
	}

	// vy�i�t�n� plochy
	public void clear() {
		Graphics gr = image1.getGraphics();
		gr.setColor(new Color(238, 238, 238));
		gr.fillRect(0, 0, image1.getWidth(), image1.getHeight());
		gr = image2.getGraphics();
		gr.setColor(new Color(238, 238, 238));
		gr.fillRect(0, 0, image2.getWidth(), image2.getHeight());
	}


	public void disablePanel(boolean b) {
		btNewScore.setVisible(false);
	}

}