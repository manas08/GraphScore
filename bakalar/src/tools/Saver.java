package tools;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Saver {

	private final JFileChooser fileChooser;
	String imageS;

	public Saver() {
		fileChooser = new JFileChooser();
		imageS = null;
	}

	// export obr�zku p�es filechooser
	public void saveImg(BufferedImage img, JButton btn) {

		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("jpg", "jpg"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("png", "png"));
		fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("gif", "gif"));
		fileChooser.setAcceptAllFileFilterUsed(false);

		if (fileChooser.showSaveDialog(btn) == JFileChooser.APPROVE_OPTION) {
			String file = fileChooser.getSelectedFile().getAbsolutePath();
			File f = null;

			try {
				f = new File(file+ "." + fileChooser.getFileFilter().getDescription());
				ImageIO.write(img, fileChooser.getFileFilter().getDescription(), f);
			} catch (IOException e) {
				e.printStackTrace();
				System.out.println("Chyba p�i ukl�d�n� obr�zku");
			}
			System.out.println("Obr�zek ulo�en     " + " um�st�n�: " + file + "    velikost: " + f.length() / 1000 + " kilobajt�");
		}
	}
}
