import java.util.ArrayList;
public class Employee{
	public String firstName;
	public String lastName;

	public char level;
	public int maxHours;
	public int desiredHours;
	public int hoursThisWeek;
	public int number;

	public Week available;
	public ArrayList<Shift> shifts;
	public int shiftCount;

	public Employee(){
		desiredHours = 15;
		shiftCount=0;
		number = -1;
		firstName = "New";
		lastName = "Employee";
		level = 0;
		hoursThisWeek=0;
		maxHours = 40;
		available=new Week(0);
		shifts = new ArrayList<Shift>();

	}
	public Employee(String frst,String lst,int n, char lvl,int maxHrs,int desHrs,Week week){
		desiredHours = desHrs;
		shiftCount=0;
		number = n;
		firstName = frst;
		lastName= lst;
		level = lvl;
		maxHours = maxHrs;
		available=week;
		hoursThisWeek=0;
		shifts = new ArrayList<Shift>();
	}

	// Accessors
	public String toString(){
		return firstName+" "+lastName+" ("+(int)level+")";
	}
	public boolean hasNumber(){
		if (number>=0)
			return true;
		return false;
	}
	public void print(){
		System.out.printf("%s %s\n",firstName,lastName);
		System.out.printf("Level: %d\n",(int)level);
		System.out.printf("Max Hours: %d\n",maxHours);
		System.out.printf("Desired Hours: %d\n",desiredHours);
		System.out.println("Availability:");
		available.print();
		System.out.printf("Upcoming Shifts (%d hours):\n",hoursThisWeek);
		for (Shift s:shifts)
			s.print();
	}
	public boolean alreadyWorksThisDay(int d){
		for (Shift s: shifts)
			if (d==s.day)
				return true;
		return false;
	}
	public boolean availableDuring(Shift s){
		return available.availableDuring(s.day,s.startTime,s.endTime);
	}
	// Mutators
	public void setName(String n,String p){
		firstName = n;
		lastName=p;
	}
	public void setLevel(int n){
		level = (char)n;
	}
	public void setAvailability(Week week){
		available=week;
	}
	public void setMaxHours(int t){
		maxHours=t;
	}
	public void setAvailability(Day d, int x){
		available.days[x]=d;
	}
	public void assignShift(Shift s){
		shifts.add(s);
		hoursThisWeek+=s.totalTime;
		shiftCount++;
		(available.days[s.day]).change(s.startTime,s.endTime-1,2);
	}
	public void removeShift(Shift s){
		shifts.remove(s);
		hoursThisWeek-=s.totalTime;
		shiftCount--;
		(available.days[s.day]).change(s.startTime,s.endTime-1,0);
	}
	public void fire(){
		for (Shift s: shifts){
			System.out.printf("Unassigning %s\n",s);
			s.unAssignf();
		}
	}
	public void sortShiftsByDay(){
		Sort.sortShiftsByDay(shifts);
	}
}