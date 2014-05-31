import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Stack;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionAdapter;
import java.awt.image.BufferedImage;

import javax.swing.*;

/**
 * The top "layer" of the design area. All mouse events are detected here.
 *  
 * @author Venea
 */
@SuppressWarnings("serial")
public class BackStitchPanel extends JPanel {
	private Point startPoint, endPoint;
	private BufferedImage img;
	private Graphics2D big;
	private ArrayList<Line> lines = new ArrayList<Line>();
	private Stack<Line> lineStack = new Stack<Line>();  //stack should be reset every time
								// we enter backstitch mode
	private Stack<Line> eraseStack = new Stack<Line>();
	private Stack<Square> squareStack = new Stack<Square>();
	private Stack<ArrayList<Square>> pbStack = new Stack<ArrayList<Square>>();
	
	private Action undoAction = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent ae) {  undo();  }
    };
    private Action zoomInAction = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent ae) {  
        	Controller.zoomIn();  }
    };
    private Action zoomOutAction = new AbstractAction()
    {
        @Override
        public void actionPerformed(ActionEvent ae) {  
        	Controller.zoomOut();  }
    };
    
	/**
	 * Constructs a new BackStitchPanel. 
	 * Creates key bindings for undo (z), zoom in (=), and zoom out (-)
	 * MouseListener detects all mouse events and performs actions depending on 
	 * the current/new mode.
	 */
	public BackStitchPanel(){
		setOpaque(false);
		//System.out.println("constructing bsPanel");
		setSize(800, 600);
		
		mouseMovedEventHandler handler = new mouseMovedEventHandler();
		//this.addMouseListener(handler); 
		this.addMouseMotionListener(handler);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("Z"),
                "undo");
		getActionMap().put("undo",
                 undoAction);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_EQUALS),
                "zoomin");
		getActionMap().put("zoomin",
                 zoomInAction);
		getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke((char) KeyEvent.VK_MINUS),
                "zoomout");
		getActionMap().put("zoomout",
                 zoomOutAction);
		addMouseListener(new MouseListener() {
	        @Override
	        public void mousePressed(MouseEvent e) {
	            
	            if ((Controller.getMode()==Controller.Mode.BACKSTITCH) ||
	            		(Controller.getMode()==Controller.Mode.ERASE && 
	            		Controller.currentEraserMode==Controller.EMode.EBS)){
	            	startPoint = new Point(approximate(e.getX()), approximate(e.getY()));
	            }
	            else if ((Controller.getMode()==Controller.Mode.PAINT) ||
		            	(Controller.getMode()==Controller.Mode.ERASE && 
		            	Controller.currentEraserMode==Controller.EMode.ESQ)){
	            	Square oldSq = Controller.paint(paintApproximate(e.getX()), 
	            			paintApproximate(e.getY()));
	            	if (!squareStack.contains(oldSq)) {squareStack.push(oldSq);}
	            }
	        }
	        @Override
	        public void mouseReleased(MouseEvent e) {
	            
	            if ((Controller.getMode()==Controller.Mode.BACKSTITCH) ||
	            	(Controller.getMode()==Controller.Mode.ERASE && 
	            	Controller.currentEraserMode==Controller.EMode.EBS)){
	            	endPoint = new Point(approximate(e.getX()), approximate(e.getY()));
	            	
	            	ArrayList<Line> segments = breakupLine(startPoint, endPoint);
	            	if (!segments.isEmpty()){
	            		if (Controller.getMode()==Controller.Mode.BACKSTITCH){
	            			for (Line l : segments){
	            				lines.add(l);
	            				lineStack.push(l); //for undo operations
	            			}
	            			int i = Square.EDGE/5; //scale with squares?
		            		i = (i<2) ? 2 : i;
		            		big.setColor(Controller.currentColor);
		            		big.setStroke(new BasicStroke(i));
		            		big.drawLine((int) startPoint.getX()*Square.EDGE, 
		            			(int) startPoint.getY()*Square.EDGE, 
		            			(int) endPoint.getX()*Square.EDGE, 
		            			(int) endPoint.getY()*Square.EDGE);
		            		repaint();
	            		}
	            		else if (Controller.getMode()==Controller.Mode.ERASE && Controller.currentEraserMode==Controller.EMode.EBS){
	            			//TODO I would like to be able to erase as I drag the mouse,
	            			//not one line (no bends) at a time
	            			ArrayList<Line> deleted = new ArrayList<Line>();
	            			//find corresponding lines in "lines" list
	            			for (Line l : segments){
	            				int index = lines.indexOf(l);
	            				if (index!=-1){
	            					Line lineToErase = lines.get(index);
	            					deleted.add(lineToErase);
	            					lines.remove(index);
	            				}
	            			}
	            			//save those lines in other stack "eraseStack"
	            			for (Line l : deleted){
	            				eraseStack.push(l); //for undo operations TODO
	            			}
	            			//delete lines from "lines"
	            			scaleBI();//force it to recognize that a line is missing
	            			repaint();
	            		}
	            	}
	            }
	            else if ((Controller.getMode()==Controller.Mode.PAINT) ||
		            	(Controller.getMode()==Controller.Mode.ERASE && 
		            	Controller.currentEraserMode==Controller.EMode.ESQ)){
	            	Square oldSq = Controller.paint(paintApproximate(e.getX()),
	            			paintApproximate(e.getY()));
	            	if (!squareStack.contains(oldSq)) {squareStack.push(oldSq);}
		        }
	            else if (Controller.getMode()==Controller.Mode.PBUCKET){
	            	ArrayList<Square> oldSquares = Controller.paintBucket(paintApproximate(e.getX()),
	            			paintApproximate(e.getY()));
	            	pbStack.push(oldSquares);
		        }
	            else if (Controller.getMode()==Controller.Mode.EYEDROPPER){
	            	Controller.useEyedropper(paintApproximate(e.getX()),
	            			paintApproximate(e.getY()));

		        }
	        }
	        @Override
	        public void mouseClicked(MouseEvent e) {}
	        @Override
	        public void mouseExited(MouseEvent e) {}
	        @Override
	        public void mouseEntered(MouseEvent e) {}
	    });
		
	}

	
    public Dimension getPreferredSize() {
        return new Dimension(Controller.getWidth()*Square.EDGE, 
        		Controller.getHeight()*Square.EDGE);
    }
    
    
	/**
	 * Draws the BufferedImage that contains the backstitch lines.
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //System.out.println("painting backstitch layer");
        Graphics2D g2d = (Graphics2D) g;
		if (img!=null) {
			
			g2d.drawImage(img, 0, 0, null);
		}
    } 
	
	/**
	 * @param d The value of the mouse's x or y position
	 * @return The closest grid line, rounded up or down
	 */
	public int approximate(double d){
		int i = (int) (d/Square.EDGE);
		if (d%Square.EDGE > Square.EDGE/2){
			i++;
		}
		return i;
	}
	
	/**
	 * @param d The value of the mouse's x or y position
	 * @return The x or y of the Square containing d
	 */
	public int paintApproximate(double d){
		return (int) (d/Square.EDGE);
	}
	
	/**
	 * Creates the Buffered Image.
	 */
	public void createBI(){
		img = new BufferedImage(Controller.getWidth()*Square.EDGE, 
				Controller.getHeight()*Square.EDGE, BufferedImage.TYPE_INT_ARGB);
		big = img.createGraphics();
	}
	
	/**
	 * Updates the Buffered Image 'img' based on the Square.EDGE value
	 */
	public void scaleBI(){
		BufferedImage newImg = new BufferedImage(Controller.getWidth()*Square.EDGE, 
				Controller.getHeight()*Square.EDGE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D grr = newImg.createGraphics();
		int i = Square.EDGE/5; //scale with squares
    	i = (i<2) ? 2 : i;
    	grr.setStroke(new BasicStroke(i));
		for (Line l : lines){
			grr.setColor(l.getColor());
			grr.drawLine((int) (l.getStartPoint().getX()*Square.EDGE), 
					(int) (l.getStartPoint().getY()*Square.EDGE), 
        			(int) (l.getEndPoint().getX()*Square.EDGE), 
        			(int) (l.getEndPoint().getY()*Square.EDGE));
		}
		img = newImg;
		big = grr;
	}
	
	/**
	 * To be able to store, scale, and modify the backstitch lines, they must be
	 * broken up into their smallest units.
	 * Options: 
	 * 	Point (0, 0) to (0, 0)
	 * 	Horizontal (0, 0) to (1, 0)
	 * 	Vertical (0, 0) to (0, 1)
	 * 	1x1 Diagonal (0, 0) to (1, 1)
	 * 	1x2 Diagonal (0, 0) to (1, 2)
	 *  2x1 Diagonal (0, 0) to (2, 1)
	 *  There are 17 possibilities ( (0,0)to(1,1) is distinct from (1,1)to(0,0) )
	 *  All angles are accounted for in the diagonals.
	 *  
	 * @param start The starting point of the entire line
	 * @param end The ending point of the entire line
	 * @return A list of all the line segments that make up the line from start to end
	 */
	public ArrayList<Line> breakupLine(Point start, Point end){
		ArrayList<Line> segments = new ArrayList<Line>();
		int spanX, spanY, x1, y1, x2, y2;
		x1 = (int) start.getX();
		y1 = (int) start.getY();
		x2 = (int) end.getX();
		y2 = (int) end.getY();
		Color c = Controller.currentColor;
		//point
		if (x1==x2 && y1==y2){ 
			segments.add(new Line(start, end, c));
		}
		//straight horizontal
		else if (y1==y2){ 
			spanX = x1 - x2;
			int x = x1;
			while (spanX!=0){
				if (spanX > 0){
					segments.add(new Line(new Point(x, y1), 
							new Point(x-1, y1), c));
					x--;
					spanX--;
				}
				else {//span X < 0
					segments.add(new Line(new Point(x, y1), 
							new Point(x+1, y1), c));
					x++;
					spanX++;
				}
			}
		}
		//straight vertical
		else if (x1==x2){ 
			spanY = y1 - y2;
			int y = y1;
			while (spanY!=0){
				if (spanY > 0){
					segments.add(new Line(new Point(x1, y), 
							new Point(x1, y-1), c));
					y--;
					spanY--;
				}
				else {//span X < 0
					segments.add(new Line(new Point(x1, y), 
							new Point(x1, y+1), c));
					y++;
					spanY++;
				}
			}
		}
		//1:1 diagonal
		else if (Math.abs(x1-x2)==Math.abs(y1-y2)){
			spanX = x1-x2;
			spanY = y1-y2;
			int y = y1;
			int x = x1;
			while ((spanX != 0)&&(spanY != 0)){
				if ((spanX > 0)&&(spanY > 0)){
					segments.add(new Line(new Point(x-1, y-1), 
							new Point(x, y), c));
					y--;
					spanY--;
					x--;
					spanX--;
				}
				else if ((spanX < 0)&&(spanY < 0)){
					segments.add(new Line(new Point(x, y), 
							new Point(x+1, y+1), c));
					y++;
					spanY++;
					x++;
					spanX++;
				}
				else if ((spanX > 0)&&(spanY < 0)){
					segments.add(new Line(new Point(x, y), 
							new Point(x-1, y+1), c));
					y++;
					spanY++;
					x--;
					spanX--;
				}
				else {  // if ((spanX < 0)&&(spanY > 0)){
					segments.add(new Line(new Point(x, y), 
							new Point(x+1, y-1), c));
					y--;
					spanY--;
					x++;
					spanX++;
				}

			}
		}
		//tall diagonal, 1x:2y
		else if ((2*Math.abs(x1-x2))==(Math.abs(y1-y2))){
			spanX = x1-x2;
			spanY = y1-y2;
			int y = y1;
			int x = x1;
			while ((spanX != 0)&&(spanY != 0)){
				if ((spanX > 0)&&(spanY > 0)){
					segments.add(new Line(new Point(x-1, y-2), 
							new Point(x, y), c));
					y = y-2;
					spanY = spanY-2;
					x--;
					spanX--;
				}
				else if ((spanX < 0)&&(spanY < 0)){
					segments.add(new Line(new Point(x, y), 
							new Point(x+1, y+2), c));
					y = y+2;
					spanY = spanY+2;
					x++;
					spanX++;
				}
				else if ((spanX > 0)&&(spanY < 0)){
					segments.add(new Line(new Point(x, y), 
							new Point(x-1, y+2), c));
					y = y+2;
					spanY = spanY+2;
					x--;
					spanX--;
				}
				else {  // if ((spanX < 0)&&(spanY > 0)){
					segments.add(new Line(new Point(x, y), 
							new Point(x+1, y-2), c));
					y = y-2;
					spanY = spanY-2;
					x++;
					spanX++;
				}

			}
		}
		//wide diagonal, 2x:1y
		else if (Math.abs(x1-x2)==(2*Math.abs(y1-y2))){
			spanX = x1-x2;
			spanY = y1-y2;
			int y = y1;
			int x = x1;
			while ((spanX != 0)&&(spanY != 0)){
				if ((spanX > 0)&&(spanY > 0)){
					segments.add(new Line(new Point(x-2, y-1), 
							new Point(x, y), c));
					y--;
					spanY--;
					x = x-2;
					spanX = spanX-2;
				}
				else if ((spanX < 0)&&(spanY < 0)){
					segments.add(new Line(new Point(x, y), 
							new Point(x+2, y+1), c));
					y++;
					spanY++;
					x = x+2;
					spanX = spanX+2;
				}
				else if ((spanX > 0)&&(spanY < 0)){
					segments.add(new Line(new Point(x, y), 
							new Point(x-2, y+1), c));
					y++;
					spanY++;
					x = x-2;
					spanX = spanX-2;
				}
				else {  // if ((spanX < 0)&&(spanY > 0)){
					segments.add(new Line(new Point(x, y), 
							new Point(x+2, y-1), c));
					y--;
					spanY--;
					x = x+2;
					spanX = spanX+2;
				}

			}
		}
		//If a line does not match any of the above cases, it is not recognized
		//and ignored.
		else {
			System.out.println("not a recognized line");
			return segments;
		}
		
		return segments;
	}

	public void resetLineStack(){
		lineStack.clear();
	}
	public void resetEraseStack(){
		eraseStack.clear();
	}
	public void resetSquareStack(){
		squareStack.clear();
	}
	public void resetPBStack(){
		pbStack.clear();
	}
	
	/**
	 * Depending on mode, undo undos the last action. 
	 * Backstitch: the last segment of the last full line
	 * Paint: the last square changed
	 * Erase: not done
	 * Paint Bucket: the entire paint bucket action, not just square by square
	 */
	public void undo(){
		
		if (Controller.getMode() == Controller.Mode.BACKSTITCH){
			if (lineStack.size()==0){
				return;
			}
			Line l = lineStack.peek();
			ArrayList<Line> deleted = new ArrayList<Line>();
			//find corresponding line in "lines" list
			int index = lines.indexOf(l);
			if (index!=-1){
				Line lineToErase = lines.get(index);
				deleted.add(lineToErase);
				lines.remove(index);
				scaleBI();//force it to recognize that a line is missing
				repaint();
				lineStack.pop();
			}
			
		}
		else if (Controller.getMode() == Controller.Mode.PAINT){
			if (squareStack.size()==0){
				return;
			}
			Square s = squareStack.peek();
			Controller.paint(s.getCol(), s.getRow(), s.getColor());
			//scaleBI();//force it to recognize that a line is missing
			repaint();
			squareStack.pop();
		}
		else if (Controller.getMode() == Controller.Mode.ERASE){
			//TODO ?
		}
		else if (Controller.getMode() == Controller.Mode.PBUCKET){
			if (pbStack.size()==0){
				return;
			}
			ArrayList<Square> sList = pbStack.peek();
			for (Square s : sList){
				Controller.paint(s.getCol(), s.getRow(), s.getColor());
			}
			repaint();
			pbStack.pop();
		}
	}
	
	
	/**
	 * Inner class to handle the mouseDragged event to change squares as the mouse moves
	 * instead of upon release.
	 *
	 */
	class mouseMovedEventHandler extends MouseMotionAdapter {           
	    @Override
	    public void mouseDragged(MouseEvent e)
	    {
	        //System.out.println(String.format("MouseDragged via MouseMotionAdapter / X,Y : %s,%s ", e.getX(), e.getY()));
	        if ((Controller.getMode()==Controller.Mode.PAINT) ||
	            	(Controller.getMode()==Controller.Mode.ERASE && Controller.currentEraserMode==Controller.EMode.ESQ)){
            	Square oldSq = Controller.paint(paintApproximate(e.getX()), paintApproximate(e.getY()));
            	
            	if (!squareStack.contains(oldSq)) {squareStack.push(oldSq);}
	        }
	    }
	  }
}
