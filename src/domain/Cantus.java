package domain;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Cantus implements Comparable<Cantus> {


    public Calendar datum;
    public String vereniging;
    public String naam;
    public String plaats;
	
	public Cantus(String str){
        String[] parts = str.split(",");
        this.datum = dateer(parts[0]);
        this.naam = parts[1];
        this.vereniging = parts[2].toLowerCase();
        this.plaats = (parts.length>3?parts[3]:null);
        //System.out.println(this );
    }

    @Override
    public int compareTo(Cantus cantus) {
        return this.datum.compareTo(cantus.datum);
    }

    private Calendar dateer(String s){
        //System.out.println(s);
        DateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        if(s.contains("/")){
            df = new SimpleDateFormat("dd/MM/yyyy");
        }

        try {
            Date d = df.parse(s);
            Calendar date = Calendar.getInstance();
            date.setTime(d);
            if(date.get(Calendar.YEAR)<100)
                date.set(Calendar.YEAR,date.get(Calendar.YEAR)+2000);

            //System.out.println(date.getTime());
            return date;

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return "Cantus: " + naam + " op " + df.format(datum.getTime()) + " bij " + vereniging;
	}



    public Calendar getDatum() {
        return datum;
    }

    public void setDatum(Calendar datum) {
        this.datum = datum;
    }

    public String getVereniging() {
        return vereniging;
    }

    public void setVereniging(String vereniging) {
        this.vereniging = vereniging;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getPlaats() {
        return plaats;
    }

    public void setPlaats(String plaats) {
        this.plaats = plaats;
    }
}
