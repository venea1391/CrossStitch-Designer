import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

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
	public enum Mode {NONE, BACKSTITCH, ERASE, PALETTE, PAINT, NEW, OPEN, SAVE, PATTERN, PBUCKET, ZOOMIN, ZOOMOUT, EXPORT, EYEDROPPER};
	private static Mode currentMode = Mode.NONE;
	public enum EMode {EBS, ESQ};
	public static EMode currentEraserMode = EMode.EBS;
	public enum PBMode {CONTIG, NOT_CONTIG};
	public static PBMode currentPBucketMode = PBMode.NOT_CONTIG;
	public static Color currentColor = Color.black;
	public static boolean changedMode = false;
	public static JFrame mainFrame;
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
			}
			else {
				currentMode = Mode.BACKSTITCH;
				bsPanel.resetLineStack();
			}
		}
		else if (m==Mode.PAINT){
			if (currentMode==Mode.PAINT){
				currentMode = Mode.NONE;
			}
			else {
				currentMode = Mode.PAINT;
				bsPanel.resetSquareStack();
			}
		}
		else if (m==Mode.ERASE){
			if (currentMode==Mode.ERASE){
				currentMode = Mode.NONE;
			}
			else {
				currentMode = Mode.ERASE;
				bsPanel.resetEraseStack();
			}
		}
		else if (m==Mode.PBUCKET){
			if (currentMode==Mode.PBUCKET){
				currentMode = Mode.NONE;
			}
			else {
				currentMode = Mode.PBUCKET;
				bsPanel.resetPBStack();
			}
		}
		else if (m==Mode.EYEDROPPER){
			if (currentMode==Mode.EYEDROPPER){
				currentMode = Mode.NONE;
			}
			else {
				currentMode = Mode.EYEDROPPER;
			}
		}
		else {
			currentMode = m;
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
		mainFrame = frame;

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
		
		//System.out.println(frame.getFocusOwner().toString());
		new StartOptionsDialog(frame);
		
	}
	
	public static void enableToolbar(){
		toolbarPanel.enableAllIcons();
	}
	public static void initializeSquareCanvas(int h, int w){
		setHeight(h);
		setWidth(w);
		setSquareCanvas(new SquareCanvas(w, h));
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
                setSquareCanvas(new SquareCanvas(img));
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
		toolbarPanel.changeCCPanel();
		//System.out.println("The selected color was:" + color);

	}
	
	public static Square paint(int x, int y){
		Square i;
		if ((x>=width*Square.EDGE) || (y>=height*Square.EDGE)){
			return null;
		}
		if (Controller.getMode()==Controller.Mode.ERASE){
			i = sqCanvas.findAndChange(x, y, null);
		}
		else {
			i = sqCanvas.findAndChange(x, y, currentColor);
		}
		if (i!=null){
			canvasPanel.repaint();
		}
		return i;
	}
	
	public static Square paint(int x, int y, Color c){
		Square i;
		if ((x>=width*Square.EDGE) || (y>=height*Square.EDGE)){
			return null;
		}
		i = sqCanvas.findAndChange(x, y, c);
		if (i!=null){
			canvasPanel.repaint();
		}
		return i;
	}
	
	public static ArrayList<Square> paintBucket(int x, int y){
		ArrayList<Square> oldColorSquares = new ArrayList<Square>();
		ArrayList<Square> toStack = new ArrayList<Square>();
		Square s = sqCanvas.find(x, y);
		Color c = s.getColor();
		if (currentPBucketMode==PBMode.NOT_CONTIG){
			oldColorSquares = sqCanvas.findAllOfColor(c);
		}
		else {  //(currentPBucketMode==PBMode.CONTIG)
			oldColorSquares = sqCanvas.findAllContigOfColor(s, c, new ArrayList<Square>());
		}
		for (Square si : oldColorSquares){
			toStack.add(new Square(si));
			sqCanvas.findAndChange(si.getCol(), si.getRow(), currentColor);
		}
		canvasPanel.repaint();
		return toStack;
	}
	
	public static void export(){
		//TODO
	}

	public static void useEyedropper(int x, int y){
		Square s = sqCanvas.find(x, y);
		currentColor = s.getColor();
		toolbarPanel.changeCCPanel();
		
	}
}
