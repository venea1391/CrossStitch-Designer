import javax.swing.JPanel;

import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;


/**
 * CanvasPanel is in charge of drawing the SquareCanvas.
 * Cannot detect mouse events because it is hidden by the BackStitchPanel.
 * 
 * @author Venea
 */
@SuppressWarnings("serial")
public class CanvasPanel extends JPanel {
	
	public CanvasPanel(){
		setSize(800, 600);
		
		/*addMouseListener(new MouseListener() {
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
	    });*/
	}
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(Controller.getWidth()*Square.EDGE, Controller.getHeight()*Square.EDGE);
    }


    /**
     * Obtains the SquareCanvas from Controller, loops through all squares
     * and draws them to the panel.
     * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
     */
    public void paintComponent(Graphics g) {
    	super.paintComponent(g); 
    	SquareCanvas sc = Controller.getSquareCanvas();
    	if (sc!=null){
	    	HashMap<Integer, HashMap<Integer, Square>> canvas = sc.getCanvas();

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
    
}

