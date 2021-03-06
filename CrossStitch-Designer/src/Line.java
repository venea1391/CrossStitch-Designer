import java.awt.Color;
import java.awt.Point;

/**
 * Defines a line consisting of a start point, end point, and color.
 * 
 * @author Venea
 *
 */
public class Line {
	private Point p1, p2;
	private Color color;
	
	/**
	 * @param p1 Start point
	 * @param p2 End point
	 * @param c Color of line
	 */
	public Line(Point p1, Point p2, Color c){
		this.p1 = p1;
		this.p2 = p2;
		this.color = c;
	}
	
	public Point getStartPoint(){
		return p1;
	}
	
	public Point getEndPoint(){
		return p2;
	}
	
	public Color getColor(){
		return color;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((p1 == null) ? 0 : p1.hashCode());
		result = prime * result + ((p2 == null) ? 0 : p2.hashCode());
		return result;
	}

	/**
	 * Does not include color so that squares of different colors but occupying
	 * the same space are counted as the same.
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
		Line other = (Line) obj;
		if (p1 == null) {
			if (other.p1 != null)
				return false;
		} else if (!p1.equals(other.p1)&&!p1.equals(other.p2))
			return false;
		if (p2 == null) {
			if (other.p2 != null)
				return false;
		} else if (!p2.equals(other.p2)&&!p2.equals(other.p1))
			return false;
		return true;
	}


}
