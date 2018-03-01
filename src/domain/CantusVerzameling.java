package domain;

import java.util.*;
import UI.Observer;

public class CantusVerzameling {
	public List<Cantus> getCantussen() {
		return Cantussen;
	}

	private List<Cantus> Cantussen;
	private Observer observer;
	
	CantusVerzameling()
	{
		this.Cantussen = new ArrayList<>();
	}

	void init(String path)
	{
		Reader reader = new Reader(path);
		Scanner sc = reader.sc;
		sc.useDelimiter(",");
		String Firstline = sc.nextLine();
		System.out.println(Firstline);

		while (sc.hasNext())
		{
			Cantus cantus = new Cantus(sc.nextLine());
			//System.out.println(cantus);
			Cantussen.add(cantus);
		}
		System.out.println(Cantussen.size() + " cantussen in CV");

	}

	public List<Calendar> getData()
	{
		List<Calendar> ret = new ArrayList<>();
		for (Cantus c : this.Cantussen)
			ret.add(c.getDatum());
		return ret;
	}

	public List<String> getPlaatsen()
	{
		List<String> ret = new ArrayList<>();
		for (Cantus c : this.Cantussen) {
			ret.add(c.getPlaats());
			//System.out.println(c.getPlaats());
		}
		return ret;
	}

	public List<String> getVerenigingen()
	{
		List<String> ret = new ArrayList<>();
		for (Cantus c : this.Cantussen)
			ret.add(c.getVereniging());
		return ret;
	}
	
	public void deleteCantussen(ArrayList<String> l){
		for(String s: l){
		    Cantus c = new Cantus(s);
		    this.Cantussen.remove(c);
		    ListIterator<Cantus> iter = this.Cantussen.listIterator();
		    while(iter.hasNext()){
		        if(iter.next().equals(c)){
		            iter.remove();
			    System.out.println(c.toString());
			}
		    }
		}
		System.out.println("newSize =" +this.Cantussen.size());
	}

	void timeline()
	{
		System.out.println("draw timeline");
		observer.drawTimeline(getData());
	}

	void verenigingPie()
    {
        System.out.println("draw verenigingen piechart");
        observer.drawPieChart(getVerenigingen());
    }

	void plaatsenPie()
	{
		System.out.println("draw zalen piechart");
		observer.drawPieChart(getPlaatsen());
	}

	void cantusTable()
	{
		System.out.println("draw cantus tabel");
		observer.drawCantusTable(this.Cantussen);
	}


	void drawCompleet(){
		System.out.println("draw all");
		observer.drawCompleet(this);
	}


	void setObserver(Observer observer)
	{
		this.observer = observer;
	}
}
