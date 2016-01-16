package samegame;

import java.awt.Container;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JTextField;
import javax.swing.KeyStroke;

/**
 * Dies ist die Hauptklasse vom SameGame-Spiel, welche den Spielablauf steuert und die graphische Oberflaeche laedt.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class SameMain {
	
	/**Sammlung von Sprach-Strings*/
	private Language lang = new Language();
	/**Programmfenster des Spiels*/
	private JFrame frame1 = new JFrame(lang.programname);
	/**Anzahl der Spielelemente nach Laenge und Breite*/
	private int[] size = {20, 12};
	/**Array aller Spielzellen*/
	private SameField[][] gameArr = new SameField[size[0]][size[1]];
	/**Eine Liste von Steinen, welche durch Benutzerklick entfernt wird*/
	private ArrayList<Point> removableStones = new ArrayList<Point>();
	/**Anzahl der Punkte im aktuellen Spiel*/
	private int points = 0;
	
	public SameMain() {
		if(!Variables.isHideWindowsMessage()) {
			sysWin();
		}
		loadFrame();
	}
	
	/**
	 * Diese Methode laedt den JFrame, in welchem die graphische Oberflaeche enthalten ist.
	 */
	private void loadFrame() {
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setPreferredSize(new Dimension(45*size[0],45*size[1]+35));
		frame1.setMinimumSize(new Dimension(30*size[0],30*size[1]+35));
		frame1.setMaximumSize(new Dimension(70*size[0],70*size[1]+35));
		frame1.setResizable(true);
		
		Container cp = frame1.getContentPane();
		cp.setLayout(new GridLayout(size[1],size[0]));
		
		setDesign();
		for(int y=0;y<size[1];y++) {
			for(int x=0;x<size[0];x++) {
				final int x1=x, y1=y;
				gameArr[x][y] = new SameField(new Random().nextInt(5));
				gameArr[x][y].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(!gameArr[x1][y1].isEmpty()) {
							findRemovableStones(x1,y1);
							removeStones();
							moveDown();
							moveLeft();
							removableStones.clear();
							showPrognosedPoints();
							frame1.setTitle(lang.pointsTitle+points);
							frame1.revalidate();
							frame1.repaint();
						}
					}
				});
				frame1.add(gameArr[x][y]);
			}
		}
		showPrognosedPoints();
		
        frame1.setJMenuBar(getMenubar());
		
		frame1.pack();
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	
	/**
	 * Laedt alle Grafiken des Spiels in einen gemeinsamen Cache in Form einer TreeMap.
	 */
	private void setDesign() {
		String[] urlList = lang.urlList;
		String key = null;
    	BufferedImage bi = null;
    	
    	if(Variables.isWithImages()) {
    		for(String str:urlList) {
            	try {
            		key = str;
            		URL url;
            		if(Variables.getDesignNum()==0) {
            			url = new URL(BaseURL.getJarBase(SameMain.class), key);
            		} else {
            			url = new URL(BaseURL.getJarBase(SameMain.class), lang.alternativePath+key);
            		}
                    bi = ImageIO.read(url);
                    Variables.getPicturecache().put(key, bi); 
                } catch (MalformedURLException e) {} catch (IOException e) {}
            }
    	}
    	frame1.repaint();
	}
	
	/**
	 * Diese Methode ermittelt alle Steine, die entfernbar sind, weil sie gleichfarbige Nachbaren haben.
	 * @param x x-Koordinate des Steins
	 * @param y y-Koordinate des Steins
	 */
	private void findRemovableStones(int x, int y) {
		int[][] dirArr = {{-1,0},{1,0},{0,-1},{0,1}};
		for(int[] dir:dirArr) {
			if(!removableStones.contains(new Point(x+dir[0],y+dir[1]))) {
				try {
					if(gameArr[x][y].getColorNum()==gameArr[x+dir[0]][y+dir[1]].getColorNum() && !gameArr[x][y].isEmpty()) {
						removableStones.add(new Point(x+dir[0],y+dir[1]));
						findRemovableStones(x+dir[0],y+dir[1]);
					}
				} catch(ArrayIndexOutOfBoundsException ae) {}
			}
		}
	}
	
	/**
	 * Diese Methode entfernt alle Steine des ausgewaehlten Blocks und fuegt eine entsprechende Punktzahl hinzu.
	 */
	private void removeStones() {
		for(Point p:removableStones) {
			gameArr[p.x][p.y].setEmpty();
		}
		points += getPrognosedPoints();
		
		Comparator<Point> comp = new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				int a = new Integer(o1.x).compareTo(o2.x);
				if(a==0) {
					a =  new Integer(o2.y).compareTo(o1.y);
				}
				return a;
			}
		};
		Collections.sort(removableStones, comp);
	}
	
	/**
	 * Diese Methode laesst die Steine ueber leeren Steinen nachruecken.
	 */
	private void moveDown() {
		for(int i=0;i<removableStones.size();i++) {
			for(int a=removableStones.get(i).y;a>0;a--) {
				gameArr[removableStones.get(i).x][a].setColorNum(gameArr[removableStones.get(i).x][a-1].getColorNum());
			}
			gameArr[removableStones.get(i).x][0].setEmpty();
			for(int j=i+1;j<removableStones.size();j++) {
				if(removableStones.get(i).x==removableStones.get(j).x) {
					removableStones.get(j).setLocation(removableStones.get(j).x, removableStones.get(j).y+1);
				}
			}
		}
	}
	
	/**
	 * Diese Methode laesst bei leeren Spalten alles rechts davon nach links aufruecken.
	 */
	private void moveLeft() {
		ArrayList<Integer> emptyColumns = new ArrayList<Integer>();
		for(int x=0;x<size[0];x++) {
			boolean empty = true;
			for(int y=0;y<size[1];y++) {
				if(!gameArr[x][y].isEmpty()) {
					empty = false;
					break;
				}
			}
			if(empty) {
				emptyColumns.add(x);
			}
		}
		
		for(int i=0;i<emptyColumns.size();i++) {
			for(int x=emptyColumns.get(i)-i;x<size[0]-1;x++) {
				for(int y=0;y<size[1];y++) {
					gameArr[x][y].setColorNum(gameArr[x+1][y].getColorNum());
				}
			}
		}
		
		if(emptyColumns.size()>0) {
			for(int y=0;y<size[1];y++) {
				for(int x=size[0]-emptyColumns.size();x<size[0];x++) {
					gameArr[x][y].setEmpty();
				}
			}
		}
	}
	
	/**
	 * Diese Methode setzt fuer alle nicht-leeren Felder die durch Anklicken erreichbare Punktzahl als ToolTipText.
	 * Wenn kein Feld mehr eine Punktzahl erreichen koennte, also alle Figuren nur noch einzeln vorkommen, wird das Spiel beendet.
	 */
	private void showPrognosedPoints() {
		boolean gameEnd = true;
		for(int y=0;y<size[1];y++) {
			for(int x=0;x<size[0];x++) {
				findRemovableStones(x,y);
				if(getPrognosedPoints()>0) {
					gameEnd = false;
				}
				int prognose = getPrognosedPoints();
				if(prognose>0) {
					gameArr[x][y].setToolTipText(lang.prognosedPoints+String.valueOf(getPrognosedPoints()));
				} else {
					gameArr[x][y].setToolTipText(lang.noPoints);
				}
				removableStones.clear();
			}
		}
		if(gameEnd) {
			frame1.setTitle(lang.pointsTitle+points);
			JOptionPane.showMessageDialog(null, lang.evaluation(points), lang.endTitle, JOptionPane.PLAIN_MESSAGE);
			if(!newGame()) {
				System.exit(0);
			}
		}
	}
	
	/**
	 * Diese Methode gitb die Anzahl an Punkten zurueck, die beim Klick erreicht wird oder wurde.
	 */
	private int getPrognosedPoints() {
		int num = removableStones.size();
		int add = (int)Math.ceil(num*(1+(num*0.4))); //0.4*num*num+num
		return add;
	}
	
	/**
	 * Diese Methode fragt, ob der Spieler eine neue Partie starten oder das Spiel beenden moechte und fuehrt die entsprechende Aktion aus.
	 */
	private boolean newGame() {
		int dialogrestart = JOptionPane.showConfirmDialog(null, lang.restartQuestion, lang.restartTitle, JOptionPane.YES_NO_OPTION);
		if(dialogrestart == 0) {
			for(int y=0;y<size[1];y++) {
				for(int x=0;x<size[0];x++) {
					gameArr[x][y].changeColor(new Random().nextInt(5));
				}
			}
			removableStones.clear();
			points = 0;
			showPrognosedPoints();
			frame1.setTitle(lang.pointsTitle+points);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * Diese Methode ueberprueft das Betriebssystem des Nutzers und gibt eine Warnmeldung fuer alle Nutzer von Windows aus,
	 * da das Programm expliziet auf und fuer Unix-basierende Systeme entwickelt wurde und auf Windows moeglicherweise zu Problemen fueren kann.
	 */
 	private void sysWin() {
 		if(System.getProperty(lang.osname).toLowerCase().indexOf(lang.win) >= 0) {
 			JOptionPane.showMessageDialog(null, lang.windows, lang.wrongSystem, JOptionPane.WARNING_MESSAGE);
 		}
 	}
 	
 	/**
 	 * Diese Methode generiert eine Menuebar fuer das Fenster, in welcher der Nutzer wichtige Programmbefehle taetigen kann.
 	 * @return Gibt eine JMenuBar zurueck
 	 */
 	private JMenuBar getMenubar() {
 		JMenuBar menuBar = new JMenuBar();
 		JMenu menuSG = new JMenu(lang.programname);
 		JMenu menuSettings = new JMenu(lang.settings);
 		JMenu menuStatistics = new JMenu(lang.statistics);
 		JMenu menuHelp = new JMenu(lang.help);
 		
 		menuBar.add(menuSG);
 		JMenuItem itemRestart = new JMenuItem(lang.restart);
 		itemRestart.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent evt) {
 				newGame();
 			}
 		});
 		itemRestart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
 		menuSG.add(itemRestart);
 		
 		JMenuItem itemClose = new JMenuItem(lang.terminate);
 		itemClose.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent evt) {
 				int dialogclose = JOptionPane.showConfirmDialog(null, lang.closeQuestion, lang.closeTitle, JOptionPane.YES_NO_OPTION);
 				if(dialogclose == 0) {
 					System.exit(0);
 				}
 			}
 		});
 		itemClose.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
 		menuSG.add(itemClose);
 		
 		menuBar.add(menuSettings);
 		JMenuItem itemChangeUserName = new JMenuItem(lang.changeName);
 		itemChangeUserName.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent evt) {
 				changeName();
 			}
 		});
 		itemChangeUserName.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_G, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
 		
 		menuSettings.add(itemChangeUserName);
 		JMenu itemChangeDesign = new JMenu(lang.changeDesign);
 		ButtonGroup bg = new ButtonGroup();
 		JRadioButtonMenuItem m1 = new JRadioButtonMenuItem(lang.design1,true);
 		JRadioButtonMenuItem m2 = new JRadioButtonMenuItem(lang.design2);
 		JRadioButtonMenuItem m3 = new JRadioButtonMenuItem(lang.design3);
 		bg.add(m1); itemChangeDesign.add(m1); m1.setMnemonic('1'); m1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_1, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
 		bg.add(m2); itemChangeDesign.add(m2); m2.setMnemonic('2'); m2.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_2, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
 		bg.add(m3); itemChangeDesign.add(m3); m3.setMnemonic('3'); m3.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_3, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
 		m1.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent evt) {
 				Variables.setDesignNum(0);
 				setDesign();
 				for(int y=0;y<size[1];y++) {
 					for(int x=0;x<size[0];x++) {
 						gameArr[x][y].repaintCell();
 					}
 				}
 				
 			}
 		});
 		m2.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent evt) {
 				Variables.setDesignNum(1);
 				setDesign();
 				for(int y=0;y<size[1];y++) {
 					for(int x=0;x<size[0];x++) {
 						gameArr[x][y].repaintCell();
 					}
 				}
 			}
 		});
 		m3.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent evt) {
 				Variables.setDesignNum(2);
 				setDesign();
 				for(int y=0;y<size[1];y++) {
 					for(int x=0;x<size[0];x++) {
 						gameArr[x][y].repaintCell();
 					}
 				}
 			}
 		});
 		
 		menuSettings.add(itemChangeDesign);
 		if(System.getProperty(lang.osname).toLowerCase().indexOf(lang.win) >= 0) {
 			JCheckBoxMenuItem itemHideWindowsMessage = new JCheckBoxMenuItem(lang.hideWindowsMessage,false);
 			itemHideWindowsMessage.addActionListener(new ActionListener() {
 				@Override
 				public void actionPerformed(ActionEvent evt) {
 					Variables.changeHideWindowsMessage();
 				}
 			});
 			itemHideWindowsMessage.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_W, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
 			menuSettings.add(itemHideWindowsMessage);
 		}
 		
 		menuBar.add(menuStatistics);
 		JMenuItem itemShowStatistics = new JMenuItem(lang.showStatistics);
 		itemShowStatistics.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent evt) {
 				int num0=0, num1=0, num2=0, num3=0, num4=0;
 				for(int y=0;y<size[1];y++) {
 					for(int x=0;x<size[0];x++) {
 						switch(gameArr[x][y].getColorNum()) {
 						case 0:num0++;break;
 						case 1:num1++;break;
 						case 2:num2++;break;
 						case 3:num3++;break;
 						case 4:num4++;break;
 						}
 					}
 				}
 				JOptionPane.showMessageDialog(null, lang.gameStatistics(num0, num1, num2, num3, num4, Variables.getDesignNum()), lang.statistics, JOptionPane.PLAIN_MESSAGE);
 			}
 		});
 		itemShowStatistics.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
 		menuStatistics.add(itemShowStatistics);
 		
 		JMenuItem itemShowLeaderboard = new JMenuItem(lang.showLeaderboard);
 		itemShowLeaderboard.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent evt) {
 				System.err.println(lang.noLeaderboard);
 				//TODO Menü: Bestenliste hinzufügen
 			}
 		});
 		itemShowLeaderboard.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_L, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
 		menuStatistics.add(itemShowLeaderboard);
 		
 		menuBar.add(menuHelp);
 		JMenuItem itemShowManual = new JMenuItem(lang.showManual);
 		itemShowManual.addActionListener(new ActionListener() {
 			@Override
 			public void actionPerformed(ActionEvent evt) {
 				try {
					Desktop.getDesktop().open(new File(lang.pathManual));
				} catch (Exception e1) {
					JOptionPane.showMessageDialog(null, lang.noManual, lang.wrongFile, JOptionPane.ERROR_MESSAGE);
				}
 				//TODO Anleitung generieren
 			}
 		});
 		itemShowManual.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_M, Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
 		menuHelp.add(itemShowManual);
 		
 		return menuBar;
 	}
 	
 	/**
 	 * Diese Methode veraendert den Benutzernamen, unter welchem der Spieler agiert.
 	 */
 	private void changeName() {
 		JTextField spielername00 = new JTextField(new FieldBoundary(16), "", 0);
 		Object[] namensfrage = {lang.questionName, spielername00};
 	    JOptionPane pane = new JOptionPane(namensfrage, JOptionPane.PLAIN_MESSAGE, JOptionPane.DEFAULT_OPTION);
 	    pane.createDialog(null, lang.questionNameTitle).setVisible(true);
 	    
 	    String oldName = Variables.getUsername();
 	    String newName = spielername00.getText();
 	    
 	    if(newName.equals("")) {
    		JOptionPane.showMessageDialog(null, lang.nameEmpty, lang.nameEmptyTitle, JOptionPane.INFORMATION_MESSAGE);
    		changeName();
    	} else if(newName.equals(oldName)) {
	    	JOptionPane.showMessageDialog(null, lang.sameName, lang.sameNameTitle, JOptionPane.INFORMATION_MESSAGE);
	    } else {
	    	JOptionPane.showMessageDialog(null, lang.nameAccepted, lang.nameAcceptedTitle, JOptionPane.INFORMATION_MESSAGE);
	    	Variables.setUsername(newName);
	    }//TODO Nachfrage nach Benutzername bei Spielende, wenn keiner vorhanden
 	}

	public static void main(String[] args) {
		new SameMain();
	}
}