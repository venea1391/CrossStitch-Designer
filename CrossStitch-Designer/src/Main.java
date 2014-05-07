import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class Main {
	static int height, width;
	
	public static void main(String[] args){
		JFrame frame = new JFrame("CrossStitch Designer");
		frame.setSize(800, 600);
		//JPanel panel = new JPanel();
		//panel.setSize(800, 600);
		//frame.add(panel);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		/*JLabel placeholder = new JLabel("");
		placeholder.setPreferredSize(new Dimension(800, 600));
		frame.getContentPane().add(placeholder);
		frame.pack();*/
		frame.setLocationRelativeTo(null);
		CanvasPanel cPanel = new CanvasPanel();
		
		//frame.getContentPane().add(new JScrollPane(cPanel), BorderLayout.CENTER);

		//frame.add(cPanel);
	    //JScrollPane scrollPane = new JScrollPane(new CanvasPanelHost(cPanel));
	    //frame.add(new JScrollPane(cPanel), BorderLayout.CENTER);
	 // Add the listbox to a scrolling pane
	 		JScrollPane scrollPanel = new JScrollPane();
	 		scrollPanel.getViewport().add( cPanel );
	 		frame.add( scrollPanel, BorderLayout.CENTER );
		Controller.setCanvasPanel(cPanel);
		Controller.setScrollPanel(scrollPanel);
		//frame.pack();
		
		frame.setVisible(true);
		//createWindow();
		//ask for user input of dimensions
		StartOptionsDialog dlg = new StartOptionsDialog(frame);

	}
	

	
	static void createWindow() {
		 
	       //Create and set up the window. 
	       JFrame frame = new JFrame("Simple GUI");
	       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
	 
	       JLabel textLabel = new JLabel("I'm a label in the window",SwingConstants.CENTER); 
	       textLabel.setPreferredSize(new Dimension(300, 100)); 
	       frame.getContentPane().add(textLabel, BorderLayout.CENTER); 
	 
	       //Display the window. 
	       frame.setLocationRelativeTo(null); 
	       frame.pack();
	       frame.setVisible(true); 
	    }
	
	
}

