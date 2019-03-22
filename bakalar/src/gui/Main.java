package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.CompoundBorder;
import entity.Hrana;
import entity.Vrchol;
import tools.Features;
import tools.MapaService;
import tools.MemMapaService;
import tools.Saver;

public class Main extends JFrame {

	private MapaService mapaservice = new MemMapaService();;
	private JPanel pnlMapa;
	JPanel pnlTlacitka = new JPanel();
	private int sirka = 1480;
	private int vyska = 956;
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
	JTextField navigace = new JTextField("   Navigace:");
	JTextField vlastnosti = new JTextField("    Vlastnosti:");
	JTextField barvaV = new JTextField("Vrcholy:");
	JTextField barvaH = new JTextField("Hrany:");
	BufferedImage image;
	int mys1X;
	int mys1Y;
	boolean podm = false;
	boolean prvnispusteni = true;
	int okno = 0;
	int poradi = -1;
	Score score;
	Integer[] cisla;
	Features features = new Features();
	tools.ColorChooser ch;
	NewVertex o;
	Note note;
	JPanel panel1 = new JPanel();
	JPanel panel2 = new JPanel();
	int stroke = 4;

	JButton btIzomor = new JButton("Izomorfismus");
	JButton btHome = new JButton("Kreslení grafu");
	JButton btColor = new JButton("Upravit vzhled grafu");
	JButton btScore = new JButton("Kreslit podle skóre");
	JButton btSmaz = new JButton("Nový graf");
	JButton btPridat = new JButton("Pøidej vrchol");
	JButton btDelHrany = new JButton("Odstraò všechny hrany");
	JButton btDelHranu = new JButton("Odstraò hranu");
	JButton btFeatures = new JButton("Zobrazit vlastnosti");
	JButton btBarvaV = new JButton();
	JButton btBarvaH = new JButton();
	JButton btNotes = new JButton("Poznámka");
	List<JButton> btns = new ArrayList<>();
	JButton btSave = new JButton();
	JButton btWord = new JButton("Návod");

	public Main() {
		super("GraphScore 2.0");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		vytvorGui();

		setSize(sirka + 200, vyska);
		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
		pack();
		image = new BufferedImage(sirka + 10, vyska + 20, BufferedImage.TYPE_INT_RGB);
		clear();
		btns.add(btHome);
		btns.add(btIzomor);
		btns.add(btScore);
		btns.add(btColor);
	}

