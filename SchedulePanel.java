import javax.swing.*;
import java.awt.*;
public class SchedulePanel extends JPanel{
	public SchedulePanelCell[] cells;
	private int blockCount;
	private final GridLayout gridLayout;
	private int rows;
	private boolean[] remove;
	public SchedulePanel(){
		super();
		gridLayout = new GridLayout(1,8,0,0);
		rows = 1;
		setLayout(gridLayout);
		remove = new boolean[248];

		cells = new SchedulePanelCell[248];
		cells[0] = new SchedulePanelCell();
		add(cells[0]);
		blockCount = 1;
		for (int i = 1; i<8;i++){
			cells[i] = new SchedulePanelCell("",Days.getDay(i-1),"");
			add(cells[i]);
			blockCount++;
			remove[i] = true;
		}
		for (int i = 8; i<248;i++){
			cells[i] = new SchedulePanelCell();
			remove[i] = false;
			//cells[i].setVisible(false);
			//add(cells[i]);
		}
	}
	public void addRow(Employee e){
		gridLayout.setRows(++rows);
		int top = blockCount+8;
		int nums[] = new int[7];
		for (int i = 0; i<7; i++)
			nums[i] = blockCount+i+1;
		cells[blockCount].setText("",e.firstName,""+e.hoursThisWeek);
		if (e.hoursThisWeek>e.maxHours)
			cells[blockCount].setColor(Color.RED);
		else cells[blockCount].setColor(Color.BLACK);
		for (;blockCount<top;blockCount++){
			//cells[blockCount].setVisible(true);
			add(cells[blockCount]);
			remove[blockCount]=true;
		}
		for (Shift s : e.shifts){
			int day = s.day;
			cells[nums[day]].setText("",s.toString(),""+s.totalTime);
		}
		repaint();
		revalidate();
	}
	public void removeRow(){
		int bottom = blockCount-8;
		for (;blockCount>bottom;blockCount--){
			//cells[blockCount].setVisible(false);
			if (!remove[blockCount])
				continue;
			//System.out.printf("Removing %d\n",blockCount);
			remove(cells[blockCount]);
			cells[blockCount].setText("","","");
			remove[blockCount] = false;
		}
		gridLayout.setRows(--rows);
		repaint();
		revalidate();
	}
	public void clear(){
		while (blockCount>8)
			removeRow();
	}
}