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
			instance = new SquareCanvas();
			return instance;
		}
	}
	
	public static HashMap<Integer, HashMap<Integer, Square>> getCanvas(){
		return canvas;
	}
	
	public static SquareCanvas createSquareCanvas(int x, int y){
		instance = new SquareCanvas();
		Square s;
		HashMap<Integer, Square> row;
		canvas = new HashMap<Integer, HashMap<Integer, Square>>();
		for (int i=0; i<y; i++){
			row = new HashMap<Integer, Square>();
			
			for (int j=0; j<x; j++){
				row.put(new Integer(j), new Square(j, i));
			}
			canvas.put(new Integer(i), row);
		}
		return instance;
	}
}
