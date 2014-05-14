import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JScrollPane;


public class Controller {
	private static int height, width;
	private static CanvasPanel canvasPanel;
	private static JScrollPane scrollPanel;
	private static SquareCanvas sqCanvas;
	private static ToolbarPanel toolbarPanel;
	private static Graphics g;
	//custom path
	static final JFileChooser fc = new JFileChooser("/Volumes/Macintosh HDD/HDD desktop/crafts/cross stitch");
	
	public static void setHeight(int h){
		height = h;
	}
	public static void setWidth(int w){
		width = w;
	}
	
	public static int getHeight(){
		return height;
	}
	public static int getWidth(){
		return width;
	}
	
	public static void setCanvasPanel(CanvasPanel cp){
		canvasPanel = cp;
	}
	
	public static void setScrollPanel(JScrollPane sp){
		scrollPanel = sp;
	}
	
	public static void setSquareCanvas(SquareCanvas sc){
		sqCanvas = sc;
	}
	
	public static void setToolbarPanel(ToolbarPanel tp){
		toolbarPanel = tp;
	}
	
	public static SquareCanvas getSquareCanvas(){
		return sqCanvas;
	}
	
	public static void updateCanvasPanel(){
		canvasPanel.repaint();
	}
	
	public static void start(){
		JFrame frame = new JFrame("CrossStitch Designer");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		//need to add a JLayeredPane !!!!!!!!!!!!!!!!!!!
		frame.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		ToolbarPanel toolbarPanel = new ToolbarPanel();
		c.insets = new Insets(0,0,0,0);
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.weightx = 1.0;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.weighty = 0;
		frame.add(toolbarPanel, c);
		CanvasPanel cPanel = new CanvasPanel();
	 	JScrollPane scrollPanel = new JScrollPane();
	 	scrollPanel.setSize(new Dimension(800, 600-32));
	 	scrollPanel.getViewport().add( cPanel );
	 	c.gridy = 1;
	 	c.weighty = 1.0;
	 	//c.ipady = 600-32;
	 	//c.ipadx = 800-20;
	 	c.fill = GridBagConstraints.BOTH;
	 	//c.weightx = 1.0;
	 	//CAN'T VIEW THE SCROLL PANEL
	 	frame.add(scrollPanel, c);
		setCanvasPanel(cPanel);
		setScrollPanel(scrollPanel);
		setToolbarPanel(toolbarPanel);
		
		frame.setVisible(true);
		new StartOptionsDialog(frame);
		
	}
	
	public static void enableToolbar(){
		toolbarPanel.enableAllIcons();
	}
	public static void initializeSquareCanvas(int h, int w){
		setHeight(h);
		setWidth(w);
		setSquareCanvas(SquareCanvas.createSquareCanvas(w, h));
		updateCanvasPanel();
		//canvasPanel.setSize(w*Square.EDGE, h*Square.EDGE);
		scrollPanel.revalidate();
		scrollPanel.repaint();
	}
	
	public static void importImage(JFrame parent){
		int returnVal = fc.showOpenDialog(parent);
		//TODO add file filter to file chooser
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = fc.getSelectedFile();
            BufferedImage img;
            try {
                img = ImageIO.read(file);
                SquareCanvas.createSquareCanvas(img);
                setHeight(img.getHeight());
        		setWidth(img.getWidth());
                updateCanvasPanel();
                scrollPanel.revalidate();
        		scrollPanel.repaint();
        		Controller.enableToolbar();
            } catch (IOException e) {
            	System.out.println("well, shit");
            }
        } else {
            //log.append("Open command cancelled by user." + newline);
        }
	}
}
