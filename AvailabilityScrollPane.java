import javax.swing.*;
import java.awt.*;
public class AvailabilityScrollPane extends JPanel{
	private final AvailabilityPanel availabilityPanel;
	private final AvailabilityPanelCell days[];
	private final AvailabilityPanelCell hours[];
	private final AvailabilityPanelCell cells[];
	private final GridBagConstraints c;
	public AvailabilityScrollPane(){
		super(new GridBagLayout());
		setPreferredSize(new Dimension(525,220));
		c = new GridBagConstraints();
		availabilityPanel = new AvailabilityPanel();
		cells = availabilityPanel.getCells();
		days = new AvailabilityPanelCell[7];
		hours = new AvailabilityPanelCell[24];

		c.gridx = 0;
		c.gridy = 0;
		add(new AvailabilityPanelCell(),c);
		c.gridx++;
		for (int i = 0; i<7; i++){
			days[i] = new AvailabilityPanelCell(Days.getDay(i));
			add(days[i],c);
			c.gridx++;
		}
		c.gridx = 0;
		c.gridy++;

		JPanel panel = new JPanel(new GridBagLayout());
		GridBagConstraints pc = new GridBagConstraints();
		pc.gridx = 0;
		pc.gridy = 0;

		int time = 12;
		int pm = 0;
		for (int i = 0; i< 24; i++){
			hours[i] = new AvailabilityPanelCell(""+time+((pm>11)?"p":"a"));
			pm++;
			if (++time > 12)
				time = 1;
			panel.add(hours[i],pc);
			c.gridy++;
		}
		pc.gridx++;
		pc.gridy = 0;
		pc.gridheight = 24;
		panel.add(new JScrollPane(availabilityPanel));

		c.gridx = 1;
		c.gridy = 1;
		c.gridwidth = 7;
		c.gridheight = 24;
		add(new JScrollPane(panel),c);
	}
}