import java.util.ArrayList;
import java.util.Iterator;
public class Schedule{

	public WorkDay[] days;
	public ArrayList<Shift> shifts;
	public int totalShifts;

	public Schedule(){
		days = new WorkDay[7];
		shifts = new ArrayList<Shift>();
		totalShifts = 0;
		for (int i = 0; i<7;i++)
			days[i] = new WorkDay(Days.getDay(i),i);
	}
	// Accessors
	public void print(){
		for (int i = 0; i< 7; i++)
			System.out.printf(" %s  ",(Days.getDay(i).toUpperCase()).substring(0,6));
		System.out.println();
		int maxShifts = 0;
		for (WorkDay day : days)
			if (maxShifts<day.shiftCount)
				maxShifts=day.shiftCount;
		for (int i = 0; i< maxShifts; i++){
			for (WorkDay day : days){
				Shift s = day.getShift(i);
				if (s==null){
					System.out.printf("%8s ","");
					continue;
				}
				if (s.assignmentStatus<0)
					System.out.print("*");
				else System.out.print(" ");
				System.out.printf("%-7s ",s);
			}
			System.out.println();
		}
	}
	public ArrayList<Shift> getShifts(){
		totalShifts = 0;
		shifts.clear();
		for (WorkDay day : days){
			for (Shift s: day.shifts){
				shifts.add(s);
				totalShifts++;
			}
		}
		return shifts;
	}
	// Mutators
}