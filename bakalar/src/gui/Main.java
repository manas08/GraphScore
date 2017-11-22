package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import entity.Hrana;
import entity.Vrchol;
import tools.Features;
import tools.MapaService;
import tools.MemMapaService;

public class Main extends JFrame {

	private MapaService mapaservice = new MemMapaService();;
	private JPanel pnlMapa;
	JPanel pnlTlacitka = new JPanel();
	private int sirka = 1136;
	private int vyska = 856;
	public static Main main;
	public int pocet = 2;
	public Hrana hrana = new Hrana();
	DelEdge edge;
	Vrchol vrchol;
	Vrchol vrchol2;
	Izomorfism izo;
	private int hran = 0;
	private int vrcholu = 0;
	JTextField hrany = new JTextField();
	JTextField vrcholy = new JTextField();
	JTextField navigace = new JTextField("Navigace:");
	JTextField vlastnosti = new JTextField("Vlastnosti:");
	BufferedImage image;
	int mys1X;
	int mys1Y;
	boolean podm = false;
	int okno = 0;
	int poradi;
	Score score;
	Integer[] cisla;
	Features features = new Features();

	JButton btIzomor = new JButton("Izomorfismus");
	JButton btHome = new JButton("Kreslení grafu");
	JButton btScore = new JButton("Kreslit podle skóre");
	JButton btSmaz = new JButton("Nový graf");
	JButton btPridat = new JButton("Pøidej vrchol");
	JButton btDelHrany = new JButton("Odstraò všechny hrany");
	JButton btDelHranu = new JButton("Odstraò hranu");
	JButton btFeatures = new JButton("Zobrazit vlastnosti");
	List<JButton> btns = new ArrayList<>();

