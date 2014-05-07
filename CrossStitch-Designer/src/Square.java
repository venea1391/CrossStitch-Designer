import java.awt.*;


public class Square {
	public final static int EDGE = 21;
	private Color color;
	private int x, y;
	
	public Square(Color c){
		this(c, -1, -1);
	}
	
	public Square(int x, int y){
		this(Color.white, x, y);
	}
	
	public Square(Color c, int x, int y){
		this.color = c;
		this.x = x;
		this.y = y;
	}
	
	public void draw(Graphics g){
		//must have coordinates
		//coordinates per pixel/square
		g.setColor(Color.black);
		g.drawLine(x*EDGE, y*EDGE, EDGE+(x*EDGE), y*EDGE);
		g.drawLine(x*EDGE, y*EDGE, x*EDGE, EDGE+(y*EDGE));
		g.setColor(color);
		g.drawRect(x*EDGE+1, y*EDGE+1, EDGE-1, EDGE-1);
	}
	
}
