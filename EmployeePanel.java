import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.util.Hashtable;
public class EmployeePanel extends JPanel {
	private Employee current;
	private final GridBagConstraints c;

	private final String[] primary = new String[]{
		"Freshly Hired",
		"Somewhat Experienced",
		"Experienced",
		"Very Experienced",
		"Complete Mastery",
		"Manager"
	};
	private final String[] secondary = new String[]{
		"Unable to perform any tasks without oversight.",
		"Basic knowledge of simple tasks; requires oversight.",
		"Strong knowledge of most tasks; does not require oversight.",
		"Instructs other employees; leads shifts and oversees store operations.",
		"Highest point of contact in absence of manager;",
		"Complete charge of store."
	};
	private final String[] ternary = new String[]{
		"line operations",
		"dishes, lobby & restroom cleaning, other basic store duties",
		"food prep, basic register operations",
		"advanced register operations, closing duties, hold store key if needed",
		"advanced customer service, training of completely new employees",
		"scheduling, inventory, hiring/dismissal of employees"
	};
	private final String[] avDescriptions = new String[]{
		"(Weekly schedule)",
		"(This week only)"
	};

	private JButton
		save, hire, fire;
	private JTextField
		firstNameTF, lastNameTF, maxHoursTF, desiredHoursTF;
	private JLabel
		nameL, maxHoursL, desiredHoursL, availabilityL, level1L, level2L,
		description1L, description2L, exampleTask1L, exampleTask2L,
		availabilityDescriptionL;
	private JSlider levelSlider;
	private JRadioButton
		templateRB, currentRB;
	private final AvailabilityPanel availabilityTemplate;
	private final AvailabilityPanel currentSchedule;
	private AvailabilityPanel currentPanel;
	private AvailabilityPanel[] panels;
	private final JPanel availabilityHolderPanel;
	public EmployeePanel(){
		super(new GridBagLayout());
		current = null;
		c = new GridBagConstraints();

		save = new JButton("Save");
		save.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				write();
			}
		});

		firstNameTF = new JTextField();
		lastNameTF = new JTextField("Last");
		maxHoursTF = new JTextField("40");
		desiredHoursTF = new JTextField("40");

		nameL = new JLabel("Name:");
		maxHoursL = new JLabel("Max Hours:");
		desiredHoursL = new JLabel("Desired Hours:");
		availabilityL = new JLabel("Availability:");
		level1L = new JLabel("Level:");
		level2L = new JLabel(primary[0]);
		description1L = new JLabel("Description:");
		description2L = new JLabel(secondary[0]);
		exampleTask1L = new JLabel("Example Tasks:");
		exampleTask2L = new JLabel(ternary[0]);
		availabilityDescriptionL = new JLabel(avDescriptions[1]);

		availabilityTemplate = new AvailabilityPanel();
		availabilityHolderPanel = new JPanel();
		currentSchedule = new AvailabilityPanel();
		panels = new AvailabilityPanel[]{availabilityTemplate,currentSchedule};
		currentPanel = currentSchedule;
		availabilityHolderPanel.add(currentPanel);

		ActionListener actionListener = new ActionListener(){
			public void actionPerformed(ActionEvent e){
				int x = Integer.parseInt(e.getActionCommand());
				availabilityHolderPanel.remove(currentPanel);
				currentPanel = panels[x];
				availabilityHolderPanel.add(currentPanel);
				availabilityDescriptionL.setText(avDescriptions[x]);
				availabilityHolderPanel.repaint();
				availabilityHolderPanel.revalidate();
			}
		};

		currentRB = new JRadioButton("Current");
		currentRB.setActionCommand("1");
		currentRB.addActionListener(actionListener);
		templateRB = new JRadioButton("Weekly");
		templateRB.setActionCommand("0");
		templateRB.addActionListener(actionListener);
		currentRB.setSelected(true);

		ButtonGroup group = new ButtonGroup();
        group.add(currentRB);
        group.add(templateRB);

		//c.fill = GridBagConstraints.HORIZONTAL;
		c.anchor = GridBagConstraints.LINE_START;
		c.insets = new Insets(5,5,5,5);
		c.gridx = 0;
		c.gridy = 0;
		add(save, c);
		c.gridy++;
		add(nameL, c);
		c.anchor = GridBagConstraints.LINE_START;
		c.gridx++;
		c.gridwidth = 4;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(firstNameTF,c);
		c.fill = GridBagConstraints.NONE;
		c.gridy++;

		c.gridx = 0;
		c.gridwidth = 2;
		add(desiredHoursL, c);
		c.gridx+=2;
		c.gridwidth = 1;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(desiredHoursTF, c);
		c.fill = GridBagConstraints.NONE;
		c.gridx++;
		add(maxHoursL, c);
		c.gridx++;
		c.fill = GridBagConstraints.HORIZONTAL;
		add(maxHoursTF, c);
		c.fill = GridBagConstraints.NONE;
		c.gridy++;

		c.gridx = 0;
		c.gridwidth = 3;
		c.gridheight = 6;
		c.insets = new Insets(5,5,5,5);
		levelSlider = new JSlider(JSlider.VERTICAL,
                                      0, 5, 0);
		levelSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e) {
				sliderChanged();
    		}
		});
		levelSlider.setMajorTickSpacing(1);
		levelSlider.setPaintTicks(true);
		levelSlider.setSnapToTicks(true);
		//Create the label table
		Hashtable labelTable = new Hashtable();
		labelTable.put( new Integer( 0 ), new JLabel("0") );
		labelTable.put( new Integer( 1 ), new JLabel("1") );
		labelTable.put( new Integer( 2 ), new JLabel("2") );
		labelTable.put( new Integer( 3 ), new JLabel("3") );
		labelTable.put( new Integer( 4 ), new JLabel("4") );
		labelTable.put( new Integer( 5 ), new JLabel("5") );
		levelSlider.setLabelTable( labelTable );
		levelSlider.setPaintLabels(true);
		add(levelSlider, c);
		c.gridheight = 1;
		c.gridwidth = 1;
		c.gridx+=3;
		c.gridy++;
		c.insets = new Insets(5,5,5,5);
		c.gridwidth = 3;
		add(level1L, c);
		c.gridx+=3;
		c.gridwidth = 1;
		add(level2L, c);
		c.gridx-=3;
		c.gridy++;
		c.gridwidth = 3;
		add(description1L, c);
		c.gridx+=3;
		c.gridwidth = 1;
		add(description2L, c);
		c.gridwidth = 3;
		c.gridx-=3;
		c.gridy++;
		add(exampleTask1L, c);
		c.gridx+=3;
		c.gridwidth = 1;
		add(exampleTask2L, c);
		c.gridwidth = 3;
		c.gridy+=3;

		c.gridx = 0;
		c.gridwidth = 3;
		c.anchor = GridBagConstraints.LINE_START;
		add(availabilityL, c);
		c.gridx+=3;
		c.gridwidth = 1;
		add(templateRB,c);
		c.gridx++;
		add(currentRB,c);
		c.gridx++;
		c.fill = GridBagConstraints.BOTH;
		add(new JSeparator(SwingConstants.VERTICAL),c);
		c.fill = GridBagConstraints.NONE;
		c.gridx++;
		c.gridwidth = 3;
		add(availabilityDescriptionL, c);
		c.gridx = 0;
		c.gridy++;

		c.gridwidth = 8;
		add(availabilityHolderPanel, c);
	}
	public void setEmployee(Employee e){
		current = e;
		if (e==null){
			setEnabled(false);
			return;
		}
		setEnabled(true);
		firstNameTF.setText(e.firstName+" "+e.lastName);
		maxHoursTF.setText(""+e.maxHours);
		desiredHoursTF.setText(""+e.desiredHours);
		levelSlider.setValue(e.level);
		availabilityTemplate.setSchedule(e.available);
	}
	private void sliderChanged(){
		int index = levelSlider.getValue();
		level2L.setText(primary[index]);
		description2L.setText(secondary[index]);
		exampleTask2L.setText(ternary[index]);
	}
	private void write(){
		System.out.println("writing employee details");
	}
}