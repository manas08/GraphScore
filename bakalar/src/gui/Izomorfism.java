package gui;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import entity.Hrana;
import entity.Vrchol;
import tools.MapaService;
import tools.MemMapaService;
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
import javax.swing.ButtonGroup;
import javax.swing.JButton;

public class Izomorfism extends JPanel {

	BufferedImage image1;
	BufferedImage image2;
	JPanel p1;
	JPanel p2;
	JPanel p3;
	JButton btNewVertex;
	JButton btNewEdge;
	JPanel pnlTlacitka;
	JRadioButton graph1 = new JRadioButton("Graf 1");
	JRadioButton graph2 = new JRadioButton("Graf 2");
	public Hrana hrana1 = new Hrana();
	public MapaService mapaservice1;
	public Hrana hrana2 = new Hrana();
	public MapaService mapaservice2;
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
	boolean graphs = false; // defaultn� graf nastaven na graf 1
	Integer[] cisla;
	List<Vrchol> puvod = new ArrayList<Vrchol>();
	MouseListener ml;
	MouseAdapter mouse;
	int pocet = 2;
	Vrchol vrchol2;
	int mys1X;
	int mys1Y;

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
		btNewVertex = new JButton("Vlo�it nov� vrchol");
		btNewEdge = new JButton("Vlo�it nov� vrchol");
		p1.setPreferredSize(new Dimension(563, 856));
		p2.setPreferredSize(new Dimension(563, 856));

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
		mouse = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isMiddleMouseButton(e) && pomoc == true && e.getSource() == p1) {
					clear();
					v1 = mapaservice1.getPodleId(poradi);
					v1.setX(e.getX());
					v1.setY(e.getY());
					present1();
				} else if (SwingUtilities.isMiddleMouseButton(e) && pomoc == true && e.getSource() == p2) {
					clear();
					v1 = mapaservice2.getPodleId(poradi);
					v1.setX(e.getX());
					v1.setY(e.getY());
					present2();
				} else if (pocet == 1) {
					clear();
					if (SwingUtilities.isLeftMouseButton(e) && e.getSource() == p1) {
						Graphics2D gr = image1.createGraphics();
						int mys2X = e.getX();
						int mys2Y = e.getY();
						gr.setStroke(new BasicStroke(4));
						gr.setColor(new Color(165, 49, 68));
						gr.drawLine(mys1X, mys1Y, mys2X, mys2Y);
						present1();
					}
				} else if (pocet == 1) {
					clear();
					if (SwingUtilities.isLeftMouseButton(e) && e.getSource() == p2) {
						Graphics2D gr = image2.createGraphics();
						int mys2X = e.getX();
						int mys2Y = e.getY();
						gr.setStroke(new BasicStroke(4));
						gr.setColor(new Color(165, 49, 68));
						gr.drawLine(mys1X, mys1Y, mys2X, mys2Y);
						present2();
					}
				}

			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		};

		ml = new MouseListener() {

			@Override
			public void mouseReleased(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() == p1) {
					int citlivost = 9;
					int porovnej = 0;
					for (int i = 0; i < mapaservice1.getVrchol().size(); i++) {
						Vrchol m = mapaservice1.getVrchol().get(i);
						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY())) && (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							if (hrana1.getPrvni() != m) {
								if (hrana1.getList().size() != 0) {

									for (int j = 0; j < hrana1.getList().size(); j++) {// prohled�me
																						// v�echny
																						// hrany
										Hrana hr = hrana1.getList().get(j);
										// kdy� u� dan� hrana bude exisovat tak
										// ned�lat novou AB BA
										if ((hr.getPrvni() == m && hr.getDruhy() == vrchol2)
												|| (hr.getPrvni() == vrchol2 && hr.getDruhy() == m)) {
											porovnej = 1;
											clear();
											present();
											return;
											// jinak ud�lat novou hranu
										} else if (pocet == 1) {
											porovnej = 0;
										}
									}
									// porovn�n� v�sledk�
									if (porovnej == 0) {
										hrana1.setDruhy(m);
										pocet = 0;
									}

								} else {
									hrana1.setDruhy(m);
									pocet = 0;
								}
							}

						}
					}
					clear();
					present();
				} else if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() == p2) {
					int citlivost = 9;
					int porovnej = 0;
					for (int i = 0; i < mapaservice2.getVrchol().size(); i++) {
						Vrchol m = mapaservice2.getVrchol().get(i);
						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY())) && (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							if (hrana2.getPrvni() != m) {
								if (hrana2.getList().size() != 0) {

									for (int j = 0; j < hrana2.getList().size(); j++) {// prohled�me
																						// v�echny
																						// hrany
										Hrana hr = hrana2.getList().get(j);
										// kdy� u� dan� hrana bude exisovat tak
										// ned�lat novou AB BA
										if ((hr.getPrvni() == m && hr.getDruhy() == vrchol2)
												|| (hr.getPrvni() == vrchol2 && hr.getDruhy() == m)) {
											porovnej = 1;
											clear();
											present();
											return;
											// jinak ud�lat novou hranu
										} else if (pocet == 1) {
											porovnej = 0;
										}
									}
									// porovn�n� v�sledk�
									if (porovnej == 0) {
										hrana2.setDruhy(m);
										pocet = 0;
									}

								} else {
									hrana2.setDruhy(m);
									pocet = 0;
								}
							}

						}
					}
					clear();
					present();
				}
				pomoc = false;
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int citlivost = 9;
				if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() == p1) {
					for (int i = 0; i < mapaservice1.getVrchol().size(); i++) {
						vrchol = mapaservice1.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY())) && (((vrchol.getX() - citlivost) <= e.getX()) && (((vrchol.getX() + citlivost) >= e.getX())))) {
							hrana1.setPrvni(vrchol);
							vrchol2 = hrana1.getPrvni();
							mys1X = e.getX();
							mys1Y = e.getY();
							pocet = 1;
						}
					}
				}else if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() == p2) {
					for (int i = 0; i < mapaservice2.getVrchol().size(); i++) {
						vrchol = mapaservice2.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY())) && (((vrchol.getX() - citlivost) <= e.getX()) && (((vrchol.getX() + citlivost) >= e.getX())))) {
							hrana2.setPrvni(vrchol);
							vrchol2 = hrana2.getPrvni();
							mys1X = e.getX();
							mys1Y = e.getY();
							pocet = 1;
						}
					}
				} else if (SwingUtilities.isMiddleMouseButton(e) && e.getSource() == p1) {
					for (int i = 0; i < mapaservice1.getVrchol().size(); i++) {
						vrchol = mapaservice1.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
							poradi = vrchol.getId();
							pomoc = true;
						}
					}
				} else if (SwingUtilities.isMiddleMouseButton(e) && e.getSource() == p2) {
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
					for (int i = 0; i < mapaservice1.getVrchol().size(); i++) {
						Vrchol m = mapaservice1.getVrchol().get(i);
						int tbPolohaX = MouseInfo.getPointerInfo().getLocation().x;
						int tbPolohaY = MouseInfo.getPointerInfo().getLocation().y;

						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY()))
								&& (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							tb = new ToolBar(m, main, hrana1, 2);
							tb.izo(getIzo());
							tb.setMapaService(mapaservice1);
							tb.setVisible(true);
							tb.setLocation(tbPolohaX + 15, tbPolohaY + 15);

						}
					}
				}

				else if (e.getButton() == MouseEvent.BUTTON3 && e.getSource() == p2) {
					for (int i = 0; i < mapaservice2.getVrchol().size(); i++) {
						Vrchol m = mapaservice2.getVrchol().get(i);
						int tbPolohaX = MouseInfo.getPointerInfo().getLocation().x;
						int tbPolohaY = MouseInfo.getPointerInfo().getLocation().y;

						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY()))
								&& (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							tb = new ToolBar(m, main, hrana2, 3);
							tb.izo(getIzo());
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

		// -------Tla��tka---------
		btNewVertex.setPreferredSize(new Dimension(170, 25));
		btNewVertex.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				NewVertex o = null;
				if (graphs == false) {
					o = new NewVertex(hlavni, getIzo(), 2);
					o.setMapaService(mapaservice1);
					o.setJPanelImage(p1);
				} else if (graphs == true) {
					o = new NewVertex(hlavni, getIzo(), 3);
					o.setMapaService(mapaservice2);
					o.setJPanelImage(p2);
				}
				o.setLocationRelativeTo(null);
				o.setVisible(true);
			}
		});
		
		btNewEdge.setPreferredSize(new Dimension(170, 25));
		btNewEdge.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				NewVertex o = null;
				if (graphs == false) {
					o = new NewVertex(hlavni, getIzo(), 2);
					o.setMapaService(mapaservice1);
					o.setJPanelImage(p1);
				} else if (graphs == true) {
					o = new NewVertex(hlavni, getIzo(), 3);
					o.setMapaService(mapaservice2);
					o.setJPanelImage(p2);
				}
				o.setLocationRelativeTo(null);
				o.setVisible(true);
			}
		});

		graph1.setBackground(new Color(47, 48, 60));
		graph1.setSelected(true);
		graph2.setBackground(new Color(47, 48, 60));
		graph1.setForeground(new Color(240, 150, 80));
		graph2.setForeground(new Color(240, 150, 80));

		ButtonGroup btn = new ButtonGroup();
		btn.add(graph1);
		btn.add(graph2);

		graph1.addActionListener(e -> {
			graphs = false;
		});

		graph2.addActionListener(e -> {
			graphs = true;
		});

		// ------Um�st�n� tla��tek--------
		btNewVertex.setBounds(15, 440, 165, 25);
		graph1.setBounds(20, 380, 70, 25);
		graph2.setBounds(20, 402, 70, 25);

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
		hlavni.repaint();
		hlavni.add(p3, "Center");
		btNewVertex.setVisible(true);
		graph1.setVisible(true);
		graph2.setVisible(true);

		pomoc = true;
		this.pnlTlacitka = hlavni.getPanel();

		if (prvni == true) {
			pnlTlacitka.add(btNewVertex);
			pnlTlacitka.add(graph1);
			pnlTlacitka.add(graph2);
			prvni = false;
		}
		hlavni.ableCounts(false);
		clear();
	}

	// samotn� vykreslen� bod� a hran
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
		gr.drawString("Po�et hran:  " + "  " + hran1, 30, 820);
		gr.setFont(new Font("TimesRoman", Font.BOLD, 12));
		gr.drawString("Po�et vrchol�:  " + "  " + vrcholu1, 30, 840);
	}

	// samotn� vykreslen� bod� a hran
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
		gr.drawString("Po�et hran:  " + "  " + hran2, 30, 820);
		gr.setFont(new Font("TimesRoman", Font.BOLD, 12));
		gr.drawString("Po�et vrchol�:  " + "  " + vrcholu2, 30, 840);
	}

	// metoda pro vykreslen�
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

	// vy�i�t�n� plochy
	public void clear() {
		clear1();
		clear2();
	}

	public void clear1() {
		Graphics gr = image1.getGraphics();
		gr.setColor(new Color(192, 192, 192));
		gr.fillRect(0, 0, image1.getWidth(), image1.getHeight());
	}

	public void clear2() {
		Graphics gr = image2.getGraphics();
		gr.setColor(new Color(192, 192, 192));
		gr.fillRect(0, 0, image2.getWidth(), image2.getHeight());
	}

	public Izomorfism getIzo() {
		return this;
	}

	public void disIzo() {
		if (hlavni != null)
			hlavni.remove(p3); // �e�� p�ep�n�n� mouselistener�
		btNewVertex.setVisible(false);
		graph1.setVisible(false);
		graph2.setVisible(false);
	}
}