import javax.swing.*;
import java.awt.Dimension;
import java.util.ArrayList;
public class ScheduleScrollPane extends JPanel{
	public SchedulePanel sch;
	public ArrayList<Employee> staff;
	public ScheduleScrollPane(ArrayList<Employee> s){
		super();
		staff = s;
		sch = new SchedulePanel();
		for (Employee e: staff){
			if (e==null)
				continue;
			sch.addRow(e);
		}
		add(sch);
	}
	public void update(ArrayList<Employee> s){
		sch.clear();
		staff = s;
		for (Employee e: s){
			if (e==null)
				continue;
			sch.addRow(e);
		}
	}
}