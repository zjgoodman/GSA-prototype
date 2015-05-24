import java.util.ArrayList;
public class WorkDay{
	public String name;
	public int number;
	public int day, month, year;

	public int shiftCount;
	public ArrayList<Shift> shifts;
	public WorkDay(String n, int num){
		name = n;
		number = num;
		shiftCount = 0;
		shifts = new ArrayList<Shift>();
	}
	// Accessors
	public Shift getShift(int n){
		if (n>=shiftCount)
			return null;
		return shifts.get(n);
	}
	// Mutators
	public void addShift(Shift s){
		shifts.add(s);
		shiftCount++;
		Sort.sortShiftsByStartTime(shifts);
	}
	public void removeShift(Shift s){
		shifts.remove(s);
		shiftCount--;
		s.unAssign();
	}
	public void printShifts(){
		for (Shift s: shifts)
			System.out.println(s);
	}
}