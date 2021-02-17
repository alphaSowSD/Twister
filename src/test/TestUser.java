package test;

import org.json.JSONObject;

import services.User;
import servlet.user.Login;
import servlet.user.Logout;

public class TestUser {
	
	public static void main(String[]argc, int argv){
		
		
		String nom = "Durant";
		String prenom = "Kevin";
		String usr = "Kevin Durant"; 
		String motDePasse = "mdp";
		String key = "key";
		String mail = "alieoe@gmail.com";
		
		JSONObject object;
		User user = new User();
		
		object = user.createUser(nom, prenom, usr, motDePasse, mail);
		object = user.login(usr, motDePasse);
		object = user.logout(key);	
		
	}

}