	public void vytvorGui() {

		pnlMapa = new JPanel();
		pnlMapa.setPreferredSize(new Dimension(sirka, vyska + 10));
		izo = new Izomorfism(main, btIzomor);
		add(pnlMapa, "Center");
		score = new Score(btScore, this);
		score.setVisible(false);

		panel1.setLayout(null);
		panel1.setPreferredSize(new Dimension(200, 360));
		panel1.setVisible(true);
		panel1.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(126, 128, 132)));

		panel2.setLayout(null);
		panel2.setPreferredSize(new Dimension(200, 80));
		panel2.setVisible(true);
		panel2.setBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(126, 128, 132)));

		pnlTlacitka.setLayout(null);
		pnlTlacitka.setPreferredSize(new Dimension(200, vyska));
		pnlTlacitka.setVisible(true);
		pnlTlacitka.setBorder(BorderFactory.createLineBorder(new Color(126, 128, 132), 2));

		mapaservice.pridejVrchol(new Vrchol(100, 235, "A", "Budova PDF A", null, new Color(0, 0, 0)));
		mapaservice.pridejVrchol(new Vrchol(392, 304, "B", "Budova B", null, new Color(0, 0, 0)));
		mapaservice.pridejVrchol(new Vrchol(600, 320, "C", "Budova C", null, new Color(0, 0, 0)));
		mapaservice.pridejVrchol(new Vrchol(464, 507, "D", "Budova FIM J", null, new Color(0, 0, 0)));

		// pøekreslování plochy pøi pohybu okna
		addComponentListener(new ComponentListener() {

			@Override
			public void componentShown(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentResized(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}

			@Override
			public void componentMoved(ComponentEvent arg0) {
				if (prvnispusteni != true) {
					if (btIzomor.isEnabled() == false) {
						prvnispusteni = false;
						izo.present();
					} else if (btHome.isEnabled() == false) {
						prvnispusteni = false;
						present();
					} else if (btScore.isEnabled() == false) {
						prvnispusteni = false;
						score.present();
					}
				}

				prvnispusteni = false;
			}

			@Override
			public void componentHidden(ComponentEvent arg0) {
				// TODO Auto-generated method stub

			}
		});

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
				if (btIzomor.isEnabled() == false) {
					podm = false;
					izo.present();
				} else if (podm == true && btHome.isEnabled() == false) {
					podm = false;
					present();
				} else if (btScore.isEnabled() == false) {
					podm = false;
					score.present();
					score.setFeatures();
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

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
							hrana.setPrvni(vrchol);
							vrchol2 = hrana.getPrvni();
							mys1X = e.getX();
							mys1Y = e.getY();
							pocet = 1;
						}
					}
				} else if (SwingUtilities.isMiddleMouseButton(e)) {
					int chyba = 0;
					for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
						vrchol = mapaservice.getVrchol().get(i);

						if (((vrchol.getY() - citlivost) <= e.getY()) && (((vrchol.getY() + citlivost) >= e.getY()))
								&& (((vrchol.getX() - citlivost) <= e.getX())
										&& (((vrchol.getX() + citlivost) >= e.getX())))) {
							poradi = vrchol.getId();
						} else {
							chyba += 1;
						}
					}
					if (chyba == mapaservice.getVrchol().size()) {
						poradi = -1;
					}
				} else if (e.getButton() == MouseEvent.BUTTON3) {
					for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
						Vrchol m = mapaservice.getVrchol().get(i);
						int tbPolohaX = MouseInfo.getPointerInfo().getLocation().x;
						int tbPolohaY = MouseInfo.getPointerInfo().getLocation().y;

						if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY()))
								&& (((m.getX() - citlivost) <= e.getX()) && (((m.getX() + citlivost) >= e.getX())))) {

							ToolBar tb = new ToolBar(m, main, hrana, 0);
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
					if (pocet == 1) {
						for (int i = 0; i < mapaservice.getVrchol().size(); i++) {
							Vrchol m = mapaservice.getVrchol().get(i);
							if (((m.getY() - citlivost) <= e.getY()) && (((m.getY() + citlivost) >= e.getY()))
									&& (((m.getX() - citlivost) <= e.getX())
											&& (((m.getX() + citlivost) >= e.getX())))) {

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
											hrana.setDruhy(m, btBarvaH.getBackground());
											pocet = 0;
										}

									} else {
										hrana.setDruhy(m, btBarvaH.getBackground());
										pocet = 0;
									}
								}

							} else
								pocet = 0;
						}
						clear();
						present();
					}
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
						gr.setStroke(new BasicStroke(stroke));
						gr.setColor(btBarvaH.getBackground());
						gr.drawLine(mys1X, mys1Y, mys2X, mys2Y);
						present();
					}
				}
				if (SwingUtilities.isMiddleMouseButton(e)) {
					if (poradi != -1) {
						clear();
						v1 = mapaservice.getPodleId(poradi);
						// ošetøení rohù abychom nekreslili body mimo okno
						if (e.getX() < 0 && e.getY() < 0) {
							v1.setX(0);
							v1.setY(0);
						} else if (e.getX() < 0 && e.getY() > vyska + 10) {
							v1.setX(0);
							v1.setY(vyska + 10);
						} else if (e.getX() > sirka + 10 && e.getY() > vyska + 10) {
							v1.setX(sirka + 10);
							v1.setY(vyska + 10);
						} else if (e.getX() > sirka + 10 && e.getY() < 0) {
							v1.setX(sirka + 10);
							v1.setY(0);
							// ošetøení hran
						} else if (e.getX() < 0) {
							v1.setX(0);
							v1.setY(e.getY());
						} else if (e.getX() > sirka + 10) {
							v1.setX(sirka + 10);
							v1.setY(e.getY());
						} else if (e.getY() < 0) {
							v1.setX(e.getX());
							v1.setY(0);
						} else if (e.getY() > vyska + 10) {
							v1.setX(e.getX());
							v1.setY(vyska + 10);
							// uvnitø okna
						} else {
							v1.setX(e.getX());
							v1.setY(e.getY());
						}
						present();
					}
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
			}

		};

		pnlMapa.addMouseMotionListener(mouse);

		btBarvaV.setBackground(new Color(0, 0, 0));
		btBarvaH.setBackground(new Color(165, 49, 68));

		// ------Funkce tlaèítek------
		// kreslení grafu
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

		// nový graf
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

		// pøidat vrchol
		btPridat.setPreferredSize(new Dimension(170, 25));
		btPridat.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				o = new NewVertex(main, izo, 1);
				o.setColor(btBarvaV.getBackground());
				o.setLocationRelativeTo(null);
				o.setMapaService(mapaservice);
				o.setJPanelImage(pnlMapa);
				o.setVisible(true);
			}
		});

		// odstranit hrany
		btDelHrany.setPreferredSize(new Dimension(170, 25));
		btDelHrany.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				pnlMapa.setEnabled(true);
				clear();
				hrana.delete();
				present();
			}
		});

		// odstranit hranu
		btDelHranu.setPreferredSize(new Dimension(170, 25));
		btDelHranu.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				DelEdge edge = new DelEdge(hrana);
				edge.setLocationRelativeTo(null);
				edge.delete(hrana, main, izo, 0);
			}
		});

		// vlastnosti grafu
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

		// izomorfismus
		btIzomor.setPreferredSize(new Dimension(170, 25));
		btIzomor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				score.disablePanel(false);
				pnlMapa.setVisible(false);
				izo.hideshowBTN(main, false, 0);
				izo.aplly(main);
				disableBTN(btIzomor);
				barvaH.setVisible(false);
				barvaV.setVisible(false);
				btBarvaH.setVisible(false);
				btBarvaV.setVisible(false);
				btColor.setVisible(false);
			}
		});

		// upravit vzhled grafu
		btColor.setPreferredSize(new Dimension(170, 25));
		btColor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						ColorChanger changer = new ColorChanger(mapaservice, hrana, main);
						changer.setLocationRelativeTo(null);
						changer.setVisible(true);
					}
				});
			}
		});

		// kreslení grafu
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
				barvaH.setVisible(true);
				barvaV.setVisible(true);
				btBarvaH.setVisible(true);
				btBarvaV.setVisible(true);
				btColor.setVisible(true);
			}
		});

		// kreslit podle skóre
		btScore.setPreferredSize(new Dimension(170, 25));
		btScore.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				izo.disIzo();
				pnlMapa.setVisible(false);
				izo.hideshowBTN(main, false, 0);
				score.aplly(main, btScore);
				disableBTN(btScore);
				ableCounts(true);
				barvaH.setVisible(false);
				barvaV.setVisible(false);
				btBarvaH.setVisible(false);
				btBarvaV.setVisible(false);
				btColor.setVisible(false);
			}
		});

		// nastavit výchozí barvu vrcholù
		btBarvaV.setPreferredSize(new Dimension(20, 20));
		btBarvaV.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (o == null)
					o = new NewVertex(main, izo, 1);

				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						ch.createAndShowGUI();
					}
				});
				ch = new tools.ColorChooser(main, o);
			}
		});

		// nastavit výchozí barvu hran
		btBarvaH.setPreferredSize(new Dimension(20, 20));
		btBarvaH.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						ch.createAndShowGUI();
					}
				});
				ch = new tools.ColorChooser(main, hrana);
			}
		});

		// poznámka
		btNotes.setPreferredSize(new Dimension(170, 25));
		btNotes.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				note = new Note();
				note.setVisible(true);
			}
		});

		try {
			File f = new File("Img/saving.png");
			Image img = ImageIO.read(f);
			btSave.setIcon(new ImageIcon(img));
		} catch (Exception ex) {
			System.out.println(ex);
		}

		// uložit obrázek grafu
		btSave.setPreferredSize(new Dimension(30, 30));
		btSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				Saver saver = new Saver();
				if (btIzomor.isEnabled() == false) {
					saver.saveImg(izo.spojeni(), btSave);
				} else if (btHome.isEnabled() == false) {
					saver.saveImg(image, btSave);
				} else if (btScore.isEnabled() == false) {
					saver.saveImg(score.image, btSave);
				}
			}
		});

		// návod
		btWord.setPreferredSize(new Dimension(170, 25));
		btWord.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Desktop dt = Desktop.getDesktop();
				try {
					dt.open(new File("doc/guide.docx"));
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});

		// navigace
		btSave.setBounds(160, 4, 30, 30);
		panel1.add(btSave);
		btSave.setBorder(null);
		btHome.setBounds(40, 70, 120, 25);
		panel1.add(btHome);
		btIzomor.setBounds(15, 120, 165, 25);
		panel1.add(btIzomor);
		btScore.setBounds(15, 170, 165, 25);
		panel1.add(btScore);
		btColor.setBounds(15, 220, 165, 25);
		panel1.add(btColor);
		btBarvaV.setBounds(75, 280, 20, 20);
		panel1.add(btBarvaV);
		btBarvaH.setBounds(160, 280, 20, 20);
		panel1.add(btBarvaH);

		// umístìní komponent
		hrany.setBounds(20, 5, 170, 25);
		hrany.setEnabled(false);
		hrany.setDisabledTextColor(new Color(47, 48, 60));
		hrany.setFont(new Font("Times New Roman", Font.BOLD, 20));
		hrany.setBackground(new Color(214, 217, 223));
		hrany.setBorder(null);
		panel2.add(hrany);

		vrcholy.setBounds(20, 40, 170, 25);
		vrcholy.setEnabled(false);
		vrcholy.setDisabledTextColor(new Color(47, 48, 60));
		vrcholy.setFont(new Font("Times New Roman", Font.BOLD, 20));
		vrcholy.setBackground(new Color(214, 217, 223));
		vrcholy.setBorder(null);
		panel2.add(vrcholy);

		navigace.setBounds(0, 0, 196, 40);
		navigace.setEnabled(false);
		navigace.setDisabledTextColor(new Color(47, 48, 60));
		navigace.setFont(new Font("Times New Roman", Font.BOLD, 20));
		navigace.setBackground(new Color(161, 187, 206));
		navigace.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(216, 240, 255)),
				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(244, 248, 255))));
		panel1.add(navigace);

		vlastnosti.setBounds(2, 333, 196, 40);
		vlastnosti.setEnabled(false);
		vlastnosti.setDisabledTextColor(new Color(47, 48, 60));
		vlastnosti.setFont(new Font("Times New Roman", Font.BOLD, 20));
		vlastnosti.setBackground(new Color(161, 187, 206));
		vlastnosti.setBorder(new CompoundBorder(BorderFactory.createMatteBorder(1, 0, 0, 0, new Color(216, 240, 255)),
				BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(244, 248, 255))));
		pnlTlacitka.add(vlastnosti);

		barvaV.setBounds(20, 280, 50, 25);
		barvaV.setEnabled(false);
		barvaV.setDisabledTextColor(new Color(47, 48, 60));
		barvaV.setFont(new Font("Times New Roman", Font.BOLD, 13));
		barvaV.setBackground(new Color(199, 202, 208));
		barvaV.setBorder(null);
		panel1.add(barvaV);

		barvaH.setBounds(110, 280, 45, 25);
		barvaH.setEnabled(false);
		barvaH.setDisabledTextColor(new Color(47, 48, 60));
		barvaH.setFont(new Font("Times New Roman", Font.BOLD, 13));
		barvaH.setBackground(new Color(199, 202, 208));
		barvaH.setBorder(null);
		panel1.add(barvaH);

		// vlastnosti
		btSmaz.setBounds(40, 400, 120, 25);
		pnlTlacitka.add(btSmaz);
		btPridat.setBounds(40, 450, 120, 25);
		pnlTlacitka.add(btPridat);
		btDelHranu.setBounds(40, 500, 120, 25);
		pnlTlacitka.add(btDelHranu);
		btDelHrany.setBounds(15, 550, 165, 25);
		pnlTlacitka.add(btDelHrany);
		btFeatures.setBounds(15, 600, 165, 25);
		pnlTlacitka.add(btFeatures);
		btFeatures.setBounds(15, 710, 165, 25);
		pnlTlacitka.add(btFeatures);

		btNotes.setBounds(40, 73, 120, 20);
		panel2.add(btNotes);
		btWord.setBounds(40, 93, 120, 20);
		panel2.add(btWord);

		panel1.setBackground(new Color(199, 202, 208));
		panel1.setBounds(2, 2, 196, 331);

		panel2.setBackground(new Color(214, 217, 223));
		panel2.setBounds(2, 760, 196, 113);

		pnlTlacitka.setBackground(new Color(214, 217, 223));
		pnlTlacitka.add(panel2);
		pnlTlacitka.add(panel1);
		add(pnlTlacitka, "East");

		pack();
	}

	public void vykresliHranu() {
		Graphics2D gr = image.createGraphics();

		for (int i = 0; i < hrana.getList().size(); i++) {
			Hrana h = hrana.getList().get(i);
			if (h != null) {
				gr.setStroke(new BasicStroke(h.getStroke()));
				gr.setColor(h.getColor());
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
		features.main(cisla, mapaservice, hrana);
	}

	public void vykresliHranu(List<Hrana> listPom) {
		hrana.setList(listPom);
		clear();
		present();
		edge = new DelEdge(hrana);
		edge.setLocationRelativeTo(null);
		edge.delete(hrana, main, izo, 0);
	}

	public void present() {
		vykresliHranu();
		if (pnlMapa.getGraphics() != null)
			pnlMapa.getGraphics().drawImage(image, 0, 0, null);
	}

	public void clear() {
		Graphics gr = image.getGraphics();
		gr.setColor(new Color(242, 242, 242));
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
		btn.setBackground(new Color(47, 48, 60));
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
			cisla[i] = mapaservice.getVrchol().get(i).getStupen();
		}
	}

	public void setFeatures(Integer[] cisla, JTextField[] btn) {
		if (features.isSouvisly())
			btn[1].setText("ANO");
		else
			btn[1].setText("NE");

		if (features.isRovinny())
			btn[3].setText("ANO");
		else
			btn[3].setText("NE");

		if (features.isEuler())
			btn[5].setText("ANO");
		else
			btn[5].setText("NE");

		if (features.isStrom())
			btn[7].setText("ANO");
		else
			btn[7].setText("NE");
		btn[9].setText(Integer.toString(features.getKomponent()));
	}

	public void ableCounts(boolean b) {
		hrany.setVisible(b);
		vrcholy.setVisible(b);
	}

	public void applyColorVrcholu(Color color) {
		btBarvaV.setBackground(color);
	}

	public void applyColorHran(Color color) {
		btBarvaH.setBackground(color);
	}

	public Color getBarvuHrany() {
		return btBarvaH.getBackground();
	}

	public void setStroke(int stroke) {
		this.stroke = stroke;
	}

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

}
