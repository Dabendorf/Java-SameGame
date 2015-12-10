package samegame;

import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class SameField extends JPanel {
	
	private int colorNum;

	public SameField(int colorNum) {
		this.colorNum = colorNum;
		this.setBorder(BorderFactory.createLineBorder(Color.black,1));
		switch(colorNum) {
		case 0:
			this.setBackground(Color.magenta);
			break;
		case 1:
			this.setBackground(Color.red);
			break;
		case 2:
			this.setBackground(Color.green);
			break;
		case 3:
			this.setBackground(Color.orange);
			break;
		case 4:
			this.setBackground(Color.blue);
			break;
		}
	}
	
	public int getColorNum() {
		return colorNum;
	}
	
	/*public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch(colorNum) {
		
		}
	}*/
}