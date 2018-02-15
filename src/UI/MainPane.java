package UI;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JPanel;

import domain.Cantus;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;


import static java.util.Collections.frequency;
import static org.jfree.util.SortOrder.DESCENDING;


public class MainPane extends JFrame implements Observer{

    private int semestercount = 1;
    private int summercount = 1;
    private int wintercount = 1;
    private static final long serialVersionUID = -9090407129402452701L;

    private Controller controller;
    private JPanel panel;
    private JSplitPane splitPaneH;

    public MainPane(Controller controller)
    {
        this.controller = controller;
        setTitle("Grafiekjes");
        panel = new JPanel();
        getContentPane().add(panel);
        splitPaneH = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        panel.add(splitPaneH, BorderLayout.EAST);
    }


    public void drawCantusTable(List<Cantus> cantussen){
		DefaultTableModel model = new DefaultTableModel();
		System.out.println("joepie");
		//Object[] column = {"Datum", "Cantus","Vereniging","plaats"};
		model.addColumn("Datum");
		model.addColumn("Cantus");
		model.addColumn("Vereniging");
		model.addColumn("plaats");
		cantussen.sort(Cantus::compareTo);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		for(Cantus cantus : cantussen){
			Object[] array = {df.format(cantus.datum.getTime()), cantus.naam, cantus.vereniging,cantus.plaats};
			model.addRow(array);
		}

		JTable table = new JTable(model);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		JScrollPane jtable = new JScrollPane(table);
		table.setPreferredScrollableViewportSize(table.getPreferredSize());
		table.setFillsViewportHeight(true);
		panel.add(jtable, BorderLayout.EAST);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS	);
		panel.setVisible(true);
		this.pack();
		this.setVisible(true);
	}

    /*
    gaat er van uit dat dataset gesorteerd is, kan niet in de tijd reizen.
     */
    //@Override
    public void drawPieChart(List<String> dataset) {
    	if(containsNotNull(dataset)){
			dataset.sort(String::compareTo);
    	} else {
    		return;
		}


        DefaultPieDataset ds = new DefaultPieDataset();
        for (String s: dataset){
			if(s != null && s.length()>0) {
				//System.out.println("string: " + s);
				if (ds.getKeys().contains(s)) {
					//System.out.println(ds.getValue(s));
					ds.setValue(s, ds.getValue(s).intValue() + 1);
				} else
					ds.setValue(s, 1);
			}
        }
        ds.sortByValues(DESCENDING);
		int count = 0;
		for(Object o: ds.getKeys()){
			if(ds.getValue((Comparable) o).intValue()<=dataset.size()/100){
				count++;
				ds.remove((Comparable) o);
			}

		}
		if (count!=0)
			ds.setValue("Overige",count);

		JFreeChart chart = ChartFactory.createPieChart(
                "TAAAAAART",                  // chart title
                ds,                // data
                true,                   // include legend
                true,
                false
        );

	splitPaneH.setLeftComponent(new ChartPanel(chart));

	panel.setVisible(true);
	this.pack();
	this.setVisible(true);
    }

    private boolean containsNotNull(List<String> l){
    	for(String s: l)
    		if(s != null && s.length()>0)
    			return true;
    	return false;
	}

	/*
	==========TIMELINE==========
	==========TIMELINE==========
	==========TIMELINE==========
	==========TIMELINE==========
	 */

	/*
	gaat er van uit dat dataset gesorteerd is, kan niet in de tijd reizen.
	 */
	@Override
	public void drawTimeline(List<Calendar> dataset)
	{
		dataset.sort(Calendar::compareTo);
		genCumul(dataset,7);

		JFreeChart chart = ChartFactory.createTimeSeriesChart(
				"Waar-liep-het-fout-lijn", // Chart
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
        plot.setDataset(1, new TimeSeriesCollection(genCumul(dataset,7)));
        plot.setDataset(2, new TimeSeriesCollection(genCumul(dataset,31)));

        // Renderer voor cumul7
        XYLineAndShapeRenderer r1 = new XYLineAndShapeRenderer(true,false);
        r1.setPaint(new Color(0,0,255));

        // Renderer voor cumul31
        XYLineAndShapeRenderer r2 = new XYLineAndShapeRenderer(true,false);
        r2.setPaint(new Color(0,255,0));

        plot.setRenderer(1,r1);
        plot.setRenderer(2,r2);

        splitPaneH.setRightComponent(new ChartPanel(chart));

        panel.setVisible(true);
        this.pack();
        this.setVisible(true);
	}

    private TimeSeriesCollection constructTSC(List<Calendar> dataset){
        TimeSeriesCollection tsc = new TimeSeriesCollection();
        TimeSeries series = new TimeSeries("Series1");
        int i = 0;

        Calendar previous,c;
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
					addCount(p);
                }
            }
            series.addOrUpdate(new Day(c.getTime()),i+1);
            i++;

        }
        tsc.addSeries(series);
        return tsc;
    }

    private TimeSeries genCumul(List<Calendar> ds, int i){
		TimeSeries series = new TimeSeries("Cumul" + i);
    	Calendar start = (Calendar) ds.get(0).clone();
    	Calendar end = (Calendar) ds.get(ds.size()-1).clone();
    	Calendar preStart= (Calendar) start.clone();
    	preStart.add(Calendar.DATE,-i);
    	int count=0;
    	while(start.before(end)){
    		if(ds.contains(preStart))
				count-=frequency(ds,preStart);
			if(ds.contains(start)){
				count+=frequency(ds,start);
			}
			//System.out.println(start.getTime()+ " cumul is " + count);
			series.addOrUpdate(new Day(start.getTime()),count);
    		start.add(Calendar.DATE,1);
			preStart.add(Calendar.DATE,1);
		}

    	return series;
	}

	public enum Period {
		SEMESTER, SUMMER, WINTER
	}

	private Period getPeriod(Calendar c){

		Calendar c2 = getStartYear(c);
		Period p;
		if (daysBetween(c2,c) < 89){
			p = Period.SEMESTER;
		}else if (daysBetween(c2,c) < 140){
			p = Period.WINTER;
		}else if (daysBetween(c2,c) < 243){
			p = Period.SEMESTER;
		}else {
			p = Period.SUMMER;
		}
		return p;
	}

	/*
berekend de start van het academiejaar voor datum c
de eerste maandag na de 20ste september die c voorafgaat
 */
	private Calendar getStartYear(Calendar c){
		Calendar c2 = Calendar.getInstance();
		c2.set(c.get(Calendar.YEAR),Calendar.SEPTEMBER,20);
		//========================System.out.println(df.format(c2.getTime()));
		nextMonday(c2);
		if(c.before(c2)) {
			c2.set(c.get(Calendar.YEAR) - 1, Calendar.SEPTEMBER, 20);
			nextMonday(c2);
		}
		return c2;
	}


	private void nextMonday(Calendar c){
		while(c.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY)
			c.add(Calendar.DATE,1);
	}

	private static long daysBetween(Calendar startDate, Calendar endDate) {
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
	private boolean diffPeriod(Calendar first, Calendar second){
		Calendar start = getStartYear(second);
		long d1 = daysBetween(start,first);
		long d2 = daysBetween(start,second);
		return (!first.after(start) ||
				(d1<89 && d2 >= 89) ||
				(d1<140 && d2 >= 140)||
				(d1<243 && d2 >= 243));
		}


	private int getCount(Period p){
		if(p.equals(Period.SEMESTER))
			return semestercount;
		if(p.equals(Period.SUMMER))
			return summercount;
		return wintercount;
	}

	private void addCount(Period p){
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
