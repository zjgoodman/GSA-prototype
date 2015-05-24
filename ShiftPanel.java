import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;
public class ShiftPanel extends JPanel {
	private JLayeredPane layeredPane;
	private ArrayList<SchedulePanelCell> cells;
	private ArrayList<Shift> shifts;
	private int[] shiftCounts;
	private AtomicBoolean scheduleGenerated;
	private SchedulePanelCell selectedPanel;
	public LeftBarPanel.ShiftLeftPanel shiftLeftPanel;
	private SchedulePanelCell selected = null;

	public ShiftPanel(ArrayList<Shift> s,AtomicBoolean b){
		super();
		scheduleGenerated = b;
		selectedPanel = null;
		cells = new ArrayList<SchedulePanelCell>();
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(525,500));
		add(layeredPane);
		shiftCounts = new int[7];
		int x = 0;
		int y = 0;
		for (int i = 0; i<7;i++){
			shiftCounts[i] = 0;
			SchedulePanelCell c = new SchedulePanelCell("",Days.getDay(i),"");

			layeredPane.add(c, new Integer(0));
			c.setBounds(x,y,75,50);
			x+=75;
		}
		update(s);
	}
	public void setShift(SchedulePanelCell cell){
		if (selected!=null)
			selected.deSelect();
		if (cell == null)
			shiftLeftPanel.setShift(null);
		else shiftLeftPanel.setShift(cell.shift);
		selected = cell;
	}
	public void update(ArrayList<Shift> s){
		shifts = s;
		clear();
		boolean sch = scheduleGenerated.get();
		for (Shift shift : s){
			SchedulePanelCell c = new SchedulePanelCell(shift,this);
			cells.add(c);
			shiftCounts[shift.day]++;
			layeredPane.add(c,new Integer(0));
			c.setBounds(shift.day*75,50*shiftCounts[shift.day],75,50);
			if (sch&&shift.assignmentStatus<0)
				c.setColor(Color.RED);
		}	
		int max = 0;
		for (int i = 0; i<7; i++)
			if (shiftCounts[i]>max)
				max = shiftCounts[i];
		layeredPane.setSize(525,(max+1)*50);
		repaint();
		revalidate();
	}
	public void clear(){
		for (SchedulePanelCell c : cells)
			layeredPane.remove(c);
		cells.clear();
		for (int i = 0; i<7; i++)
			shiftCounts[i] = 0;
	}
}