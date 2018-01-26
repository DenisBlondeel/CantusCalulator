package domain;

import UI.Observer;

/**
 * Created by bart on 23/01/18.
 */
public class Facade {
	
	private CantusVerzameling CV;

	public Facade()
	{
		this.CV = new CantusVerzameling();
	}
	
	public void passFile(String path)
	{
		CV.init(path);
	}
	
	public void drawTimeline()
	{
		CV.timeline();
	}

	public void drawPieChart(){}//CV.verenigingPie();}//CV.plaatsenPie();}
	
	public void addObserver(Observer pane)
	{
		CV.setObserver(pane);
	}

}
