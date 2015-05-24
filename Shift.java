public class Shift{
	public int
		startTime, endTime, totalTime;
	public int day;
	public int assignmentStatus;
	public Employee assignee;
	public Shift(int d, int f, int l){
		day = d;
		startTime=f;
		endTime=l;
		totalTime= Shift.getTotalTime(startTime, endTime);
		assignee=null;
		assignmentStatus=-1;
	}
	public Shift(int d, int f, int l,Employee ass){
		day = d;
		startTime=f;
		endTime=l;
		totalTime= Shift.getTotalTime(startTime, endTime);
		assign(ass);
	}
	public void changeTimes(int start, int end){
		int temp = totalTime;
		startTime = start;
		endTime = end;
		totalTime = Shift.getTotalTime(startTime, endTime);
		if (assignee!=null){
			assignee.hoursThisWeek-=temp;
			assignee.hoursThisWeek+=totalTime;
		}
	}
	public static int getTotalTime(int start, int end){
		int hours = 0;
		while (start != end){
			start++;
			if (start > 24)
				start = 1;
			hours++;
		}
		return hours;
	}
	public String toString(){
		String p = "";
		if (startTime==0||startTime==24)
			p+="12a";
		else if (startTime<=11)
			p+=startTime+"a";
		else if (startTime==12) 
			p+="12p";
		else p+=(startTime-12)+"p";
		p += "-";
		if (endTime>12&&endTime!=24)
			p+=(endTime-12)+"p";
		else if (endTime==12) 
			p+="12p";
		else if (endTime == 24)
			p+="12a";
		else p+=endTime+"a";
		return p;
	}
	public String assigneeToString(){
		if (assignee==null)
			return "*";
		return assignee.firstName;
	}
	public void print(){
		System.out.printf("%s %s\n",Days.getDay(day),toString());
	}
	public boolean assigned(){
		return assignmentStatus >= 0;
	}
	// Mutators
	public void assign(Employee e){
		if (assignee==e){
			assignmentStatus=-1;
			return;
		}
		assignee=e;
		if (e==null){
			assignmentStatus = -1;
			return;
		}
		assignmentStatus=e.number;
		assignee.assignShift(this);
	}
	public void unAssign(){
		assignmentStatus=-1;
		if (assignee==null)
			return;
		assignee.removeShift(this);
		assignee=null;
	}
	public void unAssignf(){
		assignmentStatus=-1;
		assignee=null;
	}
	public void change(int start, int end){
		startTime = start;
		endTime=end;
		totalTime=Math.abs(end-start);
	}
}