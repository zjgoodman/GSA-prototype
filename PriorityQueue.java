import java.util.ArrayList;
import java.util.Iterator;
public class PriorityQueue{
	private final ArrayList< ArrayList<Employee> > lists;
	private Iterator<Employee> iterator;
	private int currentList;
	private boolean changes[];
	public PriorityQueue(Employee[] emps){
		changes = new boolean[]{false,false,false,false,false,false};
		lists = new ArrayList< ArrayList<Employee> >();
		for (int i = 0; i<=5; i++)
			lists.add(new ArrayList<Employee>());
		for (Employee e : emps){
			if (e==null)
				continue;
			add(e);
		}
		currentList = 5;
		iterator = lists.get(currentList).iterator();
	}
	// Accessors
	public Employee nextEmployee(){
		if (iterator.hasNext())
			return iterator.next();
		else if ((--currentList)>=0){
			iterator = lists.get(currentList).iterator();
			return nextEmployee();
		}
		else
			return null;
	}
	public void print(){
		System.out.println("PriorityQueue:");
		for (int i = 5; i>=0;i--){
			System.out.printf("Level %d: ",i);
			for (Employee e : lists.get(i))
				System.out.printf("%s, ",e);
			System.out.println();
		}
	}
	// Mutators
	public void add(Employee e){
		lists.get((int)e.level).add(e);
	}
	public void remove(Employee e){
		lists.get((int)e.level).remove(e);
	}
	public void sortByHours(){
		for (int i = 0; i<6; i++){
			if (changes[i]){
				Sort.sortByHours(lists.get(i));
				changes[i]=false;
			}
		}
		iterator = lists.get(5).iterator();
		currentList = 5;
	}
	public void makeChange(int x){
		changes[x] = true;
	}
}