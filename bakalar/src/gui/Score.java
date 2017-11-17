package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
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
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import entity.Hrana;
import entity.Vrchol;
import tools.Features;
import tools.MapaService;
import tools.MemMapaService;

public class Score extends JPanel {

	BufferedImage image;
	JPanel panel;
	JButton btNewScore;
	JButton btAlternative;
	JTextField souvisly1;
	JTextField rovinny1;
	JTextField euler1;
	JTextField strom1;
	JTextField komp1;
	JTextField souvisly2;
	JTextField rovinny2;
	JTextField euler2;
	JTextField strom2;
	JTextField komp2;
	JPanel pnlTlacitka;
	public Hrana hrana = new Hrana();
	private MapaService mapaservice;
	Features features = new Features();
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
	boolean alternativni = true;
	Integer[] cisla;
	List<Vrchol> puvod = new ArrayList<Vrchol>();
	MouseListener ml;

	public Score(JButton score, Main main) {
		panel = new JPanel();
		mapaservice = new MemMapaService();
		image = new BufferedImage(sirka + 10, vyska + 10, BufferedImage.TYPE_INT_RGB);
		btNewScore = new JButton("Zadat skóre");
		btAlternative = new JButton("Alternativní graf");
		souvisly1 = new JTextField("Souvislý?");
		rovinny1 = new JTextField("Rovinný?");
		euler1 = new JTextField("Eulerovský?");
		strom1 = new JTextField("Strom?");
		komp1 = new JTextField("Poèet komponent:");
		souvisly2 = new JTextField("");
		rovinny2 = new JTextField("");
		euler2 = new JTextField("");
		strom2 = new JTextField("");
		komp2 = new JTextField("");

		// prvotní pøekreslení když pohneme myší
		score.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (pomoc == true) {
					present();
					setFeatures();
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

		ml = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				pomoc = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				System.out.println("score");
				if (SwingUtilities.isLeftMouseButton(e)) {
					for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
						vrchol = mapaservice.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
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

						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY()))
								&& (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							tb = new ToolBar(m, main, hrana, 1);
							tb.score(getScore());
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

		panel.addMouseMotionListener(mouse);
		panel.addMouseListener(ml);

		// -------Tlaèítka---------
		btNewScore.setPreferredSize(new Dimension(170, 25));
		btNewScore.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				getMapaservice().smazList();
				if (opak == true) {
					hrana.delete();
					clear();
					present();
				}
				NewScore s = new NewScore(getScore());
				s.setLocationRelativeTo(null);
				s.setVisible(true);
				opak = true;
			}
		});

		btAlternative.setPreferredSize(new Dimension(170, 25));
		btAlternative.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				hrana.delete();
				clear();
				generateEdge2(cisla);
				btAlternative.setVisible(false);
			}
		});

		// ------Umístìní tlaèítek--------
		btNewScore.setBounds(40, 400, 120, 25);
		btAlternative.setBounds(40, 460, 120, 25);

		vytvorGUI();
	}

	public void aplly(Main main, JButton score) {
		hlavni = main;
		hlavni.repaint();
		hlavni.add(panel, "Center");

		pomoc = true;
		this.pnlTlacitka = hlavni.getPanel();
		btNewScore.setVisible(true);
		souvisly1.setVisible(true);
		rovinny1.setVisible(true);
		euler1.setVisible(true);
		strom1.setVisible(true);
		komp1.setVisible(true);
		souvisly2.setVisible(true);
		rovinny2.setVisible(true);
		euler2.setVisible(true);
		strom2.setVisible(true);
		komp2.setVisible(true);

		if (prvni == true) {
			pnlTlacitka.add(btNewScore);
			pnlTlacitka.add(btAlternative);
			pnlTlacitka.add(souvisly1);
			pnlTlacitka.add(souvisly2);
			pnlTlacitka.add(rovinny1);
			pnlTlacitka.add(rovinny2);
			pnlTlacitka.add(euler1);
			pnlTlacitka.add(euler2);
			pnlTlacitka.add(strom1);
			pnlTlacitka.add(strom2);
			pnlTlacitka.add(komp1);
			pnlTlacitka.add(komp2);
			prvni = false;
			btAlternative.setVisible(false);
		}

		clear();
	}

	// samotné vykreslení bodù a hran
	public void vykresliHranu() {
		Graphics2D gr = image.createGraphics();

		for (int i = 0; i < hrana.getList().size(); i++) {
			Hrana h = hrana.getList().get(i);
			if (h != null) {
				gr.setStroke(new BasicStroke(4));
				gr.setColor(new Color(165, 49, 68));
				gr.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				gr.drawLine(h.getPrvni().getX(), h.getPrvni().getY(), h.getDruhy().getX(), h.getDruhy().getY());
			}
		}

		for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
			Vrchol m = mapaservice.getVrchol().get(i);
			m.paint(gr, m);
		}

		hran = hrana.getList().size();
		vrcholu = mapaservice.getVrchol().size();
		hlavni.setCounts(hran, vrcholu);
	}

	// metoda pro vykreslení
	public void present() {
		vykresliHranu();
		// if (panel.getGraphics() != null)
		panel.getGraphics().drawImage(image, 0, 0, null);
	}

	// vyèištìní plochy
	public void clear() {
		Graphics gr = image.getGraphics();
		gr.setColor(new Color(238, 238, 238));
		gr.fillRect(0, 0, image.getWidth(), image.getHeight());
	}

	// generování bodù do plátna do kruhu
	public void generatePoints(int vrcholy) {
		clear();
		if (vrcholy > 0) {
			int x;
			int y;
			char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
			if (vrcholy > 1) {
				for (int i = 0; i < vrcholy; i++) {
					double angle = 2 * Math.PI * i / vrcholy;
					x = (int) Math.round((sirka / 2) + 300 * Math.cos(angle));
					y = (int) Math.round((vyska / 2) + 300 * Math.sin(angle));
					mapaservice.pridejVrchol(new Vrchol(x, y, String.valueOf(alphabet[i]), " ", null));
				}
			} else if (vrcholy == 1) {
				mapaservice.pridejVrchol(new Vrchol(sirka / 2, vyska / 2, "A", " ", null));
			}
		}
	}

	public Score getScore() {
		return this;
	}

	public Hrana getHrana() {
		return hrana;
	}

	public MapaService getMapaservice() {
		return mapaservice;
	}

	// rozdìlení hran mezi vrcholy
	public void generateEdge(Integer[] cisla) {
		this.cisla = cisla;
		Integer[] druhy = new Integer[cisla.length];
		for (int i = 0; i < cisla.length; i++) {
			druhy[i] = cisla[i];
		}

		int vybrane;
		int prehoz;
		boolean podm = false;
		List<Vrchol> vrch = mapaservice.getVrchol();
		for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
			puvod.add(mapaservice.getVrchol().get(i));
		}
		for (Vrchol vrchol : vrch) {
			vrchol.saveID();
		}
		int i = 0;
		while (podm == false) {
			for (int j = 0; j < druhy.length - 1; j++) {
				for (int j2 = j + 1; j2 < druhy.length; j2++) {
					if (druhy[j] < druhy[j2]) {
						Vrchol pomocny;
						prehoz = druhy[j];
						druhy[j] = druhy[j2];
						druhy[j2] = prehoz;
						pomocny = mapaservice.getVrchol().get(j);

						vrch.set(j, vrch.get(j2));
						vrch.get(j2).setId(j + 1);
						vrch.set(j2, pomocny);
						pomocny.setId(j2 + 1);
					}
				}
			}

			vybrane = druhy[i];
			hrana.setPrvni(mapaservice.getVrchol().get(i));
			if (vybrane == 0) {
				present();
				refresh();
				for (Vrchol vrchol : vrch) {
					vrchol.setID2();
				}
				showAlternative();
				return;
			}

			for (int j = 1 + i; j <= vybrane + i; j++) {
				hrana.setDruhy(mapaservice.getVrchol().get(j));
				druhy[j] -= 1;
			}

			i++;
			if (i == druhy.length)
				podm = true;
		}
		present();
		refresh();

		for (Vrchol vrchol : vrch) {
			vrchol.setID2();
		}
		showAlternative();
	}

	// rozdìlení hran mezi vrcholy
	public void generateEdge2(Integer[] cisla) {
		this.cisla = cisla;
		Integer[] druhy = new Integer[cisla.length];
		for (int i = 0; i < cisla.length; i++) {
			druhy[i] = cisla[i];
		}

		int prehoz;
		boolean podm = false;
		for (Vrchol vrchol : puvod) {
			vrchol.saveID();
		}
		int j = 0;
		while (podm == false) {
			for (int j1 = 0; j1 < druhy.length - 1; j1++) {
				for (int j2 = j1 + 1; j2 < druhy.length; j2++) {
					if (druhy[j1] < druhy[j2]) {
						Vrchol pomocny;
						prehoz = druhy[j1];
						druhy[j1] = druhy[j2];
						druhy[j2] = prehoz;
						pomocny = mapaservice.getVrchol().get(j1);

						puvod.set(j1, puvod.get(j2));
						puvod.get(j2).setId(j1 + 1);
						puvod.set(j2, pomocny);
						pomocny.setId(j2 + 1);
					}
				}
			}

			for (int i = 0; i < druhy.length - 1; i++) {
				if (druhy[i] == 0 || druhy[i + 1] == 0) {
					present();
					refresh();
					podm = true;

					for (Vrchol vrchol : puvod) {
						vrchol.setID2();
					}
					return;
				} else {

					j = i + 1;
					hrana.setPrvni(puvod.get(i));
					hrana.setDruhy(puvod.get(j));

					druhy[i] -= 1;
					druhy[j] -= 1;
					i++;
					if (druhy[i] == 0) {
						i = druhy.length;
					}
					i--;
				}
			}
		}
		present();
		refresh();

		for (Vrchol vrchol : puvod) {
			vrchol.setID2();
		}
	}

	public void refresh() {
		cisla = mapaservice.getCisla();
		features.main(cisla, mapaservice);
		setFeatures();
	}

	public void disablePanel(boolean b) {
		btNewScore.setVisible(false);
		btAlternative.setVisible(false);
		souvisly1.setVisible(b);
		rovinny1.setVisible(b);
		euler1.setVisible(b);
		strom1.setVisible(b);
		komp1.setVisible(b);
		souvisly2.setVisible(b);
		rovinny2.setVisible(b);
		euler2.setVisible(b);
		strom2.setVisible(b);
		komp2.setVisible(b);
		if (b == false)
			if (hlavni != null)
				hlavni.remove(panel); // øeší pøepínání mouselisteneru
	}

	public void setFeatures() {
		if (def == true) {
			if (features.isSouvisly())
				souvisly2.setText("ANO");
			else
				souvisly2.setText("NE");

			if (features.isRovinny())
				rovinny2.setText("ANO");
			else
				rovinny2.setText("NE");

			if (features.isEuler())
				euler2.setText("ANO");
			else
				euler2.setText("NE");

			if (features.isStrom())
				strom2.setText("ANO");
			else
				strom2.setText("NE");
			komp2.setText(Integer.toString(features.getKomponent()));
		} else
			def = true;
	}

	// -----Umístìní informací o grafu------
	public void vytvorGUI() {
		souvisly1.setBounds(25, 500, 170, 25);
		souvisly1.setEnabled(false);
		souvisly1.setDisabledTextColor(new Color(240, 150, 80));
		souvisly1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		souvisly1.setBackground(new Color(47, 48, 60));
		souvisly1.setBorder(null);

		souvisly2.setBounds(150, 500, 30, 25);
		souvisly2.setEnabled(false);
		souvisly2.setDisabledTextColor(new Color(80, 250, 240));
		souvisly2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		souvisly2.setBackground(new Color(47, 48, 60));
		souvisly2.setBorder(null);

		rovinny1.setBounds(25, 530, 170, 25);
		rovinny1.setEnabled(false);
		rovinny1.setDisabledTextColor(new Color(240, 150, 80));
		rovinny1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		rovinny1.setBackground(new Color(47, 48, 60));
		rovinny1.setBorder(null);

		rovinny2.setBounds(150, 530, 30, 25);
		rovinny2.setEnabled(false);
		rovinny2.setDisabledTextColor(new Color(80, 250, 240));
		rovinny2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		rovinny2.setBackground(new Color(47, 48, 60));
		rovinny2.setBorder(null);

		euler1.setBounds(25, 560, 170, 25);
		euler1.setEnabled(false);
		euler1.setDisabledTextColor(new Color(240, 150, 80));
		euler1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		euler1.setBackground(new Color(47, 48, 60));
		euler1.setBorder(null);

		euler2.setBounds(150, 560, 30, 25);
		euler2.setEnabled(false);
		euler2.setDisabledTextColor(new Color(80, 250, 240));
		euler2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		euler2.setBackground(new Color(47, 48, 60));
		euler2.setBorder(null);

		strom1.setBounds(25, 590, 170, 25);
		strom1.setEnabled(false);
		strom1.setDisabledTextColor(new Color(240, 150, 80));
		strom1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		strom1.setBackground(new Color(47, 48, 60));
		strom1.setBorder(null);

		strom2.setBounds(150, 590, 30, 25);
		strom2.setEnabled(false);
		strom2.setDisabledTextColor(new Color(80, 250, 240));
		strom2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		strom2.setBackground(new Color(47, 48, 60));
		strom2.setBorder(null);

		komp1.setBounds(25, 620, 170, 25);
		komp1.setEnabled(false);
		komp1.setDisabledTextColor(new Color(240, 150, 80));
		komp1.setFont(new Font("Times New Roman", Font.BOLD, 14));
		komp1.setBackground(new Color(47, 48, 60));
		komp1.setBorder(null);

		komp2.setBounds(150, 620, 30, 25);
		komp2.setEnabled(false);
		komp2.setDisabledTextColor(new Color(80, 250, 240));
		komp2.setFont(new Font("Times New Roman", Font.BOLD, 14));
		komp2.setBackground(new Color(47, 48, 60));
		komp2.setBorder(null);
		def = true;
	}

	public JTextField[] getBTNs() {
		JTextField[] btn = new JTextField[10];
		btn[0] = souvisly1;
		btn[1] = souvisly2;
		btn[2] = rovinny1;
		btn[3] = rovinny2;
		btn[4] = euler1;
		btn[5] = euler2;
		btn[6] = strom1;
		btn[7] = strom2;
		btn[8] = komp1;
		btn[9] = komp2;
		return btn;
	}

	private void showAlternative() {
		for (Integer t : cisla) {
			if (t == 2) {
				alternativni = true;
			} else {
				alternativni = false;
				btAlternative.setVisible(alternativni);
				return;
			}
		}
		btAlternative.setVisible(alternativni);
	}
}
