import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

public class CanvasPanel extends JPanel {
	private ArrayList<Square> recentSquares = new ArrayList<Square>();
	private Stack<Square> paintStack = new Stack<Square>();
	private Stack<Square> eraseStack = new Stack<Square>();
	
	public CanvasPanel(){
		setSize(800, 600);
		
		addMouseListener(new MouseListener() {
	        @Override
	        public void mousePressed(MouseEvent e) {
	            System.out.println("sefjrgfv");
	            if ((Controller.getMode()==Controller.Mode.BACKSTITCH) ||
	            		(Controller.getMode()==Controller.Mode.ERASE && Controller.currentEraserMode==Controller.EMode.EBS)){
	            	//startPoint = new Point(approximate(e.getX()), approximate(e.getY()));
	            	System.out.println("canvas panel position: "+e.getX()+", "+e.getY());
	            }
	        }
	        @Override
	        public void mouseReleased(MouseEvent e) {
	            System.out.println("mouse released");
	        }
	        @Override
	        public void mouseClicked(MouseEvent e) {}
	        @Override
	        public void mouseExited(MouseEvent e) {}
	        @Override
	        public void mouseEntered(MouseEvent e) {}
	    });
	}
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(Controller.getWidth()*Square.EDGE, Controller.getHeight()*Square.EDGE);
    }


    public void paintComponent(Graphics g) {
    	super.paintComponent(g); 
    	System.out.println("trying to paint canvas");
    	SquareCanvas sc = Controller.getSquareCanvas();
    	if (sc!=null){
	    	HashMap<Integer, HashMap<Integer, Square>> canvas = sc.getCanvas();
	    	System.out.println("painting canvas panel");
	              
	        // Draw Text
	        //g.drawString("This is my custom Panel!",10,20);
	        if (sc!=null){
	        	for(int i=0; i<canvas.size(); i++){
	        		HashMap<Integer, Square> row = canvas.get(i);
	        		for(int j=0; j<row.size(); j++){
	        			Square s = row.get(j);
	        			s.draw(g);
	        		}
	        	}
	        }
    	}
    }  


	public int approximate(double d){
		int i = (int) (d/Square.EDGE);
		if (d%Square.EDGE > Square.EDGE/2){
			i++;
		}
		return i;
	}
    
    public void resetPaintStack(){
    	paintStack.clear();
    }
}

