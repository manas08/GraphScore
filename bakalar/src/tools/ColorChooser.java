package tools;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.event.*;

import entity.Vrchol;
import gui.ColorChanger;
import gui.JPanelImage;
import gui.ToolBar;

 
/* ColorChooserDemo.java requires no other files. */
public class ColorChooser extends JPanel
                              implements ChangeListener {
 
    protected JColorChooser tcc;
     BufferedImage img = new BufferedImage(90, 40, BufferedImage.TYPE_INT_RGB);
     JPanelImage previewLabel;
     Color color;
     int prvni = 0;
     ToolBar tb;
     JFrame frame = new JFrame("Vybrat barvu");
     int typ;
     ColorChanger changer;


	public ColorChooser(ToolBar tb) {
        super(new BorderLayout());
        typ = 1;
        this.tb = tb;
 
        //Set up the banner at the top of the window
        
 
        //Set up color chooser for setting text color
        tcc = new JColorChooser();
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder(
                                             "Choose Text Color"));
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
        UIManager.put("ColorChooser.swatchesRecentSwatchSize", new Dimension(20,20));
        UIManager.put("ColorChooser.swatchesSwatchSize", new Dimension(20,20));
        add(tcc, BorderLayout.PAGE_START);
    }
   
	public ColorChooser(ColorChanger changer, int typ) {
        super(new BorderLayout());
        this.changer = changer;
        this.typ = typ;
 
        //Set up the banner at the top of the window
        
 
        //Set up color chooser for setting text color
        tcc = new JColorChooser();
        tcc.getSelectionModel().addChangeListener(this);
        tcc.setBorder(BorderFactory.createTitledBorder(
                                             "Choose Text Color"));
        previewLabel = new JPanelImage(90, 40);
        Graphics2D g = img.createGraphics();
        g.setColor(previewLabel.getBackground());
        if(typ == 2) {
            g.fillRect(0, 0, 90, 40);
            g.setColor(new Color(0, 0, 0));
            g.fillRect(15, 0, 60, 40);
    	    setColor(new Color(0, 0, 0));
        }else if(typ == 3) {
        	g.fillRect(0, 0, 90, 40);
            g.setColor(new Color(165, 49, 68));
            g.fillRect(15, 0, 60, 40);
     	    setColor(new Color(165, 49, 68));
        }
        
        
	    previewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    previewLabel.getGraph().drawImage(img, 0, 0, null);
	    tcc.setPreviewPanel(previewLabel);
        UIManager.put("ColorChooser.swatchesRecentSwatchSize", new Dimension(20,20));
        UIManager.put("ColorChooser.swatchesSwatchSize", new Dimension(20,20));
        add(tcc, BorderLayout.PAGE_START);
	}

	public void stateChanged(ChangeEvent e) {
        Graphics2D g = img.createGraphics();
        g.setColor(tcc.getSelectionModel().getSelectedColor());
        g.fillRect(15, 0, 60, 40);
	    previewLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
	    previewLabel.getGraph().drawImage(img, 0, 0, null);
	    setColor(tcc.getSelectionModel().getSelectedColor());
	    
	    if(typ == 1)
	    	tb.applyColor(getColor());
	    else if(typ == 2)
	    	changer.applyColorVrcholu(getColor());
	    else if(typ == 3)
	    	changer.applyColorHran(getColor());
    }
 
    /**
     * Create the GUI and show it.  For thread safety,
     * this method should be invoked from the
     * event-dispatching thread.
     */
    public void createAndShowGUI() {
        //Create and set up the window.
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        //Create and set up the content pane.
        
        JComponent newContentPane = null;
        
	    if(typ == 1) {
	    	newContentPane = new ColorChooser(tb);
	    }
	    else{
	       newContentPane = new ColorChooser(changer, typ);
	    }
	    
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
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
