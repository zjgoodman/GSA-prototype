public class Week{
	public Day[] days;
	public Week(){
		days = new Day[7];
	}
	public Week(int x){
		days = new Day[7];
		for (int i=0;i<7;i++)
			days[i]=new Day(x);		
	}
	public boolean availableDuring(int d,int x, int y){
		return days[d].availableDuring(x,y);
	}
	// Mutators
	public void print(){
		System.out.println("    MON TUE WED THU FRI SAT SUN");
		char ampm = 'a';
		int hour = 12;
		for (int i = 0; i<24;i++){
			System.out.printf("%2d%c ",hour,ampm);
			for (int x = 0; x<7;x++){
				String string = "   ";
				if (days[x].hours[i]==1)
					string = "OFF";
				else if (days[x].hours[i]==2)
					string = "WRK";
				System.out.printf("%s ",string);
			}
			System.out.println();
			if (++hour>12)
				hour=1;
			if (i==11)
				ampm = 'p';
		}
	}
	public void setDay(Day d, int n){
		days[n]=d;
	}
}