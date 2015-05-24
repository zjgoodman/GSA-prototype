import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.*;
public class LeftBarPanel extends JPanel{
	private final GSAFrame frame;
	private JPanel[] panels;
	private JPanel current;
	private boolean alert = false;
	public LeftBarPanel(GSAFrame f){
		super();
		frame = f;
		panels = new JPanel[]{new ScheduleLeftPanel(),new EmployeeLeftPanel(f.store),new ShiftLeftPanel(f.shiftPanel)};
		//setPreferredSize(new Dimension(300,300));
		setBorder(BorderFactory.createLoweredBevelBorder());
		current = panels[1];
		add(current);
	}
	public void show(int n){
		remove(current);
		current = panels[n];
		add(current);
	}
	public void setEmployeePanel(EmployeePanel e){
		((EmployeeLeftPanel)panels[1]).employeePanel = e;
		e.setEmployee(((EmployeeLeftPanel)panels[1]).staffList.get(0));
	}
	private class ScheduleLeftPanel extends JPanel {
		private JButton
			clear, generate, special;
		private GridBagConstraints c;
		private DialougeBox d;
		public ScheduleLeftPanel(){
			super(new GridBagLayout());
			c = new GridBagConstraints();
			clear = new JButton("Clear");
			generate = new JButton("Generate");
			d = new DialougeBox("Are you sure you want to clear the schedule?",frame);

			c.gridx=0;
			c.gridy=0;
			c.weighty=0.0;
			add(generate,c);
			//c.gridx++;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridy++;
			add(clear,c);
			c.gridy++;
			c.weighty = 1.0;
			//c.gridx--;
			//c.gridwidth=2;
			add(new JLabel(),c);

			generate.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					frame.makeSchedule();
					((ShiftLeftPanel)panels[2]).setShift(null);
				}
			});
			clear.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					if (alert){
						frame.setEnabled(false);
						d.setVisible(true);
					}
					else {
						frame.clearSchedule();
						((ShiftLeftPanel)panels[2]).setShift(null);
					}
				}
			});
		}
	}
	class ShiftLeftPanel extends JPanel {
		private Shift shift;
		private JButton create, delete, save;
		private JLabel dayL, startTimeL, endTimeL, totalTimeL, assigneeL;
		private JComboBox dayC, startTimeC, endTimeC, amPMs, amPMe, assigneeC;
		private JTextField totalTimeT;
		private GridBagConstraints c;
		private ShiftPanel shiftPanel;
		private boolean shiftSelected = false;

		public ShiftLeftPanel(ShiftPanel sp){
			super(new GridBagLayout());
			shift = null;
			shiftPanel = sp;
			shiftPanel.shiftLeftPanel = this;

			create = new JButton("Create New");
			create.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					int day = dayC.getSelectedIndex();
					int start = startTimeC.getSelectedIndex();
					if (amPMs.getSelectedIndex()==1)
						start+=12;
					else if (start==0){
						start = 24;
					}
					int end = endTimeC.getSelectedIndex();
					if (amPMe.getSelectedIndex()==1)
						end+=12;
					else if (end==0){
						end = 24;
					}
					Shift shift = new Shift(day,start,end);
					frame.empMngr.addShift(shift);
					frame.update();
				}
			});
			delete = new JButton("<html><font color='red'>Delete</font></html>");
			delete.setEnabled(false);
			delete.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					frame.empMngr.remShift(shift);
					shift = null;
					frame.update();
					delete.setEnabled(false);
					save.setEnabled(false);
					shiftSelected = false;
				}
			});
			save = new JButton("Save");
			save.setEnabled(false);
			save.addActionListener(new ActionListener(){
				public void actionPerformed(ActionEvent e){
					int day = dayC.getSelectedIndex();
					int start = startTimeC.getSelectedIndex();
					if (amPMs.getSelectedIndex()==1)
						start+=12;
					else if (start==0){
						start = 24;
					}
					int end = endTimeC.getSelectedIndex();
					if (amPMe.getSelectedIndex()==1)
						end+=12;
					else if (end==0){
						end = 24;
					}
					shift.day = day;
					shift.changeTimes(start, end);
					frame.update();
					
					setShift(null);
				}
			});

			dayL = new JLabel("Day:");
			startTimeL = new JLabel("Start:");
			endTimeL = new JLabel("End:");
			totalTimeL = new JLabel("Hours:");
			assigneeL = new JLabel("Assignee:");

			ActionListener listen = new ActionListener(){
				public void actionPerformed(ActionEvent e) {
			        updateTime();
			    }
			};

			dayC = new JComboBox(Days.days);
			startTimeC = new JComboBox(Numbers.numbers);
			startTimeC.addActionListener (listen);
			endTimeC = new JComboBox(Numbers.numbers);
			endTimeC.addActionListener (listen);
			amPMs = new JComboBox(new String[]{"AM","PM"});
			amPMs.addActionListener (listen);
			amPMe = new JComboBox(new String[]{"AM","PM"});
			amPMe.addActionListener (listen);
			assigneeC = new JComboBox(new String[]{"None"});

			totalTimeT = new JTextField("0");
			totalTimeT.setEditable(false);

			c = new GridBagConstraints();
			c.gridx = 0;
			c.gridy = 0;
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridwidth = 1;
			add(dayL,c);
			c.gridx++;
			add(dayC, c);
			c.gridx = 0;
			c.gridy++;
			add(startTimeL, c);
			c.gridx++;
			add(startTimeC, c);
			c.gridx++;
			add(amPMs, c);
			c.gridx = 0;
			c.gridy++;
			add(endTimeL, c);
			c.gridx++;
			add(endTimeC, c);
			c.gridx++;
			add(amPMe, c);
			c.gridx = 1;
			c.gridy++;
			add(totalTimeL, c);
			c.gridx++;
			add(totalTimeT, c);
			c.gridx = 0;
			c.gridy++;
			add(assigneeL, c);
			c.gridx = 0;
			c.gridy++;
			c.gridwidth = 3;
			add(assigneeC, c);
			c.gridwidth = 1;
			c.gridy++;
			c.anchor = GridBagConstraints.PAGE_END;
			add(save, c);
			c.gridx++;
			add(create, c);
			c.gridx++;
			add(delete, c);
		}
		private void updateTime(){
			int start = startTimeC.getSelectedIndex();
			if (start == 0)
				start = 12;
			boolean pm = amPMs.getSelectedIndex()==1;
			if (!pm&&start==12)
				start+=12;
			else if (pm&&start!=12)
				start+=12;
			int end = endTimeC.getSelectedIndex();
			if (end == 0)
				end = 12;
			pm = amPMe.getSelectedIndex()==1;
			if (!pm&&end==12)
				end+=12;
			else if (pm&&end!=12)
				end+=12;
			totalTimeT.setText(""+Shift.getTotalTime(start,end));
		}
		public void setShift(Shift s){
			shift = s;
			if (s==null){
				delete.setEnabled(false);
				save.setEnabled(false);
				shiftSelected = false;
				return;
			}
			if (!shiftSelected){
				save.setEnabled(true);
				delete.setEnabled(true);
				shiftSelected = true;
			}
			int startTime = s.startTime;
			int ampm1;
			if (startTime>=12){
				if (startTime==24){
					startTime = 0;
					ampm1 = 0;
				} else {
					startTime-=12;
					ampm1 = 1;
				}
			}
			else ampm1 = 0;
			int endTime = s.endTime;
			int ampm2;
			if (endTime>=12){
				if (endTime==24){
					endTime = 0;
					ampm2 = 0;
				} else {
					endTime-=12;
					ampm2 = 1;
				}
			}
			else ampm2 = 0;
			int d = s.day;
			dayC.setSelectedIndex(d);
			startTimeC.setSelectedIndex(startTime);
			amPMs.setSelectedIndex(ampm1);
			endTimeC.setSelectedIndex(endTime);
			amPMe.setSelectedIndex(ampm2);
			totalTimeT.setText(""+s.totalTime);
		}
	}
	private class EmployeeLeftPanel extends JPanel implements ListSelectionListener {
		private JList list;
	    private DefaultListModel listModel;

	    private final Store store;
	    public ArrayList<Employee> staffList;
	 
	    private static final String hireString = "Hire";
	    private static final String fireString = "Fire";
	    private JButton fireButton;
	    private JTextField employeeName;
	    public EmployeePanel employeePanel;
	 
	    public EmployeeLeftPanel(Store s) {
	        super(new BorderLayout());
	        store = s;
	        staffList = s.getStaff();
	 
	        listModel = new DefaultListModel();
	        for (Employee e : staffList){
	        	listModel.addElement(e.firstName+" "+e.lastName);
	        }
	 
	        //Create the list and put it in a scroll pane.
	        list = new JList(listModel);
	        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
	        list.setSelectedIndex(0);
	        list.addListSelectionListener(this);
	        list.setVisibleRowCount(20);
	        JScrollPane listScrollPane = new JScrollPane(list);
	 
	        JButton hireButton = new JButton(hireString);
	        hireButton.setEnabled(false);
	 
	        fireButton = new JButton(fireString);
	        fireButton.setActionCommand(fireString);
	 
	        employeeName = new JTextField(10);
	        String name = listModel.getElementAt(
	                              list.getSelectedIndex()).toString();
	 
	        //Create a panel that uses BoxLayout.
	        JPanel buttonPane = new JPanel();
	        buttonPane.setLayout(new BoxLayout(buttonPane,
	                                           BoxLayout.LINE_AXIS));
	        buttonPane.add(fireButton);
	        buttonPane.add(Box.createHorizontalStrut(5));
	        buttonPane.add(new JSeparator(SwingConstants.VERTICAL));
	        buttonPane.add(Box.createHorizontalStrut(5));
	        //buttonPane.add(employeeName);
	        buttonPane.add(hireButton);
	        buttonPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
	 
	        add(listScrollPane, BorderLayout.CENTER);
	        add(buttonPane, BorderLayout.PAGE_END);
	    }
	 
	    //This method is required by ListSelectionListener.
	    public void valueChanged(ListSelectionEvent e) {
	        if (e.getValueIsAdjusting() == false) {
	 
	            if (list.getSelectedIndex() == -1) {
	            //No selection, disable fire button.
	                fireButton.setEnabled(false);
	 
	            } else {
	            //Selection, enable the fire button.
	                employeePanel.setEmployee(staffList.get(list.getSelectedIndex()));
	            }
	        }
	    }
	}
}