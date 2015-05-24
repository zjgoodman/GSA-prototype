import java.util.Collections;
import java.util.Comparator;
import java.util.ArrayList;
public class Sort {
	private static abstract class CustomComparator implements Comparator<Object> {
    	@Override
    	public abstract int compare(Object o1, Object o2);
	}
	// Filters
	private static CustomComparator employeeHours = new CustomComparator(){
		public int compare(Object one, Object two){
			int e1 = ((Employee)one).hoursThisWeek;
			int e2 = ((Employee)two).hoursThisWeek;
			if (e1<e2)
				return -1;
			if (e1==e2)
				return 0;
			return 1;
		}
	};
	private static CustomComparator shiftDay = new CustomComparator(){
		public int compare(Object one, Object two){
			int e1 = ((Shift)one).day;
			int e2 = ((Shift)two).day;
			if (e1<e2)
				return -1;
			if (e1>e2)
				return 1;
			return shiftStartTime.compare(one,two);
		}
	};
	private static CustomComparator shiftLength = new CustomComparator(){
		public int compare(Object one, Object two){
			int e1 = ((Shift)one).totalTime;
			int e2 = ((Shift)two).totalTime;
			if (e1>e2)
				return -1;
			if (e1<e2)
				return 1;
			return shiftStartTime.compare(one,two);
		}
	};
	private static CustomComparator shiftStartTime = new CustomComparator(){
		public int compare(Object one, Object two){
			int e1 = ((Shift)one).startTime;
			int e2 = ((Shift)two).startTime;
			if (e1==24&&e2==24)
				return compareTo(((Shift)one).endTime,((Shift)two).endTime);
			if (e1==24)
				return -1;
			if (e2==24)
				return 1;
			if (e1<e2)
				return -1;
			if (e1>e2)
				return 1;
			return compareTo(((Shift)one).endTime,((Shift)two).endTime);
		}
	};
	private static int compareTo(int x, int y){
		if (x<y)
			return -1;
		if (x>y)
			return 1;
		return 0;
	}
	// Methods
	public static void sortByHours(ArrayList<Employee> list){
		Collections.sort(list,employeeHours);
	}
	public static void sortShiftsByLength(ArrayList<Shift> list){
		Collections.sort(list,shiftLength);
	}
	public static void sortShiftsByStartTime(ArrayList<Shift> list){
		Collections.sort(list,shiftStartTime);
	}
	public static void sortShiftsByDay(ArrayList<Shift> list){
		Collections.sort(list,shiftDay);
	}
}