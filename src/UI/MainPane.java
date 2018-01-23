package UI;

import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

public class MainPane extends JFrame implements Observer{
	
	private static final long serialVersionUID = -9090407129402452701L;
	
	private Controller controller;
	private JFrame frame;

	public MainPane(Controller controller)
	{
		this.controller = controller;
	}

	@Override
	public void drawTimeline(List<Date> dataset)
	{
		frame = new JFrame(); //creates new frame with set dimensions
		frame.setSize(950, 400);
		frame.setTitle("Plot");
		frame.setVisible(true);
		this.pack();
		this.setVisible(true);
		
	}

}
