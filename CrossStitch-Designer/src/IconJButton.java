import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.*;

	
/**
 * Specifies how an action icon works in the toolbar.
 * 
 * @author Venea
 *
 */
@SuppressWarnings("serial")
public class IconJButton extends JButton {
	public static final int IMAGE_SIZE = 32;
	
	private ImageIcon disabled, enabled, highlighted, selected;
	public iconType type;
	private enum statusType {DISABLED, ENABLED, HIGHLIGHTED, SELECTED};
	private statusType status;
	
	/**
	 * Constructs a button initially set to disabled, defines actions based
	 * on mouse events.
	 * 
	 * @param img BufferedImage of the 4 different states of the icon
	 * @param type The iconType of the image
	 */
	public IconJButton(BufferedImage img, iconType type){
		this.type = type;
		this.disabled = new ImageIcon(img
    			.getSubimage(0, 0, IMAGE_SIZE, IMAGE_SIZE));
		this.enabled = new ImageIcon(img
    			.getSubimage(IMAGE_SIZE, 0, IMAGE_SIZE, IMAGE_SIZE));
		this.highlighted = new ImageIcon(img
    			.getSubimage(2*IMAGE_SIZE, 0, IMAGE_SIZE, IMAGE_SIZE));
		this.selected = new ImageIcon(img
    			.getSubimage(3*IMAGE_SIZE, 0, IMAGE_SIZE, IMAGE_SIZE));
		setIcon(disabled);
		setPreferredSize(new Dimension(32, 32));
		
		addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
            	highlight();
            }
            public void mouseExited(MouseEvent evt) {
            	if (status==statusType.DISABLED){
            		setEnabled(false);
            	}
            	else if(status==statusType.ENABLED){
            		enableImage();
            	}
            	else if(status==statusType.SELECTED){
            		select();
            	}
            	//enableImage();
            }
            public void mousePressed(MouseEvent evt) {
            	select();
            }
            public void mouseReleased(MouseEvent evt) {
            	if (Controller.changedMode==true){
            		if (status==statusType.DISABLED){
            			setEnabled(false);
                	}
                	else if(status==statusType.ENABLED){
                		enableImage();
                	}
                	else if(status==statusType.SELECTED){
                		select();
                	}
            	}
            	else {
	            	if (status==statusType.DISABLED){
	            		setEnabled(false);
	            	}
	            	else {
	            		enableImage();
	            	}
            	}
            }
        });
		
	}
	

	/**
	 * Updates the icon image, sets status to enabled
	 */
	public void enableImage(){
		setIcon(enabled);
		status = statusType.ENABLED;
	}
	
	/**
	 * Updates the icon image, sets status to enabled or disabled.
	 * @see javax.swing.AbstractButton#setEnabled(boolean)
	 */
	public void setEnabled(boolean b){
		if (b){
			setIcon(enabled);
			status = statusType.ENABLED;
		}
		else {
			setIcon(disabled);
			status = statusType.DISABLED;
		}
	}
	
	
	/**
	 * Updates image of icon.
	 */
	public void highlight(){
		setIcon(highlighted);
	}
	
	/**
	 * Updates image of icon and changes status to selected.
	 */
	public void select(){
		setIcon(selected);
		status = statusType.SELECTED;
	}

}
