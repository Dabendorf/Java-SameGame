package samegame;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 * Diese Klasse erstellt die Bestenliste und ordnet selbige in eine primitive Tabelle ein.
 * 
 * @author Lukas Schramm
 * @version 1.0
 */
public class Leaderboard {

	private JFrame frame1 = new JFrame("Bestenliste");
	private JTable table1 = new JTable();
	private ArrayList<Highscore> highscorelist = new ArrayList<Highscore>();
	private LoadFile lf = new LoadFile(0);

	public Leaderboard() {
		frame1.setPreferredSize(new Dimension(500,300));
        frame1.setMinimumSize(new Dimension(500,300));
	    frame1.setResizable(true);
	    frame1.addWindowListener(new WindowAdapter() {
			@Override
            public void windowClosing(WindowEvent e) {
            	frame1.dispose();
            }
        });
	    
	    Container cp = frame1.getContentPane();
	    cp.setLayout(new GridLayout());
	    
	    loadHighscores();
	}
	
	/**
	 * Diese Methode beantragt alle Highscores aus der xml-Datei und fuegt sie einer ArrayList hinzu.
	 */
	private void loadHighscores() {
		for(Highscore hsc:lf.getAllHighscores()) {
			highscorelist.add(hsc);
		}
	}
	
	/**
	 * Diese Methode erstellt einen neuen Highscore.
	 * @param systemtime Zeit zu der der Highscore erreicht wurde.
	 * @param name Name des Spielers.
	 * @param numOfRestCells Anzahl restlich verbliebener Spielzellen.
	 * @param points Erreichte Punktzahl.
	 */
	public void addHighscore(long systemtime, String name, int numOfRestCells, int points) {
		highscorelist.add(new Highscore(systemtime,name,numOfRestCells,points));
	}
	
	/**
	 * Diese Methode sortiert alle Highscores und fuegt sie in die Tabelle ein.
	 */
	public void sort() {
		Collections.sort(highscorelist);
		Vector<Object> tableContent = new Vector<Object>();
		for(Highscore hsc:highscorelist) {
			if(highscorelist.indexOf(hsc)<25) {
				Vector<Object> line = new Vector<Object>();
				line.add(hsc.getName());
				line.add(hsc.getPoints());
				line.add(hsc.getNumOfRestCells());
				long tempSystem = hsc.getSystemtime();
				Date date = new Date(tempSystem);
			    Format format = new SimpleDateFormat("dd.MM.yyyy HH:mm:ss");
				line.add(format.format(date));
				tableContent.add(line);
				lf.addHighscore(hsc, highscorelist.indexOf(hsc));
			}
		}

		Vector<String> titel = new Vector<String>();
		titel.add("Name");
		titel.add("Punkte");
		titel.add("Restzellen");
		titel.add("Erreicht");
		table1 = new JTable(tableContent, titel);
		
		table1.getColumn("Name").setPreferredWidth(200);
	    table1.getColumn("Punkte").setPreferredWidth(100);
	    table1.getColumn("Restzellen").setPreferredWidth(100);
	    table1.getColumn("Erreicht").setPreferredWidth(200);
	    table1.getTableHeader().setBackground(Color.lightGray);
	    table1.setEnabled(false);
	    
	    DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
	    centerRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
	    for(int x=0;x<table1.getColumnCount();x++) {
	    	table1.getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
	    	table1.getTableHeader().getColumnModel().getColumn(x).setCellRenderer(centerRenderer);
	    }
	    
	    table1.setDefaultRenderer(String.class, centerRenderer);
	    
	    frame1.pack();
	    frame1.setLocationRelativeTo(null);
	    table1.setVisible(true);
	    frame1.getContentPane().add(new JScrollPane(table1));
	}
	
	/**
	 * Diese Methode kann die Bestenliste anzeigen oder wieder ausblenden.
	 * @param anzeigen Boolean, ob angezeigt oder ausgeblendet wird.
	 */
	public void show(boolean anzeigen) {
		frame1.setVisible(anzeigen);
	}

	public JFrame getFrame1() {
		return frame1;
	}
}