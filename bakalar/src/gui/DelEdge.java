package gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import entity.Button;
import entity.Hrana;

public class DelEdge extends JFrame {
	private JPanel pnl1 = (new JPanel());
	private int vyska = 20;

	public DelEdge(Hrana hrana) {
		super("Odstranit hranu");
		setVisible(true);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(250, (int) (30 * hrana.getList().size()) + 100);
		setResizable(true);
	}

	public void delete(Hrana hrana, Main main, Izomorfism izo, int w) {
		List<JButton> buttons = new ArrayList<>();
		JLabel uvod = new JLabel("Vyberte hranu k odstranìní:     ");
		uvod.setBounds(10, 5, 200, 20);
		uvod.setPreferredSize(new Dimension(200, 20));
		pnl1.add((uvod));
		getContentPane().add(pnl1, "North");
		JPanel pnl = new JPanel();
		pnl.setLayout(null);

		// každá hrana má svoje tlaèítko pro smazání
		for (int i = 0; i < hrana.getList().size(); i++) {

			Button btn = new Button(new JButton("X"), i);
			buttons.add(btn.getBtn());

			ActionListener l = new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					List<Hrana> listPom = new ArrayList<Hrana>();
					for (int w = 0; w < hrana.getList().size(); w++) {
						if (w != btn.getId()) {
							listPom.add(hrana.getList().get(w));
						} else {
							hrana.getList().get(w).getPrvni().setStupen();
							hrana.getList().get(w).getDruhy().setStupen();
						}
					}
					if (w == 0)
						main.vykresliHranu(listPom);
					else if (w == 1)
						izo.vykresliHranu(listPom);
					setVisible(false);
					dispose();
				}
			};

			btn.getBtn().addActionListener(l);
			btn.getBtn().setPreferredSize(new Dimension(50, 20));
			btn.getBtn().setBounds(140, vyska, 50, 20);
			pnl.add(btn.getBtn());

			JLabel popis = new JLabel("Hrana z " + hrana.getList().get(i).getPrvni().getNazev() + " do "
					+ hrana.getList().get(i).getDruhy().getNazev());
			popis.setBounds(30, vyska, 100, 20);
			popis.setPreferredSize(new Dimension(100, 20));
			pnl.add(popis);

			vyska += 30;
		}

		JScrollPane scroll = new JScrollPane(pnl);
		getContentPane().add(scroll, "Center");
	}
}
