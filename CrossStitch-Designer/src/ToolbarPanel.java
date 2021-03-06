import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * Contains all icons for actions, options related to actions, and current
 * status (of current color)
 * 
 * @author Venea
 *
 */
@SuppressWarnings("serial")
public class ToolbarPanel extends JPanel {
	public static final int ICON_NUM = 13;
	public static final int IMAGE_SIZE = 32;
	private BufferedImage icons_img;
	private IconJButton _backstitch, _brush, _eraser, _new, _open, _paint_bucket, _palette,
				_pattern, _save, _zoom_in, _zoom_out, _export, _eyedropper;
	private IconJButton[] icon_buttons = new IconJButton[ICON_NUM];
	private JPanel options;
	private CCPanel ccPanel;
	private JRadioButton eraseBS, eraseSQ;
	private JCheckBox contiguousCB;

	/**
	 * Grabs the file of all the icon images, creates each icon as an IconJButton.
	 * ActionListener added to each iconjbutton, iconjbuttons added to an array
	 * Panel created with GridBagLayout with a panel for currentColor, paintbucket
	 * checkbox option, and eraser radio button options
	 */
	public ToolbarPanel() {
		try {
			icons_img = ImageIO.read(new File("icons/all_icons.png"));
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

		_backstitch = new IconJButton(icons_img.getSubimage(0, 0, IMAGE_SIZE*4, IMAGE_SIZE), iconType.BACKSTITCH); //backstitch
		_brush = new IconJButton(icons_img.getSubimage(0, IMAGE_SIZE*1, IMAGE_SIZE*4, IMAGE_SIZE), iconType.BRUSH); //brush
		_eraser = new IconJButton (icons_img.getSubimage(0, IMAGE_SIZE*2, IMAGE_SIZE*4, IMAGE_SIZE), iconType.ERASER); //eraser
		_new = new IconJButton (icons_img.getSubimage(0, IMAGE_SIZE*3, IMAGE_SIZE*4, IMAGE_SIZE), iconType.NEW); //new
		_open = new IconJButton (icons_img.getSubimage(0, IMAGE_SIZE*4, IMAGE_SIZE*4, IMAGE_SIZE), iconType.OPEN); //open
		_paint_bucket = new IconJButton (icons_img.getSubimage(0, IMAGE_SIZE*5, IMAGE_SIZE*4, IMAGE_SIZE), iconType.PAINT_BUCKET); //paint_bucket
		_palette = new IconJButton (icons_img.getSubimage(0, IMAGE_SIZE*6, IMAGE_SIZE*4, IMAGE_SIZE), iconType.PALETTE); //palette
		_pattern = new IconJButton (icons_img.getSubimage(0, IMAGE_SIZE*7, IMAGE_SIZE*4, IMAGE_SIZE), iconType.PATTERN); //pattern
		_save = new IconJButton (icons_img.getSubimage(0, IMAGE_SIZE*8, IMAGE_SIZE*4, IMAGE_SIZE), iconType.SAVE); //save
		_zoom_in = new IconJButton(icons_img.getSubimage(0, IMAGE_SIZE*9, IMAGE_SIZE*4, IMAGE_SIZE), iconType.ZOOM_IN); //zoom_in
		_zoom_out = new IconJButton(icons_img.getSubimage(0, IMAGE_SIZE*10, IMAGE_SIZE*4, IMAGE_SIZE), iconType.ZOOM_OUT); //zoom_out
		_export = new IconJButton(icons_img.getSubimage(0, IMAGE_SIZE*11, IMAGE_SIZE*4, IMAGE_SIZE), iconType.EXPORT); //export
		_eyedropper = new IconJButton(icons_img.getSubimage(0, IMAGE_SIZE*12, IMAGE_SIZE*4, IMAGE_SIZE), iconType.EYEDROPPER); //eyedropper
		
		_export.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.export();
		    	Controller.changedMode = false;
		    }
		});
		_eyedropper.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.Mode oldMode = Controller.getMode();
		    	Controller.setMode(Controller.Mode.EYEDROPPER);
		    	changeSelectedIcon(oldMode);
		    	showEraseType(false);
		    	showPBucketType(false);
		    	Controller.changedMode = true;}
		});
		_zoom_in.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.zoomIn();
		    	Controller.changedMode = false;
		    }
		});
		_zoom_out.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.zoomOut();
		    	Controller.changedMode = false;
		    }
		});
		_backstitch.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.Mode oldMode = Controller.getMode();
		    	Controller.setMode(Controller.Mode.BACKSTITCH);
		    	changeSelectedIcon(oldMode);
		    	showEraseType(false);
		    	showPBucketType(false);
		    	Controller.changedMode = true;}
		});
		_eraser.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.Mode oldMode = Controller.getMode();
		    	Controller.setMode(Controller.Mode.ERASE);
		    	changeSelectedIcon(oldMode);
		    	Controller.changedMode = true;
		    	showPBucketType(false);
		    	if (oldMode==Controller.Mode.ERASE){
		    		showEraseType(false);
		    	}
		    	else {showEraseType(true);}
		    }
		});
		_brush.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.Mode oldMode = Controller.getMode();
		    	Controller.setMode(Controller.Mode.PAINT);
		    	changeSelectedIcon(oldMode);
		    	Controller.changedMode = true;
		    	showEraseType(false);
		    	showPBucketType(false);
		    	}
		});
		_paint_bucket.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.Mode oldMode = Controller.getMode();
		    	Controller.setMode(Controller.Mode.PBUCKET);
		    	changeSelectedIcon(oldMode);
		    	Controller.changedMode = true;
		    	showEraseType(false);
		    	if (oldMode==Controller.Mode.PBUCKET){
		    		showPBucketType(false);
		    	}
		    	else {showPBucketType(true);}
		    }
		});
		_palette.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				Controller.chooseColor();
				Controller.changedMode = false;
			}
		});
		_pattern.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				Controller.makePattern();
				Controller.changedMode = false;
			}
		});
		
		
		icon_buttons[0] = _new;
		icon_buttons[1] = _open;
		icon_buttons[2] = _save;
		icon_buttons[3] = _export;
		icon_buttons[4] = _pattern;
		icon_buttons[5] = _backstitch;
		icon_buttons[6] = _brush;
		icon_buttons[7] = _eraser;
		icon_buttons[8] = _paint_bucket;
		icon_buttons[9] = _eyedropper;
		icon_buttons[10] = _palette;
		icon_buttons[11] = _zoom_in;
		icon_buttons[12] = _zoom_out;
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,0,0,0);
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.WEST;
		for(int i=0 ; i<ICON_NUM ; i++){
			c.gridx = i;
	        add(icon_buttons[i], c);
	    }	
		c.gridx = ICON_NUM;
		ccPanel = new CCPanel(Color.black);
		add(ccPanel, c);
		
		options = new JPanel();
		options.setLayout(new GridBagLayout());
		GridBagConstraints cOptions = new GridBagConstraints();
		cOptions.insets = new Insets(0,0,0,0);
		cOptions.ipadx = 0;
		cOptions.ipady = 0;
		cOptions.gridx = 0;
		cOptions.gridy = 0;
		cOptions.fill = GridBagConstraints.HORIZONTAL;
		cOptions.anchor = GridBagConstraints.WEST;  // y u no work
		options.setBackground(Color.white);
		options.setPreferredSize(new Dimension(800-((ICON_NUM+1)*IMAGE_SIZE), 32));
		
		eraseBS = new JRadioButton("Erase Backstitch");
		eraseBS.setSelected(true);
	    eraseSQ = new JRadioButton("Erase Square");
	    ButtonGroup typeGroup = new ButtonGroup();
	    typeGroup.add(eraseBS);
	    typeGroup.add(eraseSQ);
	    eraseBS.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.currentEraserMode = Controller.EMode.EBS;}
		});
	    eraseSQ.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.currentEraserMode = Controller.EMode.ESQ;}
		});
	    //options.add(ccPanel, cOptions);
	    cOptions.gridx = 0;
	    options.add(eraseBS, cOptions);
	    cOptions.gridx = 1;
	    options.add(eraseSQ, cOptions);
	    contiguousCB = new JCheckBox("Contiguous");
	    contiguousCB.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	if (((AbstractButton) evt.getSource()).isSelected()){
		    		Controller.currentPBucketMode = Controller.PBMode.CONTIG;
		    	}
		    	else {Controller.currentPBucketMode = Controller.PBMode.NOT_CONTIG;}
		    }	
		});
	    cOptions.gridx = 2;
	    options.add(contiguousCB, cOptions);
	    
		
		//c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = ICON_NUM;
		//add(status, c);
		//c.gridx = 12;
		add(options, c);
		showEraseType(false);
		showPBucketType(false);
		
		setSize(800, 32);
		setBackground(Color.white);
		
	}

	/**
	 * Loops through the array of iconjbuttons and enables each one
	 */
	public void enableAllIcons(){
		for(int i=0 ; i<ICON_NUM; i++){
	        icon_buttons[i].setEnabled(true);
	    }	
	}
	
	/**
	 * Sets the old mode related button to enabled (clearing any selected status)
	 * Sets the new mode related button to selected
	 * @param m Old Mode
	 */
	public void changeSelectedIcon(Controller.Mode m){
		if (m==Controller.Mode.BACKSTITCH){
			_backstitch.enableImage();
		}
		else if (m==Controller.Mode.ERASE){
			_eraser.enableImage();
		}
		else if (m==Controller.Mode.PAINT){
			_brush.enableImage();
		}
		else if (m==Controller.Mode.PBUCKET){
			_paint_bucket.enableImage();
		}
		else if (m==Controller.Mode.EYEDROPPER){
			_eyedropper.enableImage();
		}
		
		switch (Controller.getMode()){
			case BACKSTITCH:
				_backstitch.select();
				break;
			case ERASE:
				_eraser.select();
				break;
			case PAINT:
				_brush.select();
				break;
			case PBUCKET:
				_paint_bucket.select();
				break;
			case EYEDROPPER:
				_eyedropper.select();
				break;
			default:
				break;
		}
	}
	
	/**
	 * Shows/hides the eraser options 
	 * @param b Boolean to show or not show
	 */
	public void showEraseType(boolean b){
		if (b){
			eraseBS.setVisible(true);
			eraseSQ.setVisible(true);
		}
		else {
			eraseBS.setVisible(false);
			eraseSQ.setVisible(false);
		}
	}
	
	/**
	 * Shows/Hides the pbucket contiguous checkbox
	 * @param b Boolean to show or not show
	 */
	public void showPBucketType(boolean b){
		if (b){
			contiguousCB.setVisible(true);
		}
		else {
			contiguousCB.setVisible(false);
		}
	}
	
	/**
	 * Updates the color of the ccPanel to Controller.currentColor
	 */
	public void changeCCPanel(){
		ccPanel.changeColor(Controller.currentColor);
	}
}
