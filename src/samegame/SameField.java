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
	private Color[] colorList = {new Color(63,110,249), new Color(89,146,35), new Color(218,0,0), new Color(142,37,241), new Color(224,112,27)};
	/**Liste von Pfaden zu den verfuegbaren Bildern*/
	private String[] urlList = new Language().urlList;
	/**Pfad des aktuellen Bilds*/
	private String key;
	/**Aktuell anzuzeigendes Bildobjekt*/
	private BufferedImage bi;
	/**Systemzeit wann Feld zuletzt angeklickt wurde*/
	private long clickedTime = 0L;
	

	public SameField(int colorNum) {
		changeColor(colorNum);
	}
	
	public int getColorNum() {
		return colorNum;
	}
	
	public void setColorNum(int colorNum) {
		changeColor(colorNum);
		repaintCell();
	}
	
	/**
	 * Diese Methode tauscht die Farbe bzw. das Bild der Zelle gegen eine andere aus und laedt die Zelle neu.
	 * @param colorNum Nummer des neuen Farbobjekts.
	 */
	public void changeColor(int colorNum) {
		this.colorNum = colorNum;
		if(!this.isEmpty()) {
			key = urlList[this.colorNum];
			bi = Variables.getPicturecache().get(key);
			getFigure();
		}
	}
	
	/**
	 * Diese Methode generiert die anzuzeigende Figur der Zelle neu.
	 */
	private void getFigure() {
		if(Variables.isWithImages()) {
			this.setBackground(new Color(0x0f6c91));
		} else if(!this.isEmpty()) {
			this.setBackground(colorList[colorNum]);
		}
		this.setBorder(BorderFactory.createLineBorder(new Color(0x08394d),1));
	}
	
	public void paintComponent(Graphics gr) {
		super.paintComponent(gr);
		if(!this.isEmpty()) {
			if(Variables.isWithImages()) {
				gr.drawImage(bi, 0, 0, getWidth(), getHeight(), null);
			}
		} else if(!Variables.isWithImages()) {
			gr.clearRect(0, 0, getWidth(), getHeight());
		}
	}
	
	/**
	 * Diese Methode ueberprueft, ob dem Feld keine echte Farbe, also -1 als Wert zugeordnet ist.
	 * @return Gibt Zustand zurueck.
	 */
	public boolean isEmpty() {
		if(colorNum==-1) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Diese Methode leert ein einzelnes Feld oder gibt ihm eine Farbe zurueck.
	 */
	public void setEmpty() {
		colorNum=-1;
		repaintCell();
	}
	
	/**
	 * Diese Methode laedt die Zelle neu, was insbesondere bei Designaenderungen relevant ist.
	 */
	public void repaintCell() {
		bi = Variables.getPicturecache().get(key);
		getFigure();
		this.repaint();
	}

	public long getClickedTime() {
		return clickedTime;
	}

	public void setClickedTime(long clickedTime) {
		this.clickedTime = clickedTime;
	}
}