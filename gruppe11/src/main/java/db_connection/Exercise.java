package db_connection;

public class Exercise {

	private static String name;
	private static int id;
	
	public Exercise(String name, int id) {
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
