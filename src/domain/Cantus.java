package domain;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Cantus {


    public Date datum;
	public String vereniging;
	public String naam;
	public String plaats;
	
	public Cantus(Date datum, String naam, String vereniging, String plaats){
		this.datum = datum;
		this.vereniging = vereniging;
		this.naam = naam;
		this.plaats = plaats;
	}
	public Cantus(Date datum, String naam, String vereniging){
		this.datum = datum;
		this.vereniging = vereniging;
		this.naam = naam;
	}

	public Cantus(String str){
        String[] parts = str.split(",");
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

    public String toString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return "Cantus: " + naam + " op " + df.format(datum) + " bij " + vereniging;
	}



    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
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
