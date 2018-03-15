package db_connection;

public class Exercise {

	private String name;
	private int id;
	private Apparat ap;
	
	public Exercise(String name, int id) {
		this.name = name;
		this.id = id;
	}
	
	public Exercise(String name, int id, Apparat ap) {
		this.name = name;
		this.id = id;
		this.ap = ap;
	}
	
	public String getName() {
		return name;
	}
	
	public int getId() {
		return id;
	}
	
	public Apparat getApparat() {
		return ap;
	}
	
	@Override
	public String toString() {
		return name;
	}
	

	
	
}
