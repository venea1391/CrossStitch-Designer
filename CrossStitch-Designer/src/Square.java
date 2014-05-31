import java.awt.*;


/**
 * Defines a cross-stitch square on the canvas.
 * Initial size is 21x21, with a line on 
 * @author Venea
 *
 */
public class Square {
	public static int EDGE = 20;
	private Color color;
	private int col, row;
	
	/**
	 * Without a x and y specified, the square is given position -1, -1
	 * 
	 * @param c Color of the square
	 */
	public Square(Color c){
		this(c, -1, -1);
	}
	
	/**
	 * If color is not specified the color is set to null
	 * 
	 * @param row The row (y) of the square
	 * @param col The col (x) of the square
	 */
	public Square(int row, int col){
		this(null, row, col);
	}

	public Square(Color c, int row, int col){
		this.color = c;
		this.col = col;
		this.row = row;
	}
	
	/** Copy constructor */
	public Square(Square source) {
		this.color = source.color;
		this.col = source.col;
		this.row = source.row;
    }
	
	/**
	 * Draws the square: a line on top and on the left side (to create grid)
	 * and the color is an edge-1 by edge-1 square.
	 * If the row or column is divisible by 10, a 3px wide line is drawn for the grid
	 * to make the 10s stand out.
	 * 
	 * @param g
	 */
	public void draw(Graphics g){
		g.setColor(Color.black);
		
		if (row%10==0) {
			g.fillRect((col*EDGE)-1, (row*EDGE)-1, EDGE+1, 3);
		}
		else{
			g.drawLine(col*EDGE, row*EDGE, EDGE+(col*EDGE), row*EDGE); //top line
		}
		
		if (col%10==0){
			g.fillRect((col*EDGE)-1, (row*EDGE)-1, 3, EDGE+1);
		}
		else {
			g.drawLine(col*EDGE, row*EDGE, col*EDGE, EDGE+(row*EDGE)); //left line
		}
		
		if (color!=null){
			g.setColor(color);
			g.fillRect(col*EDGE+1, row*EDGE+1, EDGE-1, EDGE-1);
		}
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	public Color getColor() {
		return color;
	}
	
	public int getCol() {
		return col;
	}

	public void setCol(int col) {
		this.col = col;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + row;
		return result;
	}

	/** 
	 * Does not include color so that squares in the same space are treated the same.
	 * Yeah equal opportunity >.>
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Square other = (Square) obj;
		if (col != other.col)
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
