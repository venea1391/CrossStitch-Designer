import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.image.PixelGrabber;
import java.util.ArrayList;
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
		Square s = find(x, y);
		if (s==null){
			return null;
		}
		Square r = new Square(s);
		s.setColor(c);
		return r;
	}
	
	public Square find(int x, int y){
		if (canvas.get(y)!=null){
			return canvas.get(y).get(x);
		}
		return null;
	}
	
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
