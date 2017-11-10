package gui;

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
import tools.MapaService;

public class ToolBar extends JFrame {
	private MapaService mapaservice;
	private JPanel pnl = (new JPanel(new GridLayout(4, 1, 0, 0)));
	private JPanel pnl2 = (new JPanel(new FlowLayout()));
	private JPanel pnl3 = (new JPanel(new FlowLayout()));
	private JButton btSmazat;
	private String nazevBodu;
	private int tbID;
	Score score;
	Izomorfism izo;

	public ToolBar(Vrchol m, Main main, Hrana hrana, int i) {
		super("Detaily bodu");

		setSize(400, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		tbID = m.getId();
		nazevBodu = m.getNazev();

		pnl.add((new JLabel("   Název:     " + nazevBodu)));
		pnl.add(new JLabel("   Popis:    " + m.getPopis()));
		pnl.add(new JLabel("   ID:    " + tbID));
		pnl.add(new JLabel("   Stupeò:    " + m.getStupen()));

		pnl2.add(btSmazat = (new JButton("<html><font size=3>Smazat bod</font></html>")));
		btSmazat.setPreferredSize(new Dimension(200, 25));
		btSmazat.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				int vysledek;
				vysledek = JOptionPane.showConfirmDialog(null, "Smazat bod: " + nazevBodu + "?", "OK", JOptionPane.YES_NO_OPTION);

				if (vysledek == JOptionPane.YES_OPTION) {
					mapaservice.smazVrchol(tbID, hrana);
					btSmazat.setEnabled(false);
					
					// ----když to volá MAIN----
					if(i == 0){
						main.clear();
						main.present();
					// ----když to volá SCORE----
					}else if (i == 1){
						score.clear();
						score.present();
						score.refresh();
					}else if (i == 2){
						izo.clear1();
						izo.present1();
					}else if (i == 3){
						izo.clear2();
						izo.present2();
					}
					setVisible(false);
				}
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
		this.score=sc;
	}

	public void izo(Izomorfism iz) {
		this.izo=iz;
	}
}