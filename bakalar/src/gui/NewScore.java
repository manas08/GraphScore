package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import entity.Hrana;

public class NewScore extends JFrame {

	public JTextField tfNazevVrcholu;
	public JButton btGenerate;
	private JButton btZavrit;
	private JPanel pnlCenter = new JPanel(new GridLayout(3, 1));
	public JPanel tbPanel = new JPanel(new GridLayout(4, 2));
	private JPanel pnlSouth = new JPanel(new FlowLayout());
	Score score;
	Integer[] cisla;
	int pocet = 0;
	Hrana hrana;
	boolean nula = false;

	public NewScore(Score score) {
		super("Zadat skóre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(250, 150);
		setResizable(false);
		this.score = score;
		this.hrana = score.getHrana();

		getContentPane().add(pnlCenter, "Center");
		getContentPane().add(pnlSouth, "South");
		pnlCenter.add(new JLabel(" "));
		pnlCenter.add(new JLabel("  " + "  " + " Zadejte skóre grafu ve tvaru (A,B,C ...)"));
		pnlCenter.add(tfNazevVrcholu = (new JTextField("()")));
		tfNazevVrcholu.setCaretPosition(1);

		pnlSouth.add(btGenerate = (new JButton("Vygenerovat graf")));

		pnlSouth.add(btZavrit = (new JButton("Zavøít")));

		// -------Tlaèítka-------
		btZavrit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		btGenerate.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				decomposition();
				if(nula == false) {
					if (korekce()) {
						score.generatePoints(pocet);
						score.generateEdge(cisla);
					} else {
						JOptionPane.showMessageDialog(getNewScore(), "Toto není skóre grafu!!", "Chyba",
								JOptionPane.ERROR_MESSAGE);
					}
				}
			}

		});
	}

	public void decomposition() {
		char[] retezec = tfNazevVrcholu.getText().toCharArray();
		char c;
		int j;

		if (retezec.length == 3) {
			pocet = 1;
			cisla = new Integer[pocet];
			c = retezec[0];
			j = (int) c;
			if (j != 40) {
				error();
			} else {
				c = retezec[1];
				j = (int) c;
				if (j == 48) {
					nula = true;
					cisla[0] = j;
					c = retezec[2];
					j = (int) c;

					if (j != 41) {
						error();
					} else {
						dispose();
						score.generatePoints(pocet);
					}
				} else {
					error();
					return;
				}
			}
		}

		else if (retezec.length > 3) {
			pocet = (retezec.length - 1) / 2;
			cisla = new Integer[pocet];

			c = retezec[0];
			j = (int) c;

			if (j != 40) {
				error();
			} else {

				int pozice = 0;
				for (int i = 1; i < retezec.length - 1; i++) {
					c = retezec[i];
					j = (int) c;
					if (j >= 48 && j <= 57) {
						j = j - 48;
						if (j + 1 > pocet) {
							error();
							return;
						} else {
							cisla[pozice] = j;
							pozice++;
							i++;
							c = retezec[i];
							j = (int) c;

							if (j != 44 && i < retezec.length - 2) {
								error();
							}
						}
					} else {
						error();
						return;
					}
				}

				c = retezec[retezec.length - 1];
				j = (int) c;

				if (j != 41 || pocet == 0) {
					error();
				} else {
					dispose();
				}
			}
		} else {
			error();
		}

	}

	public void error() {
		JOptionPane.showMessageDialog(this, "Špatnì zadaný formát skóre!!", "Chyba", JOptionPane.ERROR_MESSAGE);
	}

	// jedná se o skóre grafu?
	public boolean korekce() {
		Integer[] druhy = new Integer[cisla.length];
		for (int i = 0; i < cisla.length; i++) {
			druhy[i] = cisla[i];
		}

		if (even()) {
			int vybrane;
			boolean podm = false;
			int i = 0;
			while (podm == false) {
				Arrays.sort(druhy, Collections.reverseOrder());
				for (int j = 0; j < druhy.length; j++) {
				}
				vybrane = druhy[i];
				if (vybrane == 0)
					return true;
				for (int j = 1 + i; j <= vybrane + i; j++) {
					if (nullDetection(druhy[j])) {
						return false;
					} else {
						druhy[j] -= 1;
					}
				}

				i++;
				if (i == druhy.length)
					podm = true;
			}
			return true;

		} else {
			return false;
		}
	}

	private boolean nullDetection(int cislo) {
		if (cislo == 0)
			return true;
		else
			return false;
	}

	// souèet hran musí být sudý
	private boolean even() {
		float sudy = 0;
		float vydelen;
		for (int i = 0; i < cisla.length; i++) {
			sudy += cisla[i];
		}
		vydelen = (float) sudy % 2;
		if (vydelen == 0)
			return true;
		else
			return false;
	}

	public NewScore getNewScore() {
		return this;
	}
}
