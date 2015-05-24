import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class AvailabilityPanelCell extends JButton {
	public boolean selected;
	private JLabel text;
	public AvailabilityPanelCell[] controlledCells;
	public int code = 0;
	public AvailabilityPanel mainPanel;
	public int day, hour;
	public AvailabilityPanelCell(AvailabilityPanel a){
		super();
		mainPanel = a;
		text = new JLabel();
		selected = false;
		//add(text);
		setPreferredSize(new Dimension(65,20));
		//setBorder(BorderFactory.createLineBorder(Color.black));
	}
	public AvailabilityPanelCell(String name){
		super(name);
		text = new JLabel(name);
		selected = false;
		//add(text);
		setPreferredSize(new Dimension(65,20));
		//setBorder(BorderFactory.createLineBorder(Color.black));
	}
	public void addListener(){
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	if (code==2)
            		return;
            	if (selected)
            		deselect();
            	else select(1);
            	report();
            }
        });
	}
	public void addAffector(AvailabilityPanelCell[] list){
		controlledCells = list;
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	if (selected){
            		selected = false;
            		for (AvailabilityPanelCell c : controlledCells)
            			if (c.code!=2){
            				c.deselect();
            			}
            	}
            	else {
            		selected = true;
            		for (AvailabilityPanelCell c : controlledCells)
            			if (c.code!=2){
            				c.select(1);
            			}
            	}
            	controlledCells[0].mainPanel.setAvailability(controlledCells[0].day, controlledCells[0].code);
            }
        });
	}
	public void select(int code){
		this.code = code;
		selected = true;
		if (code==1){
			setBackground(Color.PINK);
			setText("OFF");
		}
		else if (code==2){
			setBackground(Color.ORANGE);
			setText("WRK");
		}
		else deselect();
	}
	public void report(){
        mainPanel.setAvailability(day,hour,code);
	}
	public void deselect(){
		code = 0;
		setText("");
		setBackground(Color.LIGHT_GRAY);
		selected = false;
	}
	public void setDayHour(int d, int h){
		day = d;
		hour = h;
	}
}