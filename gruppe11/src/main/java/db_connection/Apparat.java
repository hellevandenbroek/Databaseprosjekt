package db_connection;

public class Apparat {
	private String name;
	private int id;
	
	private int kilo;
	private int sett;
	
	// with apparat
	public Apparat(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	@Override
	public String toString() {
		return name + " " + kilo + " " + sett ;
	}
	
	public int getKilo() {
		return kilo;
	}
	
	public void setKilo(int kilo) {
		this.kilo = kilo;
	}

	public int getSett() {
		return sett;
	}

	public void setSett(int sett) {
		this.sett = sett;
	}
 }
