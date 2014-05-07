import javax.swing.JPanel;
import javax.swing.BorderFactory;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.util.HashMap;

public class CanvasPanel extends JPanel {
	private SquareCanvas sc;
	
	public CanvasPanel(){
		//setSize(800, 600);
	}
	
	@Override
    public Dimension getPreferredSize() {
        return new Dimension(Controller.getWidth()*Square.EDGE, Controller.getHeight()*Square.EDGE);
    }


    public void paintComponent(Graphics g) {
    	HashMap<Integer, HashMap<Integer, Square>> sc = SquareCanvas.getCanvas();
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
}

