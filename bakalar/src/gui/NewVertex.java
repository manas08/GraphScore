package gui;

import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import entity.Vrchol;
import tools.MapaService;

public class NewVertex extends JFrame {

	public MapaService mapaservice2;
	private JTextField tfText1;
	private JTextField tfPopisVrcholu;
	private JTextField tfStav;
	public JTextField tfNazevVrcholu;
	public JButton btUloz;
	private JButton btZavrit;
	private JButton btPozice;
	private JPanel pnlMapa2;
	private int polohaX;
	private int polohaY;
	private JPanel pnlNorth = new JPanel(new FlowLayout());
	private JPanel pnlCenter = new JPanel(new GridLayout(6, 1));
	private JPanel pnlSouth = new JPanel(new FlowLayout());
	public JPanel tbPanel = new JPanel(new GridLayout(4, 2));
	public String tbNazev;
	public String tbPopis;
	public String tbId;
	public String s1 = "";
	private Izomorfism izo;
	private boolean pomocna = false; // pri vytvoreni nastavena na false

	public NewVertex(Main main, Izomorfism izo, int which) {
		super("Nový vrchol");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setSize(450, 250);
		setResizable(false);
		this.izo=izo;
		
		getContentPane().add(pnlNorth, "North");
		getContentPane().add(pnlCenter, "Center");
		getContentPane().add(pnlSouth, "South");

		pnlNorth.add(btPozice = (new JButton("<html><font size=5 color='red' >Vyberte polohu vrcholu</font></html>")));
		pnlCenter.add(new JLabel(" Zadejte název vrcholu"));
		pnlCenter.add(tfNazevVrcholu = (new JTextField(20)));
		pnlCenter.add(new JLabel(" Zadejte popis vrcholu"));
		pnlCenter.add(tfPopisVrcholu = (new JTextField(20)));

		pnlCenter.add(btUloz = (new JButton("Uložit")));

		pnlCenter.add(btZavrit = (new JButton("Zavøít")));

		pnlSouth.add(tfStav = (new JTextField("", 25)), "South");
		tfStav.setEditable(false);

		pack();

		btZavrit.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dispose();
				pomocna = true; //
				if(which==1) {
					main.vykresliHranu();
					main.clear();
					main.present();
				} else if(which==2) {
					izo.vykresliHranu1();
					izo.clear1();
					izo.present1();
				} else if(which==3) {
					izo.vykresliHranu2();
					izo.clear2();
					izo.present2();
				} 
			}
		});

		btPozice.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				pnlNorth.add(new JLabel("<html><font size =2>Kliknutím zvol pozici na plátnì </font></html>"));
				pnlNorth.add(tfText1 = (new JTextField(" X:        " + " Y:", 8)));
				tfText1.setEditable(false);
				btPozice.setVisible(false);
				main.toFront();
				
				urciPolohu();
			};
		});

		btUloz.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				ulozVrchol();
			}
		});
	}

	public void setJPanelImage(JPanel pnlMapa) {
		this.pnlMapa2 = pnlMapa;
	}

	private void urciPolohu() {

		pnlMapa2.addMouseListener(new MouseListener() {

			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
			}

			public void mouseClicked(MouseEvent e) {
				polohaX = (int) e.getX();
				polohaY = (int) e.getY() - 4;

				tfText1.setText("X:" + " " + polohaX + " " + "Y:" + " " + polohaY);
				if (pomocna == false) {
					setVisible(true);
				}
				if (pomocna == true) {
					setVisible(false);
				}
			}
		});
	}

	public void ulozVrchol() {

		tfNazevVrcholu.getText();
		tfPopisVrcholu.getText();
		s1 = (tfNazevVrcholu.getText());

		if (polohaX == 0) {
			tfStav.setText("Nejdøíve zadej polohu !");

		} else if (s1.length() == 0) {
			tfStav.setText(" Zadej název !");

		} else {
			btUloz.setEnabled(false);
			pomocna = true;

			mapaservice2.pridejVrchol(
					new Vrchol(polohaX, polohaY, tfNazevVrcholu.getText(), tfPopisVrcholu.getText(), null));
			tfStav.setText("  Bod " + tfNazevVrcholu.getText() + " byl uložen");
			tfNazevVrcholu.setEditable(false);
			tfPopisVrcholu.setEditable(false);
			tfText1.setText("X:" + "         " + "Y:");
		}

	}

	public void setMapaService(MapaService mapaservice) {
		this.mapaservice2 = mapaservice;
	}
}
