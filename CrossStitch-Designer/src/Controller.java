import java.awt.Dimension;
import java.awt.Graphics;
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
	
	public static SquareCanvas getSquareCanvas(){
		return sqCanvas;
	}
	
	public static void updateCanvasPanel(){
		canvasPanel.repaint();
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
            } catch (IOException e) {
            	System.out.println("well, shit");
            }
        } else {
            //log.append("Open command cancelled by user." + newline);
        }
	}
}
