import java.io.*;
import java.util.Scanner;
public class IOManager {
	private FileWriter fwriter;
	private PrintWriter pwriter;
	private Scanner sc;
	private File file;
	private Store store;

	public IOManager(){
		load();
	}

	public void read(){
		System.out.println("parsing info.gsaif\n");
		try{
			do{
				readStore();
			} while (sc.hasNext());
		} catch (java.util.NoSuchElementException e){
			System.out.println("File is corrupt! Creating new store.");
			store = new Store();
		}
		sc.close();
		return;
	}
	public void readStore(){
		store = new Store(sc.nextLine(),sc.nextInt());
		int emps = sc.nextInt();
		for (int i=0;i<emps;i++)
			readEmployee();
		readShifts();
		store.generated = (sc.nextInt()==1)? true : false;
		return;
	}
	public void readShifts(){
		Schedule sch = store.schedule;
		for (int i = 0; i<7;i++){
			WorkDay day = sch.days[i];
			int y = sc.nextInt();
			for (int x = 0; x<y; x++){
				day.addShift(new Shift(i,sc.nextInt(),sc.nextInt(),store.getEmployee(sc.nextInt())));
			}
		}
		return;
	}
	public void readEmployee(){
		Employee e= new Employee(sc.next(),sc.next(),sc.nextInt(),(char)sc.nextInt(),sc.nextInt(),sc.nextInt(),readAvailability());
		store.hire(e);
		return;
	}
	public Week readAvailability(){
		Week w = new Week();
		for (int d=0;d<7;d++){
			Day day = new Day(sc.nextInt());
			if (day.wholeDay==2){
				for (int h = 0; h<24; h++){
					day.hours[h]=sc.nextInt();
				}
			}
			w.setDay(day,d);
		}
		return w;
	}

	public void write(){
		try{
			fwriter = new FileWriter(file);
			pwriter = new PrintWriter(file);
			writeStore();
			pwriter.close();
			fwriter.close();
			System.out.println("info.gsaif successfully written.");
		}
		catch (IOException e){
			System.out.println("Error writing to info.gsaif");
		}
		return;
	}

	public void writeStore(){
		pwriter.println(store.name);
		pwriter.println(store.number);

		//writeAvailability(store.openHours);

		pwriter.println(store.employeeCount);
		for (Employee e : store.staffList)
			writeEmployee(e);
		writeShifts();
		pwriter.printf("%d",(store.generated)?1:0);
		return;
	}
	public void writeShifts(){
		Schedule sch = store.schedule;
		for (int i = 0; i<7;i++){
			WorkDay day = sch.days[i];
			pwriter.printf("%d ",day.shiftCount);
			for (int x = 0; x<day.shiftCount; x++){
				Shift s = day.getShift(x);
				pwriter.printf("%d %d %d ",s.startTime,s.endTime,s.assignmentStatus);
			}
			pwriter.println();
		}
		return;
	}
	public void writeEmployee(Employee e){
		if (e==null)
			return;

		pwriter.println(e.firstName);
		pwriter.println(e.lastName);

		pwriter.println(e.number);

		pwriter.println((int)e.level);
		pwriter.println(e.maxHours);
		pwriter.println(e.desiredHours);

		writeAvailability(e.available);
		return;
	}
	public void writeAvailability(Week w){
		for (int d = 0; d<7;d++){
			if (w.days[d].wholeDay==0||w.days[d].wholeDay==1)
				pwriter.println(w.days[d].wholeDay);
			else{
				pwriter.print("2 ");
				for (int h=0; h<24;h++){
					pwriter.printf("%d ",w.days[d].hours[h]);
				}
				pwriter.println();
			}
		}
		return;
	}

	public void load(){
		System.out.println("IOManager > load");

		store = new Store();

		file = new File("info.gsaif");
		try{
			sc = new Scanner(file);
			System.out.println("  info.gsaif loaded successfully!");
			read();
		} catch (FileNotFoundException e){
			System.out.println("  Unable to find info.gsaif\n  Running for first time use.");
			try{
				fwriter = new FileWriter(file);
				fwriter.close();
			}
			catch(IOException ex){
				System.out.println("Unable to create file!");
			}
		}
	}
	public Store getStore(){
		return store;
	}
}