	public Main() {
		super("GraphScore 0.2.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		vytvorGui();

		setSize(sirka + 200, vyska);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(false);
		pack();
		image = new BufferedImage(sirka + 10, vyska + 10, BufferedImage.TYPE_INT_RGB);
		clear();
		btns.add(btHome);
		btns.add(btIzomor);
		btns.add(btScore);
	}

	public void vytvorGui() {

		pnlMapa = new JPanel();
		pnlMapa.setPreferredSize(new Dimension(sirka + 10, vyska + 10));
		izo = new Izomorfism(main, btIzomor);
		add(pnlMapa, "Center");
		score = new Score(btScore,this);
		score.setVisible(false);

		pnlTlacitka.setLayout(null);
		pnlTlacitka.setPreferredSize(new Dimension(200, vyska));
		pnlTlacitka.setVisible(true);
		pnlTlacitka.setBorder(BorderFactory.createLineBorder(new Color(0, 0, 0), 5));

		mapaservice.pridejVrchol(new Vrchol(100, 235, "A", "Budova PDF A", null));
		mapaservice.pridejVrchol(new Vrchol(392, 304, "B", "Budova B", null));
		mapaservice.pridejVrchol(new Vrchol(600, 320, "C", "Budova C", null));
		mapaservice.pridejVrchol(new Vrchol(464, 507, "D", "Budova FIM J", null));
		addWindowListener(new WindowListener() {

			@Override
			public void windowOpened(WindowEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void windowIconified(WindowEvent e) {
				podm = true;
			}

			@Override
			public void windowDeiconified(WindowEvent e) {
			}

			@Override
			public void windowDeactivated(WindowEvent e) {
			}

			@Override
			public void windowClosing(WindowEvent e) {

			}

			@Override
			public void windowClosed(WindowEvent e) {

			}

			@Override
			public void windowActivated(WindowEvent e) {
				if (btIzomor.isEnabled()==false) {
					podm = false;
					izo.present();
				} else if (podm == true && btHome.isEnabled()==false) {
					podm = false;
					present();
				} else if (btScore.isEnabled()==false) {
					podm = false;
					score.present();
				}
			}
		});
		pnlMapa.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {
			}

			public void mouseEntered(MouseEvent e) {
			}

			public void mouseExited(MouseEvent e) {
			}

			public void mousePressed(MouseEvent e) {
				int citlivost = 9;
				if (e.getButton() == MouseEvent.BUTTON1) {
					for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
						vrchol = mapaservice.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY())) && (((vrchol.getX() - citlivost) <= e.getX()) && (((vrchol.getX() + citlivost) >= e.getX())))) {
							hrana.setPrvni(vrchol);
							vrchol2 = hrana.getPrvni();
							mys1X = e.getX();
							mys1Y = e.getY();
							pocet = 1;
						}
					}
				}else if (SwingUtilities.isMiddleMouseButton(e)) {
					for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
						vrchol = mapaservice.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
							poradi = vrchol.getId();
						}
					}
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
						Vrchol m = mapaservice.getVrchol().get(i);
						int tbPolohaX = MouseInfo.getPointerInfo().getLocation().x;
						int tbPolohaY = MouseInfo.getPointerInfo().getLocation().y;

						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY())) && (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							ToolBar tb = new ToolBar(m, main, hrana,0);
							tb.setMapaService(mapaservice);
							tb.setVisible(true);
							tb.setLocation(tbPolohaX + 15, tbPolohaY + 15);
							tb.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

						}
					}
					}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1) {
					int citlivost = 9;
					int porovnej = 0;
					for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
						Vrchol m = mapaservice.getVrchol().get(i);
						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY())) && (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							if (hrana.getPrvni() != m) {
								if (hrana.getList().size() != 0) {

									for (int j = 0; j < hrana.getList().size(); j++) {// prohledáme
																						// všechny
																						// hrany
										Hrana hr = hrana.getList().get(j);
										// když už daná hrana bude exisovat tak
										// nedìlat novou AB BA
										if ((hr.getPrvni() == m && hr.getDruhy() == vrchol2)
												|| (hr.getPrvni() == vrchol2 && hr.getDruhy() == m)) {
											porovnej = 1;
											clear();
											present();
											return;
											// jinak udìlat novou hranu
										} else if (pocet == 1) {
											porovnej = 0;
										}
									}
									// porovnání výsledkù
									if (porovnej == 0) {
										hrana.setDruhy(m);
										pocet = 0;
									}

								} else {
									hrana.setDruhy(m);
									pocet = 0;
								}
							}

						}
					}
					clear();
					present();
				}
			}
		});

		MouseAdapter mouse = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				Vrchol v1;
				if (pocet == 1) {
					clear();
					if (SwingUtilities.isLeftMouseButton(e)) {
						Graphics2D gr = image.createGraphics();
						int mys2X = e.getX();
						int mys2Y = e.getY();
						gr.setStroke(new BasicStroke(4));
						gr.setColor(new Color(165, 49, 68));
						gr.drawLine(mys1X, mys1Y, mys2X, mys2Y);
						present();
					}
				} if (SwingUtilities.isMiddleMouseButton(e)) {
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
		
		pnlMapa.addMouseMotionListener(mouse);

		btHome.addMouseMotionListener(new MouseMotionListener() {

			@Override
			public void mouseMoved(MouseEvent e) {
				if (okno == 1) {
					present();
					okno = 0;
				}
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});

		btSmaz.setPreferredSize(new Dimension(170, 25));
		btSmaz.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				pnlMapa.setEnabled(true);
				hrana.delete();
				mapaservice.smazList();
				clear();
				present();
			}
		});

		btPridat.setPreferredSize(new Dimension(170, 25));
		btPridat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				NewVertex o = new NewVertex(main, izo, 1);
				o.setLocationRelativeTo(null);
				o.setMapaService(mapaservice);
				o.setJPanelImage(pnlMapa);
				o.setVisible(true);
			}
		});

		btDelHrany.setPreferredSize(new Dimension(170, 25));
		btDelHrany.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pnlMapa.setEnabled(true);
				clear();
				hrana.delete();
				present();
			}
		});

		btDelHranu.setPreferredSize(new Dimension(170, 25));
		btDelHranu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DelEdge edge = new DelEdge(hrana);
				edge.setLocationRelativeTo(null);
				edge.delete(hrana, main, izo, 0);
			}
		});

		btFeatures.setPreferredSize(new Dimension(170, 25));
		btFeatures.addMouseListener(new java.awt.event.MouseAdapter() {
		    public void mouseEntered(java.awt.event.MouseEvent evt) {
				izo.hideshowBTN(main, false, 1);
				score.vytvorGUI();
				score.disablePanel(true);
				JTextField[] btn = score.getBTNs();
				for (JTextField jTextField : btn) {
					pnlTlacitka.add(jTextField);
				}
				setFeatures(cisla, btn);
		    }

		    public void mouseExited(java.awt.event.MouseEvent evt) {
				izo.hideshowBTN(main, true, 1);
				score.disablePanel(false);
		    }
		});

		btIzomor.setPreferredSize(new Dimension(170, 25));
		btIzomor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				score.disablePanel(false);
				pnlMapa.setVisible(false);
				izo.hideshowBTN(main, false, 0);
				izo.aplly(main);
				disableBTN(btIzomor);
			}
		});

		disableBTN(btHome);
		btHome.setPreferredSize(new Dimension(170, 25));
		btHome.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				izo.hideshowBTN(main, true, 0);
				pnlMapa.setVisible(true);
				okno = 1;
				disableBTN(btHome);
				score.disablePanel(false);
				ableCounts(true);
				izo.disIzo();
			}
		});

		btScore.setPreferredSize(new Dimension(170, 25));
		btScore.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				izo.disIzo();
				pnlMapa.setVisible(false);
				izo.hideshowBTN(main, false, 0);
				score.aplly(main, btScore);
				disableBTN(btScore);
				ableCounts(true);
			}
		});
		

		hrany.setBounds(20, 750, 170, 25);
		hrany.setEnabled(false);
		hrany.setDisabledTextColor(new Color(240, 150, 80));
		hrany.setFont(new Font("Times New Roman", Font.BOLD, 20));
		hrany.setBackground(new Color(47, 48, 60));
		hrany.setBorder(null);
		pnlTlacitka.add(hrany);

		vrcholy.setBounds(20, 800, 170, 25);
		vrcholy.setEnabled(false);
		vrcholy.setDisabledTextColor(new Color(240, 150, 80));
		vrcholy.setFont(new Font("Times New Roman", Font.BOLD, 20));
		vrcholy.setBackground(new Color(47, 48, 60));
		vrcholy.setBorder(null);
		pnlTlacitka.add(vrcholy);

		navigace.setBounds(20, 40, 170, 25);
		navigace.setEnabled(false);
		navigace.setDisabledTextColor(new Color(240, 150, 80));
		navigace.setFont(new Font("Times New Roman", Font.BOLD, 20));
		navigace.setBackground(new Color(47, 48, 60));
		navigace.setBorder(null);
		pnlTlacitka.add(navigace);

		vlastnosti.setBounds(20, 350, 170, 25);
		vlastnosti.setEnabled(false);
		vlastnosti.setDisabledTextColor(new Color(240, 150, 80));
		vlastnosti.setFont(new Font("Times New Roman", Font.BOLD, 20));
		vlastnosti.setBackground(new Color(47, 48, 60));
		vlastnosti.setBorder(null);
		pnlTlacitka.add(vlastnosti);

		// vlastnosti
		btSmaz.setBounds(40, 410, 120, 25);
		pnlTlacitka.add(btSmaz);
		btPridat.setBounds(40, 470, 120, 25);
		pnlTlacitka.add(btPridat);
		btDelHranu.setBounds(40, 530, 120, 25);
		pnlTlacitka.add(btDelHranu);
		btDelHrany.setBounds(15, 590, 165, 25);
		pnlTlacitka.add(btDelHrany);
		btFeatures.setBounds(15, 650, 165, 25);
		pnlTlacitka.add(btFeatures);
		btFeatures.setBounds(15, 710, 165, 25);
		pnlTlacitka.add(btFeatures);

		// navigace
		btHome.setBounds(40, 100, 120, 25);
		pnlTlacitka.add(btHome);
		btIzomor.setBounds(15, 160, 165, 25);
		pnlTlacitka.add(btIzomor);
		btScore.setBounds(15, 220, 165, 25);
		pnlTlacitka.add(btScore);
		pnlTlacitka.setBackground(new Color(47, 48, 60));

		add(pnlTlacitka, "East");

		pack();
	}

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
		setCounts(hran, vrcholu);
		graphScore();
	}

	public void vykresliHranu(List<Hrana> listPom) {
		hrana.setList(listPom);
		clear();
		present();
		edge = new DelEdge(hrana);
		edge.setLocationRelativeTo(null);
		edge.delete(hrana, main, izo, 0);
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		main = new Main();
		SwingUtilities.invokeLater(() -> {
			SwingUtilities.invokeLater(() -> {
				SwingUtilities.invokeLater(() -> {
					SwingUtilities.invokeLater(() -> {
						main.present();
					});
				});
			});
		});
	}

	public void present() {
		vykresliHranu();
		if (pnlMapa.getGraphics() != null)
			pnlMapa.getGraphics().drawImage(image, 0, 0, null);
	}

	public void clear() {
		Graphics gr = image.getGraphics();
		gr.setColor(new Color(238, 238, 238));
		gr.fillRect(0, 0, image.getWidth(), image.getHeight());
	}

	public List<JButton> getBTN() {
		List<JButton> blist = new ArrayList<JButton>();
		blist.add(btDelHranu);
		blist.add(btDelHrany);
		blist.add(btPridat);
		blist.add(btSmaz);
		blist.add(btFeatures);
		return blist;
	}

	public void disableBTN(JButton btn) {
		JButton b;
		for (int i = 0; i < btns.size(); i++) {
			b = btns.get(i);
			b.setEnabled(true);
			b.setBackground(new JButton().getBackground());
		}
		btn.setEnabled(false);
		btn.setBackground(new Color(255, 255, 51));
	}

	public void setCounts(int hran, int vrcholu) {
		hrany.setText("Poèet hran:          " + hran);
		vrcholy.setText("Poèet vrcholù:     " + vrcholu);
	}

	public JPanel getPanel() {
		return pnlTlacitka;
	}


	public void graphScore() {
		cisla = new Integer[mapaservice.getVrchol().size()];
		for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
			cisla[i]=mapaservice.getVrchol().get(i).getStupen();
		}
	}
	
	public void setFeatures(Integer[] cisla, JTextField[] btn){

		features.main(cisla, mapaservice, hrana);
		
		for (Hrana hr : hrana.getList()) {
			System.out.println(hr.getPrvni().getNazev() + " " + hr.getDruhy().getNazev() + "TOTO");
		}
		
		
		if(features.isSouvisly())
			btn[1].setText("ANO");
		else
			btn[1].setText("NE");

		if(features.isRovinny())
			btn[3].setText("ANO");
		else
			btn[3].setText("NE");

		if(features.isEuler())
			btn[5].setText("ANO");
		else
			btn[5].setText("NE");

		if(features.isStrom())
			btn[7].setText("ANO");
		else
			btn[7].setText("NE");
		btn[9].setText(Integer.toString(features.getKomponent()));
	}
	
	public void ableCounts(boolean b) {
		hrany.setVisible(b);
		vrcholy.setVisible(b);
	}

}
