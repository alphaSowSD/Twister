package bd;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBStatic {

	public static boolean mysql_pooling = false;
	public static String mysql_host = "localhost";
	public static String mysql_db = "sow_pidery";
	public static String mysql_user_name = "root";
	public static String mysql_password = "root";
	
	public static String mongo_url = "localhost";
	public static String mongo_db = "sow_pidery";
	
			
	public DBStatic(){
		
	}
	
}
