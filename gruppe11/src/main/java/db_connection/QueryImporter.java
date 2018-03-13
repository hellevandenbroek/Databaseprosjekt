package db_connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

public class QueryImporter {
	
	public static String getQuery(String resourceName) {
		String fileContents = "";
		try (InputStream is = QueryImporter.class.getResourceAsStream(resourceName)){
			BufferedReader br = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
			StringBuilder sb = new StringBuilder();
			String line = null;
			while ((line = br.readLine()) != null) {
				sb.append(line.trim()).append('\n');
			}
			fileContents = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
			fileContents = "";
		}
		return fileContents;
	}

//	public static void main(String[] args) {
//		System.out.println(QueryImporter.getQuery("getDatagivers.sql"));
//	}
}
