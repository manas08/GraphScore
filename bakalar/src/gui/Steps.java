package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Steps extends JFrame {

	JTextArea area = new JTextArea();
	String text;

	public Steps() {
		super("Zjednodušení skóre");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(320, 300));
		setSize(400, 200);

		vytvorGui();

		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
		pack();
		area.setSize(200, 200);
		area.setEditable(true);
		area.setLineWrap(true);
		area.setWrapStyleWord(true);
		area.setFont(new Font("Times New Roman", Font.LAYOUT_LEFT_TO_RIGHT, 15));
	}

	private void vytvorGui() {
		add(area, BorderLayout.CENTER);
		JPanel leva = new JPanel();
		add(leva, BorderLayout.LINE_START);
		JPanel prava = new JPanel();
		add(prava, BorderLayout.LINE_END);

		leva.setBackground(area.getBackground());
		prava.setBackground(area.getBackground());
	}

	// vypsání rozboru skóre do textfieldu
	public void write(Integer[] cisla) {
		boolean podm = true;
		int k = 0;
		text = "";
		while (podm) {
			int prehoz;
			for (int j = 0; j < cisla.length - 1; j++) {
				for (int j2 = j + 1; j2 < cisla.length; j2++) {
					if (cisla[j] > cisla[j2]) {
						prehoz = cisla[j];
						cisla[j] = cisla[j2];
						cisla[j2] = prehoz;
					}
				}
			}

			for (int i = 0; i < cisla.length - k; i++) {
				if (i == 0)
					area.setText("( " + cisla[i].toString());
				else
					area.setText(area.getText() + ", " + cisla[i].toString());
			}
			area.setText(area.getText() + " )");

			text = String.valueOf(text + "\n" + area.getText());
			area.setText(text);

			for (int i = 1; i <= cisla[cisla.length - k - 1]; i++) {
				cisla[cisla.length - k - (1 + i)]--;
			}

			k++;
			if (k >= cisla.length)
				podm = false;
		}
	}

}
