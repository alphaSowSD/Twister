package servlet.friend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class Recommandation extends HttpServlet{
	
	public Recommandation(){
		super();
	}

	public void doGet(HttpServletRequest requeste, HttpServletResponse response) throws ServletException, IOException{
		
		response.setContentType("text / plain");
	
		String cle = requeste.getParameter("key");
		JSONObject obj = new JSONObject();
		
		if(cle == null){
			obj = servicesTools.servicesRefused.servicesRefused("Il manque des parametres", 1001);
			response.getWriter().println(obj.toString());
		}
		else{
			try{
				obj = services.Friend.listRecommandation(cle);
			}catch(Exception e){
				e.printStackTrace();
			}
			
			PrintWriter out = response.getWriter();
			out.println(obj.toString());
		}

	}
}