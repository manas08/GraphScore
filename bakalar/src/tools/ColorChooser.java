package tools;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*;
import entity.Hrana;
import gui.ColorChanger;
import gui.JPanelImage;
import gui.Main;
import gui.NewVertex;
import gui.ToolBar;

public class ColorChooser extends JPanel implements ChangeListener {

	protected JColorChooser tcc;
	BufferedImage img = new BufferedImage(90, 40, BufferedImage.TYPE_INT_RGB);
	JPanelImage previewLabel;
	Color color;
	int prvni = 0;
	ToolBar tb;
	JFrame frame = new JFrame("Vybrat barvu");
	int typ;
	ColorChanger changer;
	Main main;
	NewVertex o;
	Hrana hrana;

	// vol�n� z nab�dky prav�ho tla��tka
	public ColorChooser(ToolBar tb) {
		super(new BorderLayout());
		typ = 1;
		this.tb = tb;

		tcc = new JColorChooser();
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Vyberte barvu"));
		previewLabel = new JPanelImage(90, 40);
		Graphics2D g = img.createGraphics();
		g.setColor(previewLabel.getBackground());
		g.fillRect(0, 0, 90, 40);
		g.setColor(tb.getVrchol().getColor());
		g.fillRect(15, 0, 60, 40);
		setColor(tb.getVrchol().getColor());

		previewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		previewLabel.getGraph().drawImage(img, 0, 0, null);
		tcc.setPreviewPanel(previewLabel);
		UIManager.put("ColorChooser.swatchesRecentSwatchSize", new Dimension(20, 20));
		UIManager.put("ColorChooser.swatchesSwatchSize", new Dimension(20, 20));
		add(tcc, BorderLayout.PAGE_START);
	}

	// vol�n� z main uprav barev
	public ColorChooser(ColorChanger changer, int typ) {
		super(new BorderLayout());
		this.changer = changer;
		this.typ = typ;

		tcc = new JColorChooser();
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Vyberte barvu"));
		previewLabel = new JPanelImage(90, 40);
		Graphics2D g = img.createGraphics();
		g.setColor(previewLabel.getBackground());
		if (typ == 2) {
			g.fillRect(0, 0, 90, 40);
			g.setColor(new Color(0, 0, 0));
			g.fillRect(15, 0, 60, 40);
			setColor(new Color(0, 0, 0));
		} else if (typ == 3) {
			g.fillRect(0, 0, 90, 40);
			g.setColor(new Color(165, 49, 68));
			g.fillRect(15, 0, 60, 40);
			setColor(new Color(165, 49, 68));
		}

		previewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		previewLabel.getGraph().drawImage(img, 0, 0, null);
		tcc.setPreviewPanel(previewLabel);
		UIManager.put("ColorChooser.swatchesRecentSwatchSize", new Dimension(20, 20));
		UIManager.put("ColorChooser.swatchesSwatchSize", new Dimension(20, 20));
		add(tcc, BorderLayout.PAGE_START);
	}

	// vol�n� z main pr zm�nu barvy vrcholu
	public ColorChooser(Main main, NewVertex o) {
		super(new BorderLayout());
		this.main = main;
		this.typ = 4;
		this.o = o;

		tcc = new JColorChooser();
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Vyberte barvu"));
		previewLabel = new JPanelImage(90, 40);
		Graphics2D g = img.createGraphics();
		g.setColor(previewLabel.getBackground());
		g.fillRect(0, 0, 90, 40);
		g.setColor(o.getColor());
		g.fillRect(15, 0, 60, 40);
		setColor(o.getColor());

		previewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		previewLabel.getGraph().drawImage(img, 0, 0, null);
		tcc.setPreviewPanel(previewLabel);
		UIManager.put("ColorChooser.swatchesRecentSwatchSize", new Dimension(20, 20));
		UIManager.put("ColorChooser.swatchesSwatchSize", new Dimension(20, 20));
		add(tcc, BorderLayout.PAGE_START);
	}

	// vol�n� z main pro zm�nu barvy hran
	public ColorChooser(Main main, Hrana hrana) {
		super(new BorderLayout());
		this.main = main;
		this.typ = 5;
		this.hrana = hrana;

		tcc = new JColorChooser();
		tcc.getSelectionModel().addChangeListener(this);
		tcc.setBorder(BorderFactory.createTitledBorder("Vyberte barvu"));
		previewLabel = new JPanelImage(90, 40);
		Graphics2D g = img.createGraphics();
		g.setColor(previewLabel.getBackground());
		g.fillRect(0, 0, 90, 40);
		g.setColor(hrana.getColor());
		g.fillRect(15, 0, 60, 40);
		setColor(hrana.getColor());

		previewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		previewLabel.getGraph().drawImage(img, 0, 0, null);
		tcc.setPreviewPanel(previewLabel);
		UIManager.put("ColorChooser.swatchesRecentSwatchSize", new Dimension(20, 20));
		UIManager.put("ColorChooser.swatchesSwatchSize", new Dimension(20, 20));
		add(tcc, BorderLayout.PAGE_START);
	}

	public void stateChanged(ChangeEvent e) {
		Graphics2D g = img.createGraphics();
		g.setColor(tcc.getSelectionModel().getSelectedColor());
		g.fillRect(15, 0, 60, 40);
		previewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		previewLabel.getGraph().drawImage(img, 0, 0, null);
		setColor(tcc.getSelectionModel().getSelectedColor());

		if (typ == 1)
			tb.applyColor(getColor());
		else if (typ == 2)
			changer.applyColorVrcholu(getColor());
		else if (typ == 3)
			changer.applyColorHran(getColor());
		else if (typ == 4)
			main.applyColorVrcholu(getColor());
		else if (typ == 5)
			main.applyColorHran(getColor());
	}

	public void createAndShowGUI() {
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JComponent newContentPane = null;

		if (typ == 1) {
			newContentPane = new ColorChooser(tb);
		} else if (typ == 4) {
			newContentPane = new ColorChooser(main, o);
		} else if (typ == 5) {
			newContentPane = new ColorChooser(main, hrana);
		} else {
			newContentPane = new ColorChooser(changer, typ);
		}

		newContentPane.setOpaque(true);
		frame.setContentPane(newContentPane);
		frame.pack();
		frame.setVisible(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
}
