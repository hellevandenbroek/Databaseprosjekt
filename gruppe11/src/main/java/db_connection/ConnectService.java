package db_connection;
import java.sql.SQLException;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;

public class ConnectService {
	private static ConnectService instance = null;
	@SuppressWarnings("unused")
	private MysqlDataSource datasource;
	
    public static ConnectService getInstance() throws SQLException{
        if (instance == null) {
            instance = new ConnectService();
        }
        return instance;
        
    }
    
    private ConnectService() throws SQLException {
        this.datasource = getDatasource();
    }
    
    public static MysqlDataSource getDatasource(){
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setServerName("mysql.stud.ntnu.no");
        dataSource.setDatabaseName("sandeb_dbProsjekt");
        dataSource.setUser("sandeb");
        dataSource.setPassword("sander4");

        return dataSource;
    }
}
