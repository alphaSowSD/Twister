package servlet.user;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

public class CreateUser extends HttpServlet{
	
	public CreateUser(){
		super();
	}
	
	public void doGet(HttpServletRequest requeste, HttpServletResponse response) throws ServletException, IOException{
		
		response.setContentType("text / plain");
		
		String login = requeste.getParameter("login");
		String name = requeste.getParameter("prenom");
		String frame = requeste.getParameter("nom");
		String pwd = requeste.getParameter("pwd");
		String mail = requeste.getParameter("mail");
		
		JSONObject ret = new JSONObject();
		
		if(login == null || name == null || frame == null || pwd == null || mail == null){
			ret = servicesTools.servicesRefused.servicesRefused("Il manque des parametres", 1001);
			response.getWriter().println(ret.toString());
		}
		else{
			
			try{

				ret = services.User.createUser(frame, name, login, pwd, mail);
			
			}catch(Exception e){
				e.printStackTrace();
			}
			
			PrintWriter out = response.getWriter();
			out.println(ret.toString());
		}
		
	}
	
}
