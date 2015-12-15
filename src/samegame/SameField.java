package samegame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Diese Klasse stellt ein einzelnes Zellenobjekt des Spiels dar, in welcher die Farbe oder das Bild angezeigt wird.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class SameField extends JPanel {
	
	/**Nummer des angezeigten Farbobjekts*/
	private int colorNum;
	/**Liste von verschiedenen, nutzbaren Farben*/
	private Color[] colorList = {new Color(89,146,35), new Color(63,110,249), new Color(224,112,27), new Color(142,37,241), new Color(218,0,0)};
	/**Liste von Pfaden zu den verfuegbaren Bildern*/
	private String[] urlList = Variables.getUrlList();
	/**Pfad des aktuellen Bilds*/
	private String key;
	/**Aktuell anzuzeigendes Bildobjekt*/
	private BufferedImage bi;

	public SameField(int colorNum) {
		changeColor(colorNum);
	}
	
	public int getColorNum() {
		return colorNum;
	}
	
	/**
	 * Diese Methode tauscht die Farbe bzw. das Bild der Zelle gegen eine andere aus und laedt die Zelle neu.
	 * @param colorNum Nummer des neuen Farbobjekts
	 */
	public void changeColor(int colorNum) {
		this.colorNum = colorNum;
		key = urlList[this.colorNum];
		bi = Variables.getPicturecache().get(key);
		getFigure();
	}
	
	/**
	 * Diese Methode generiert die anzuzeigende Figur der Zelle neu.
	 */
	public void getFigure() {
		if(Variables.isWithImages()) {
			this.setBackground(new Color(0x0f6c91));
			this.setBorder(BorderFactory.createLineBorder(new Color(0x08394d),1));
		} else {
			this.setBackground(colorList[colorNum]);
			this.setBorder(BorderFactory.createLineBorder(new Color(0x08394d),1));
		}
	}
	
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		if(Variables.isWithImages()) {
			gr.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
		}
	}
}