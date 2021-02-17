package services;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.mongodb.DBObject;

public class Message {

	public Message(){
		
	}
	
	public static JSONObject addMessage(String key, String content){
		JSONObject rep = new JSONObject();
		
		try {	
			if(bd.UserTools.keyExists(key)){
				if(bd.UserTools.sessionNotExpired(key)){	
					bd.MessageTools.addToBDMessage(key, content);
					rep.put("key", key);
					rep.put("content", content);
					rep.put("status", "ok");
				}else{
					rep.put("status", "ko");
					rep.put("error", "Votre session à expirée veuillez vous reconnecter.");
				}
				
				return rep;
			}else{
				return servicesTools.servicesRefused.servicesRefused("error", 1001);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return rep;
	}
		
	public static JSONObject deleteMessage(String key, String idMessage){
		
		JSONObject rep = new JSONObject();
		
		try{
			if(bd.UserTools.keyExists(key)){	
				if(bd.UserTools.sessionNotExpired(key)){
					if(bd.MessageTools.isYourMessage(key,idMessage)){
						
						bd.MessageTools.deleteToBDMessage(key, idMessage);
						rep.put("status", "ok");		
						
					}else{
						rep.put("status", "ko");
						rep.put("error ", "ce n'est pas votre message.");
					}
				}else{
					rep.put("status", "ko");
					rep.put("error", "Votre session à expirée veuillez vous reconnecter.");
				}
				
			}else{
				return servicesTools.servicesRefused.servicesRefused("error", 1001);
			}
		}catch(JSONException e){
			e.printStackTrace();
		}
		
		return rep;
	}

	public static JSONObject getMessages(String key, int [] listIdUsers){
		
		JSONArray tab = new JSONArray();
		JSONObject repfinal = new JSONObject();
		int nb_msg = 0;
		int id_msg = 0;
		List<DBObject> listMessages = bd.MessageTools.getBDMessage(key, listIdUsers);
		int id_user = bd.UserTools.getIdUserByKey(key);
		
		try {
			if(bd.UserTools.keyExists(key)){
				if(bd.UserTools.sessionNotExpired(key)){
					for(DBObject db : listMessages){
						try {
							JSONObject rep = new JSONObject();
							rep.put("id", id_msg);
							rep.put("id_message", db.get("_id"));
							rep.put("user", db.get("user_id"));
							rep.put("date", db.get("date"));
							rep.put("content",db.get("content"));
							rep.put("likes", db.get("likes"));
												
							id_msg++;
							
							if((Integer)db.get("user_id") == id_user) {
								nb_msg++;
							}
							
							tab.put(rep);
							
						} catch (JSONException e) {
							e.printStackTrace();
						}
					}
					repfinal.put("messages", tab);
					repfinal.put("nbMessages", nb_msg);
					repfinal.put("status", "ok");
				}else{
					repfinal.put("error", "Votre session à expirée veuillez vous reconnecter.");
					repfinal.put("status", "ko");
				}
			}else{
				repfinal.put("error", "vous n'etes pas connecté(e).");
				repfinal.put("status", "ko");
			}
		}catch (JSONException e) {
			e.printStackTrace();
		}
		
		return repfinal;	
	}
	
}