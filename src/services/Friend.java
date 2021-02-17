package services;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DBObject;

public class Friend {

// Utiliser id plutot que cle
	
	public static JSONObject addFriend(String loginFriend, String cle){
		
		JSONObject rep = new JSONObject();
		
		int idUser = -1;
		
		try{
			if(bd.UserTools.keyExists(cle)){
				
				idUser = bd.UserTools.getIdUserByKey(cle);
				
				if(bd.UserTools.sessionNotExpired(cle)){
					if(bd.UserTools.userExists(loginFriend)){
						if(bd.FriendTools.isYourFriend(loginFriend, cle)){
							rep.put("status", "ko");
							rep.put("error", 1001);
							rep.put(loginFriend, " est déjà votre ami(e).");
						}else{
							bd.FriendTools.addToBDFriend(loginFriend, cle);
							rep.put(loginFriend, " a été ajouté.");
							rep.put("status", "ok");
						}
					}else{
						rep.put("status", "ko");
						return servicesTools.servicesRefused.servicesRefused("error", 1001);
					}
				}else{	
					return servicesTools.servicesRefused.servicesRefused("Votre session à expirée veuillez vous reconnecter.",101);
				}
			}else{
				return servicesTools.servicesRefused.servicesRefused("Vous n'etes pas connecté(e).", 1001);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return rep;
		
	}
	
	public static JSONObject deleteFriend(String loginFriend, String cle){
		
		JSONObject rep = new JSONObject();
		
		if(bd.UserTools.keyExists(cle)){		
			if(bd.UserTools.sessionNotExpired(cle)){	
				if(bd.FriendTools.isYourFriend(loginFriend, cle)){
					try{
						bd.FriendTools.deleteFriend(loginFriend, cle);
						rep.put("status", "OK");
						rep.put(loginFriend, "supprimé");
					}catch(JSONException e){
						e.printStackTrace();
					}
							
					return rep;
					
				}else{
					return servicesTools.servicesRefused.servicesRefused("L'utilisateur n'est pas votre ami(e).", 1001);
				}	
			}else{
				return servicesTools.servicesRefused.servicesRefused("Votre session à expirée veuillez vous reconnecter.",101);
			}
		}else{
			return servicesTools.servicesRefused.servicesRefused("Vous n'etes pas connecté(e).", 1001);
		}
	}

	public static JSONObject listFriend(String loginUser, String cle){
		
		JSONObject rep = new JSONObject();
		JSONArray tab = new JSONArray();
		JSONObject repfinal = new JSONObject();
		
		try {
			if(bd.UserTools.sessionNotExpired(cle)){				
				List<Integer> listFriend = bd.FriendTools.listFriend(cle);
				int j = 0;
				
				for(Integer i : listFriend){
					try {
						rep = new JSONObject();
						rep.put("idfriend", i);
						tab.put(rep);
						j++;
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
				repfinal.put("status", "ok");
				repfinal.put("amis", tab);
		
			}else{
				return servicesTools.servicesRefused.servicesRefused("Votre session à expirée veuillez vous reconnecter.",101);
			}
		}catch(JSONException e){
				e.printStackTrace();
		}
		
		return repfinal;
	}
	
	
	public static JSONObject listRecommandation(String key){
		
		JSONObject recommand = new JSONObject();
		
		try {
			if(bd.UserTools.sessionNotExpired(key)){
				recommand = bd.FriendTools.usersExceptMe(key);
				recommand.put("status", "ok");
				
			}else{
				return servicesTools.servicesRefused.servicesRefused("Votre session à expirée veuillez vous reconnecter.",101);
			}
		}catch(JSONException  e){
				e.printStackTrace();
		}
		
		return recommand;
	}
	
}
