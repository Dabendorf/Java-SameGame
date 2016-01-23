package samegame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Diese Klasse laedt und speichert alle Spielstaende und Einstellungen des Projekts verschluesselt in xml-Dateien.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class LoadFile {
	
	/**Pfad zur Speicherdatei fuer Spieleinstellungen*/
	private String fileSettings = "sgfiles/settings.xml";
	/**Pfad zur Speicherdatei fuer Highscores*/
	private String fileHighscore = "sgfiles/highscores.xml";
	/**Schluessel fuer die Vigenereverschluesselung*/
	private char[] vigKey = "Heizoelrueckstossabdaempfung".toCharArray();
	/**Objekt der Sprachspeicherklasse*/
	private Language lang = new Language();
	/**Zu speichernde Propertieselemente*/
	private Properties props = new Properties();
	/**Die geladene Speicherdatei*/
	private File file;
	
	public LoadFile(int fileNum) {
		switch(fileNum) {
		case 0:file = new File(fileHighscore);break;
		case 1:file = new File(fileSettings);break;
		}
	}
	
	/**
	 * Diese Methode schreibt alle Einstellungen eines Spielers in einer xml-Datei.
	 */
	public void writeSettings() {
		try {
			props.setProperty("username", encrypt(Variables.getUsername()));
			props.setProperty("designNum", encrypt(String.valueOf(Variables.getDesignNum())));
			props.setProperty("hideWindowsMessage", encrypt(String.valueOf(Variables.isHideWindowsMessage())));
			
			FileOutputStream fileOut = new FileOutputStream(file);
			props.storeToXML(fileOut, "SameGame Settings");
			fileOut.close();
		} catch(IOException e) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				lang.fileDamage(fileSettings);
			}
		}
	}

	/**
	 * Diese Methode liest alle Einstellungen eines Spielers aus einer xml-Datei.
	 */
	public void readSettings() {
		try {
			FileInputStream fileInput = new FileInputStream(file);
			props.loadFromXML(fileInput);
			fileInput.close();
			Variables.setUsername(decrypt(props.getProperty("username")));
			Variables.setDesignNum(Integer.valueOf(decrypt(props.getProperty("designNum"))));
			Variables.setHideWindowsMessage(Boolean.valueOf(decrypt(props.getProperty("hideWindowsMessage"))));
		} catch(IOException e) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				lang.fileDamage(fileSettings);
			}
		}
	}
	
	/**
	 * Diese Methode fuegt einen Highscore zur HighscoreDatei hinzu.
	 * @param hsc Der hinzugefuegte Highscore.
	 * @param num Die Nummer des Highscores.
	 */
	public void addHighscore(Highscore hsc,int num) {
		try {
			String temp = String.valueOf(hsc.getSystemtime()+","+hsc.getName()+","+hsc.getNumOfRestCells()+","+hsc.getPoints());
			props.setProperty("highscore"+num, encrypt(temp));
			props.setProperty("numOfHighscores", encrypt(String.valueOf(num+1)));
			FileOutputStream fileOut = new FileOutputStream(file);
			props.storeToXML(fileOut, "SameGame Highscores");
			fileOut.close();
		} catch(IOException e) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				lang.fileDamage(fileHighscore);
			}
		}
	}
	
	/**
	 * Diese Methode gibt einen Array aller abgespeicherten Highscores zurueck.
	 * @return Gibt Highscore-Array zurueck.
	 */
	public Highscore[] getAllHighscores() {
		try {
			FileInputStream fileInput = new FileInputStream(file);
			props.loadFromXML(fileInput);
			fileInput.close();
		} catch(IOException e) {
			try {
				file.createNewFile();
			} catch (IOException e1) {
				lang.fileDamage(fileHighscore);
			}
		}
		
		int numOfHighscores;
		try {
			numOfHighscores = Integer.valueOf(decrypt(props.getProperty("numOfHighscores")));
		} catch(Exception e) {
			numOfHighscores = 0;
		}
		
		Highscore[] highscores = new Highscore[numOfHighscores];
		for(int i=0;i<highscores.length;i++) {
			highscores[i] = getHighscore("highscore"+i);
		}
		return highscores;
	}
	
	/**
	 * Diese Methode gibt einen einzelnen Highscore zurueck.
	 * @param key Key unter dem der Highscore intern abgespeichert ist.
	 * @return Gibt Highscore zurueck.
	 */
	private Highscore getHighscore(String key) {
		try {
			String temp = decrypt(props.getProperty(key));
			String[] temp2 = temp.split(",");
			Highscore hsc = new Highscore(Long.valueOf(temp2[0]),temp2[1],Integer.valueOf(temp2[2]),Integer.valueOf(temp2[3]));
			return hsc;
		} catch (NullPointerException np) { 
			return null;
		}
	}
	
	/**
	 * Diese Methode verschluesselt den eingegebenen String.
	 * @param original Nimmt den Originalstring entgegen.
	 * @return Gibt den verschluesselten String aus.
	 */
	private String encrypt(String original) {
		char[] temp = original.toCharArray();
		String crypt = new String("");
		for(int i=0;i<temp.length;i++) {
			int result = (temp[i] + vigKey[i%vigKey.length]) % 256;
			crypt += (char) result;
		}
		return crypt;
	}
 	
 	/**
	 * Diese Methode entschluesselt den eingegebenen String.
	 * @param encrypted Nimmt den verschluesselten String entgegen.
	 * @return Gibt den entschluesselten String aus.
	 */
 	private String decrypt(String encrypted) {
  		char[] temp = encrypted.toCharArray();
  		String decrypt = new String("");
  		for(int i=0;i<temp.length;i++) {
  			int result;
  			if(temp[i] - vigKey[i%vigKey.length] < 0) {
  				result =  (temp[i] - vigKey[i%vigKey.length]) + 256;
  			} else {
  				result = (temp[i] - vigKey[i%vigKey.length]) % 256;
  			}
  			decrypt += (char) result;
  		}
  		return decrypt;
 	}
}