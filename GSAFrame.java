import javax.swing.JFrame;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import javax.swing.*;
import java.awt.*;
import java.util.concurrent.atomic.AtomicBoolean;

public class GSAFrame extends JFrame implements WindowListener{
	public final Store store;
	public final EmployeeManager empMngr;
    public final TopBarPanel topBar;
    public final LeftBarPanel leftBar;
    public ShiftPanel shiftPanel;
	public ScheduleScrollPane sch;
    public JComponent panels[];
    public JComponent current;
    public final JTextArea console;
    private JScrollPane consoleScroller;
    private AtomicBoolean scheduleGenerated;
    public final IOManager ioManager;
    private final JScrollPane contentPane;
    public void pl(String p){
        console.append("\n"+p);
    }
	public GSAFrame(EmployeeManager e, IOManager io){
		super("GSA v0.1");
		empMngr=e;
        ioManager = io;
		store = e.store;
		setSize(800,600);
        setMinimumSize(new Dimension(800,600));
        addWindowListener(this);

        scheduleGenerated = new AtomicBoolean(store.generated);
        sch = new ScheduleScrollPane(store.staffList);
        shiftPanel = new ShiftPanel(store.schedule.getShifts(),scheduleGenerated);
        topBar = new TopBarPanel(this);
        leftBar = new LeftBarPanel(this);
        topBar.leftBar = leftBar;
        panels = new JComponent[]{sch,new EmployeePanel(),shiftPanel};
        leftBar.setEmployeePanel((EmployeePanel)panels[1]);
        current = panels[1];
        console = new JTextArea();
        consoleScroller = new JScrollPane(console);
        console.setEditable(false);
        contentPane = new JScrollPane(current);


        add(topBar,BorderLayout.PAGE_START);
        add(leftBar,BorderLayout.LINE_START);
        add(consoleScroller,BorderLayout.PAGE_END);
        //add(new JButton("LINE_END"),BorderLayout.LINE_END);
        add(contentPane,BorderLayout.CENTER);
	}
    public void show(int n){
        current = panels[n];
        contentPane.setViewportView(current);
    }
    public void makeSchedule(){
        empMngr.makeSchedule();
        pl("Schedule generated.");
        scheduleGenerated.set(true);
        shiftPanel.update(store.schedule.getShifts());
    }
    public void clearSchedule(){
        empMngr.clearSchedule();
        pl("Schedule cleared.");
        scheduleGenerated.set(false);
        shiftPanel.update(store.schedule.getShifts());
    }
    public void update(){
        shiftPanel.update(store.schedule.getShifts());
        sch.update(store.staffList);
    }
	public void windowActivated(WindowEvent e) {}
    public void windowClosed(WindowEvent e) {}
    public void windowClosing(WindowEvent e) { 
   		System.out.println("window closing");
   		empMngr.running = false;
        System.exit(0);
    }
    public void windowDeactivated(WindowEvent e) {}
    public void windowDeiconified(WindowEvent e) {}
    public void windowIconified(WindowEvent e) {}
    public void windowOpened(WindowEvent e) {}
}