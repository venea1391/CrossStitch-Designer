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
	private SquareCanvas sc;
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
	            		(Controller.getMode()==Controller.Mode.ERASE_SQ)){
	            	//startPoint = new Point(approximate(e.getX()), approximate(e.getY()));
	            	System.out.println("canvas panel position: "+e.getX()+", "+e.getY());
	            }
	        }
	        @Override
	        public void mouseReleased(MouseEvent e) {
	            System.out.println("mouse released");
	            /*if ((Controller.getMode()==Controller.Mode.BACKSTITCH) ||
	            	(Controller.getMode()==Controller.Mode.ERASE_SQ)){
	            	endPoint = new Point(approximate(e.getX()), approximate(e.getY()));
	            	
	            	
	            	if (!segments.isEmpty()){
	            		if (Controller.getMode()==Controller.Mode.BACKSTITCH){
	            			for (Line l : segments){
	            				lines.add(l);
	            				lineStack.add(l); //for undo operations
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
	            		else if (Controller.getMode()==Controller.Mode.ERASE){
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
	            				eraseStack.add(l); //for undo operations
	            			}
	            		}
	            	}
	            }*/
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
    	System.out.println("trying to paint canvas");
    	HashMap<Integer, HashMap<Integer, Square>> sc = SquareCanvas.getCanvas();
    	System.out.println("painting canvas panel");
        super.paintComponent(g);       
        // Draw Text
        //g.drawString("This is my custom Panel!",10,20);
        if (sc!=null){
        	for(int i=0; i<sc.size(); i++){
        		HashMap<Integer, Square> row = sc.get(i);
        		for(int j=0; j<row.size(); j++){
        			Square s = row.get(j);
        			s.draw(g);
        		}
        	}
        }
    }  
    
    public void updateCanvas(){
    	if (this.sc==null){
    		this.sc = sc;
    		//paintComponent(g);
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

