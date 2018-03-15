package db_connection;


public class Main {

	public static void main(String[] args) {
		Credentials test = new Credentials(
				"mysql.stud.ntnu.no", 
				3306,
				"sandeb_dbProsjekt",
				"trulsabe",
				"zenbook4");
		QueryImporter qi = new QueryImporter();
		String pulse = qi.getQuery("getDatagivers_PulseData.sql");
		DataSourceBuilder dsb = new DataSourceBuilder(test);
		
	}
}
