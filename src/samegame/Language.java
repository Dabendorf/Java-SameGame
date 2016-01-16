package samegame;

public class Language {
	
	public String linebreak = System.getProperty("line.separator");
	public String programname = "SameGame";
	public String alternativePath = "./alternativepictures/";
	public String[] urlList = {"banana.png", "apple.png", "strawberry.png", "plum.png", "orange.png"};
	public String pointsTitle = programname+"; Punkte: ";
	public String prognosedPoints = "Punkteprognose: ";
	public String noPoints = "Keine Punkte";
	public String allowedChars = "[a-zA-Z0-9ÄÖÜäöüß]*";
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
	public String statistics = "Statistik";
	public String terminate = "Beenden";
	public String changeName = "Spielername ändern";
	public String changeDesign = "Design ändern";
	public String design1 = "Früchte";
	public String design2 = "3D-Platten";
	public String design3 = "Farben";
	public String designChanged = "Design verändert";
	public String hideWindowsMessage = "Warnmeldung deaktivieren";
	public String showStatistics = "Spielstatistik";
	public String showLeaderboard = "<html><body><s>Bestenliste</html></body></s>";
	public String noLeaderboard = "Es gibt noch keine Bestenliste!";
	public String help = "Hilfe";
	public String showManual = "<html><body><s>Anleitung</html></body></s>";
	public String pathManual = "sgfiles/manual.pdf";
	public String wrongFile = "Fehlerhafte Datei";
	public String noManual = "Die Datei /"+pathManual+" ist nicht vorhanden."+linebreak+"Stelle die Datei wieder her, um die Anleitung öffnen zu können.";
	public String questionName = "Wie heißt Du?";
	public String questionNameTitle = "Namensabfrage";
	public String nameEmpty = "Du hast keinen Namen eingegeben."+linebreak+"Bitte gib Deinen Namen ein.";
	public String nameEmptyTitle = "Name fehlt";
	public String sameName = "Du hast den gleichen Namen eingegeben."+linebreak+"Er verändert sich nicht.";
	public String sameNameTitle = "Name unverändert";
	public String nameAccepted = "Dein Name wurde abgespeichert.";
	public String nameAcceptedTitle = "Name akzeptiert";
	
	/**
	 * Diese Methode gibt eine Spielendmeldung mit Punktzahl zurueck.
	 * @param points Nimmt Punktzahl entgegen.
	 * @return Gibt auszugebenden String zurueck.
	 */
	public String evaluation(int points) {
		return "Das Spiel ist vorbei."+linebreak+"Du hast "+points+" Punkte erreicht.";
	}
	
	/**
	 * Diese Methode generiert den Statistikstring, wie viele Steine noch von welcher Sorte uebrig sind.
	 * @param num0 Anzahl der Steine von Sorte 0.
	 * @param num1 Anzahl der Steine von Sorte 1.
	 * @param num2 Anzahl der Steine von Sorte 2.
	 * @param num3 Anzahl der Steine von Sorte 3.
	 * @param num4 Anzahl der Steine von Sorte 4.
	 * @param designNum Nummer des verwendeten Spieldesigns.
	 * @return Gibt den Statistikstring zurueck.
	 */
	public String gameStatistics(int num0, int num1, int num2, int num3, int num4, int designNum) {
		if(designNum==0) {
			return new String("Bananen: "+num0+linebreak+"Äpfel: "+num1+linebreak+"Erdbeeren: "+num2+linebreak+"Pflaumen: "+num3+linebreak+"Orangen: "+num4+linebreak+"Gesamt: "+(num0+num1+num2+num3+num4));
		} else {
			return new String("Blau: "+num0+linebreak+"Grün: "+num1+linebreak+"Rot: "+num2+linebreak+"Lila: "+num3+linebreak+"Orange: "+num4+linebreak+"Gesamt: "+(num0+num1+num2+num3+num4));
		}
	}
  	//TODO Aufraeumen
}