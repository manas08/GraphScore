package gui;

import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.Border;

import entity.Hrana;
import entity.Vrchol;
import tools.MapaService;
import tools.MemMapaService;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
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

import javax.swing.BorderFactory;
import javax.swing.JButton;

public class Izomorfism extends JPanel {

	BufferedImage image1;
	BufferedImage image2;
	JPanel p1;
	JPanel p2;
	JPanel p3;
	JButton btNewVertex;
	JPanel pnlTlacitka;
	public Hrana hrana1 = new Hrana();
	private MapaService mapaservice1;
	public Hrana hrana2 = new Hrana();
	private MapaService mapaservice2;
	Vrchol vrchol;
	Vrchol v1;
	Main hlavni;
	ToolBar tb;
	int hran1 = 0;
	int vrcholu1 = 0;
	int hran2 = 0;
	int vrcholu2 = 0;
	int poradi = 0;
	int citlivost = 9;
	boolean pomoc = false;
	boolean opak = false;
	boolean def = false;
	boolean prvni = true;
	Integer[] cisla;
	List<Vrchol> puvod = new ArrayList<Vrchol>();

	public Izomorfism(Main main, JButton izom) {

		setSize(800, 400);

		p1 = new JPanel();
		p2 = new JPanel();
		p3 = new JPanel();

		add(p1, "West");
		add(p2, "East");
		setVisible(true);

		mapaservice1 = new MemMapaService();
		mapaservice2 = new MemMapaService();
		image1 = new BufferedImage(563, 856, BufferedImage.TYPE_INT_RGB);
		image2 = new BufferedImage(563, 856, BufferedImage.TYPE_INT_RGB);
		btNewVertex = new JButton("Vložit nový vrchol");
		p1.setPreferredSize(new Dimension(563, 856));
		p2.setPreferredSize(new Dimension(563, 856));

		// prvotní pøekreslení když pohneme myší
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

		// táhnutí bodù
		MouseAdapter mouse = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isLeftMouseButton(e) && pomoc == true && e.getSource() == p1) {
					clear();
					v1 = mapaservice1.getPodleId(poradi);
					v1.setX(e.getX());
					v1.setY(e.getY());
					present1();
				} else if (SwingUtilities.isLeftMouseButton(e) && pomoc == true && e.getSource() == p2) {
					clear();
					v1 = mapaservice2.getPodleId(poradi);
					v1.setX(e.getX());
					v1.setY(e.getY());
					present2();
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
				if (SwingUtilities.isLeftMouseButton(e) && e.getSource() == p1) {
					for (int i = 0; i < mapaservice1.getVrchol().size(); i++) {
						vrchol = mapaservice1.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
							poradi = vrchol.getId();
							pomoc = true;
						}
					}
				} else if (SwingUtilities.isLeftMouseButton(e) && e.getSource() == p2) {
					for (int i = 0; i < mapaservice2.getVrchol().size(); i++) {
						vrchol = mapaservice2.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
							poradi = vrchol.getId();
							pomoc = true;
						}
					}
				}

				else if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() == p1) {
					int citlivost = 9;
					for (int i = 0; i < mapaservice1.getVrchol().size(); i++) {
						Vrchol m = mapaservice1.getVrchol().get(i);
						int tbPolohaX = MouseInfo.getPointerInfo().getLocation().x;
						int tbPolohaY = MouseInfo.getPointerInfo().getLocation().y;

						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY()))
								&& (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							tb = new ToolBar(m, main, hrana1, 1);
							// tb.score(getScore());
							tb.setMapaService(mapaservice1);
							tb.setVisible(true);
							tb.setLocation(tbPolohaX + 15, tbPolohaY + 15);

						}
					}
				}

				else if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() == p2) {
					int citlivost = 9;
					for (int i = 0; i < mapaservice2.getVrchol().size(); i++) {
						Vrchol m = mapaservice2.getVrchol().get(i);
						int tbPolohaX = MouseInfo.getPointerInfo().getLocation().x;
						int tbPolohaY = MouseInfo.getPointerInfo().getLocation().y;

						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY()))
								&& (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							tb = new ToolBar(m, main, hrana2, 1);
							// tb.score(getScore());
							tb.setMapaService(mapaservice2);
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

		// -------Tlaèítka---------
		btNewVertex.setPreferredSize(new Dimension(170, 25));
		btNewVertex.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});

		// ------Umístìní tlaèítek--------
		btNewVertex.setBounds(40, 400, 120, 25);

		mapaservice1.pridejVrchol(new Vrchol(100, 235, "A", "Budova PDF A", null));
		mapaservice1.pridejVrchol(new Vrchol(392, 304, "B", "Budova B", null));
		mapaservice1.pridejVrchol(new Vrchol(160, 507, "C", "Budova FIM J", null));
		p3.add(p1, "East");
		p3.add(p2, "West");
	}

	public void hideshowBTN(Main main, boolean b, int i) {
		for (JButton btn : main.getBTN()) {
			if (i == 1 && btn.getText() == "Zobrazit vlastnosti") {

			} else
				btn.setVisible(b);
		}
	}

	public void aplly(Main main) {
		hlavni = main;
		hlavni.add(p3, "Center");
		pomoc = true;
		this.pnlTlacitka = hlavni.getPanel();
		btNewVertex.setVisible(true);

		if (prvni == true) {
			pnlTlacitka.add(btNewVertex);
			prvni = false;
		}
		hlavni.ableCounts(false);
		clear();
	}

	// samotné vykreslení bodù a hran
	public void vykresliHranu1() {
		Graphics2D gr = image1.createGraphics();

		for (int i = 0; i < hrana1.getList().size(); i++) {
			Hrana h = hrana1.getList().get(i);
			if (h != null) {
				gr.setStroke(new BasicStroke(4));
				gr.setColor(new Color(165, 49, 68));
				gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				gr.drawLine(h.getPrvni().getX(), h.getPrvni().getY(), h.getDruhy().getX(), h.getDruhy().getY());
			}
		}
		for (int i = 0; i < mapaservice1.getVrchol().size(); i++) {
			Vrchol m = mapaservice1.getVrchol().get(i);
			m.paint(gr, m);
		}

		gr.setFont(new Font("TimesRoman", Font.BOLD, 18));
		gr.setColor(Color.BLACK);
		gr.drawString("Graf 1:", 30, 30);

		hran1 = hrana1.getList().size();
		vrcholu1 = mapaservice1.getVrchol().size();
		hlavni.setCounts(hran1, vrcholu1);
		gr.setFont(new Font("TimesRoman", Font.BOLD, 12));
		gr.drawString("Poèet hran:  " + "  " + hran1, 30, 820);
		gr.setFont(new Font("TimesRoman", Font.BOLD, 12));
		gr.drawString("Poèet vrcholù:  " + "  " + vrcholu1, 30, 840);
	}

	// samotné vykreslení bodù a hran
	public void vykresliHranu2() {
		Graphics2D gr = image2.createGraphics();

		for (int i = 0; i < hrana2.getList().size(); i++) {
			Hrana h = hrana2.getList().get(i);
			if (h != null) {
				gr.setStroke(new BasicStroke(4));
				gr.setColor(new Color(165, 49, 68));
				gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				gr.drawLine(h.getPrvni().getX(), h.getPrvni().getY(), h.getDruhy().getX(), h.getDruhy().getY());
			}
		}
		for (int i = 0; i < mapaservice2.getVrchol().size(); i++) {
			Vrchol m = mapaservice2.getVrchol().get(i);
			m.paint(gr, m);
		}

		gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		gr.setFont(new Font("TimesRoman", Font.BOLD, 18));
		gr.setColor(Color.BLACK);
		gr.drawString("Graf 2:", 30, 30);

		hran2 = hrana2.getList().size();
		vrcholu2 = mapaservice2.getVrchol().size();
		gr.setFont(new Font("TimesRoman", Font.BOLD, 12));
		gr.drawString("Poèet hran:  " + "  " + hran2, 30, 820);
		gr.setFont(new Font("TimesRoman", Font.BOLD, 12));
		gr.drawString("Poèet vrcholù:  " + "  " + vrcholu2, 30, 840);
	}

	// metoda pro vykreslení
	public void present() {
		present1();
		present2();
	}
	
	public void present1() {
		Graphics gr = p1.getGraphics();
		vykresliHranu1();
		if (gr != null)
			gr.drawImage(image1, 0, 0, null);
	}
	
	public void present2() {
		Graphics gr = p2.getGraphics();
		vykresliHranu2();
		if (gr != null)
			gr.drawImage(image2, 0, 0, null);
	}

	// vyèištìní plochy
	public void clear() {
		Graphics gr = image1.getGraphics();
		gr.setColor(new Color(192, 192, 192));
		gr.fillRect(0, 0, image1.getWidth(), image1.getHeight());
		gr = image2.getGraphics();
		gr.setColor(new Color(192, 192, 192));
		gr.fillRect(0, 0, image2.getWidth(), image2.getHeight());
	}

	public void disablePanel(boolean b) {
		btNewVertex.setVisible(false);
	}

}