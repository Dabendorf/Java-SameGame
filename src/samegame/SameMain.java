package samegame;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JFrame;

public class SameMain {
	
	private JFrame frame1 = new JFrame("SameGame");
	private int[] size = {20, 12};
	private SameField[][] gameArr = new SameField[size[0]][size[1]];
	private boolean withImages = false;
	private ArrayList<Point> removableStones = new ArrayList<Point>();
	
	public SameMain() {
		frame1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame1.setPreferredSize(new Dimension(35*size[0],35*size[1]+20));
		frame1.setMinimumSize(new Dimension(10*size[0],10*size[1]+20));
		frame1.setMaximumSize(new Dimension(60*size[0],60*size[1]+20));
		frame1.setResizable(true);
		Container cp = frame1.getContentPane();
		cp.setLayout(new GridLayout(size[1],size[0]));
		
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
	
	private void removeStones(int x, int y) {
		//int[] dirX = s
	}

	public static void main(String[] args) {
		new SameMain();
	}
}