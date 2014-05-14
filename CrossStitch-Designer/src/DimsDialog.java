import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

@SuppressWarnings("serial")
public class DimsDialog extends JDialog implements ActionListener {
	JButton ok;
	JTextField height, width;
	JFrame parent;
	
	public DimsDialog(JFrame parent) {
		super(parent, "Enter Dimensions:", true);
		this.parent = parent;
		/*if (parent != null) {
			Dimension parentSize = parent.getSize(); 
		    Point p = parent.getLocation(); 
		    setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}*/
		
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5,5,5,5);
		height = new JTextField();
		height.setPreferredSize(new Dimension(75, 30));
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel("Height:"), c);
		c.gridx = 1;
		panel.add(height, c);
		
		c.gridx = 0;
		c.gridy = 1;
		panel.add(new JLabel("Width:"), c);
		c.gridx = 1;
		width = new JTextField();
		width.setPreferredSize(new Dimension(75, 30));
		panel.add(width, c);
		
		c.gridx = 0;
		c.gridy = 2;
		c.gridwidth = 2;
		ok = new JButton("OK");
		ok.addActionListener(this);
		
		panel.add(ok, c);
		getContentPane().add(panel);
		//getContentPane().add(ok);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack(); 
		setLocationRelativeTo(parent);
		setVisible(true);
		return;
	}
	public void actionPerformed(ActionEvent e) {
		
		//get input from text fields, verify
		int h = Integer.parseInt(height.getText());
		int w = Integer.parseInt(width.getText());
		Controller.initializeSquareCanvas(h, w);
		
		setVisible(false); 
		dispose(); 
		Controller.enableToolbar();

	}
}