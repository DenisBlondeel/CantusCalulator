package domain;

/**
 * Created by bart on 23/01/18.
 */
public class Facade {
	
	private CantusVerzameling CV;

	public Facade()
	{
		this.CV = new CantusVerzameling();
	}
	
	public void passFile(String path)
	{
		CV.init(path);
	}

}
