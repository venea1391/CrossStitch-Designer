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
	
}
