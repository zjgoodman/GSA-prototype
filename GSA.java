public class GSA{
	public static void main(String[] args){
		GSAFrame frame;
		IOManager ioManager;
		Store store;
		EmployeeManager empMngr;

		ioManager = new IOManager();
		store = ioManager.getStore();
		empMngr = new EmployeeManager(store);

		System.out.println("GSA > empMngr");

		frame = new GSAFrame(empMngr, ioManager);
		frame.setVisible(true);
		empMngr.frame = frame;

		empMngr.run();
		ioManager.write();
		System.out.println("GSA > quit");
		System.out.println("Exiting GSA");
		System.exit(0);
	}
}