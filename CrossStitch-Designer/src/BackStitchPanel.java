import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;

import javax.swing.*;

@SuppressWarnings("serial")
public class BackStitchPanel extends JPanel {
	private Point startPoint, endPoint;
	private BufferedImage img;
	private Graphics2D big;
	private ArrayList<Line> lines = new ArrayList<Line>();
	
	public BackStitchPanel(){
		setOpaque(false);
		System.out.println("constructing bsPanel");
		setSize(800, 600);
		
		addMouseListener(new MouseListener() {
	        @Override
	        public void mousePressed(MouseEvent e) {
	            System.out.println("mouse pressed");
	            if (Controller.getBackstitchMode()){
	            	startPoint = new Point(approximate(e.getX()), approximate(e.getY()));
	            }
	        }
	        @Override
	        public void mouseReleased(MouseEvent e) {
	            System.out.println("mouse released");
	            if (Controller.getBackstitchMode()){
	            	endPoint = new Point(approximate(e.getX()), approximate(e.getY()));
	            	int i = Square.EDGE/5; //scale with squares?
	            	i = (i<2) ? 2 : i;
	            	big.setColor(Color.black);
	            	big.setStroke(new BasicStroke(i));
	            	big.drawLine((int) startPoint.getX()*Square.EDGE, 
	            			(int) startPoint.getY()*Square.EDGE, 
	            			(int) endPoint.getX()*Square.EDGE, 
	            			(int) endPoint.getY()*Square.EDGE);
	            	lines.add(new Line(startPoint, endPoint));
	            	repaint();
	            }
	        }
	        @Override
	        public void mouseClicked(MouseEvent e) {
	            System.out.println("mouse clicked");
	            if (Controller.getBackstitchMode()){
	            	//get coords, draw dot
	            	//unnecessary
	            }
	        }
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
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("painting backstitch layer");
        // Draw Text
        g.setColor(new Color(0, 255, 0));
        g.fillRect(0, 0, 50, 50);
        //fix size of img
        Graphics2D g2d = (Graphics2D) g;
		if (img!=null) {
			System.out.println(img.getWidth());
			g2d.drawImage(img, 0, 0, null);
		}
    } 
	
	public int approximate(double d){
		int i = (int) (d/Square.EDGE);
		if (d%Square.EDGE > Square.EDGE/2){
			i++;
		}
		return i;
	}
	
	public void createBI(){
		img = new BufferedImage(Controller.getWidth()*Square.EDGE, 
				Controller.getHeight()*Square.EDGE, BufferedImage.TYPE_INT_ARGB);
		big = img.createGraphics();
	}
	public void scaleBI(){
		BufferedImage newImg = new BufferedImage(Controller.getWidth()*Square.EDGE, 
				Controller.getHeight()*Square.EDGE, BufferedImage.TYPE_INT_ARGB);
		Graphics2D grr = newImg.createGraphics();
		int i = Square.EDGE/5; //scale with squares
    	i = (i<2) ? 2 : i;
		grr.setColor(Color.black);
    	grr.setStroke(new BasicStroke(i));
		for (Line l : lines){
			grr.drawLine((int) (l.getStartPoint().getX()*Square.EDGE), 
					(int) (l.getStartPoint().getY()*Square.EDGE), 
        			(int) (l.getEndPoint().getX()*Square.EDGE), 
        			(int) (l.getEndPoint().getY()*Square.EDGE));
		}
		img = newImg;
		big = grr;
	}
}
