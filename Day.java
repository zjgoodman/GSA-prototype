public class Day{
	public int hours[];
	public int wholeDay; // 0 = free // 1 = not free // 2 = free some of the day
	public Day(int d){
		wholeDay = d;
		hours = new int[24];
		switch(d){
			case(0): free(); break;
			case(1): notFree(); break;
			default: custom();
		}
	}
	// Accessors
	public boolean availableDuring(int x, int y){
		if (wholeDay==1)
			return false;
		for (int i = x; i<y;i++)
			if (hours[i]>0)
				return false;
		return true;
	}
	// Mutators
	public void free(){
		wholeDay = 0;
		for (int i = 0; i<24;i++)
			hours[i] = 0;
	}
	public void notFree(){
		wholeDay = 1;
		for (int i = 0; i<24;i++)
			hours[i] = 1;
	}
	public void custom(){
		java.util.Random gen = new java.util.Random();
		for (int i = 0; i<24;i++){
			if (gen.nextInt(2)==0)
				hours[i] = 0;
			else hours[i] = 1;
		}
	}
	public void change(int hr1,int hr2, int av){
		for (int i = hr1;i<=hr2;i++)
			hours[i]=av;
		boolean match = true;
		for (int i = 1; i<24;i++){
			if (av!=hours[i]){
				match = false;
				break;
			}
		}
		if (match)
			if (av==0)
				wholeDay=0;
			else wholeDay=1;
		else wholeDay=2;
		return;
	}
	public void copyFrom(Day d){
		wholeDay=d.wholeDay;
		for (int i = 0;i<24;i++)
			hours[i]=d.hours[i];
	}
}