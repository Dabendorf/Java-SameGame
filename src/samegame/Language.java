package samegame;

public class Language {
	
	public String linebreak = System.getProperty("line.separator");
	public String programname = "SameGame";
	public String alternativePath = "./alternativepictures/";
	public String pointsTitle = programname+"; Punkte: "; //"SameGame; Punkte: "+points
	public String prognosedPoints = "Punkteprognose: ";
	//public String nameerlaubtezeichen = "[a-zA-Z0-9ÄÖÜäöüß]*";
	public String osname = "os.name";
	public String win = "win";
	public String wrongSystem = "System veraltet";
	//public String dateifehltTitel = "Fehlerhafte Datei";
	public String windows = "Dieses Computerspiel wurde für OS X und Linux entwickelt."+linebreak+"Es ist nicht primär mit Windows kompatibel."+linebreak+"Sollte es zu Problemen bei der Ausführung kommen,"+linebreak+"dann öffne das Spiel bitte auf einem PC"+linebreak+"mit Mac OS X oder Linux!";
	public String endTitle = "Spielende";
	public String restartTitle = "Neue Runde?";
	public String restartQuestion = "Möchtest Du eine neue Partie starten?";
	public String closeTitle = "Programm beenden?";
	public String closeQuestion = "Möchtest Du das Programm beenden?";
	public String settings = "Einstellungen";
	public String restart = "Neustart";
	public String leaderboard = "Bestenliste";
	public String terminate = "Beenden";
	public String changeName = "Spielername ändern";
	public String changeDesign = "Design ändern";
	public String design1 = "Früchte";
	public String design2 = "3D Platten";
	public String design3 = "Farben";
	public String designChanged = "Design verändert";
	public String hideWindowsMessage = "Warnmeldung deaktivieren";
	public String show = "Anzeigen";
	public String noLeaderboard = "Es gibt noch keine Bestenliste!";
	//public String anleitungfehlt = "Die Speicherdatei /dateien/anleitung.pdf ist nicht vorhanden."+umbruch+"Stelle die Datei wieder her, um die Anleitung öffnen zu können.";
	//public String fragespielername = "Wie heißen die Spieler?";
	
	public String evaluation(int points) {
		return "Das Spiel ist vorbei."+linebreak+"Du hast "+points+" Punkte erreicht.";
	}
  	//TODO Aufraeumen
}