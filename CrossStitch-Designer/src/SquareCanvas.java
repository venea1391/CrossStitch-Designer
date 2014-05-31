import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * SquareCanvas is the model for the canvas of squares.
 * 
 * @author Venea
 *
 */
public class SquareCanvas {
	private int width, height;
	private HashMap<Integer, HashMap<Integer, Square>> canvas; //map of rows of squares
	
	/**
	 * Constructor
	 * @param x The width of the canvas
	 * @param y The height of the canvas
	 */
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
	
	/**
	 * Constructor
	 * @param img The imported image to create the canvas from.
	 * 			Each pixel of the image translates to a square in the canvas.
	 */
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
	
	/**
	 * Finds the square at the given coordinates and changes that square's 
	 * color to c.
	 * @param x X of the square
	 * @param y Y of the square
	 * @param c Color to change it to
	 * @return A copy of the unmodified Square
	 */
	public Square findAndChange(int x, int y, Color c){
		Square s = find(x, y);
		if (s==null){
			return null;
		}
		Square r = new Square(s);
		s.setColor(c);
		return r;
	}
	
	/**
	 * Find
	 * @param x X of the square
	 * @param y Y of the square
	 * @return The square at the given coordinates, or null if none exists.
	 */
	public Square find(int x, int y){
		if (canvas.get(y)!=null){
			return canvas.get(y).get(x);
		}
		return null;
	}
	
	/**
	 * Finds all squares of the given color
	 * @param c Color to find
	 * @return List of copies of the unmodified squares
	 */
	public ArrayList<Square> findAllOfColor(Color c){
		ArrayList<Square> sList = new ArrayList<Square>();
		HashMap<Integer, Square> row = new HashMap<Integer, Square>();
		Square s;
		//HashMap<Integer, HashMap<Integer, Square>>
		for (int i=0; i<height; i++){
			row = canvas.get(i);
			for (int j=0; j<width; j++){
				s = row.get(j);  //paint bucket has problem working on erased squares
				if (s.getColor()==null){
					if (c==null){
						sList.add(s);
					}
				}
				else if (s.getColor().equals(c)){
					sList.add(s);
				}
			}
			
		}
		return sList;
	}
	
	/**
	 * Recursive function that finds all contiguous squares of the given color.
	 * @param s The square to start from
	 * @param c The color to match
	 * @param olds List of old unmodified squares to avoid duplicates+
	 * @return List of old unmodified squares
	 */
	public ArrayList<Square> findAllContigOfColor(Square s, Color c, ArrayList<Square> olds){
		Square adj;
		if (!olds.contains(s)){  //is the equals ok for this?
			olds.add(s);
		}
		adj = find(s.getCol()+1, s.getRow());
		if (adj != null){
			if (adj.getColor()==null){
				if (c==null && !olds.contains(adj)){
					olds.add(adj);
					olds = findAllContigOfColor(adj, c, olds);
				}
			}
			else if (adj.getColor().equals(c) && !olds.contains(adj)){
				olds.add(adj);
				olds = findAllContigOfColor(adj, c, olds);
			}
		}
		adj = find(s.getCol()-1, s.getRow());
		if (adj != null){
			if (adj.getColor()==null){
				if (c==null && !olds.contains(adj)){
					olds.add(adj);
					olds = findAllContigOfColor(adj, c, olds);
				}
			}
			else if (adj.getColor().equals(c) && !olds.contains(adj)){
				olds.add(adj);
				olds = findAllContigOfColor(adj, c, olds);
			}
		}
		adj = find(s.getCol(), s.getRow()+1);
		if (adj != null){
			if (adj.getColor()==null){
				if (c==null && !olds.contains(adj)){
					olds.add(adj);
					olds = findAllContigOfColor(adj, c, olds);
				}
			}
			else if (adj.getColor().equals(c) && !olds.contains(adj)){
				olds.add(adj);
				olds = findAllContigOfColor(adj, c, olds);
			}
		}
		adj = find(s.getCol(), s.getRow()-1);
		if (adj != null){
			if (adj.getColor()==null){
				if (c==null && !olds.contains(adj)){
					olds.add(adj);
					olds = findAllContigOfColor(adj, c, olds);
				}
			}
			else if (adj.getColor().equals(c) && !olds.contains(adj)){
				olds.add(adj);
				olds = findAllContigOfColor(adj, c, olds);
			}
		}
		
		return olds;
	}
}
