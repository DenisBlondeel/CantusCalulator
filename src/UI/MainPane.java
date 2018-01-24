package UI;

import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.axis.DateAxis;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;

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
		int i = 1;
		TimeSeriesCollection ds = new TimeSeriesCollection();
		TimeSeries series = new TimeSeries("Series1");
		for(Date d: dataset){
			series.addOrUpdate(new Day(d),i);
			i++;
		}
		ds.addSeries(series);
		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"cantustijdlijn test", // Chart
				"Date", // X-Axis Label
				"cantus", // Y-Axis Label
				ds);

		XYPlot plot = (XYPlot)chart.getPlot();
		//plot.setBackgroundPaint(new Color(255,228,196));

		ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);

		frame.setVisible(true);
		
		DateAxis ax;
		
		this.pack();
		this.setVisible(true);
		
	}

}
