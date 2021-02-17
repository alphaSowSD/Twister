package bd;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class UserTools {

	public UserTools(){
		
	}
	
	public static boolean userExists(String login){
		
		boolean retour = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			try {
				Connection c = Database.getMySQLConnection();
				String query = "SELECT * FROM user WHERE login = \""+login+"\";";
				Statement st = c.createStatement();
				st.executeQuery(query);
				ResultSet curseur = st.getResultSet();
				
				if(curseur.next()){ // utilisateur exists
					retour = true;
				}
				else{
					retour = false;
				}
				
				curseur.close();
				st.close();
				c.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return retour;
		
	}

	public static int getIdUserByLogin(String login) {
		// Récupérer l'id de l'utilisateur
		int idUser = -1;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection c = Database.getMySQLConnection();
			String querySelectUser = "SELECT id FROM user WHERE login = \""+login+"\";";
			Statement stUser = c.createStatement();
			stUser.executeQuery(querySelectUser);
			ResultSet curseur = stUser.getResultSet();
			
			if(curseur.next()){ // utilisateur exists
				idUser = curseur.getInt(1);
			}
			
		}catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 		

		return idUser;
	}
	
	public static int getIdUserByKey(String key) {
		// Récupérer l'id de l'utilisateur
		int idUser = -1;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection c = Database.getMySQLConnection();
			String querySelectUser = "SELECT idUser FROM new_session WHERE cle = \""+key+"\";";
			Statement stUser = c.createStatement();
			stUser.executeQuery(querySelectUser);
			ResultSet curseur = stUser.getResultSet();
			
			if(curseur.next()){ // utilisateur exists
				idUser = curseur.getInt(1);
			}
			
		}catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 		

		return idUser;
	}
	
	public static void addToBDUser(String nom, String prenom, String user, String mdp, String mail){
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection c = Database.getMySQLConnection();
			String query = "INSERT INTO user (login, mail, nom, prenom, pwd) values (\""+user+"\",\""+mail+"\",\""+nom+"\",\""+prenom+"\",PASSWORD(\""+mdp+"\"));";
			Statement st = c.createStatement();
			int valide = st.executeUpdate(query);
			
			System.out.println(valide);
			st.close();
			c.close();

		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void deleteToBDUser(String user){
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection c = Database.getMySQLConnection();
			String query = "DELETE FROM user WHERE login = (\""+user+"\");";
			Statement st = c.createStatement();
			int valide = st.executeUpdate(query);
			
			System.out.println(valide);
			st.close();
			c.close();
			
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		
	}
	
	public static boolean checkPassword (String user, String mdp){
		boolean retour = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection c = Database.getMySQLConnection();
			String query = "SELECT * FROM user WHERE login = \""+user+"\" AND pwd = PASSWORD(\""+mdp+"\");";
			Statement st = c.createStatement();
			st.executeQuery(query);
			ResultSet curseur = st.getResultSet();
			
			if(curseur.next()){
				retour = true;
			}
			else{
				retour = false;
			}
			
			curseur.close();
			st.close();
			c.close();
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		
		return retour;
	}
	
	public static boolean isConnected(String user) {
		boolean retour = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection c = Database.getMySQLConnection();
			
			// Récupérer l'id de l'utilisateur
			String querySelectUser = "SELECT id FROM user WHERE login = \""+user+"\";";
			Statement stUser = c.createStatement();
			stUser.executeQuery(querySelectUser);
			ResultSet curseur = stUser.getResultSet();
			
			int idUser = getIdUserByLogin(user);
			
			if(idUser == -1) {
				return false;
			}
			
			String query = "SELECT * FROM new_session WHERE  idUser = \""+idUser+"\";";
			Statement st = c.createStatement();
			st.executeQuery(query);
			curseur = st.getResultSet();
			
			if(curseur.next()){ 
				retour = true;
			}
			else{
				retour = false;
			}
			
			curseur.close();
			st.close();
			c.close();		
			
		}catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return retour;
		
	}
	
	public static String insererConnexion (String user, int root){
		String key = "";
		
		if(isConnected(user)) {
			return "error";
		}else {
			try {
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				
				Connection c = Database.getMySQLConnection();
				
				Random rand = new Random();
				String alphabet = "abcdefghijklmnopqrstuvwxyz12356789";
				int longueur = alphabet.length();
				
				for(int i = 0; i < 20; i++) {
				  int k = rand.nextInt(longueur);
				  key+= alphabet.charAt(k);
				}
				
				int idUser = getIdUserByLogin(user);
				
				if(idUser == -1) {
					return "error";
				}
				
				String query = "INSERT INTO new_session (idUser, cle, dateConnexion, isRoot) values (\""+idUser+"\",\""+key+"\",CURRENT_TIMESTAMP ,\""+root+"\");";
				Statement st = c.createStatement();
				int valide = st.executeUpdate(query);
				
				System.out.println(valide);
				st.close();
				c.close();
			}catch (InstantiationException |IllegalAccessException | ClassNotFoundException | SQLException e) {
				e.printStackTrace();
			}
			
			return key;
		}
	}
	
	public static boolean deleteConnexion(String key) {
		boolean retour = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection c = Database.getMySQLConnection();
			
			String query = "DELETE FROM new_session WHERE cle = \""+key+"\";";
			Statement st = c.createStatement();
			int valide = st.executeUpdate(query);
			
			if(valide == 1){ 
				retour = true;
			}
			else{
				retour = false;
			}
		
			st.close();
			c.close();		
			
		}catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return retour;
	}
	
	public static boolean isRoot(String user){
		boolean retour = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			Connection c = Database.getMySQLConnection();
			String query = "SELECT isRoot FROM new_session, user WHERE user.login = \""+user+"\" AND new_session.idUser = user.id;";				
		
			Statement st = c.createStatement();
			st.executeQuery(query);
			ResultSet curseur = st.getResultSet();
			
			if(curseur.next()){ 					
				if(curseur.getInt(1) == 1){
					retour = true;
				}
			}
			else{
				System.out.println("echec!");
				retour = false;
			}
			
			curseur.close();
			st.close();
			c.close();
			
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
		
		return retour;
	}
	
	public static boolean keyExists(String key){
		boolean retour = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection c = Database.getMySQLConnection();
			String query = "SELECT * FROM new_session WHERE cle = \""+key+"\";";
			Statement st = c.createStatement();
			st.executeQuery(query);
			ResultSet curseur = st.getResultSet();
			
			if(curseur.next()){ 
				retour = true;
			}
			else{
				System.out.println("echec!");
				retour = false;
			}
			
			curseur.close();
			st.close();
			c.close();		
			
		}catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		return retour;
	}
	
	public static boolean sessionNotExpired(String key){
		
		boolean retour = false;
		
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			
			Connection c = Database.getMySQLConnection();
			String query = "SELECT * FROM new_session WHERE cle = \""+key+"\" AND dateConnexion BETWEEN timestamp(DATE_SUB(NOW(), INTERVAL 30 MINUTE)) AND timestamp(NOW());";
			Statement st = c.createStatement();
			st.executeQuery(query);
			ResultSet curseur = st.getResultSet();
		 
			if(curseur.next()){
				rechargedSession(key);
				retour = true;
				
			}else{
				deleteConnexion(key);
			}
			
			curseur.close();
			st.close();
			c.close();		
			
		}catch (SQLException | InstantiationException | IllegalAccessException | ClassNotFoundException e) {
			e.printStackTrace();
		}

		return retour;
		
	}

	public static void rechargedSession(String key){
		try {
			Class.forName("com.mysql.jdbc.Driver").newInstance();
		
			Connection c = Database.getMySQLConnection();
			String query = "UPDATE new_session SET dateConnexion = NOW() WHERE cle = \""+key+"\";";				
		
			Statement st = c.createStatement();
			int valide = st.executeUpdate(query);
			
			st.close();
			c.close();
			
		} 
		catch (InstantiationException | IllegalAccessException | ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		} 
	}
	
	public static void main(String args[]){
		
		//addToBDUser("Pidery", "Ale", "sosa", "test1"); // Test ajout. 
		//System.out.println(userExists("sosa")); // Test user présent dans BD.
		
		//addToBDUser("User", "user", "delete", "test1"); // Test suppression.
		//deleteToBDUser("delete");
		
		//System.out.println(checkPassword("sosa", "test1")); //Test mdp correct.
		//System.out.println(checkPassword("sosa", "test2")); //Test mdp incorrect.
		
		//System.out.println(insererConnexion("sosa", 1)); //Test inserer connexion. 
		//System.out.println(insererConnexion("sosa", 1)); // Affiche error car connexion deja inserrée.
		
		//System.out.println(isRoot("sosa"));
		
	
	}
}
