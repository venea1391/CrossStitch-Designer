import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class ToolbarPanel extends JPanel {
	public static final int ICON_NUM = 11;
	public static final int IMAGE_SIZE = 32;
	private BufferedImage icons_img;
	private IconJButton _backstitch, _brush, _eraser, _new, _open, _paint_bucket, _palette,
				_pattern, _save, _zoom_in, _zoom_out;
	private IconJButton[] icon_buttons = new IconJButton[ICON_NUM];
	private JLabel statusLabel;
	private JPanel eraseType;
	private JRadioButton eraseBS, eraseSQ;

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
		
		
		_zoom_in.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){Controller.zoomIn();}
		});
		_zoom_out.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){Controller.zoomOut();}
		});
		_backstitch.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.Mode oldMode = Controller.getMode();
		    	Controller.setMode(Controller.Mode.BACKSTITCH);
		    	changeSelectedIcon(oldMode);
		    	showEraseType(false);}
		});
		_eraser.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){
		    	Controller.Mode oldMode = Controller.getMode();
		    	if (eraseBS.isSelected()){
		    		Controller.setMode(Controller.Mode.ERASE_BS);
		    	}
		    	else {
		    		Controller.setMode(Controller.Mode.ERASE_SQ);
		    	}
		    	changeSelectedIcon(oldMode);
		    	if (oldMode==Controller.Mode.ERASE_BS || oldMode==Controller.Mode.ERASE_SQ){
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
		    	showEraseType(false);
		    	}
		});
		_palette.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent evt){
				Controller.chooseColor();
				//showEraseType(false);
			}
		});
		
		icon_buttons[0] = _new;
		icon_buttons[1] = _open;
		icon_buttons[2] = _save;
		icon_buttons[3] = _pattern;
		icon_buttons[4] = _backstitch;
		icon_buttons[5] = _brush;
		icon_buttons[6] = _eraser;
		icon_buttons[7] = _paint_bucket;
		icon_buttons[8] = _palette;
		icon_buttons[9] = _zoom_in;
		icon_buttons[10] = _zoom_out;
		
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(0,0,0,0);
		c.ipadx = 0;
		c.ipady = 0;
		c.gridx = 0;
		c.gridy = 0;
		for(int i=0 ; i<ICON_NUM ; i++){
			c.gridx = i;
	        add(icon_buttons[i], c);
	    }		
		
		eraseType = new JPanel();
		eraseType.setBackground(Color.white);
		eraseType.setPreferredSize(new Dimension(800-(11*IMAGE_SIZE), 32));
		eraseBS = new JRadioButton("Erase Backstitch");
		eraseBS.setSelected(true);
	    eraseSQ = new JRadioButton("Erase Square");
	    ButtonGroup typeGroup = new ButtonGroup();
	    typeGroup.add(eraseBS);
	    typeGroup.add(eraseSQ);
	    eraseBS.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){Controller.setMode(Controller.Mode.ERASE_BS);}
		});
	    eraseSQ.addActionListener(new ActionListener(){
		    public void actionPerformed(ActionEvent evt){Controller.setMode(Controller.Mode.ERASE_SQ);}
		});
	    eraseType.add(eraseBS);
	    eraseType.add(eraseSQ);
	    
		//type.
		JPanel status = new JPanel();
		status.setPreferredSize(new Dimension(800-(11*IMAGE_SIZE), 32));
		status.setBackground(Color.white);
		statusLabel = new JLabel();
		status.add(statusLabel);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 11;
		//add(status, c);
		//c.gridx = 12;
		add(eraseType, c);
		showEraseType(false);
		
		setSize(800, 32);
		setBackground(Color.white);
		
	}


	public void enableAllIcons(){
		for(int i=0 ; i<ICON_NUM; i++){
	        icon_buttons[i].setEnabled(true);
	    }	
	}
	
	public void changeStatus(String s){
		statusLabel.setText(s);
		
	}
	
	public void changeSelectedIcon(Controller.Mode m){
		if (m==Controller.Mode.BACKSTITCH){
			_backstitch.enableImage();
		}
		else if (m==Controller.Mode.ERASE_BS || m==Controller.Mode.ERASE_SQ){
			_eraser.enableImage();
		}
		else if (m==Controller.Mode.PAINT){
			_brush.enableImage();
		}
		
		switch (Controller.getMode()){
			case BACKSTITCH:
				_backstitch.select();
				break;
			case ERASE_BS:
				_eraser.select();
				break;
			case ERASE_SQ:
				_eraser.select();
				break;
			case PAINT:
				_brush.select();
				break;
			default:
				break;
		}
	}
	
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
}
