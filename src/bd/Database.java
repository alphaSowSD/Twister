package bd;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Collection;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;

public class Database {

	private DataSource dataSource;
	private static Database database;
	
	public Database(String jndiname) throws SQLException{
		try{
			dataSource = (DataSource) new InitialContext().lookup("java:com/env/" + jndiname);
		}catch(NamingException e){
			throw new SQLException(jndiname + "is missing in JNDI! : "+e.getMessage());
		}
	}
	
	public Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
	
	public static Connection getMySQLConnection() throws SQLException{
		if(DBStatic.mysql_pooling == false){
			return (DriverManager.getConnection("jdbc:mysql://"+DBStatic.mysql_host+"/"+DBStatic.mysql_db,DBStatic.mysql_user_name, DBStatic.mysql_password));
		}else{
			if(database == null){
				database = new Database("jdbc/db");
			}
			return (database.getConnection());
		}
	} 
	
	public static DBCollection getConnection(String nomCollection) throws UnknownHostException{
		
		Mongo m = new Mongo(DBStatic.mongo_url);
		DB db = m.getDB(DBStatic.mongo_db);
		DBCollection collection = db.getCollection(nomCollection);

		return collection;
	}
	
	public static void main(String args[]){
		
		try {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
			} catch (InstantiationException e) {
				
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				
				e.printStackTrace();
			}
			Connection c = getMySQLConnection();
		} catch (SQLException e) {
			
			e.printStackTrace();
		}
	}
}
