import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
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
		setLocationRelativeTo(parent);
		setVisible(true);
		return;
	}
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		dispose();
		
		if (e.getSource()==button1) {
			//TODO
			//Create a file chooser
			Controller.importImage(parent);
			
		}
		if (e.getSource()==button2) {
			JDialog dimsDialog = new DimsDialog(parent);
		}
	}
}