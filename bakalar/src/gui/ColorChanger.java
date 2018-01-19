package gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import entity.Hrana;
import entity.Vrchol;
import tools.ColorChooser;
import tools.MapaService;

public class ColorChanger extends JFrame {
	private JPanel pnl = (new JPanel(new GridLayout(5, 0, 0, 0)));
	private JPanel pnl2 = (new JPanel(new GridLayout(5, 0, 0, 0)));
	private JPanel pnl3 = new JPanel();
	private JButton btThickness;
	private JButton btStroke;
	private JButton btBarvaVrcholu;
	private JButton btBarvaHran;
	private JTextArea tf1;
	private JTextArea tf2;
	Score score;
	Izomorfism izo;
	ColorChooser ch;
	MapaService mapaservice;
	Hrana hrana;
	Main main;

	public ColorChanger(MapaService mapaservice, Hrana hrana, Main main) {
		super("Zm�nit barvu");
		setSize(400, 300);
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		if(hrana.getList().size()!=0) {
			setPreferredSize(new Dimension(250, 320));
		}else
			setPreferredSize(new Dimension(250, 160));
		
		this.mapaservice = mapaservice;
		this.hrana = hrana;
		this.main = main;


		pnl.add((new JLabel("Vrcholy")));
		pnl.add((new JLabel("   Velikost bodu: ")));
		tf1 = new JTextArea("" + mapaservice.getVrchol().get(0).getThickness());
		pnl.add(tf1);
		btThickness = new JButton("Ulo�it");
		pnl.add(btThickness);
		btBarvaVrcholu = new JButton("Zm�nit barvu v�ech vrchol�");
		pnl.add(btBarvaVrcholu);
		
		if(hrana.getList().size()!=0) {
			pnl2.add(new JLabel("Hrany"));
			pnl2.add((new JLabel("   Velikost hrany: ")));
			tf2 = new JTextArea("" + hrana.getList().get(0).getStroke());
			pnl2.add(tf2);
			btStroke = new JButton("Ulo�it");
			pnl2.add(btStroke);
			btBarvaHran = new JButton("Zm�nit barvu v�ech hran");
			pnl2.add(btBarvaHran);
		}

		
		btThickness.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try { 
			        Integer.parseInt(tf1.getText()); 
			    } catch(NumberFormatException e1) { 
					JOptionPane.showMessageDialog(null, "�patn� zvolen� veliskot. Zadejte ��slo v rozsahu 4-15!");
					return;
			    }
				
				if(Integer.parseInt(tf1.getText()) >= 4 && Integer.parseInt(tf1.getText()) <= 15) {
					for (Vrchol v : mapaservice.getVrchol()) {
						v.setThickness(Integer.parseInt(tf1.getText()));
					}
					main.clear();
					main.present();
				}else
					JOptionPane.showMessageDialog(null, "�patn� zvolen� veliskot. Zadejte ��slo v rozsahu 4-15!");
			}
		});


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


		if(hrana.getList().size()!=0) {
			
			btStroke.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					try { 
				        Integer.parseInt(tf2.getText()); 
				    } catch(NumberFormatException e1) { 
						JOptionPane.showMessageDialog(null, "�patn� zvolen� veliskot. Zadejte ��slo v rozsahu 1-10!");
						return;
				    }
					
					if(Integer.parseInt(tf2.getText()) >= 1 && Integer.parseInt(tf2.getText()) <= 10) {
						for (Hrana h : hrana.getList()) {
							h.setStroke(Integer.parseInt(tf2.getText()));
						}
						main.clear();
						main.present();
					}else
						JOptionPane.showMessageDialog(null, "�patn� zvolen� veliskot. Zadejte ��slo v rozsahu 1-10!");
				}
			});
			
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
		}

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