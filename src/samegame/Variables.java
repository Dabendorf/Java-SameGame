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
	/**Name des Spielers*/
	private static String username;
	/**Nummer des verwendeten Designs*/
	private static int designNum = 0;
	/**Variable ob Windowswarnmeldung deaktiviert wird*/
	private static boolean hideWindowsMessage = false; 

	public static Map<String, BufferedImage> getPicturecache() {
		return picturecache;
	}

	public static boolean isWithImages() {
		return designNum==0||designNum==1 ? true : false;
	}

	public static String getUsername() {
		return username;
	}

	public static void setUsername(String username) {
		Variables.username = username;
	}

	public static int getDesignNum() {
		return designNum;
	}

	public static void setDesignNum(int designNum) {
		Variables.designNum = designNum;
	}

	public static boolean isHideWindowsMessage() {
		return hideWindowsMessage;
	}
	
	public static void setHideWindowsMessage(boolean hideWindowsMessage) {
		Variables.hideWindowsMessage = hideWindowsMessage;
	}
	
	public static void changeHideWindowsMessage() {
		Variables.hideWindowsMessage = !Variables.hideWindowsMessage;
	}
}