import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;

import javax.swing.*;

public class BackStitchPanel extends JPanel {
	
	public BackStitchPanel(){
		//setOpaque(false);
		System.out.println("constructing bsPanel");
		setBackground(new Color(0,0,0,64));
	}
	
    public Dimension getPreferredSize() {
        return new Dimension(Controller.getWidth()*Square.EDGE, Controller.getHeight()*Square.EDGE);
    }
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("painting backstitch layer");
        // Draw Text
        g.setColor(new Color(0, 0, 0, 128));
        g.fillRect(0, 0, 50, 50);
    } 
}
