package domain;
import java.nio.file.FileStore;
import java.util.*;
import java.text.*;
import java.io.FileNotFoundException;

import java.util.HashSet;

public class CantusVerzameling {
    private Reader reader;
	public HashSet<Cantus> hs;
    public Scanner sc;
	
	CantusVerzameling(){
		this.hs = new HashSet<>();
	}

	public void init(String path){
	    this.reader = new Reader(path) ;
	    this.sc= reader.sc;
        System.out.println(sc.hasNext());
	   CantusVerzameling CV = new CantusVerzameling();
	   String Firstline = sc.nextLine();
        System.out.println(Firstline);

	   if(Firstline.toLowerCase().contains("plaats".toLowerCase())){
           System.out.println("plaats in eerste lijn");
           while(sc.hasNext()){
               hs.add(new Cantus(dateer(sc.next()),sc.next(),sc.next(),sc.next()));
           }
           System.out.println(hs);
       }
       else{
           System.out.println("plaats niet in eerste lijn");
           while(sc.hasNext()){
               System.out.println("yay");
               hs.add(new Cantus(dateer(sc.next()),sc.next(),sc.next()));
           }
           System.out.println("yay2");
           System.out.println(hs.size());
       }

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
}
