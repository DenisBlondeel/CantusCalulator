package domain;
import java.nio.file.FileStore;
import java.util.*;
import java.text.*;


import java.util.HashSet;

public class CantusVerzameling {
	public HashSet<Cantus> hs;
	
	CantusVerzameling(){
		this.hs = new HashSet<>();
	}

	public void init(String file){
	   Scanner sc = new Scanner(file);
	   CantusVerzameling CV = new CantusVerzameling();
	   String Firstline = sc.nextLine();

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
               hs.add(new Cantus(dateer(sc.next()),sc.next(),sc.next()));
           }
           System.out.println(hs);
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
