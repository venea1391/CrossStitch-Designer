import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;


public class Controller {
	private static int height, width;
	private static CanvasPanel canvasPanel;
	private static JScrollPane scrollPanel;
	private static SquareCanvas sqCanvas;
	private static ToolbarPanel toolbarPanel;
	private static BackStitchPanel bsPanel;
	private static JLayeredPane designArea;
	public enum Mode {NONE, BACKSTITCH, ERASE_BS, ERASE_SQ, PALETTE, PAINT};
	private static Mode currentMode = Mode.NONE;
	public static Color currentColor = Color.black;
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
	
	public static void setBackStitchPanel(BackStitchPanel bsp){
		bsPanel = bsp;
	}
	public static void setDesignArea(JLayeredPane da){
		designArea = da;
	}
	
	public static void setMode(Mode m){
		if (m==Mode.BACKSTITCH){
			if (currentMode==Mode.BACKSTITCH){
				currentMode = Mode.NONE;
				toolbarPanel.changeStatus("");
			}
			else {
				currentMode = Mode.BACKSTITCH;
				toolbarPanel.changeStatus("Backstitch mode enabled");
				bsPanel.resetLineStack();
			}
		}
		else if (m==Mode.PAINT){
			if (currentMode==Mode.PAINT){
				currentMode = Mode.NONE;
				toolbarPanel.changeStatus("");
			}
			else {
				currentMode = Mode.PAINT;
				toolbarPanel.changeStatus("Paint mode enabled");
				canvasPanel.resetPaintStack();
				//bsPanel.setEnabled(false);
				//canvasPanel.setEnabled(true);
				//canvasPanel.setFocusable(true);
			}
		}
		//I need to handle ERASE_SQ here.
		else if (m==Mode.ERASE_BS){
			if (currentMode==Mode.ERASE_BS){
				currentMode = Mode.NONE;
				toolbarPanel.changeStatus("");
			}
			else {
				currentMode = Mode.ERASE_BS;
				toolbarPanel.changeStatus("Erase mode enabled");
				bsPanel.resetEraseStack();
			}
		}
		else {
			currentMode = m;
			toolbarPanel.changeStatus("why am i here");
		}
	}
	public static Mode getMode(){
		return currentMode;
	}
	
	public static SquareCanvas getSquareCanvas(){
		return sqCanvas;
	}
	
	public static void updateDesignArea(){
		//canvasPanel.repaint();
		canvasPanel.setSize(new Dimension(width*Square.EDGE, height*Square.EDGE));
		bsPanel.setSize(new Dimension(width*Square.EDGE, height*Square.EDGE));
		designArea.setSize(new Dimension(width*Square.EDGE, height*Square.EDGE));
		designArea.setPreferredSize(new Dimension(width*Square.EDGE, height*Square.EDGE));
	}
	
	public static void repaintBSPanel(){
		bsPanel.repaint();
	}
	
	public static void start(){
        
		JFrame frame = new JFrame("CrossStitch Designer");
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);

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
		BackStitchPanel bsPanel = new BackStitchPanel();
		JLayeredPane designArea = new JLayeredPane();
		designArea.add(cPanel, new Integer(0), 0);
		designArea.add(bsPanel, new Integer(1), 0);

	 	JScrollPane scrollPanel = new JScrollPane();
	 	scrollPanel.setSize(new Dimension(800, 600-32));
	 	scrollPanel.getViewport().add(designArea);

	 	c.gridy = 1;
	 	c.weighty = 1.0;
	 	c.fill = GridBagConstraints.BOTH;
	 	frame.add(scrollPanel, c);

		setCanvasPanel(cPanel);
		setScrollPanel(scrollPanel);
		setToolbarPanel(toolbarPanel);
		setBackStitchPanel(bsPanel);
		setDesignArea(designArea);
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
		updateDesignArea();
		bsPanel.createBI();
		System.out.println("new blank canvas");
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
                updateDesignArea();
                bsPanel.createBI();
                scrollPanel.revalidate();
        		scrollPanel.repaint();
        		//bsPanel.repaint();
        		Controller.enableToolbar();
            } catch (IOException e) {
            	System.out.println("well, shit");
            }
        } else {
            //log.append("Open command cancelled by user." + newline);
        }
	}
	
	public static void zoomIn(){
		if (Square.EDGE == 2){
			Square.EDGE = 5;
			updateDesignArea();
			bsPanel.scaleBI();
			scrollPanel.revalidate();
			scrollPanel.repaint();
		}
		else if (Square.EDGE < 40){
			Square.EDGE = Square.EDGE+5;
			updateDesignArea();
			bsPanel.scaleBI();
			scrollPanel.revalidate();
			scrollPanel.repaint();
		}
		else {
			//do nothing
		}
	}
	public static void zoomOut(){
		if (Square.EDGE > 5){
			Square.EDGE = Square.EDGE-5;
			updateDesignArea();
			bsPanel.scaleBI();
			scrollPanel.revalidate();
			scrollPanel.repaint();
		}
		else if (Square.EDGE == 5){
			Square.EDGE = 3;
			updateDesignArea();
			bsPanel.scaleBI();
			scrollPanel.revalidate();
			scrollPanel.repaint();
		}
		else {
			//do nothing
		}
	}
	
	public static void chooseColor(){
		currentColor = JColorChooser.showDialog(designArea, "Choose a color", currentColor);
		//System.out.println("The selected color was:" + color);

	}
	
	public static void paint(int x, int y){
		System.out.println("sldkfjwaoifd");
		if ((x>=width*Square.EDGE) || (y>=height*Square.EDGE)){
			return;
		}
		int i = sqCanvas.findAndChange(x, y, currentColor);
		if (i==1){
			canvasPanel.repaint();
		}
	}
}
