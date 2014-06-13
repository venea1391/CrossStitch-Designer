
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;


public class PatternMaker {
	private BufferedImage patternShapesB, patternShapesW;
	private int numOfShapes;
	private final int SHAPE_SIZE = 21;
	private ArrayList<BufferedImage> whiteShapes = new ArrayList<BufferedImage>();
	private ArrayList<BufferedImage> blackShapes = new ArrayList<BufferedImage>();

	public PatternMaker(){
		try {
			patternShapesB = ImageIO.read(new File("icons/symbols-clear.png"));
			patternShapesW = ImageIO.read(new File("icons/symbols-white-clear.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
		numOfShapes = (patternShapesW.getHeight() / SHAPE_SIZE);
		for (int i=0; i<numOfShapes; i++){
			whiteShapes.add(patternShapesW.getSubimage(0, SHAPE_SIZE*i, SHAPE_SIZE, SHAPE_SIZE));
		}
		numOfShapes = (patternShapesB.getHeight() / SHAPE_SIZE);
		for (int i=0; i<numOfShapes; i++){
			blackShapes.add(patternShapesB.getSubimage(0, SHAPE_SIZE*i, SHAPE_SIZE, SHAPE_SIZE));
		}
		
		
		Collections.shuffle(blackShapes, new Random(System.currentTimeMillis()));
		Collections.shuffle(whiteShapes, new Random(System.currentTimeMillis()));

	}
	
	public BufferedImage createPattern(SquareCanvas sc, ArrayList<Line> bsLines, File file){
		BufferedImage pattern = new BufferedImage(SHAPE_SIZE*Controller.getWidth()+150, 
				SHAPE_SIZE*Controller.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = pattern.createGraphics();
		BufferedImage whiteShape = makeWhiteShape();
		BufferedImage nullShape = makeNullShape();
		BufferedImage blackShape = makeBlackShape(); 
		HashMap<Color, BufferedImage> patternMap = new HashMap<Color, BufferedImage>();
		HashMap<Integer, Square> row = new HashMap<Integer, Square>();
		HashMap<Integer, HashMap<Integer, Square>>canvas = sc.getCanvas();
		ArrayList<Color> colorList = new ArrayList<Color>();
		colorList.add(Color.black);
		colorList.add(Color.white);
		
		patternMap.put(Color.black, blackShape);
		patternMap.put(Color.white, whiteShape);
		Square s;
		Color c;
		BufferedImage thisShape;
		//HashMap<Integer, HashMap<Integer, Square>>
		for (int i=0; i<Controller.getHeight(); i++){
			row = canvas.get(i);
			for (int j=0; j<Controller.getWidth(); j++){
				s = row.get(j);  
				if (s.getColor()==null) {
					thisShape = nullShape;
					g.setColor(null);
				}
				else {
					if (patternMap.containsKey(s.getColor())){
						thisShape = patternMap.get(s.getColor());
					}
					else {
						c = s.getColor();
						double avg = (c.getBlue()+c.getRed()+c.getGreen())/3.0;
						if (avg < 128){ //dark color
							thisShape = whiteShapes.get(0);
							whiteShapes.remove(0);
						}
						else { //light color
							thisShape = blackShapes.get(0);
							blackShapes.remove(0);
						}
						
						patternMap.put(s.getColor(), thisShape);
						colorList.add(s.getColor());
					}
						//draw
					
					g.setColor(s.getColor());
					g.fillRect(j*SHAPE_SIZE, i*SHAPE_SIZE, SHAPE_SIZE, SHAPE_SIZE);
				}
				
				g.drawImage(thisShape, j*SHAPE_SIZE, i*SHAPE_SIZE, null);
				//g.setColor(null);
				
			}
				
		}
		
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(3));
		for (int k=0; k<(pattern.getWidth()-(10*SHAPE_SIZE)); k=k+(10*SHAPE_SIZE)){
			g.drawLine(k, 0, k, pattern.getHeight());
		}
		for (int l=0; l<(pattern.getHeight()-(10*SHAPE_SIZE)); l=l+(10*SHAPE_SIZE)){
			g.drawLine(0, l, pattern.getWidth(), l);
		}


    	g.setStroke(new BasicStroke(4));
		for (Line l : bsLines){
			g.setColor(l.getColor());
			g.drawLine((int) (l.getStartPoint().getX()*SHAPE_SIZE), 
					(int) (l.getStartPoint().getY()*SHAPE_SIZE), 
        			(int) (l.getEndPoint().getX()*SHAPE_SIZE), 
        			(int) (l.getEndPoint().getY()*SHAPE_SIZE));
		}
		//draw lines
		
		
		BufferedImage legend = generateLegend(patternMap, colorList);
		g.drawImage(legend, SHAPE_SIZE*Controller.getWidth(), 10, null);
		
		//File outputfile = new File("pattern.png");
		try {
			ImageIO.write(pattern, "png", file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return pattern;
	}
	
	public BufferedImage makeWhiteShape(){
		BufferedImage w = new BufferedImage(SHAPE_SIZE, SHAPE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = w.createGraphics();
		g.setColor(Color.black);
		g.drawLine(0, 0, SHAPE_SIZE, 0);
		g.drawLine(0, 0, 0, SHAPE_SIZE);
		g.setColor(Color.white);
		g.fillRect(1, 1, SHAPE_SIZE-1, SHAPE_SIZE-1);
		return w;
	}
	
	public BufferedImage makeNullShape(){
		BufferedImage w = new BufferedImage(SHAPE_SIZE, SHAPE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = w.createGraphics();
		g.setColor(Color.black);
		g.drawLine(0, 0, SHAPE_SIZE, 0);
		g.drawLine(0, 0, 0, SHAPE_SIZE);
		return w;
	}
	
	public BufferedImage makeBlackShape(){
		BufferedImage w = new BufferedImage(SHAPE_SIZE, SHAPE_SIZE, BufferedImage.TYPE_INT_ARGB);
		Graphics g = w.createGraphics();
		g.setColor(Color.black);
		g.fillRect(0, 0, SHAPE_SIZE, SHAPE_SIZE);
		return w;
	}
	
	public BufferedImage generateLegend(HashMap<Color, BufferedImage> symbolMap, ArrayList<Color> colorList){
		BufferedImage legend = new BufferedImage(150, 
				symbolMap.size()*35, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = legend.createGraphics();
		g.setColor(Color.black);
		g.drawString("Symbol", 10, 10);
		g.drawString("Color", 60, 10);
		g.drawString("DMC", 100, 10);
		
		BufferedImage img;
		for (int i=0; i<colorList.size(); i++){
			g.setColor(colorList.get(i));
			img = symbolMap.get(colorList.get(i));
			g.fillRect(10, 20+(i*30), 21, 21);
			g.drawImage(img, 10, 20+(i*30), null);
			
			g.fillRect(60, 20+(i*30), 21, 21);
			g.setColor(Color.black);
			g.drawString("0000", 100, 40+(i*30)); //get number later
			
		}
		
		return legend;
		/*File outputfile = new File("legend.png");
		try {
			ImageIO.write(legend, "png", outputfile);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
