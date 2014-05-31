import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;


/**
 * Panel solely to display the currently active color.
 * Displayed on ToolBarPanel next to the icons.
 * 
 * @author Venea
 *
 */
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
