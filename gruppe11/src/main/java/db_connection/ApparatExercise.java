package db_connection;

public class ApparatExercise extends Exercise {
 
	private Apparat ap;
	public ApparatExercise(String name, int id, Apparat ap) {
		super(name, id);
		this.ap = ap;
	}
	
	public Apparat getApparat() {
		return ap;
	}
	
}
