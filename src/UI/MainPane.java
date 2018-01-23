package UI;

import java.util.Date;
import java.util.List;

public class MainPane implements Observer{
	
	private Controller controller;

	public MainPane(Controller controller)
	{
		this.controller = controller;
	}

	@Override
	public void drawTimeline(List<Date> dataset)
	{
		// TODO Auto-generated method stub
		
	}

}
