import java.awt.*;


public class Square {
	public static int EDGE = 20;
	private Color color;
	private int col, row;
	
	public Square(Color c){
		this(c, -1, -1);
	}
	
	public Square(int row, int col){
		this(null, row, col);
	}

	public Square(Color c, int row, int col){
		this.color = c;
		this.col = col;
		this.row = row;
	}
	
	public void draw(Graphics g){
		//must have coordinates
		//coordinates per pixel/square
		g.setColor(Color.black);
		g.drawLine(col*EDGE, row*EDGE, EDGE+(col*EDGE), row*EDGE);
		g.drawLine(col*EDGE, row*EDGE, col*EDGE, EDGE+(row*EDGE));
		if (color!=null){
			g.setColor(color);
			g.fillRect(col*EDGE+1, row*EDGE+1, EDGE-1, EDGE-1);
		}
	}
	
	public void setColor(Color c){
		color = c;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + col;
		result = prime * result + ((color == null) ? 0 : color.hashCode());
		result = prime * result + row;
		return result;
	}

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
		if (color == null) {
			if (other.color != null)
				return false;
		} else if (!color.equals(other.color))
			return false;
		if (row != other.row)
			return false;
		return true;
	}
}
