import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


public class Exporter {
	public static void chooseExportType(JFrame frame){
		
	}
	
	public static void exportPixel(SquareCanvas sc, File file){
		BufferedImage image = new BufferedImage(Controller.getWidth(), 
				Controller.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		HashMap<Integer, Square> row = new HashMap<Integer, Square>();
		HashMap<Integer, HashMap<Integer, Square>>canvas = sc.getCanvas();
		
		Square s;
		for (int i=0; i<Controller.getHeight(); i++){
			row = canvas.get(i);
			for (int j=0; j<Controller.getWidth(); j++){
				s = row.get(j);  
				if (s.getColor()==null) {
					g.setColor(null);
				}
				else {
					g.setColor(s.getColor());
					g.fillRect(j, i, 1, 1);
				}
			}
				
		}
		
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void exportWithLines(SquareCanvas sc, ArrayList<Line> bsLines, File file){
		int size = 10;
		BufferedImage image = new BufferedImage(size*Controller.getWidth(), 
				size*Controller.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = image.createGraphics();
		HashMap<Integer, Square> row = new HashMap<Integer, Square>();
		HashMap<Integer, HashMap<Integer, Square>>canvas = sc.getCanvas();
		
		Square s;
		for (int i=0; i<Controller.getHeight(); i++){
			row = canvas.get(i);
			for (int j=0; j<Controller.getWidth(); j++){
				s = row.get(j);  
				if (s.getColor()==null) {
					g.setColor(null);
				}
				else {
					g.setColor(s.getColor());
					g.fillRect(j*size, i*size, size, size);
				}
				
			}
				
		}

    	g.setStroke(new BasicStroke(3));
		for (Line l : bsLines){
			g.setColor(l.getColor());
			g.drawLine((int) (l.getStartPoint().getX()*size), 
					(int) (l.getStartPoint().getY()*size), 
        			(int) (l.getEndPoint().getX()*size), 
        			(int) (l.getEndPoint().getY()*size));
		}
		
		//File outputfile = new File("pattern.png");
		try {
			ImageIO.write(image, "png", file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
}
