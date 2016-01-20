package samegame;

/**
 * Diese Klasse fasst Highscores mit Spielername, Systemzeit, Anzahl der Restzahlen und Punktzahl zusammen.
 * 
 * @author Lukas Schramm
 * @version 1.0
 */
public class Highscore implements Comparable<Highscore> {

	/**Zeit zu der der Highscore erreicht wurde*/
	private long systemtime;
	/**Name des Spielers*/
	private String name;
	/**Anzahl verbliebener Zellen bei Spielende*/
	private int numOfRestCells;
	/**Punktzahl des Highscores*/
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
	 * Sie vergleicht, welcher Spieler mehr Punkte erreichte.
	 */
	public int compareTo(Highscore o) {
		return ((Integer)o.points).compareTo(points);
	}
}