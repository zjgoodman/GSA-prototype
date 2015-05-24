import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
public class AvailabilityPanel extends JPanel {
	private final AvailabilityPanelCell[] hours;
	private final AvailabilityPanelCell[] days;
	private final AvailabilityPanelCell[][] cells;
	private final JScrollPane mScroll;
	private Week schedule;
	public AvailabilityPanel(){
		super(new GridBagLayout());
		GridBagConstraints outer = new GridBagConstraints();
		GridBagConstraints middle = new GridBagConstraints();

		JPanel middlePanel = new JPanel(new GridBagLayout());
		JPanel innerPanel = new JPanel(new GridLayout(24,7));

		days = new AvailabilityPanelCell[7];
		hours = new AvailabilityPanelCell[24];
		cells = new AvailabilityPanelCell[24][7];

		for (int i = 0; i < 24; i++) {
			for (int j = 0; j< 7; j++){
				cells[i][j] = new AvailabilityPanelCell(this);
				cells[i][j].setDayHour(j, i);
				cells[i][j].addListener();
				cells[i][j].setBackground(Color.LIGHT_GRAY);
				innerPanel.add(cells[i][j]);
			}
		}

		middle.gridx = 0;
		middle.gridy = 0;

		int time = 12;
		int pm = 0;
		for (int i = 0; i< 24; i++){
			hours[i] = new AvailabilityPanelCell(""+time+((pm>11)?"p":"a"));
			hours[i].setPreferredSize(new Dimension(60,20));
			hours[i].addAffector(cells[i]);
			pm++;
			if (++time > 12)
				time = 1;
			middlePanel.add(hours[i],middle);
			middle.gridy++;
		}
		middle.gridx++;
		middle.gridy = 0;
		middle.gridheight = 24;
		JScrollPane iScroll = new JScrollPane(innerPanel);
		iScroll.setPreferredSize(new Dimension(525,485));
		middlePanel.add(innerPanel,middle);

		outer.gridx = 0;
		outer.gridy = 0;
		JPanel empty = new JPanel();
		empty.setPreferredSize(new Dimension(60,20));
		add(empty,outer);
		outer.gridx++;
		for (int i = 0; i<7; i++){
			days[i] = new AvailabilityPanelCell((Days.getDay(i)).substring(0,3).toUpperCase());
			add(days[i],outer);
			AvailabilityPanelCell[] temp = new AvailabilityPanelCell[24];
			for (int j = 0; j<24; j++)
				temp[j] = cells[j][i];
			days[i].addAffector(temp);
			outer.gridx++;
		}
		empty = new JPanel();
		add(empty,outer);

		outer.gridx = 0;
		outer.gridy++;
		outer.gridwidth = 9;
		mScroll = new JScrollPane(middlePanel);
		mScroll.getVerticalScrollBar().setUnitIncrement(20);
		mScroll.setPreferredSize(new Dimension(533,360));
		mScroll.getViewport().setViewPosition(new Point(0, 121));
		add(mScroll,outer);
	}
	public void setSchedule(Week w){
		schedule = w;
		if (w==null){
			for (AvailabilityPanelCell[] array : cells)
				for (AvailabilityPanelCell c : array)
					c.deselect();
			return;
		}
		for (int i = 0; i<7; i++){
			Day d = w.days[i];
			int wholeDay = d.wholeDay;
			if (wholeDay<2){
				for (AvailabilityPanelCell[] cell : cells){
					if (wholeDay==1)
						cell[i].select(1);
					else cell[i].deselect();
				}
			}
			else {
				for (int j = 0; j<24;j++){
					int value = d.hours[j];
					cells[j][i].select(value);
				}
			}
		}
	}
	public void setAvailability(int day, int hour, int avCode){
		System.out.printf("Setting availability code for %s at %d to %d\n",Days.getDay(day),hour,avCode);
		schedule.days[day].change(hour,hour,avCode);
	}
	public void setAvailability(int day, int avCode){
		System.out.printf("Setting availability code for %s to %d\n",Days.getDay(day),avCode);
		if (avCode==0)
			schedule.days[day].free();
		else
			schedule.days[day].notFree();
	}
}