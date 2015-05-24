import java.util.Scanner;
public class EmployeeManager{
	public Scanner sc;
	public Store store;
	public Employee currentEmployee;
	public Shift currentShift;
	public int selectedEmployee;
	public boolean running;
	public GSAFrame frame;

	public EmployeeManager(Store a){
		store = a;
		currentShift = null;
		currentEmployee=null;
		selectedEmployee=-1;
		sc= new Scanner(System.in);
		running = true;
	}
	public boolean running(){
		return running;
	}

	private String getCommand(){
		System.out.print("empMngr > ");
		return (sc.next()).toLowerCase();
	}
	private boolean quit(String command){
		if (command.equals("quit")||command.equals("q")||command.equals("exit"))
			return true;
		return false;
	}
	private void newEmp(){
		currentEmployee = new Employee();
		store.hire(currentEmployee);
		selectedEmployee = store.employeeCount;
		System.out.println("New Employee Created");
	}
	private void select(){
		int n = sc.nextInt();
		currentEmployee = store.getEmployee(n);
		selectedEmployee = n;
		if (currentEmployee==null)
			System.out.println("No such employee.");
		else System.out.printf("Employee %d selected\n",n);
		return;
	}
	private void fire(){
		if (currentEmployee!=null){
			store.fire(currentEmployee);
			System.out.printf("%s fired.\n",currentEmployee);
			currentEmployee=null;
			selectedEmployee=-1;
		} else System.out.println("No employee selected.");
		return;
	}
	private void print(){
		if (currentEmployee==null)
			System.out.println("No employee selected.");
		else currentEmployee.print();
	}
	private void rename(){
		if (currentEmployee==null)
			System.out.println("No employee selected.");
		else currentEmployee.setName(sc.next(), sc.next());		
	}
	private void level(){
		if (currentEmployee==null)
			System.out.println("No employee selected.");
		else currentEmployee.setLevel(sc.nextInt());		
	}
	private void deshours(){
		if (currentEmployee==null)
			System.out.println("No employee selected.");
		else currentEmployee.desiredHours=sc.nextInt();		
	}
	private void hours(){
		if (currentEmployee==null)
			System.out.println("No employee selected.");
		else currentEmployee.setMaxHours(sc.nextInt());		
	}
	private void makeManager(){
		if (currentEmployee==null)
			System.out.println("No employee selected.");
		else if (store.staff[0]!=null)
			System.out.println("Store already has manager");
		else if (store.staff[0]==currentEmployee){
			System.out.println("Employee is already manager");
		}
		else{
			store.makeManager(currentEmployee);
			System.out.printf("Promoted %s to manager.\n",currentEmployee);
		}
	}
	private void availability(){
		if (currentEmployee==null){
			System.out.println("No employee selected.");
			return;
		}
		else {
			System.out.print("Enter day: ");
			String day = sc.next();
			day = day.toLowerCase();
			int d;
			switch(day){
				case("sunday"):
				case("sun"): d = 6; break;
				case("mon"):
				case("monday"):d = 0; break;
				case("tue"):
				case("tues"):
				case("tuesday"):d = 1; break;
				case("wed"):
				case("wednesday"):d = 2; break;
				case("thurs"):
				case("thur"):
				case("thursday"):d = 3; break;
				case("fri"):
				case("friday"):d = 4; break;
				case("sat"):
				case("saturday"):d = 5; break;
				default:d = -1; System.out.println("Unknown day entered.");
				return;
			}
			System.out.print("Enter time (0-23) 24 all day: ");
			int h = sc.nextInt();
			int h2 = 0;
			if (h<0||h>24){
				System.out.println("Invalid hour.");
				return;
			}
			else if (h<24){
				h2=sc.nextInt();
				if (h2<h){
					System.out.println("invalid");
					return;
				}
				else if (h>23){
					System.out.println("invalid");
					return;
				}
			}
			System.out.print("Enter availability: ");
			String p = sc.next();
			int b;
			p=p.toLowerCase();
			switch(p){
				case("on"):
				case("true"):
				case("t"): b = 0;
					break;
				case("f"):
				case("false"):
				case("off"): b = 1;
					break;
				default: b = 0;//currentEmployee.available.days[d].hours[h]; 
					System.out.println("Unknown availability.");
			}
			if (h == 24){
				if (b==0)
					currentEmployee.available.days[d].free();
				else currentEmployee.available.days[d].notFree();
			}
			else
				currentEmployee.available.days[d].change(h,h2,b);
			String temp;
			if (b==0)
				temp = "available";
			else temp = "unavailable";
			System.out.printf("%s is %s on %s",currentEmployee,temp,day);
			if (h <24)
				System.out.printf(" from %d to %d.\n",h,h2);
			else System.out.println(".");
		}
	}
	// shifts
	private void newShift(){
		System.out.print("Enter day: ");
			String day = sc.next().toLowerCase();
			int d;
			switch(day){
				case("sunday"):
				case("sun"): d = 6; break;
				case("mon"):
				case("monday"):d = 0; break;
				case("tue"):
				case("tues"):
				case("tuesday"):d = 1; break;
				case("wed"):
				case("wednesday"):d = 2; break;
				case("thurs"):
				case("thur"):
				case("thursday"):d = 3; break;
				case("fri"):
				case("friday"):d = 4; break;
				case("sat"):
				case("saturday"):d = 5; break;
				default:d = -1; System.out.println("Unknown day entered.");
				return;
			}
			System.out.print("Enter times (start, end): ");
			int s = sc.nextInt();
			int e = sc.nextInt();
			store.schedule.days[d].addShift(new Shift(d,s,e));
			System.out.printf("shift added %s %d-%d\n",day,s,e);
	}
	public void addShift(Shift s){
		store.schedule.days[s.day].addShift(s);
	}
	private void remShift(){
		if (currentShift == null){
			System.out.println("No shift selected");
			return;
		}
		store.schedule.days[currentShift.day].removeShift(currentShift);
		currentShift=null;
	}
	public void remShift(Shift s){
		store.schedule.days[s.day].removeShift(s);
	}
	private void selectShift(){
		System.out.print("Enter day: ");
		String day = sc.next().toLowerCase();
		int d;
		switch(day){
				case("sunday"):
				case("sun"): d = 6; break;
				case("mon"):
				case("monday"):d = 0; break;
				case("tue"):
				case("tues"):
				case("tuesday"):d = 1; break;
				case("wed"):
				case("wednesday"):d = 2; break;
				case("thurs"):
				case("thur"):
				case("thursday"):d = 3; break;
				case("fri"):
				case("friday"):d = 4; break;
				case("sat"):
				case("saturday"):d = 5; break;
				default:d = -1; System.out.println("Unknown day entered.");
				return;
		}
		System.out.print("Enter shift number: ");
		int n = sc.nextInt();
		currentShift=store.schedule.days[d].shifts.get(n);
		System.out.printf("%s %s selected\n",day,currentShift);
	}
	private void assignShift(){
		if (currentShift==null||currentEmployee==null){
			System.out.println("No employee or shift selected.");
			return;
		}
		currentShift.assign(currentEmployee);
	}
	private void unassignShift(){
		if (currentShift==null){
			System.out.println("No shift selected.");
			return;
		}
		currentShift.unAssign();
	}
	//store hours
	/*public void storeHours(){
		System.out.println("Store Hours:");
		store.schedule.print();
	}
	private void sHours(){
			System.out.print("Enter day: ");
			String day = sc.next();
			day = day.toLowerCase();
			int d;
			switch(day){
				case("sunday"):
				case("sun"): d = 6; break;
				case("mon"):
				case("monday"):d = 0; break;
				case("tue"):
				case("tues"):
				case("tuesday"):d = 1; break;
				case("wed"):
				case("wednesday"):d = 2; break;
				case("thurs"):
				case("thur"):
				case("thursday"):d = 3; break;
				case("fri"):
				case("friday"):d = 4; break;
				case("sat"):
				case("saturday"):d = 5; break;
				default:d = -1; System.out.println("Unknown day entered.");
				return;
			}
			System.out.print("Enter time (0-23) 24 all day: ");
			int h = sc.nextInt();
			int h2 = 0;
			if (h<0||h>24){
				System.out.println("Invalid hour.");
				return;
			}
			else if (h<24){
				h2=sc.nextInt();
				if (h2<h){
					System.out.println("invalid");
					return;
				}
				else if (h>23){
					System.out.println("invalid");
					return;
				}
			}
			System.out.print("Enter availability: ");
			String p = sc.next();
			boolean b;
			p=p.toLowerCase();
			switch(p){
				case("on"):
				case("true"):
				case("o"):
				case("open"):
				case("t"): b = true;
					break;
				case("closed"):
				case("close"):
				case("clo"):
				case("f"):
				case("false"):
				case("off"): b = false;
					break;
				default: b = true; 
					System.out.println("Unknown availability.");
			}
			if (h == 24){
				if (b)
					store.openHours.days[d].free();
				else store.openHours.days[d].notFree();
			}
			else
				store.openHours.days[d].change(h,h2,b);
			String temp;
			if (b)
				temp = "open";
			else temp = "closed";
			System.out.printf("store is %s on %s",temp,day);
			if (h <24)
				System.out.printf(" from %d to %d.\n",h,h2);
			else System.out.println(".");
	}
	private void sHoursCopy(){
			System.out.print("Enter two days (original, copyTo): ");
			String day = sc.next();
			day = day.toLowerCase();
			String day2 = (sc.next()).toLowerCase();
			int d;
			switch(day){
				case("sunday"):
				case("sun"): d = 6; break;
				case("mon"):
				case("monday"):d = 0; break;
				case("tue"):
				case("tues"):
				case("tuesday"):d = 1; break;
				case("wed"):
				case("wednesday"):d = 2; break;
				case("thurs"):
				case("thur"):
				case("thursday"):d = 3; break;
				case("fri"):
				case("friday"):d = 4; break;
				case("sat"):
				case("saturday"):d = 5; break;
				default:d = -1; System.out.println("Unknown day entered.");
				return;
			}
			int d2;
			switch(day2){
				case("sunday"):
				case("sun"): d2= 6; break;
				case("mon"):
				case("monday"):d2= 0; break;
				case("tue"):
				case("tues"):
				case("tuesday"):d2= 1; break;
				case("wed"):
				case("wednesday"):d2= 2; break;
				case("thurs"):
				case("thur"):
				case("thursday"):d2= 3; break;
				case("fri"):
				case("friday"):d2= 4; break;
				case("sat"):
				case("saturday"):d2= 5; break;
				default:d2= -1; System.out.println("Unknown day entered.");
				return;
			}
			store.openHours.days[d2].copyFrom(store.openHours.days[d]);
			System.out.printf("%s hours copied to %s.\n",day,day2);
	}*/
	public void makeSchedule(){
		if (store.employeeCount < 1){
			System.out.println("Not enough employees to make a schedule.");
			return;
		}
		System.out.print("Generating schedule... ");
		store.makeSchedule();
		System.out.println("done!");
		frame.sch.update(store.staffList);
	}
	public void clearSchedule(){
		store.clearSchedule();
		frame.sch.update(store.staffList);
		System.out.println("Schedule cleared.");
		frame.pl("Schedule cleared.");
	}
	public void run(){
		System.out.println("Employee Management Tool v0.1\n");
		String command;
		while (!quit( command=getCommand() ) ){
			switch(command){
				case("n"):
				case("new"): newEmp();
					frame.sch.update(store.staffList);
					break;
				case("staff"): store.printStaff();
					break;
				case("s"):
				case("sel"):
				case("select"): select();
					break;
				case("fire"): fire();
					frame.sch.update(store.staffList);
					frame.update();
					break;
				case("name"):
				case("rename"): rename();
					frame.sch.update(store.staffList); 
					break;
				case("print"):
				case("p"): print();
					break;
				case("level"):
				case("lev"): level();
					break;
				/*case("storehours"):
				case("shrs"): storeHours();
					break;
				case("storeav"):
				case("sav"): sHours();
					break;
				case("storeavcopy"):
				case("scopy"):
				case("savc"): sHoursCopy();
					break;*/
				case("hrs"):
				case("hours"): hours();
					break;
				case("dhrs"):
				case("dh"):
				case("deshrs"): deshours();
					break;

				case("sch"):
				case("schedule"): store.schedule.print();
					break;
				case("msch"):
				case("gsch"):
				case("ms"):
				case("gs"): makeSchedule();
					break;
				case("cs"):
				case("csch"): clearSchedule();
					break;

				// shifts
				case("nshift"):
				case("ns"): newShift();
					break;
				case("selectshift"):
				case("ss"):
				case("sshift"): selectShift();
					break;
				case("removeshift"):
				case("rs"):
				case("remshift"): remShift();
					break;
				case("assignshift"):
				case("as"):
				case("assshift"): assignShift();
					break;
				case("unassignshift"):
				case("uas"):
				case("unas"):
				case("unassshift"): unassignShift();
					break;

				// manager
				case("makeman"):
				case("mm"):
				case("man"): makeManager();
					break;

				//
				case("availability"):
				case("available"):
				case("avail"):
				case("av"): availability();
					break;
				case("help"): System.out.println("commands include:\nshits: nshift, sshift, remshift,unasshift,assshift\nNEW, STAFF, SELECT, FIRE, RENAME, PRINT, HOURS, LEVEL");
					break;
				default: System.out.println("Unknown command entered");
			}
		}
		System.out.println("Exiting Employee Manager");
	}
}