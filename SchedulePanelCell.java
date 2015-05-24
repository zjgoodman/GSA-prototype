import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class SchedulePanelCell extends JPanel{
	private GridBagConstraints c;
	private JLabel topText, middleText, bottomText;
	private boolean selected;
	private ShiftPanel shiftPanel;
	public Shift shift;
	public SchedulePanelCell(){
		super(new GridBagLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		c = new GridBagConstraints();		
		topText = new JLabel();
		middleText = new JLabel();
		bottomText = new JLabel();
		selected = false;

		c.gridx = 0;
		c.gridy = 0;
		add(topText,c);

		c.gridy++;
		add(middleText,c);
		c.gridy++;
		add(bottomText,c);

		setPreferredSize(new Dimension(50,50));
		setMaximumSize( new Dimension(50,50) );
	}
	public SchedulePanelCell(String t, String m, String b){
		super(new GridBagLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		c = new GridBagConstraints();		
		topText = new JLabel(t);
		middleText = new JLabel(m);
		bottomText = new JLabel(b);

		c.gridx = 0;
		c.gridy = 0;
		add(topText,c);

		c.gridy++;
		add(middleText,c);
		c.gridy++;
		add(bottomText,c);

		setPreferredSize(new Dimension(50,50));
	}
	public SchedulePanelCell(Shift sh, ShiftPanel sp){
		super(new GridBagLayout());
		setBorder(BorderFactory.createLineBorder(Color.black));
		c = new GridBagConstraints();
		shiftPanel = sp;
		shift = sh;		
		topText = new JLabel(shift.toString());
		middleText = new JLabel(""+shift.totalTime);
		bottomText = new JLabel(shift.assigneeToString());

		addListener();

		c.gridx = 0;
		c.gridy = 0;
		add(topText,c);

		c.gridy++;
		add(middleText,c);
		c.gridy++;
		add(bottomText,c);

		setPreferredSize(new Dimension(75,75));
	}
	public void setTop(String a){
		topText.setText(a);
	}
	public void setMiddle(String a){
		middleText.setText(a);
	}
	public void setBottom(String a){
		bottomText.setText(a);
	}
	public void setText(String a, String b, String c){
		setTop(a);
		setMiddle(b);
		setBottom(c);
	}
	public void setColor(Color c){
		topText.setForeground(c);
		middleText.setForeground(c);
		bottomText.setForeground(c);
	}
	public void setBorderColor(Color c){
		setBorder(BorderFactory.createLineBorder(c));
	}
	public void addListener(){
		addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
            }
            @Override
            public void mouseReleased(MouseEvent e) {
            	select();
            }
        });
	}
	public void select(){
        if (selected){
        	deSelect();
        	shiftPanel.setShift(null);
        }
        else {
        	setBorderColor(Color.GREEN);
        	selected = true;
        	shiftPanel.setShift(this);
        }
	}
	public void deSelect(){
        setBorderColor(Color.BLACK);
        selected = false;
	}
}