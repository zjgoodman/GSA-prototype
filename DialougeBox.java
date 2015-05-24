import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicBoolean;
public class DialougeBox extends JFrame {
	private final GridBagConstraints c;
	private JButton
		yes, no;
	private JLabel question;
	private GSAFrame frame;
	public DialougeBox(String q,GSAFrame v){
		super("Warning:");
		setSize(300,100);
		setResizable(false);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setLayout(new GridBagLayout());
		c = new GridBagConstraints();
		yes = new JButton("Yes");
		no = new JButton("No");
		question = new JLabel(q);
		frame = v;

		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		c.weighty = 0.5;
		add(question,c);
		c.gridy++;
		c.weighty = 0.0;
		c.weightx = 0.33;
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridwidth = 1;
		add(yes,c);
		c.gridx++;
		add(new JLabel(),c);
		c.gridx++;
		add(no,c);

		yes.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				frame.empMngr.clearSchedule();
				setVisible(false);
				frame.setEnabled(true);
			}
		});

		no.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setVisible(false);
				frame.setEnabled(true);
			}
		});
	}
}