package samegame;


/**
 * Diese Klasse fasst Highscores mit Spielername, Systemzeit, Anzahl der Restzahlen und Punktzahl zusammen.
 * 
 * @author Lukas Schramm
 * @version 1.0
 */
public class Highscore implements Comparable<Highscore> {
	
	private long systemtime;
	private String name;
	private int numOfRestCells;
	private int points;
	
	public Highscore(long systemtime, String name, int numOfRestCells, int points) {
		this.systemtime = systemtime;
		this.name = name;
		this.numOfRestCells = numOfRestCells;
		this.points = points;
	}

	public long getSystemtime() {
		return systemtime;
	}

	public String getName() {
		return name;
	}

	public int getNumOfRestCells() {
		return numOfRestCells;
	}

	public int getPoints() {
		return points;
	}

	/**
	 * Diese compareTo-Methode vergleicht die Highscores.
	 * Zuerst wird bevorzugt, wer am wenigsten Zeilen benoetigt hat und bei Gleichstand, wer weniger Zeit benoetigte.
	 */
	public int compareTo(Highscore o) {
		return ((Integer)o.points).compareTo(points);
	}
}