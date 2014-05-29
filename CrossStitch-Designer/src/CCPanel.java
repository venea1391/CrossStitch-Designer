import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;


@SuppressWarnings("serial")
public class CCPanel extends JPanel {
	public CCPanel(Color c){
		setBackground(c);
		setPreferredSize(new Dimension(24, 24));
	}
	
	public void changeColor(Color c){
		setBackground(c);
	}
}
