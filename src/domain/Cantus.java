package domain;
import java.util.Date;


public class Cantus {
	public Date datum;
	public String vereniging;
	public String naam;
	public String plaats;
	
	Cantus(Date datum, String vereniging, String naam, String plaats){
		this.datum = datum;
		this.vereniging = vereniging;
		this.naam = naam;
		this.plaats = plaats;
	}
}
