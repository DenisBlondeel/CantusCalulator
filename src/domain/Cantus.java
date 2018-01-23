package domain;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class Cantus {
	public Date datum;
	public String vereniging;
	public String naam;
	public String plaats;
	
	public Cantus(Date datum, String vereniging, String naam, String plaats){
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

	public String toString(){
		DateFormat df = new SimpleDateFormat("dd/MM/yyyy");
		return "Cantus: " + naam + " op " + df.format(datum) + " bij " + vereniging;
	}
}
