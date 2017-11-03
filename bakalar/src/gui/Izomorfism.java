package gui;

import javax.swing.JPanel;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Izomorfism extends JPanel  {


	JPanelImage p1;
	JPanelImage p2;
	BufferedImage image1;
	BufferedImage image2;

	public Izomorfism(Main main) {

		setSize(800,400);	
		
		p1 = new JPanelImage(563,866);
		p2 = new JPanelImage(563,866);
		
		add(p1,"West");
		add(p2,"East");
		setVisible(true);

		image1 = new BufferedImage(563, 866, BufferedImage.TYPE_INT_RGB);
		image2 = new BufferedImage(563, 866, BufferedImage.TYPE_INT_RGB);
		clear1();
		present1();
		clear2();
		present2();
	}
	
	public void clear1(){
		Graphics gr = image1.getGraphics();
		gr.setColor(new Color(96,238,238));
		gr.fillRect(0, 0, image1.getWidth(), image1.getHeight());
	}

	public void clear2(){
		Graphics gr = image1.getGraphics();
		gr.setColor(new Color(238,56,238));
		gr.fillRect(0, 0, image1.getWidth(), image1.getHeight());
	}
	
	public void present1(){
			Graphics2D gr = image1.createGraphics();
			gr.setStroke(new BasicStroke(4));
			gr.setColor(new Color(165, 49, 68));
			gr.drawLine(50,50,100,20);
	
		if (p1.getGraphics() != null)
			p1.getGraphics().drawImage(image1, 0, 0, null);
	}

	public void present2(){
		if (p2.getGraphics() != null)
			p2.getGraphics().drawImage(image2, 0, 0, null);
	}

	public void hideshowBTN(Main main, boolean b, int i) {
		for (JButton btn : main.getBTN()) {
			if(i == 1 && btn.getText()=="Zobrazit vlastnosti"){
				
			}else
				btn.setVisible(b);
		}
		if(b==false){
			clear1();
			clear2();
			present1();
			present2();
		}
	}
}
