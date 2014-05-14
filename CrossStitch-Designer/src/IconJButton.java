import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

	
@SuppressWarnings("serial")
public class IconJButton extends JButton {
	public static final int IMAGE_SIZE = 32;
	
	private ImageIcon disabled, enabled, highlighted, pressed;
	public iconType type;
	
	public IconJButton(BufferedImage img, iconType type){
		this.type = type;
		this.disabled = new ImageIcon(img
    			.getSubimage(0, 0, IMAGE_SIZE, IMAGE_SIZE));
		this.enabled = new ImageIcon(img
    			.getSubimage(IMAGE_SIZE, 0, IMAGE_SIZE, IMAGE_SIZE));
		this.highlighted = new ImageIcon(img
    			.getSubimage(2*IMAGE_SIZE, 0, IMAGE_SIZE, IMAGE_SIZE));
		this.pressed = new ImageIcon(img
    			.getSubimage(3*IMAGE_SIZE, 0, IMAGE_SIZE, IMAGE_SIZE));
		setIcon(disabled);
		setPreferredSize(new Dimension(32, 32));
		
		addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
            	highlight();
            }
            public void mouseExited(MouseEvent evt) {
            	enable();
            }
            public void mousePressed(MouseEvent evt) {
            	press();
            }
            public void mouseReleased(MouseEvent evt) {
            	enable();
            }
        });
		
	}
	
	public void disable(){
		setIcon(disabled);
	}
	public void enable(){
		setIcon(enabled);
	}
	public void highlight(){
		setIcon(highlighted);
	}
	public void press(){
		setIcon(pressed);
	}

}
