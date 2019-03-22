package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import entity.Hrana;
import entity.Vrchol;
import tools.ColorChooser;
import tools.MapaService;

public class ColorChanger extends JFrame {
	private JPanel pnl = (new JPanel(new GridLayout(6, 0, 0, 0)));
	private JPanel pnl2 = (new JPanel(new GridLayout(6, 0, 0, 0)));
	private JPanel pnl3 = new JPanel();
	private JButton btThickness;
	private JButton btStroke;
	private JButton btBarvaVrcholu;
	private JButton btBarvaHran;
	private JButton btStrokeAll;
	private JButton btThicknessAll;
	private JTextArea tf1;
	private JTextArea tf2;
	Score score;
	Izomorfism izo;
	ColorChooser ch;
	MapaService mapaservice;
	Hrana hrana;
	Main main;

	public ColorChanger(MapaService mapaservice, Hrana hrana, Main main) {
		super("Zmìnit barvu");
		setSize(400, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(250, 345));

		this.mapaservice = mapaservice;
		this.hrana = hrana;
		this.main = main;

		// GUI pro úpravu vrcholù
		pnl.add((new JLabel("Vrcholy")));
		pnl.add((new JLabel("   Velikost bodu: ")));
		tf1 = new JTextArea("" + Vrchol.getThicknessAll());
		pnl.add(tf1);
		tf1.setCaretPosition(String.valueOf(Vrchol.getThicknessAll()).length());
		btThickness = new JButton("Nastavit velikost bodù");
		pnl.add(btThickness);
		btThicknessAll = new JButton("Uložit pro budoucí vrcholy");
		pnl.add(btThicknessAll);
		btBarvaVrcholu = new JButton("Zmìnit barvu všech vrcholù");
		pnl.add(btBarvaVrcholu);
		btBarvaVrcholu.setBackground(new Color(0, 255, 242));

		// GUI pro úpravu hran
		pnl2.add(new JLabel("Hrany"));
		pnl2.add((new JLabel("   Velikost hrany: ")));
		tf2 = new JTextArea("" + Hrana.getStrokeAll());
		pnl2.add(tf2);
		btStroke = new JButton("Nastavit tloušku hrany");
		pnl2.add(btStroke);
		btStrokeAll = new JButton("Uložit pro budoucí hrany");
		pnl2.add(btStrokeAll);
		btBarvaHran = new JButton("Zmìnit barvu všech hran");
		pnl2.add(btBarvaHran);
		btBarvaHran.setBackground(new Color(0, 255, 242));

		// nastavení velikosti vrcholù
		btThickness.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer.parseInt(tf1.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Špatnì zvolená veliskot. Zadejte èíslo v rozsahu 4-15!");
					return;
				}

				if (Integer.parseInt(tf1.getText()) >= 4 && Integer.parseInt(tf1.getText()) <= 15) {
					for (Vrchol v : mapaservice.getVrchol()) {
						v.setThickness(Integer.parseInt(tf1.getText()));
					}
					main.clear();
					main.present();
				} else
					JOptionPane.showMessageDialog(null, "Špatnì zvolená veliskot. Zadejte èíslo v rozsahu 4-15!");
			}
		});

		// nastavení velikosti budoucích vrcholù
		btThicknessAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer.parseInt(tf1.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Špatnì zvolená veliskot. Zadejte èíslo v rozsahu 4-15!");
					return;
				}

				if (Integer.parseInt(tf1.getText()) >= 4 && Integer.parseInt(tf1.getText()) <= 15) {
					Vrchol.setThicknessAll(Integer.parseInt(tf1.getText()));
				} else
					JOptionPane.showMessageDialog(null, "Špatnì zvolená veliskot. Zadejte èíslo v rozsahu 4-15!");
			}
		});

		// zmìna barvy vrcholù
		btBarvaVrcholu.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ch = new ColorChooser(getThis(), 2);
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						ch.createAndShowGUI();
					}
				});
				setVisible(false);
			}
		});

		// nastavení tloušky hran
		btStroke.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer.parseInt(tf2.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Špatnì zvolená velikost. Zadejte èíslo v rozsahu 1-10!");
					return;
				}

				if (Integer.parseInt(tf2.getText()) >= 1 && Integer.parseInt(tf2.getText()) <= 10) {
					for (Hrana h : hrana.getList()) {
						h.setStroke(Integer.parseInt(tf2.getText()));
					}
					main.clear();
					main.present();
				} else
					JOptionPane.showMessageDialog(null, "Špatnì zvolená velikost. Zadejte èíslo v rozsahu 1-10!");
			}
		});

		// nastavení tloušky budoucích hran
		btStrokeAll.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					Integer.parseInt(tf2.getText());
				} catch (NumberFormatException e1) {
					JOptionPane.showMessageDialog(null, "Špatnì zvolená velikost. Zadejte èíslo v rozsahu 4-15!");
					return;
				}

				if (Integer.parseInt(tf2.getText()) >= 4 && Integer.parseInt(tf2.getText()) <= 15) {
					Hrana.setStrokeAll(Integer.parseInt(tf2.getText()));
					main.setStroke(Integer.parseInt(tf2.getText()));
				} else
					JOptionPane.showMessageDialog(null, "Špatnì zvolená velikost. Zadejte èíslo v rozsahu 4-15!");
			}
		});

		// zmìna barvy hran
		btBarvaHran.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				ch = new ColorChooser(getThis(), 3);
				javax.swing.SwingUtilities.invokeLater(new Runnable() {
					public void run() {
						ch.createAndShowGUI();
					}
				});
				setVisible(false);
			}
		});

		getContentPane().add(pnl2, "South");
		getContentPane().add(pnl3, "Center");

		getContentPane().add(pnl, "North");

		pack();

	}

	private ColorChanger getThis() {
		return this;
	}

	public void applyColorVrcholu(Color color) {
		for (Vrchol v : mapaservice.getVrchol()) {
			v.setColor(color);
		}
		main.present();
	}

	public void applyColorHran(Color color) {
		for (Hrana hrana : hrana.getList()) {
			hrana.setColor(color);
		}
		main.present();
	}
}