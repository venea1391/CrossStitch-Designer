import java.awt.Graphics;


public class Controller {
	private static int height, width;
	private static CanvasPanel canvasPanel;
	private static SquareCanvas sqCanvas;
	private static Graphics g;
	
	public static void setHeight(int h){
		height = h;
	}
	public static void setWidth(int w){
		width = w;
	}
	
	public static void setCanvasPanel(CanvasPanel cp){
		canvasPanel = cp;
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
}
