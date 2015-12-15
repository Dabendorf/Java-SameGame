package samegame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

/**
 * Dies ist die Hauptklasse vom SameGame-Spiel, welche den Spielablauf steuert und die graphische Oberflaeche laedt.
 * 
 * @author Lukas Schramm
 * @version 1.0
 *
 */
public class SameMain {
	
	/**Programmfenster des Spiels*/
	private JFrame frame1 = new JFrame("SameGame");
	/**Anzahl der Spielelemente nach Laenge und Breite*/
	private int[] size = {20, 12};
	/**Array aller Spielzellen*/
	private SameField[][] gameArr = new SameField[size[0]][size[1]];
	/***/ //TODO
	private ArrayList<Point> removableStones = new ArrayList<Point>();
	
	public SameMain() {
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setPreferredSize(new Dimension(45*size[0],45*size[1]+20));
		frame1.setMinimumSize(new Dimension(30*size[0],30*size[1]+20));
		frame1.setMaximumSize(new Dimension(70*size[0],70*size[1]+20));
		frame1.setResizable(true);
		Container cp = frame1.getContentPane();
		cp.setLayout(new GridLayout(size[1],size[0]));
		
		loadPictures();
		for(int y=0;y<size[1];y++) {
			for(int x=0;x<size[0];x++) {
				final int x1=x, y1=y;
				gameArr[x][y] = new SameField(new Random().nextInt(5));
				gameArr[x][y].addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						removeStones(x1,y1);
					}
				});
				frame1.add(gameArr[x][y]);
			}
		}
		
		frame1.pack();
		frame1.setLocationRelativeTo(null);
		frame1.setVisible(true);
	}
	
	/**
	 * Laedt alle Grafiken des Spiels in einen gemeinsamen Cache in Form einer TreeMap.
	 */
	private void loadPictures() {
		String[] urlList = Variables.getUrlList();
		String key = null;
    	BufferedImage bi = null;
    	
    	for(String str:urlList) {
        	try {
        		key = str;
                URL url = new URL(BaseURL.getJarBase(SameMain.class), key);
                bi = ImageIO.read(url);
                Variables.getPicturecache().put(key, bi); 
            } catch (MalformedURLException e) {} catch (IOException e) {}
        }
	}
	
	//TODO
	private void removeStones(int x, int y) {
		for(int y2=0;y2<size[1];y2++) {
			for(int x2=0;x2<size[0];x2++) {
				gameArr[x2][y2].changeColor(new Random().nextInt(5));
			}
		}
		//System.out.println("Gattööse");
		//int[] dirX = s
	}

	public static void main(String[] args) {
		new SameMain();
	}
}