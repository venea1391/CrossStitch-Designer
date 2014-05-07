import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JScrollPane;


public class Controller {
	private static int height, width;
	private static CanvasPanel canvasPanel;
	private static JScrollPane scrollPanel;
	private static SquareCanvas sqCanvas;
	private static Graphics g;
	
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
		canvasPanel.setSize(w*Square.EDGE, h*Square.EDGE);  //Dimension is flipped?
		scrollPanel.revalidate();
		scrollPanel.repaint();
	}
}
