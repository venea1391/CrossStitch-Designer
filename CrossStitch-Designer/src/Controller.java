import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JScrollPane;


/**
 * The static class that acts something like a controller in an MVC pattern.
 * Starts the program with displaying all of the panels, contains logic that takes
 * inputs from the panels and sends messages or data to other models or views.
 * 
 * @author Venea
 *
 */
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
	
	/**
	 * @param h The number of squares in the design's height
	 */
	public static void setHeight(int h){
		height = h;
	}
	/**
	 * @param w The number of squares in the design's width
	 */
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
	
	/**
	 * Takes in the new Mode - if the new mode equals the current mode, currentMode changes to 
	 * NONE. Otherwise currentMode is set to the new Mode.
	 * When Modes are entered that involve undo operations, the stacks are reset
	 * to prevent complexity of actions from different Modes affecting the same squares.
	 * 
	 * @param m The new Mode
	 */
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
	
	
	/**
	 * Forces the panels to recognize their new size to accommodate positioning
	 * and scrolling.
	 */
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
	
	/**
	 * Creates the main Frame for the program and initializes all the panels.
	 * Uses the annoying GridBagLayout.
	 */
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
	
	/**
	 * Tells the toolbarPanel to enable the icons.
	 */
	public static void enableToolbar(){
		toolbarPanel.enableAllIcons();
	}
	
	/**
	 * Creates a new canvas from the "enter dimensions" dialog.
	 * 
	 * @param h The height of the canvas
	 * @param w The width of the canvas
	 */
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
	
	
	/**
	 * Displays the file chooser to select an image to import.
	 * Creates a new canvas from the image. 
	 * Image size is limited due to memory space.
	 * 
	 * @param parent The JFrame parent to contain the dialog
	 */
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
	
	
	/**
	 * Makes the Canvas larger. If edge is 2, increases to 5.
	 * Until it reaches 40, it increases by 5.
	 * 40 is the limit.
	 */
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
	
	/**
	 * Makes the Canvas smaller. If edge is 2, increases to 5.
	 * Until it reaches 5, it decreases by 5.
	 * At 5 it decreases to 2, which is the limit.
	 */
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
	
	
	/**
	 * Opens the color chooser dialog.
	 * Sets the currentColor to the chosen color
	 * Updates the ccPanel to display new color
	 */
	public static void chooseColor(){
		currentColor = JColorChooser.showDialog(designArea, "Choose a color", currentColor);
		toolbarPanel.changeCCPanel();
		//System.out.println("The selected color was:" + color);

	}
	
	
	/**
	 * Changes the color of the square at the given coordinates.
	 * In ERASE mode the color is changed to null, otherwise it is changed to
	 * currentColor.
	 * 
	 * @param x X of the square
	 * @param y Y of the square
	 * @return A copy of the old Square
	 */
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
	
	/**
	 * Here color is specified for instances when squares are to be colored 
	 * something other than currentColor, such as when undoing an action.
	 * 
	 * @param x X of the Square
	 * @param y Y of the Square
	 * @param c The color to change the square to
	 * @return A copy of the old Square
	 */
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
	
	/**
	 * Finds either contiguous or all non-contiguous squares of the same color
	 * and changes them to currentColor.
	 * 
	 * @param x X of square
	 * @param y Y of square
	 * @return A list of copies of the old squares that were changed.
	 */
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

	/**
	 * Gets the color of the square at the coordinates and makes it the currentColor.
	 * Also updates the ccPanel to match.
	 * 
	 * @param x X of the square
	 * @param y Y of the square
	 */
	public static void useEyedropper(int x, int y){
		Square s = sqCanvas.find(x, y);
		currentColor = s.getColor();
		toolbarPanel.changeCCPanel();
	}
}
