package UI;

import domain.Cantus;

import java.util.Calendar;
import java.util.List;

public interface Observer 
{
	void drawTimeline(List<Calendar> dataset);

	void drawPieChart(List<String> dataset);

	void drawCantusTable(List<Cantus> dataset);


}
