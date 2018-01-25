package UI;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

public interface Observer 
{
	void drawTimeline(List<Calendar> dataset);

	void drawPieChart(List<String> dataset);
}
