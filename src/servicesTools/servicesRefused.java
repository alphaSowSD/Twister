package servicesTools;

import org.json.JSONException;
import org.json.JSONObject;

public class servicesRefused {
	
	public servicesRefused(){
		
	}

	public static JSONObject servicesRefused (String m, int idError){
	
		JSONObject reponse = new JSONObject();
		
		try{
			reponse.put("status", "ko");
			reponse.put("message", m);
			reponse.put("error", idError);
			
		}catch(JSONException e){
			e.printStackTrace();			
		}
			
		return reponse;
	}
}
