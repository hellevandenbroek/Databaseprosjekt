package db_connection;

public class Apparat {
	private String name;
	private int id;
	
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
		return name;
	}
}
