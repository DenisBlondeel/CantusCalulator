package domain;
import java.util.*;
import java.text.*;

class CantusVerzameling {
    private Reader reader;
	public List<Cantus> Cantussen;
    public Scanner sc;
	
	CantusVerzameling(){
		this.Cantussen = new ArrayList<Cantus>();
	}

	public void init(String path){
	    this.reader = new Reader(path);
	    this.sc= reader.sc;
        sc.useDelimiter(",");
	   CantusVerzameling CV = new CantusVerzameling();
	   String Firstline = sc.nextLine();
        System.out.println(Firstline);

        while(sc.hasNext()){
            Cantus cantus= new Cantus(sc.nextLine().substring(1));
            System.out.println(cantus);
            Cantussen.add(cantus);
        }
        System.out.println(Cantussen.size() + " cantussen in CV");

	}

	private Date dateer(String s){
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
	    try {
            Date date = df.parse(s);
            if(date.getYear()<100)
                date.setYear(date.getYear()+2000);
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Date> getData(){
        List<Date> ret = new ArrayList<Date>();
        for(Cantus c: this.Cantussen)
            ret.add(c.getDatum());
	    return ret;
    }

    public List<String> getPlaatsen(){
        List<String> ret = new ArrayList<String>();
        for(Cantus c: this.Cantussen)
            ret.add(c.getPlaats());
        return ret;
    }

    public List<String> getVerenigingen(){
        List<String> ret = new ArrayList<String>();
        for(Cantus c: this.Cantussen)
            ret.add(c.getVereniging());
        return ret;
    }
}
