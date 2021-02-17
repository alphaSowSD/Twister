package bd;

import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Random;

import org.bson.types.ObjectId;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

public class MessageTools {
	
	public MessageTools(){
		
	}

	public static void addToBDMessage(String key, String content){
		
		try {
			DBCollection message  = Database.getConnection("messages");
			BasicDBObject dbo = new BasicDBObject();
			GregorianCalendar calendar = new GregorianCalendar();
			Date date_jour = calendar.getTime();
			
			Connection c = Database.getMySQLConnection();
			
			// Récupérer l'id de l'utilisateur qui ajoute l'ami
			String querySelectUser = "SELECT * FROM new_session WHERE cle = \""+key+"\";";
			Statement stUser = c.createStatement();
			stUser.executeQuery(querySelectUser);
			ResultSet curseur = stUser.getResultSet();
			
			int idUser = 0;
			
			if(curseur.next()){ // utilisateur exists
				idUser = curseur.getInt(2);
			}
			
			Random rand = new Random();
			
			dbo.put("_id", key+rand.nextInt(100));
			dbo.put("user_id", idUser);
			dbo.put("date", date_jour );
			dbo.put("content", content);
			dbo.put("likes", 0);
	
			message.insert(dbo);
			
		} catch (UnknownHostException | SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static void deleteToBDMessage(String key, String idMessage) {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection c = Database.getMySQLConnection();
			
			// Récupérer l'id de l'utilisateur qui ajoute l'ami
			String querySelectUser = "SELECT * FROM new_session WHERE cle = \""+key+"\";";
			Statement stUser = c.createStatement();
			stUser.executeQuery(querySelectUser);
			ResultSet curseur = stUser.getResultSet();
			
			int idUser = 0;
			
			if(curseur.next()){ // utilisateur exists
				idUser = curseur.getInt(2);
			}
			
			stUser.close();
			
			DBCollection message  = Database.getConnection("messages");
			BasicDBObject dbo = new BasicDBObject();
			
			dbo.put("_id", idMessage);
			dbo.put("user_id", idUser);
			
			message.remove(dbo);
			
		} catch (UnknownHostException | InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {	
			e.printStackTrace();
		} 
	}
	
	public static List<DBObject> getBDMessage(String key, int [] users){
		
		List<DBObject> ret = new ArrayList();
		
		DBCollection message;
		
		try {
			Connection co = Database.getMySQLConnection();
			
			// Récupérer l'id de l'utilisateur
			String querySelectUser = "SELECT * FROM new_session WHERE cle = \""+key+"\";";
			Statement stUser = co.createStatement();
			stUser.executeQuery(querySelectUser);
			ResultSet curseur = stUser.getResultSet();
			
			int idUser = 0;
			
			if(curseur.next()){ // utilisateur exists
				idUser = curseur.getInt(2);
			}
			
			message = Database.getConnection("messages");
			BasicDBObject query = new BasicDBObject();
			query.put("user_id", new BasicDBObject("$in", users));
			
			// Pour trier les messages par ordre de publication.
			
			BasicDBObject tri = new BasicDBObject();
			tri.put("date", -1);
			
			DBCursor c = message.find(query).sort(tri);
			
			while(c.hasNext()){
				ret.add(c.next());
			}
		} catch (UnknownHostException | SQLException e) {
			e.printStackTrace();
		}
		
		return ret;
	}
	
	public static boolean isYourMessage(String key, String idMessage){
		
		boolean retour = false;
		DBCollection message;
		
		try {
			
			Connection co = Database.getMySQLConnection();
			
			// Récupérer l'id de l'utilisateur
			String querySelectUser = "SELECT * FROM new_session WHERE cle = \""+key+"\";";
			Statement stUser = co.createStatement();
			stUser.executeQuery(querySelectUser);
			ResultSet curseur = stUser.getResultSet();
			
			int idUser = 0;
			
			if(curseur.next()){ // utilisateur exists
				idUser = curseur.getInt(2);
			}
			
			message = Database.getConnection("messages");
			BasicDBObject query = new BasicDBObject();
			query.put("_id", idMessage);
			
			DBCursor c = message.find(query);
			
			DBObject obj = null;
			
			
			if(c.hasNext()){
				obj = c.next();
				if(Integer.parseInt(obj.get("user_id").toString()) == idUser){
					retour = true;
				}	
			}
			
		} catch (UnknownHostException | SQLException e) {
			e.printStackTrace();
		} 
		
		return retour;
	}
	
}
