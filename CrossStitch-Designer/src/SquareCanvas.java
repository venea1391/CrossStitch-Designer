import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.Map;
import java.util.HashMap;


public class SquareCanvas {
	private static SquareCanvas instance;
	private int width, height;
	private static HashMap<Integer, HashMap<Integer, Square>> canvas; //map of rows of squares
	
	public SquareCanvas(){
		this.width = 0;
		this.height = 0;
	}
	
	
	public static SquareCanvas getInstance(){
		if (instance!=null){
			return instance;
		}
		else {
			System.out.println("oops no canvas created");
			//instance = new SquareCanvas();
			return null;
		}
	}
	
	public static HashMap<Integer, HashMap<Integer, Square>> getCanvas(){
		return canvas;
	}
	
	public static SquareCanvas createSquareCanvas(int x, int y){
		instance = new SquareCanvas();
		instance.height = y;
		instance.width = x;

		HashMap<Integer, Square> row;
		canvas = new HashMap<Integer, HashMap<Integer, Square>>();
		for (int i=0; i<y; i++){
			row = new HashMap<Integer, Square>();
			
			for (int j=0; j<x; j++){
				row.put(new Integer(j), new Square(i, j));
			}
			canvas.put(new Integer(i), row);
		}
		return instance;
	}
	public static SquareCanvas createSquareCanvas(BufferedImage img){
		instance = new SquareCanvas();
		instance.height = img.getHeight();
		instance.width = img.getWidth();
		HashMap<Integer, Square> row;
		Square s;
		canvas = new HashMap<Integer, HashMap<Integer, Square>>();
		for (int i=0; i<instance.height; i++){
			row = new HashMap<Integer, Square>();
			
			for (int j=0; j<instance.width; j++){
				Color color = new Color(img.getRGB(j, i));
				s = new Square(color, i, j);
				row.put(new Integer(j), s);
			}
			canvas.put(new Integer(i), row);
		}
		return instance;
	}
}
