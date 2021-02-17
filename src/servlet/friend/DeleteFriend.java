package servlet.friend;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class DeleteFriend extends HttpServlet{
	
	public DeleteFriend(){
		super();
	}

	protected void doGet(HttpServletRequest requeste, HttpServletResponse response) throws ServletException, IOException{
		
		response.setContentType("Text / Plain");
		
		String login = requeste.getParameter("login");
		String key = requeste.getParameter("cle");
		JSONObject obj = new JSONObject();
		
		if(login == null || key == null){
			obj = servicesTools.servicesRefused.servicesRefused("Il manque des parametres", 1001);
			response.getWriter().println(obj.toString());
		}
		else{
			try{
				obj = services.Friend.deleteFriend(login,key);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
	}
}
