import java.util.ArrayList;
public class Store{
	public String name;
	public int number;

	public Employee staff[];
	public Employee manager;
	public ArrayList<Employee> staffList;

	public int employeeCount;
	public int maxEmp=30;

	//public Week openHours;
	public Schedule schedule;
	public boolean generated;

	public Store(){
		name = "New Store";
		number = 28508;
		manager = null;
		staff = new Employee[maxEmp];
		staffList = new ArrayList<Employee>();
		employeeCount = 0;
		schedule = new Schedule();
		generated = false;
		//openHours = new Week(0);
	}
	public Store(String n, int num){
		name = n;
		number = num;
		manager = null;
		//openHours=av;
		staff = new Employee[maxEmp];
		staffList = new ArrayList<Employee>();
		employeeCount = 0;
		schedule = new Schedule();
		//openHours.makeShifts();
	}
	// Accessors
	public String toString(){
		return name;
	}
	public void printStaff(){
		System.out.printf("Staff (%d):\n",employeeCount);
		for (int i = 0; i<maxEmp;i++){
			if (staff[i]!=null)
				System.out.printf("%d: %s\n",i,staff[i]);
		}
	}
	public Employee getEmployee(int n){
		if (n<0||n>maxEmp)
			return null;
		return staff[n];
	}
	public ArrayList<Employee> getStaff(){
		staffList.clear();
		for (Employee e : staff)
			if (e!=null)
				staffList.add(e);
		return staffList;
	}

	// Mutators
	public void hire(Employee a){
		if (a.hasNumber()){
			staff[a.number]=a;
			System.out.printf("Hiring %s as employee %d\n",a,a.number);
		}
		else
			for (int i= 1; i<maxEmp;i++){
				if (staff[i]==null){
					System.out.printf("Hiring %s as employee %d\n",a,i);
					staff[i]=a;
					a.number = i;
					break;
				}
			}
		if (a.number==0){
			manager=a;
			System.out.printf("Added %s as manager.\n",a);
		}
		employeeCount++;
		staffList.add(a);
	}
	public void fire(Employee a){
		a.fire();
		staff[a.number]=null;
		employeeCount--;
		staffList.remove(a);
	}
	public void makeManager(Employee e){
		staff[0]=e;
		staff[e.number]=null;
		e.number = 0;
		e.level = 5;
		staffList.remove(e);
		staffList.add(0,e);
	}

	// Scheduling

	public void makeSchedule(){
		ArrayList<Shift> shifts = schedule.getShifts();
		PriorityQueue employees = new PriorityQueue(staff);
		employees.print();
		Sort.sortShiftsByLength(shifts);
		for (Shift s : shifts){
			boolean successful = false;
			if (s.assigned())
				continue;
			Employee emp;
			while ((emp = employees.nextEmployee())!=null){
				if (meetsCriteria(emp,s)){
					s.assign(emp);
					employees.makeChange((int)emp.level);
					successful=true;
					break;
				}
			}
			if (!successful){
				System.out.printf("Unable to assign ");s.print();
			}
			else{
				employees.sortByHours();
			}
		}
		for (Employee e: staff){
			if (e==null)
				continue;
			e.sortShiftsByDay();
		}
		generated = true;
	}
	private boolean meetsCriteria(Employee emp, Shift s){
		if (emp.desiredHours == 0) // order 1
			return false;
		if (emp.maxHours < s.totalTime+emp.hoursThisWeek) // order 1
			return false;
		if (emp.desiredHours +3 < s.totalTime+emp.hoursThisWeek) // order 1
			return false;
		if (emp.shiftCount>=5) // order 1
			return false;
		if (!(emp.availableDuring(s))) // order n
			return false;
		if (emp.alreadyWorksThisDay(s.day)) // order n
			return false;
		return true;
	}
	public void clearSchedule(){
		ArrayList<Shift> shifts = schedule.getShifts();
		for (Shift s : shifts)
			s.unAssign();
		generated = false;
	}
}