package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import entity.Hrana;
import entity.Vrchol;
import tools.ColorChooser;
import tools.MapaService;

public class ToolBar extends JFrame {
	private MapaService mapaservice;
	private JPanel pnl = (new JPanel(new GridLayout(5, 1, 0, 0)));
	private JPanel pnl2 = (new JPanel(new GridLayout(2, 1, 0, 5)));
	private JPanel pnl3 = (new JPanel(new FlowLayout()));
	private JButton btSmazat;
	private JButton btBarva;
	private String nazevBodu;
	private int tbID;
	Score score;
	Izomorfism izo;
	ColorChooser ch;
	Vrchol v;
	Main main;
	int from;

	public ToolBar(Vrchol m, Main main, Hrana hrana, int i) {
		super("Detaily bodu");
		this.from = i;
		this.v = m;
		this.main = main;
		setSize(400, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		tbID = v.getId();
		nazevBodu = v.getNazev();

		pnl.add((new JLabel("   Název:     " + nazevBodu)));
		pnl.add(new JLabel("   Popis:    " + v.getPopis()));
		pnl.add(new JLabel("   ID:    " + tbID));
		pnl.add(new JLabel("   Stupeò:    " + v.getStupen()));

		pnl2.add(btSmazat = (new JButton("<html><font size=3>Smazat bod</font></html>")));
		btSmazat.setPreferredSize(new Dimension(200, 25));
		btSmazat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int vysledek;
				vysledek = JOptionPane.showConfirmDialog(null, "Smazat bod: " + nazevBodu + "?", "OK",
						JOptionPane.YES_NO_OPTION);

				if (vysledek == JOptionPane.YES_OPTION) {
					mapaservice.smazVrchol(tbID, hrana);
					btSmazat.setEnabled(false);

					// ----když to volá MAIN----
					if (from == 0) {
						main.clear();
						main.present();
						// ----když to volá SCORE----
					} else if (from == 1) {
						score.clear();
						score.present();
						score.refresh();
						// ----když to volá IZOM 1.POLE----
					} else if (from == 2) {
						izo.clear1();
						izo.present1();
						// ----když to volá IZOM 2.POLE----
					} else if (from == 3) {
						izo.clear2();
						izo.present2();
					}
					setVisible(false);
				}
			}
		});

		pnl2.add(btBarva = (new JButton("<html><font size=3>Zmìnit barvu bodu</font></html>")));
		btBarva.setPreferredSize(new Dimension(200, 25));
		btBarva.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						ch.createAndShowGUI();
					}
				});
				ch = new ColorChooser(getThis());
				setVisible(false);
			}
		});

		getContentPane().add(pnl, "Center");
		getContentPane().add(pnl2, "South");
		getContentPane().add(pnl3, "North");

		pack();

	}

	public void setMapaService(MapaService ms) { // implementuje mapaservice
		this.mapaservice = ms; // do 1. mapaservice
	}

	public void score(Score sc) {
		this.score = sc;
	}

	public void izo(Izomorfism iz) {
		this.izo = iz;
	}

	public ToolBar getThis() {
		return this;
	}

	public void applyColor(Color color) {
		v.setColor(color);
		// ----když to volá MAIN----
		if (from == 0) {
			main.clear();
			main.present();
			// ----když to volá SCORE----
		} else if (from == 1) {
			score.clear();
			score.present();
			score.refresh();
			// ----když to volá IZOM 1.POLE----
		} else if (from == 2) {
			izo.clear1();
			izo.present1();
			// ----když to volá IZOM 2.POLE----
		} else if (from == 3) {
			izo.clear2();
			izo.present2();
		}
	}

	public Vrchol getVrchol() {
		return v;
	}
}