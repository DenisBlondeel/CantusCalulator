package UI;

import java.util.Date;
import java.util.List;

import javax.swing.JFrame;

public class MainPane implements Observer{
	
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
		
	}

}
