import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
public class TopBarPanel extends JPanel{
	private GridBagConstraints c;
	private JLayeredPane layeredPane;
	private JButton
		schedule, shift, employee, save;
	private JLabel status;
	private GSAFrame frame;
	private JButton current;
	public LeftBarPanel leftBar;
	public TopBarPanel(GSAFrame f){
		super(new GridBagLayout());
		frame = f;
		c = new GridBagConstraints();
		layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(400,150));

		schedule = new JButton("Schedule");
		//schedule.setBackground(Color.LIGHT_GRAY);
		//schedule.setEnabled(false);
		//current = schedule;
		employee = new JButton("Employee");
		shift = new JButton("Shift");
		save = new JButton("Save");
		current = employee;
		current.setEnabled(false);
		status = new JLabel("Employee");
		//status.setBorder(BorderFactory.createLoweredBevelBorder());
		status.setFont (status.getFont ().deriveFont (32.0f));

		/*layeredPane.add(status, new Integer(0));
		status.setBounds(0,0,100,100);
		layeredPane.add(schedule, new Integer(0));
		schedule.setBounds(100,0,100,100);
		layeredPane.add(employee, new Integer(0));
		employee.setBounds(200,0,100,100);
		layeredPane.add(shift, new Integer(0));
		shift.setBounds(300,0,100,100);

		add(layeredPane);*/

		//c.anchor = GridBagConstraints.LINE_START;
		c.gridx = 0;
		c.gridy = 1;
		c.weightx = 0.3;
		c.insets = new Insets(10,10,0,0);
		add(status, c);

		//c.fill = GridBagConstraints.VERTICAL;
		c.gridx++;
		c.insets = new Insets(0,0,0,0);
		c.weightx = 0.0;
		add(schedule,c);
		c.gridx++;
		add(employee,c);
		c.gridx++;
		add(shift,c);
		c.gridx++;
		c.weightx=0.5;
		add(new JLabel(),c);
		c.gridx++;
		c.weightx = 0.0;
		c.anchor = GridBagConstraints.LINE_END;
		c.insets = new Insets(0,0,0,10);
		add(save,c);

		schedule.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				status.setText("Schedule");
				frame.show(0);
				leftBar.show(0);
				//current.setBackground(null);
				current.setEnabled(true);
				current = schedule;
				//current.setBackground(Color.LIGHT_GRAY);
				current.setEnabled(false);
			}
		});
		employee.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				status.setText("Employees");
				frame.show(1);
				leftBar.show(1);
				//current.setBackground(null);
				current.setEnabled(true);
				current = employee;
				//current.setBackground(Color.LIGHT_GRAY);
				current.setEnabled(false);
			}
		});
		shift.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				status.setText("Shifts");
				frame.show(2);
				leftBar.show(2);
				//current.setBackground(null);
				current.setEnabled(true);
				current = shift;
				//current.setBackground(Color.LIGHT_GRAY);
				current.setEnabled(false);
			}
		});
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.setEnabled(false);
				frame.ioManager.write();
				frame.setEnabled(true);
				frame.pl("info.gsaif successfully written.");
			}
		});
	}
}