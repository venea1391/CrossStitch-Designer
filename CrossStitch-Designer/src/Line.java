import java.awt.Point;

public class Line {
	private Point p1, p2;
	public Line(Point p1, Point p2){
		this.p1 = p1;
		this.p2 = p2;
	}
	
	public Point getStartPoint(){
		return p1;
	}
	
	public Point getEndPoint(){
		return p2;
	}

}
