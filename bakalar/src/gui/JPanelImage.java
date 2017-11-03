package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

public class JPanelImage extends JPanel {
	private Graphics2D gr = null;
	private BufferedImage img = null;

	public JPanelImage(int sirka, int vyska) {
		setPreferredSize(new Dimension(sirka, vyska));
		img = new BufferedImage(sirka + 10, vyska + 10, BufferedImage.TYPE_3BYTE_BGR);
		gr = img.createGraphics();
	}

	public void paint(Graphics g) {
		super.paint(g);
		if (img != null) {
			g.drawImage(img, 0, 0, null);
		}
	}

	public Graphics2D getGraph() {
		return gr;
	}

}
