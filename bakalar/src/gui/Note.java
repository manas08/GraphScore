package gui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class Note extends JFrame{

	JTextArea area = new JTextArea();
	
	public Note() {
		super("Poznámka");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(400, 200));
		setSize(400, 200);
		

		vytvorGui();

		setLocationRelativeTo(null);
		setVisible(true);
		setResizable(true);
		pack();
		area.setSize(200, 200);
		area.setEditable(true);
		area.setLineWrap(true);
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
	
}
