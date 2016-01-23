package samegame;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class LoadFile {
	
	private String fileSettings = "sgfiles/settings.xml";
	private String fileHighscore = "sgfiles/highscores.xml";
	private char[] vigKey = "Heizoelrueckstossabdaempfung".toCharArray();
	private Language lang = new Language();
	private Properties props = new Properties();
	private File file;
	
	public LoadFile(int fileNum) {
		switch(fileNum) {
		case 0:file = new File(fileHighscore);break;
		case 1:file = new File(fileSettings);break;
		}
	}
	
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