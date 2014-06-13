import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;


@SuppressWarnings("serial")
public class ChooseDialog extends JDialog implements ActionListener {
	JButton button1, button2;
	JFrame parent;
	
	/**
	 * Constructor creates dialog and adds the two buttons.
	 * @param parent JFrame the dialog belongs to
	 */
	public ChooseDialog(JFrame parent) {
		super(parent, "Choose type of export", true);
		this.parent = parent;
		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new GridLayout(2, 1));
		button1 = new JButton("Pixel (without lines)");
		button2 = new JButton("With lines");
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
			Controller.exportPixel();
			
		}
		if (e.getSource()==button2) {
			Controller.exportLines();
		}
	}
}