package gui;

import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingUtilities;
import entity.Hrana;
import entity.Vrchol;
import tools.Features;
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
import javax.swing.JOptionPane;

public class Izomorfism extends JPanel {

	BufferedImage image1;
	BufferedImage image2;
	JPanel p1;
	JPanel p2;
	JPanel p3;
	JButton btNewVertex;
	JButton btNewGraph;
	JButton btDelHrany;
	JButton btDelHranu;
	JButton btStejne;
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
	boolean graphs = false; // defaultní graf nastaven na graf 1
	Integer[] cisla;
	List<Vrchol> puvod = new ArrayList<Vrchol>();
	MouseListener ml;
	MouseAdapter mouse;
	int pocet = 2;
	Vrchol vrchol2;
	int mys1X;
	int mys1Y;
	Features features1;
	Features features2;
	Color color = new Color(165, 49, 68);

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
		image1 = new BufferedImage(535, 866, BufferedImage.TYPE_INT_RGB);
		image2 = new BufferedImage(535, 866, BufferedImage.TYPE_INT_RGB);
		btNewVertex = new JButton("Přidej vrchol");
		btNewGraph = new JButton("Nový graf");
		btDelHrany = new JButton("Odstraň všechny hrany");
		btDelHranu = new JButton("Odstraň hranu");
		btStejne = new JButton("Jsou izomorfní?");
		p1.setPreferredSize(new Dimension(535, 866));
		p2.setPreferredSize(new Dimension(535, 866));

		// prvotní překreslení když pohneme myší
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

		// táhnutí bodů

