import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class StartOptionsDialog extends JDialog implements ActionListener {
	JButton button1, button2;
	JFrame parent;
	
	public StartOptionsDialog(JFrame parent) {
		super(parent, "Get started", true);
		this.parent = parent;
		/*if (parent != null) {
			Dimension parentSize = parent.getSize(); 
		    Point p = parent.getLocation(); 
		    setLocation(p.x + parentSize.width / 4, p.y + parentSize.height / 4);
		}*/
		setLocationRelativeTo(null);
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(2, 1));
		button1 = new JButton("Import image");
		button2 = new JButton("Enter dimensions");
		buttonPane.add(button1);
		buttonPane.add(button2);
		button1.addActionListener(this);
		button2.addActionListener(this);
		getContentPane().add(buttonPane, BorderLayout.SOUTH);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack(); 
		setVisible(true);
		return;
	}
	public void actionPerformed(ActionEvent e) {
		setVisible(false); 
		dispose(); 
		if (e.getSource()==button1) {
			//TODO
		}
		if (e.getSource()==button2) {
			JDialog dimsDialog = new DimsDialog(parent);
		}
	}
}