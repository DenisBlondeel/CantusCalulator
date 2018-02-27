package UI;

import java.awt.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import java.lang.Integer;
import domain.Cantus;
import domain.CantusVerzameling;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.util.ShapeUtilities;

import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import javax.swing.JScrollPane;


import static java.util.Collections.frequency;
import static org.jfree.util.SortOrder.DESCENDING;

import java.awt.GridLayout;
import java.awt.GridBagLayout;

import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import java.awt.Shape;

public class MainPane extends JFrame implements Observer{

    private int semestercount = 1;
    private int summercount = 1;
    private int wintercount = 1;
    private static final long serialVersionUID = -9090407129402452701L;

    private boolean plaatsFlag;//true als er plaatsen in de file staan, false anders
    private Controller controller;
    private JPanel panel;
    private JSplitPane splitPaneH;

    public MainPane(Controller controller)
    {
	System.out.println("draw mainpain");
	this.controller = controller;
        setTitle("Grafiekjes");
    }
    
    //teken al de charts en tabel.
    public void drawCompleet(CantusVerzameling CV){
	// SETUP
	JPanel jpanel = new JPanel();
	controller.screen.mainFrame.add(jpanel);
	GridBagLayout g = new GridBagLayout();
	jpanel.setLayout(g);
		

	//Get the charts and table
    	this.plaatsFlag=containsNotNull(CV.getPlaatsen());
		
	JScrollPane tabel = makeScrollTable(CV.getCantussen());

	JFreeChart vereniggingenchart = makePieChart(CV.getVerenigingen());	
	vereniggingenchart.getPlot().setBackgroundPaint(Color.WHITE);
	vereniggingenchart.setTitle("Verenigingen");
	ChartPanel vereniggingenchartpanel = new ChartPanel(vereniggingenchart);

	JFreeChart plaatsenchart = makePieChart(CV.getPlaatsen());
	ChartPanel plaatsenchartpanel =  new ChartPanel(plaatsenchart);
				
	if(plaatsFlag){
	    plaatsenchart.setTitle("Locaties");
	    plaatsenchart.getPlot().setBackgroundPaint(Color.WHITE);
	}
	ChartPanel timechart =  new ChartPanel(makeTimeLine(CV.getData()));
		
		
	//Add the charts to the panel in the right column/rows met de juiste verhoudingen.
	GridBagConstraints c = new GridBagConstraints();   
 	c.gridx = 0; c.gridy = 0; c.gridwidth=2; c.weightx=0.7; c.weighty=0.8;
	c.fill = GridBagConstraints.BOTH;
	jpanel.add(timechart,c);
	
	c=new GridBagConstraints();
	c.gridx=0; c.gridy=1; c.weightx=0.35; c.weighty=.2;
	if(!plaatsFlag)
	    c.gridwidth=2;
	c.fill = GridBagConstraints.BOTH;
	jpanel.add(vereniggingenchartpanel,c);
		
	if(plaatsFlag){
	    c=new GridBagConstraints();
	    c.gridx=1; c.gridy=1; c.weightx=.35; c.weighty=.2; 
	    c.fill = GridBagConstraints.BOTH;
	    jpanel.add(plaatsenchartpanel,c);
	}
		
	c=new GridBagConstraints();
	c.gridx=2; c.gridy=0; c.weightx=1; c.weighty=.1; c.gridheight=2; c.gridwidth=1;
	c.fill = GridBagConstraints.BOTH;//.VERTICAL;
	jpanel.add(tabel,c);

	JTable t = (JTable) tabel.getViewport().getView();
	
	//update als er iets word aangeduid in de tabel
	t.getSelectionModel().addListSelectionListener(e -> {
            updateRows(t.getSelectedRows(), t,timechart,vereniggingenchartpanel,plaatsenchartpanel);
        });

        controller.screen.mainFrame.setVisible(true);
    }
	
    //update de charts aan de hand van de rijen die aangeduid zijn in de tabel
    public void updateRows(int[] rows, JTable t, ChartPanel chartt,ChartPanel chartv, ChartPanel chartp){
        //aanduiden in timechart
	JFreeChart c = chartt.getChart();
	XYPlot xyp = c.getXYPlot();
	XYLineAndShapeRenderer r = (XYLineAndShapeRenderer) xyp.getRenderer();
	TimeSeriesCollection tsc = (TimeSeriesCollection) xyp.getDataset();
	if(tsc.getSeries("invisible")==null){
	    tsc.addSeries( new TimeSeries("invisible"));
            Shape shape = new java.awt.geom.Ellipse2D.Double(-2.0, -2.0, 5.0, 5.0);
            r.setSeriesShape(tsc.getSeriesIndex("invisible"), shape);
            r.setSeriesShapesVisible(tsc.getSeriesIndex("invisible"),true);
            r.setSeriesLinesVisible(tsc.getSeriesIndex("invisible"), false);
            r.setSeriesVisibleInLegend(tsc.getSeriesIndex("invisible"),false );
            r.setSeriesPaint(tsc.getSeriesIndex("invisible"), Color.black);
	    }
        TimeSeries s = tsc.getSeries("invisible");
        s.clear();
	HashSet<String> pset = new HashSet<>();
	HashSet<String> vset = new HashSet<>();
	for(int row: rows){
	    for(int i=0; i<tsc.getSeriesCount();i++){
	        TimeSeries ts = tsc.getSeries(i);
		for(int j=0; j<ts.getItemCount();j++){
		    if(samedate(t.getValueAt(row,0).toString(), (Day) ts.getDataItem(j).getPeriod()))
                        s.addOrUpdate(ts.getDataItem(j).getPeriod(), ts.getDataItem(j).getValue());
		}
	    }	
    	    //voorbereidend werk voor de piecharts te exploden
	    if(plaatsFlag)
		    pset.add(t.getValueAt(row,3).toString());
	    vset.add(t.getValueAt(row,2).toString());
	    }
	//explode de piecharts
	for(Object o:  ((PiePlot) chartv.getChart().getPlot()).getDataset().getKeys()){
	    String str = o.toString();
	    if(vset.contains(str))
	        ((PiePlot) chartv.getChart().getPlot()).setExplodePercent(str,.33);
    	    else if(str!=null)	    
	        ((PiePlot) chartv.getChart().getPlot()).setExplodePercent(str,0);
	}
	if(plaatsFlag){
   	    for(Object o:  ((PiePlot) chartp.getChart().getPlot()).getDataset().getKeys()){
	        String str = o.toString();
  	        if(pset.contains(str))
	            ((PiePlot) chartp.getChart().getPlot()).setExplodePercent(str,.33);
    	        else if(str!=null)	    
	            ((PiePlot) chartp.getChart().getPlot()).setExplodePercent(str,0);
	    }
	}
    }

