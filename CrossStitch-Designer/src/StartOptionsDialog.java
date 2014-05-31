import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Dialog displays the initial options a user has when the program starts.
 * 'Import image' or 'enter dimensions'. Will add 'load from file'
 * 
 * @author Venea
 *
 */
@SuppressWarnings("serial")
public class StartOptionsDialog extends JDialog implements ActionListener {
	JButton button1, button2;
	JFrame parent;
	
	/**
	 * Constructor creates dialog and adds the two buttons.
	 * @param parent JFrame the dialog belongs to
	 */
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
	
	/**
	 * Either calls import an image or opens the dimension dialog.
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		setVisible(false);
		dispose();
		
		if (e.getSource()==button1) {
			Controller.importImage(parent);
			
		}
		if (e.getSource()==button2) {
			new DimsDialog(parent);
		}
	}
}