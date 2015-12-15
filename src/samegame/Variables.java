package samegame;

import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.TreeMap;

/**
 * Diese Klasse enthaelt eine Ansammlung saemtlicher globaler Variablen des gesamten Javaprojektes an einem Ort gebuendelt.<br>
 * Die Variablen enthalten Getter und Setter, um sie fuer die anderen Objekte freizugeben.<br>
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class Variables {
	
	/**TreeMap-Cache in welcher alle Spielbilder gespeichert sind*/
	private static Map<String,BufferedImage> picturecache = new TreeMap<String,BufferedImage>();
	/**Boolean, ob der Spieler mit Bildern oder Farbigen Feldern spielen moechte*/
	private static boolean withImages = true;
	/**Liste von Pfaden zu den Bildern des Spiels*/
	private static String[] urlList = {"./banana.png", "./apple.png", "./strawberry.png", "./plum.png", "./orange.png"};

	public static Map<String, BufferedImage> getPicturecache() {
		return picturecache;
	}

	public static boolean isWithImages() {
		return withImages;
	}

	public static void setWithImages(boolean withImages) {
		Variables.withImages = withImages;
	}

	public static String[] getUrlList() {
		return urlList;
	}
}