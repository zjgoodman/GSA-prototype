public class Time {
	public int hour;
	public boolean pm;
	public Time(int h){
		hour = h;
		if (hour == 0){
			pm = false;
			hour = 12;
		}
		else if (hour >=12){
			if (hour > 12)
				hour -= 12;
			pm = true;
		}
	}
	public String toString(){
		return ""+hour+":00 "+((pm)? "P":"A")+"M";
	}
}