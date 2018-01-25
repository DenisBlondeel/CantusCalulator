package UI;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

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

	int semestercount = 1;
	int summercount = 1;
	int wintercount = 1;
	private static final long serialVersionUID = -9090407129402452701L;
	
	private Controller controller;
	private JFrame frame;

	public MainPane(Controller controller)
	{
		this.controller = controller;
	}

	/*
	gaat er van uit dat dataset gesorteerd is, kan niet in de tijd reizen.
	 */
	@Override
	public void drawTimeline(List<Calendar> dataset)
	{
		dataset.sort(Calendar::compareTo);
		frame = new JFrame(); //creates new frame with set dimensions
		frame.setSize(950, 400);
		frame.setTitle("Plot");


		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"cantustijdlijn test", // Chart
				"Date", // X-Axis Label
				"cantus", // Y-Axis Label
				constructTSC(dataset));

		XYPlot plot = (XYPlot)chart.getPlot();
		plot.setBackgroundPaint(new Color(255,255,255));
        plot.setDomainGridlinePaint(new Color(155,155,155));
        plot.setRangeGridlinePaint(new Color(155,155,155));

        for (int i = 0; i < plot.getSeriesCount(); i++) {
            plot.getRenderer().setSeriesStroke(i,new BasicStroke(2.0f));
        }

        ChartPanel panel = new ChartPanel(chart);
		setContentPane(panel);

		frame.setVisible(true);
		
		DateAxis ax;
		
		this.pack();
		this.setVisible(true);
		
	}

    public TimeSeriesCollection constructTSC(List<Calendar> dataset){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries("Series1");
        int i = 0;

        Calendar previous,c;
        //Calendar c = dataset.get(1);
        //System.out.println(series.getKey());
        while(i < dataset.size()){
            c = dataset.get(i);

            Period p = getPeriod(c);
            if(i == 0){
                series.setKey(p.name().toLowerCase() + "1");
                addCount(p);
            }
            else {
                previous = dataset.get(i-1);
                if (diffPeriod(previous,c)) {
                    series.addOrUpdate(new Day(c.getTime()),i+1);
                    tsc.addSeries(series);
                    series = new TimeSeries(p.name().toLowerCase() + getCount(p));
                    //System.out.println("diff: " + df.format(c.getTime())+ " start " + p.name().toLowerCase() + getCount(p));
					System.out.println( df.format(c.getTime()) + "startte " + p.name().toLowerCase() + getCount(p));
					addCount(p);
                }


            }
            series.addOrUpdate(new Day(c.getTime()),i+1);
            i++;

        }
        //System.out.println(series.getItemCount());
        tsc.addSeries(series);
        return tsc;
    }

	public enum Period {
		SEMESTER, SUMMER, WINTER
	}

	public Period getPeriod(Calendar c){

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		Calendar c2 = getStartYear(c);
		Period p;
		//System.out.println(df.format(c.getTime()) + " was " + daysBetween(c2,c) + " after start of semester " + df.format(c2.getTime()));
		if (daysBetween(c2,c) < 89){
			p = Period.SEMESTER;
		}else if (daysBetween(c2,c) < 140){
			p = Period.WINTER;
		}else if (daysBetween(c2,c) < 243){
			p = Period.SEMESTER;
		}else {
			p = Period.SUMMER;
		}
		//System.out.println(df.format(c.getTime()) + " was in de " + p.name().toLowerCase());
		return p;
	}

	/*
berekend de start van het academiejaar voor datum c
de eerste maandag na de 20ste september die c voorafgaat
 */
	public Calendar getStartYear(Calendar c){
		Calendar c2 = Calendar.getInstance();
		c2.set(c.get(c.YEAR),8,20);
		//========================System.out.println(df.format(c2.getTime()));
		nextMonday(c2);
		if(c.before(c2)) {
			c2.set(c.get(c.YEAR) - 1, 8, 20);
			nextMonday(c2);
		}
		return c2;
	}


	private void nextMonday(Calendar c){
		while(c.get(c.DAY_OF_WEEK)!=2)
			c.add(c.DATE,1);
	}

	public static long daysBetween(Calendar startDate, Calendar endDate) {
		long end = endDate.getTimeInMillis();
		long start = startDate.getTimeInMillis();
		return (end - start)/(60*60*24*1000) +1;
	}




    /*
    kijkt na of first en second in een verschillende periode liggen:
    valt first voor de start van het academiejaar van second?
    semester 1: start academiejaar .. 89 dagen later
    winter: 89dagen na start .. 140 dagen na start
    semester 2: 140 dagen na start .. 191 dagen na start
    zomer: 191 dagen na start .. volgende academiejaar.
     */
	public boolean diffPeriod(Calendar first, Calendar second){
		Calendar start = getStartYear(second);
		long d1 = daysBetween(start,first);
		long d2 = daysBetween(start,second);

		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		System.out.println(df.format(start.getTime()) +" + "+ d2 + " = second =" +  df.format(second.getTime()));
		if((!first.after(start) ||
				(d1<89 && d2 >= 89) ||
				(d1<140 && d2 >= 140)||
				(d1<243 && d2 >= 243))){

			//System.out.println(df.format(start.getTime()) +" + "+ d1 + " = first =" +  df.format(first.getTime()));
			//System.out.println(df.format(start.getTime()) +" + "+ d2 + " = second =" +  df.format(second.getTime()));
		}
		return (!first.after(start) ||
				(d1<89 && d2 >= 89) ||
				(d1<140 && d2 >= 140)||
				(d1<243 && d2 >= 243));
		}


	public int getCount(Period p){
		if(p.equals(Period.SEMESTER))
			return semestercount;
		if(p.equals(Period.SUMMER))
			return summercount;
		return wintercount;
	}

	public void addCount(Period p){
		if(p.equals(Period.SEMESTER)) {
			//System.out.println("add semester count");
			semestercount++;
		}
		if(p.equals(Period.SUMMER))
			summercount++;
		if(p.equals(Period.WINTER))
			wintercount++;
	}

}