		// p1 značí plátno 1, p2 plátno 2
		mouse = new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
			}

			@Override
			public void mouseDragged(MouseEvent e) {
				if (SwingUtilities.isMiddleMouseButton(e) && pomoc == true && e.getSource() == p1) {
					clear1();
					v1 = mapaservice1.getPodleId(poradi);
					// ošetření rohů abychom nekreslili body mimo okno
					if (e.getX() < 0 && e.getY() < 0) {
						v1.setX(0);
						v1.setY(0);
					} else if (e.getX() < 0 && e.getY() > 866) {
						v1.setX(0);
						v1.setY(866);
					} else if (e.getX() > 535 && e.getY() > 866) {
						v1.setX(535);
						v1.setY(866);
					} else if (e.getX() > 535 && e.getY() < 0) {
						v1.setX(535);
						v1.setY(0);
						// ošetření hran
					} else if (e.getX() < 0) {
						v1.setX(0);
						v1.setY(e.getY());
					} else if (e.getX() > 535) {
						v1.setX(535);
						v1.setY(e.getY());
					} else if (e.getY() < 0) {
						v1.setX(e.getX());
						v1.setY(0);
					} else if (e.getY() > 866) {
						v1.setX(e.getX());
						v1.setY(866);
						// uvnitř okna
					} else {
						v1.setX(e.getX());
						v1.setY(e.getY());
					}
					present1();
				} else if (SwingUtilities.isMiddleMouseButton(e) && pomoc == true && e.getSource() == p2) {
					clear2();
					v1 = mapaservice2.getPodleId(poradi);
					// ošetření rohů abychom nekreslili body mimo okno
					if (e.getX() < 0 && e.getY() < 0) {
						v1.setX(0);
						v1.setY(0);
					} else if (e.getX() < 0 && e.getY() > 866) {
						v1.setX(0);
						v1.setY(866);
					} else if (e.getX() > 535 && e.getY() > 866) {
						v1.setX(535);
						v1.setY(866);
					} else if (e.getX() > 535 && e.getY() < 0) {
						v1.setX(535);
						v1.setY(0);
						// ošetření hran
					} else if (e.getX() < 0) {
						v1.setX(0);
						v1.setY(e.getY());
					} else if (e.getX() > 535) {
						v1.setX(535);
						v1.setY(e.getY());
					} else if (e.getY() < 0) {
						v1.setX(e.getX());
						v1.setY(0);
					} else if (e.getY() > 866) {
						v1.setX(e.getX());
						v1.setY(866);
						// uvnitř okna
					} else {
						v1.setX(e.getX());
						v1.setY(e.getY());
					}
					present2();
				} else if (pocet == 1) {
					if (SwingUtilities.isLeftMouseButton(e) && e.getSource() == p1) {
						clear1();
						Graphics2D gr = image1.createGraphics();
						int mys2X = e.getX();
						int mys2Y = e.getY();
						gr.setStroke(new BasicStroke(4));
						gr.setColor(new Color(165, 49, 68));
						gr.drawLine(mys1X, mys1Y, mys2X, mys2Y);
						present1();
					} else if (SwingUtilities.isLeftMouseButton(e) && e.getSource() == p2) {
						clear2();
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
				if (pocet == 1) {
					if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() == p1) {
						int citlivost = 9;
						int porovnej = 0;
						for (int i = 0; i < mapaservice1.getVrchol().size(); i++) {
							Vrchol m = mapaservice1.getVrchol().get(i);
							if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY()))
									&& (((m.getX() - citlivost) <= e.getX())
											&& (((m.getX() + citlivost) >= e.getX())))) {

								if (hrana1.getPrvni() != m) {
									if (hrana1.getList().size() != 0) {

										for (int j = 0; j < hrana1.getList().size(); j++) {// prohledáme
																							// všechny
																							// hrany
											Hrana hr = hrana1.getList().get(j);
											// když už daná hrana bude exisovat tak
											// nedělat novou AB BA
											if ((hr.getPrvni() == m && hr.getDruhy() == vrchol2)
													|| (hr.getPrvni() == vrchol2 && hr.getDruhy() == m)) {
												porovnej = 1;
												clear();
												present();
												return;
												// jinak udělat novou hranu
											} else if (pocet == 1) {
												porovnej = 0;
											}
										}
										// porovnání výsledků
										if (porovnej == 0) {
											hrana1.setDruhy(m, color);
											pocet = 0;
										}

									} else {
										hrana1.setDruhy(m, color);
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
							if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY()))
									&& (((m.getX() - citlivost) <= e.getX())
											&& (((m.getX() + citlivost) >= e.getX())))) {

								if (hrana2.getPrvni() != m) {
									if (hrana2.getList().size() != 0) {

										for (int j = 0; j < hrana2.getList().size(); j++) {// prohledáme
																							// všechny
																							// hrany
											Hrana hr = hrana2.getList().get(j);
											// když už daná hrana bude exisovat tak
											// nedělat novou AB BA
											if ((hr.getPrvni() == m && hr.getDruhy() == vrchol2)
													|| (hr.getPrvni() == vrchol2 && hr.getDruhy() == m)) {
												porovnej = 1;
												clear();
												present();
												return;
												// jinak udělat novou hranu
											} else if (pocet == 1) {
												porovnej = 0;
											}
										}
										// porovnání výsledků
										if (porovnej == 0) {
											hrana2.setDruhy(m, color);
											pocet = 0;
										}

									} else {
										hrana2.setDruhy(m, color);
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
			}

			@Override
			public void mousePressed(MouseEvent e) {
				int citlivost = 9;
				int chyba = 0;
				if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() == p1) {
					for (int i = 0; i < mapaservice1.getVrchol().size(); i++) {
						vrchol = mapaservice1.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
							hrana1.setPrvni(vrchol);
							vrchol2 = hrana1.getPrvni();
							mys1X = e.getX();
							mys1Y = e.getY();
							pocet = 1;
						} else {
							chyba += 1;
						}
					}
					if (chyba == mapaservice1.getVrchol().size()) {
						pocet = 0;
					}
				} else if (e.getButton() == MouseEvent.BUTTON1 && e.getSource() == p2) {
					for (int i = 0; i < mapaservice2.getVrchol().size(); i++) {
						vrchol = mapaservice2.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
							hrana2.setPrvni(vrchol);
							vrchol2 = hrana2.getPrvni();
							mys1X = e.getX();
							mys1Y = e.getY();
							pocet = 1;
						} else {
							chyba += 1;
						}
					}
					if (chyba == mapaservice2.getVrchol().size()) {
						pocet = 0;
					}
				} else if (SwingUtilities.isMiddleMouseButton(e) && e.getSource() == p1) {
					for (int i = 0; i < mapaservice1.getVrchol().size(); i++) {
						vrchol = mapaservice1.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
							poradi = vrchol.getId();
							pomoc = true;
						} else {
							chyba++;
						}
					}
					if (chyba == mapaservice1.getVrchol().size()) {
						pocet = 0;
						pomoc = false;
					}
				} else if (SwingUtilities.isMiddleMouseButton(e) && e.getSource() == p2) {
					for (int i = 0; i < mapaservice2.getVrchol().size(); i++) {
						vrchol = mapaservice2.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
							poradi = vrchol.getId();
							pomoc = true;
						} else {
							chyba++;
						}
					}
					if (chyba == mapaservice2.getVrchol().size()) {
						pocet = 0;
						pomoc = false;
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

		// -------Funkce tlačítek---------
		// přidat vrchol
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

		// nový graf
		btNewGraph.setPreferredSize(new Dimension(170, 25));
		btNewGraph.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (graphs == false) {
					hrana1.delete();
					mapaservice1.smazList();
				} else if (graphs == true) {
					hrana2.delete();
					mapaservice2.smazList();
				}
				clear();
				present();
			}
		});

		// odstranění všech hran
		btDelHrany.setPreferredSize(new Dimension(170, 25));
		btDelHrany.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (graphs == false) {
					hrana1.delete();
					MapaService mapaservice3 = new MemMapaService();
					for (Vrchol v : mapaservice1.getVrchol()) {
						mapaservice3.pridejVrchol(new Vrchol(v.getX(), v.getY(), v.getNazev(), v.getPopis(), null, v.getColor()));
					}
					mapaservice1 = new MemMapaService();
					mapaservice1 = mapaservice3;
				} else if (graphs == true) {
					hrana2.delete();
					MemMapaService mapaservice3 = new MemMapaService();
					for (Vrchol v : mapaservice2.getVrchol()) {
						mapaservice3.pridejVrchol(new Vrchol(v.getX(), v.getY(), v.getNazev(), v.getPopis(), null, v.getColor()));
					}
					mapaservice2 = new MemMapaService();
					mapaservice2 = mapaservice3;
				}
				clear();
				present();
			}
		});

		// odstranění hrany
		btDelHranu.setPreferredSize(new Dimension(170, 25));
		btDelHranu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (graphs == false) {
					DelEdge edge = new DelEdge(hrana1);
					edge.setLocationRelativeTo(null);
					edge.delete(hrana1, hlavni, getIzo(), 1);
				} else if (graphs == true) {
					DelEdge edge = new DelEdge(hrana2);
					edge.setLocationRelativeTo(null);
					edge.delete(hrana2, hlavni, getIzo(), 1);
				}
			}
		});

		// testování izomorfismu
		btStejne.setPreferredSize(new Dimension(170, 25));
		btStejne.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				boolean pokracuj = false;
				System.out.println(mapaservice1.getVrchol().size() + " " + mapaservice2.getVrchol().size());
				if(mapaservice1.getVrchol().size() > 0 || mapaservice2.getVrchol().size() > 0) {
				if (mapaservice1.getVrchol().size() == mapaservice2.getVrchol().size()
						&& hrana1.getList().size() == hrana2.getList().size()) {
					pokracuj = true;
				} else
					pokracuj = false;

				if (pokracuj) {

					int prehoz;
					List<Vrchol> vrch1 = mapaservice1.getVrchol();
					Integer[] druhy1 = new Integer[vrch1.size()];
					for (int i = 0; i < vrch1.size(); i++) {
						druhy1[i] = vrch1.get(i).getStupen();
					}
					for (int j = 0; j < druhy1.length - 1; j++) {
						for (int j2 = j + 1; j2 < druhy1.length; j2++) {
							if (druhy1[j] < druhy1[j2]) {
								Vrchol pomocny;
								prehoz = druhy1[j];
								druhy1[j] = druhy1[j2];
								druhy1[j2] = prehoz;
								pomocny = mapaservice1.getVrchol().get(j);

								vrch1.set(j, vrch1.get(j2));
								vrch1.get(j2).setId(j + 1);
								vrch1.set(j2, pomocny);
								pomocny.setId(j2 + 1);
							}
						}
					}

					List<Vrchol> vrch2 = mapaservice2.getVrchol();
					Integer[] druhy2 = new Integer[vrch2.size()];
					for (int i = 0; i < vrch2.size(); i++) {
						druhy2[i] = vrch2.get(i).getStupen();
					}
					for (int j = 0; j < druhy2.length - 1; j++) {
						for (int j2 = j + 1; j2 < druhy2.length; j2++) {
							if (druhy2[j] < druhy2[j2]) {
								Vrchol pomocny;
								prehoz = druhy2[j];
								druhy2[j] = druhy2[j2];
								druhy2[j2] = prehoz;
								pomocny = mapaservice2.getVrchol().get(j);

								vrch2.set(j, vrch2.get(j2));
								vrch2.get(j2).setId(j + 1);
								vrch2.set(j2, pomocny);
								pomocny.setId(j2 + 1);
							}
						}
					}

					for (int i = 0; i < druhy1.length; i++) {
						if (druhy1[i] != druhy2[i]) {
							JOptionPane.showMessageDialog(null, "Grafy nejsou izomorfní.", "Izomorfismus", 1);
							return;
						}
					}

					if (druhy1[0] == 0) {
						boolean nuly = true;
						for (int i = 0; i < druhy1.length; i++) {
							if (druhy1[i] != druhy2[i]) {
								nuly = false;
							}
						}
						if (nuly == true)
							JOptionPane.showMessageDialog(null, "Grafy jsou izomorfní.", "Izomorfismus", 1);
						else
							JOptionPane.showMessageDialog(null, "Grafy nejsou izomorfní.", "Izomorfismus", 1);

					} else {

						// porovnání počtu komponent
						features1 = new Features();
						features1.main(druhy1, mapaservice1, hrana1);
						int p1 = features1.getKomponent();
						features2 = new Features();
						features2.main(druhy2, mapaservice2, hrana2);
						int p2 = features2.getKomponent();
						if (p1 != p2) {
							JOptionPane.showMessageDialog(null, "Grafy nejsou izomorfní.", "Izomorfismus", 1);
							return;
						} else {

							for (Vrchol v1 : vrch1) {
								v1.navstiveno = false;
								v1.prozkoumano = false;
							}
							for (Vrchol v2 : vrch2) {
								v2.navstiveno = false;
								v2.prozkoumano = false;
							}

							int vrcholy = 0;

							// porovnávání stupňů sousedních vrcholů
							for (int i = 0; i < vrch1.size(); i++) {
								for (int k = 0; k < vrch2.size(); k++) {
									if (vrch1.get(i).navstiveno == false && vrch2.get(k).navstiveno == false) {
										for (Vrchol v1 : vrch1.get(i).getSousedi()) {
											v1.setProzkoumano(false);
										}
										for (Vrchol v1 : vrch2.get(k).getSousedi()) {
											v1.setProzkoumano(false);
										}
										int uspechu = 0;

										if (vrch2.get(k).getStupen() == vrch1.get(i).getStupen()) {
											for (int j = 0; j < vrch1.get(i).getSousedi().size(); j++) {
												int count = 0;
												int soucet = 0;

												for (int l = 0; l < vrch2.get(k).getSousedi().size(); l++) {
													if ((vrch1.get(i).getSousedi().get(j).getStupen() == vrch2.get(k)
															.getSousedi().get(l).getStupen())
															&& (vrch2.get(k).getSousedi().get(l)
																	.getProzkoumano() == false)) {
														count++;
														vrch2.get(k).getSousedi().get(l).setProzkoumano(true);
														l = vrch1.get(i).getSousedi().size();
													}
												}

												int chyby = 0;
												for (int h = 0; h < vrch2.get(k).getSousedi().size(); h++) {
													if (vrch2.get(k).getSousedi().get(h).getProzkoumano() == true) {
														chyby++;
													}
												}
												if ((chyby == vrch2.get(k).getSousedi().size() && count == 0)) {
													JOptionPane.showMessageDialog(null, "Grafy nejsou izomorfní.1",
															"Izomorfismus", 1);
													return;
												}
												soucet++;
												if (count != 0) {
													uspechu++;
													for (int l = 0; l < vrch2.get(k).getSousedi().size(); l++) {
														vrch2.get(k).getSousedi().get(l).setProzkoumano(false);
													}
												} else {
													continue;
												}
											}
										}

										if (uspechu == vrch1.get(i).getSousedi().size()) {
											vrcholy++;
											vrch1.get(i).navstiveno = true;
										} else
											continue;
									}
								}
							}
							if (vrcholy == vrch1.size()) {

								// počítání kružnic v plátně 1
								int kruznice1 = 0;

								for (int i = 0; i < vrch1.size(); i++) {
									for (int j = 0; j < vrch1.get(i).getSousedi().size() - 1; j++) {
										for (int k = j; k < vrch1.get(i).getSousedi().size(); k++) {
											for (Vrchol v : vrch1.get(i).getSousedi().get(j).getSousedi()) {
												if (v.getNazev() == vrch1.get(i).getSousedi().get(k).getNazev()) {
													kruznice1++;
												}
											}
										}
									}
								}

								// počítání kružnic v plátně 2
								int kruznice2 = 0;

								for (int i = 0; i < vrch2.size(); i++) {
									for (int j = 0; j < vrch2.get(i).getSousedi().size() - 1; j++) {
										for (int k = j; k < vrch2.get(i).getSousedi().size(); k++) {
											for (Vrchol v : vrch2.get(i).getSousedi().get(j).getSousedi()) {
												if (v.getNazev() == vrch2.get(i).getSousedi().get(k).getNazev()) {
													kruznice2++;
												}
											}
										}
									}
								}

								// porovnání počtu kružnic velikosti 3
								if (kruznice1 == kruznice2) {
									JOptionPane.showMessageDialog(null, "Grafy jsou izomorfní.2", "Izomorfismus", 1);
									return;
								} else {
									JOptionPane.showMessageDialog(null, "Grafy nejsou izomorfní.3", "Izomorfismus", 1);
									return;
								}
							} else {
								JOptionPane.showMessageDialog(null, "Grafy nejsou izomorfní.4", "Izomorfismus", 1);
								return;
							}
						}
					}

				} else
					JOptionPane.showMessageDialog(null, "Grafy nejsou izomorfní.5", "Izomorfismus", 1);
			} else
				JOptionPane.showMessageDialog(null, "Oba grafy jsou prázdné.", "Izomorfismus", 1);
			}
		});

		graph1.setBackground(new Color(214, 217, 223));
		graph1.setSelected(true);
		graph2.setBackground(new Color(214, 217, 223));
		graph1.setForeground(new Color(47, 48, 60));
		graph2.setForeground(new Color(47, 48, 60));

		ButtonGroup btn = new ButtonGroup();
		btn.add(graph1);
		btn.add(graph2);

		graph1.addActionListener(e -> {
			graphs = false;
		});

		graph2.addActionListener(e -> {
			graphs = true;
		});

		// ------Umístění tlačítek--------
		btNewGraph.setBounds(15, 472, 165, 25);
		btNewVertex.setBounds(15, 522, 165, 25);
		btDelHranu.setBounds(15, 572, 165, 25);
		btDelHrany.setBounds(15, 622, 165, 25);
		btStejne.setBounds(15, 672, 165, 25);
		graph1.setBounds(20, 400, 70, 25);
		graph2.setBounds(20, 422, 70, 25);

		mapaservice1.pridejVrchol(new Vrchol(100, 235, "A", "Budova PDF A", null, new Color(0, 0, 0)));
		mapaservice1.pridejVrchol(new Vrchol(392, 304, "B", "Budova B", null, new Color(0, 0, 0)));
		mapaservice1.pridejVrchol(new Vrchol(160, 507, "C", "Budova FIM J", null, new Color(0, 0, 0)));
		mapaservice1.pridejVrchol(new Vrchol(220, 115, "D", "Budova FIM J", null, new Color(0, 0, 0)));

		mapaservice2.pridejVrchol(new Vrchol(100, 235, "A", "Budova PDF A", null, new Color(0, 0, 0)));
		mapaservice2.pridejVrchol(new Vrchol(392, 304, "B", "Budova B", null, new Color(0, 0, 0)));
		mapaservice2.pridejVrchol(new Vrchol(160, 507, "C", "Budova FIM J", null, new Color(0, 0, 0)));
		mapaservice2.pridejVrchol(new Vrchol(220, 115, "D", "Budova FIM J", null, new Color(0, 0, 0)));
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
		btNewGraph.setVisible(true);
		btDelHrany.setVisible(true);
		btDelHranu.setVisible(true);
		btStejne.setVisible(true);
		graph1.setVisible(true);
		graph2.setVisible(true);

		pomoc = true;
		this.pnlTlacitka = hlavni.getPanel();

		if (prvni == true) {
			pnlTlacitka.add(btNewVertex);
			pnlTlacitka.add(btNewGraph);
			pnlTlacitka.add(btDelHrany);
			pnlTlacitka.add(btDelHranu);
			pnlTlacitka.add(btStejne);
			pnlTlacitka.add(graph1);
			pnlTlacitka.add(graph2);
			prvni = false;
		}
		hlavni.ableCounts(false);
		clear();
	}

	// samotné vykreslení bodů a hran na plátně 1
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
		gr.drawString("Počet hran:  " + "  " + hran1, 30, 820);
		gr.setFont(new Font("TimesRoman", Font.BOLD, 12));
		gr.drawString("Počet vrcholů:  " + "  " + vrcholu1, 30, 840);
	}

	// samotné vykreslení bodů a hran na plátně 2
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
		gr.drawString("Počet hran:  " + "  " + hran2, 30, 820);
		gr.setFont(new Font("TimesRoman", Font.BOLD, 12));
		gr.drawString("Počet vrcholů:  " + "  " + vrcholu2, 30, 840);
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

	// vyčištění plochy
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
			hlavni.remove(p3); // řeší přepínání mouselistenerů
		btNewVertex.setVisible(false);
		btNewGraph.setVisible(false);
		btDelHrany.setVisible(false);
		btDelHranu.setVisible(false);
		btStejne.setVisible(false);
		graph1.setVisible(false);
		graph2.setVisible(false);
	}

	public void vykresliHranu(List<Hrana> listPom) {
		DelEdge edge;
		if (graphs == false) {
			hrana1.setList(listPom);
			edge = new DelEdge(hrana1);
			edge.setLocationRelativeTo(null);
			edge.delete(hrana1, hlavni, getIzo(), 1);
			clear1();
			present1();
		} else if (graphs == true) {
			hrana2.setList(listPom);
			edge = new DelEdge(hrana2);
			edge.setLocationRelativeTo(null);
			edge.delete(hrana2, hlavni, getIzo(), 1);
			clear2();
			present2();
		}
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	// spojení obou pláten pro jejich jednotné uložení jako obrázku
	public BufferedImage spojeni() {
		int offset = 5;
		int wid = image1.getWidth() + image2.getWidth() + offset;
		int height = Math.max(image1.getHeight(), image2.getHeight()) + offset;
		BufferedImage newImage = new BufferedImage(wid, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2 = newImage.createGraphics();
		Color oldColor = g2.getColor();

		// vykreslení pozadí
		g2.setPaint(new Color(242, 242, 242));
		g2.fillRect(0, 0, wid, height);
		// vykreslení pláten
		g2.setColor(oldColor);
		g2.drawImage(image1, null, 0, 0);
		g2.drawImage(image2, null, image1.getWidth() + offset, 0);
		g2.dispose();
		return newImage;
	}
}