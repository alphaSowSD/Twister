package services;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class User {

	public User(){
		
	}
	
	public static JSONObject createUser(String nom, String prenom, String user, String motDePasse, String mail){
		
		JSONObject rep = new JSONObject();
		
		if(!bd.UserTools.userExists(user)){
			try{
				rep.put("status", "ok");
				rep.put("nom", nom);
				rep.put("prenom", prenom);
				bd.UserTools.addToBDUser(nom,prenom,user,motDePasse,mail);
				rep.put("etat : ", "utilisateur "+user+" ajouté.");
			}catch(JSONException e){
				e.printStackTrace();	
			}
			return rep;
			
		}else{
			return servicesTools.servicesRefused.servicesRefused("L'utilisateur existe déjà.", 1001);
		}
	}
	
	public static JSONObject deleteUser(String user){
		
		JSONObject rep = new JSONObject();
		
		if(bd.UserTools.userExists(user)){
			try{
				rep.put("status", "ok");
				bd.UserTools.deleteToBDUser(user);
				rep.put("etat", "utilisateur "+user+" supprimé.");
				
			}catch(JSONException e){
				e.printStackTrace();	
			}
			
			return rep;
			
		}else{
			return servicesTools.servicesRefused.servicesRefused("L'utilisateur n'existe pas.", 1001);
		}
	}
	
	public static JSONObject login(String user, String mdp){
				
		JSONObject ret = new JSONObject();
		
		if(user == null || mdp == null){
			ret = servicesTools.servicesRefused.servicesRefused("Il manque des parametres", 1001);	
		}
		else{
			if(bd.UserTools.userExists(user)){
				if(bd.UserTools.checkPassword(user, mdp)){
					String key = bd.UserTools.insererConnexion(user, 0);
					int id = bd.UserTools.getIdUserByKey(key);
					List<Integer> follows = bd.FriendTools.listFriend(key);
					int nbAbonne = bd.FriendTools.imTheirFriend(id).size();
					try{
						ret.put("status", "ok");
						ret.put("key", key);
						ret.put("id",id);
						ret.put("login", user);
						ret.put("follows", follows);
						ret.put("nbAbo", nbAbonne);
					}
					catch(JSONException e){
						e.printStackTrace();
					}					
				}
				else{
					ret = servicesTools.servicesRefused.servicesRefused("Le mot de passe est incorrect.", 1001);
				}
			}
			else{
				ret = servicesTools.servicesRefused.servicesRefused("L'utilisateur n'existe pas.", 1001);
			}
		}
		
		return ret;
	}

	public static JSONObject logout(String key){
		
		JSONObject ret = new JSONObject();
		
		if(key == null){
			ret = servicesTools.servicesRefused.servicesRefused("Il manque des parametres", 1001);
		}
		else{
			if(bd.UserTools.keyExists(key)){
				if(bd.UserTools.deleteConnexion(key)) {
					try{
						System.out.println("Déconnecté!");
						ret.put("status", "ok");
						ret.put("etat", "deconnecté");
					}
					catch(JSONException e){
						e.printStackTrace();
					}	
				}else {
					ret = servicesTools.servicesRefused.servicesRefused("L'utilisateur n'est pas déconnecté", 1001);
				}	
			}else{
				ret = servicesTools.servicesRefused.servicesRefused("L'utilisateur n'est pas connecté", 1001);	
			}	
		}
		
		return ret;
	}
		
}
