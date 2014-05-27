import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.Map;
import java.util.HashMap;


public class SquareCanvas {
	private int width, height;
	private HashMap<Integer, HashMap<Integer, Square>> canvas; //map of rows of squares
	
	public SquareCanvas(int x, int y){
		this.width = x;
		this.height = y;
		HashMap<Integer, Square> row;
		this.canvas = new HashMap<Integer, HashMap<Integer, Square>>();
		for (int i=0; i<y; i++){
			row = new HashMap<Integer, Square>();
			
			for (int j=0; j<x; j++){
				row.put(new Integer(j), new Square(i, j));
			}
			canvas.put(new Integer(i), row);
		}
	}
	
	public SquareCanvas(BufferedImage img){
		this.height = img.getHeight();
		this.width = img.getWidth();
		HashMap<Integer, Square> row;
		Square s;
		canvas = new HashMap<Integer, HashMap<Integer, Square>>();
		for (int i=0; i<height; i++){
			row = new HashMap<Integer, Square>();
			
			for (int j=0; j<width; j++){
				Color color = new Color(img.getRGB(j, i));
				s = new Square(color, i, j);
				row.put(new Integer(j), s);
			}
			canvas.put(new Integer(i), row);
		}
	}
	
	public HashMap<Integer, HashMap<Integer, Square>> getCanvas(){
		return canvas;
	}
	
	public Square findAndChange(int x, int y, Color c){
		Square s = find(x, y); //changing a copy???
		if (s==null){
			return null;
		}
		Square r = new Square(s);
		s.setColor(c);//////
		return r;
	}
	
	public Square find(int x, int y){
		if (canvas.get(y)!=null){
			return canvas.get(y).get(x);
		}
		return null;
	}
}