    //kijk na of een string die uit de tabel komt dezelfde dag voorstelt als een dag die uit de timechart is gehaald.
    public boolean samedate(String s, Day d){
	String[] parts = s.split("/");
	Day sd = new Day(Integer.parseInt(parts[0]),Integer.parseInt(parts[1]),Integer.parseInt(parts[2]));
	return(sd.equals(d));
    }

    //draw de cantusTable, wordt momenteel niet meer gebruikt, kan later terug nodig zijn.
    public void drawCantusTable(List<Cantus> cantussen){

		JScrollPane jtable = makeScrollTable(cantussen);
		panel.add(jtable);
		panel.setVisible(true);
		this.pack();
		this.setVisible(true);
	}

    //construeer de scrollTable met een tabel van alle cantussen
    public JScrollPane makeScrollTable(List<Cantus> cantussen){
		DefaultTableModel model = new DefaultTableModel();
		model.addColumn("Datum");
		model.addColumn("Cantus");
		model.addColumn("Vereniging");
		if(plaatsFlag)
			model.addColumn("plaats");
		cantussen.sort(Cantus::compareTo);
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		if(plaatsFlag){
			for(Cantus cantus : cantussen){
				Object[] array = {df.format(cantus.datum.getTime()), cantus.naam, cantus.vereniging,cantus.plaats};
				model.addRow(array);
			}
		} else {
			for(Cantus cantus : cantussen){
				Object[] array = {df.format(cantus.datum.getTime()), cantus.naam, cantus.vereniging};
				model.addRow(array);
			}			
		}
		JTable table = new JTable(model);

		this.setExtendedState(JFrame.MAXIMIZED_BOTH);
		return new JScrollPane(table);
	}

    //drawt een pieChart; wordt momenteel niet meer gebruikt, kan later terug nodig zijn.
    //@Override
    public void drawPieChart(List<String> dataset) {

		JFreeChart chart = makePieChart(dataset);

		splitPaneH.setLeftComponent(new ChartPanel(chart));

		panel.setVisible(true);
		this.pack();
		this.setVisible(true);
    }

    //gaat na of een lijst een niet null element bevat. nodig om te kijken of er plaatsen zijn ingegeven.
    private boolean containsNotNull(List<String> l){
    	for(String s: l)
    		if(s != null && s.length()>0)
    			return true;
    	return false;
	}

    //construeer een piechart van een dataset. wordt gebruikt om de verenigingen en de plaatsen in een piechart te zetten.
    public JFreeChart makePieChart(List<String> dataset){
		if(plaatsFlag){
			dataset.sort(String::compareTo);
		} else {
			return null;
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

		return ChartFactory.createPieChart(
				"TAAAAAART",                  // chart title
				ds,                // data
				true,                   // include legend
				true,
				false);
	}


	//drawt de timeline; wordt momenteel niet gebruikt, kan later terug nodig zijn.
	@Override
	public void drawTimeline(List<Calendar> dataset){
        splitPaneH.setRightComponent(new ChartPanel(makeTimeLine(dataset)));
        panel.setVisible(true);
        this.pack();
        this.setVisible(true);
	}

	//construeert de timeline
	public JFreeChart makeTimeLine(List<Calendar> dataset){
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
		return chart;
	}
	
    //construeer de timeseriescollection van de lijst van data.
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

    //genereer de cumulatieve lijn voor de laatste %i dagen.
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

    	//enum van mogelijke periodes
	public enum Period {
		SEMESTER, SUMMER, WINTER
	}
	
	//kijk na in welke periode een dag valt.
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

	//vormt een dag om naar de eerstvolgende maandag.
	private void nextMonday(Calendar c){
		while(c.get(Calendar.DAY_OF_WEEK)!=Calendar.MONDAY)
			c.add(Calendar.DATE,1);
	}
	
	//geef het aantal dagen tussen 2 dagen terug
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

	//geef aan de hoeveelste keer dat een periode gebruikt is.
	private int getCount(Period p){
		if(p.equals(Period.SEMESTER))
			return semestercount;
		if(p.equals(Period.SUMMER))
			return summercount;
		return wintercount;
	}

	//tel 1 op bij de bijhorende periodcount van de period
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
