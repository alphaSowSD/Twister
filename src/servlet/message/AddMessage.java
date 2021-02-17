package servlet.message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class AddMessage extends HttpServlet{

	public AddMessage(){
		
	}
	
	protected void doGet(HttpServletRequest requeste, HttpServletResponse response) throws ServletException, IOException{
		
		response.setContentType("Text / Plain");
		
		String key = requeste.getParameter("key");
		String content = requeste.getParameter("content");
		
		JSONObject obj = new JSONObject();
		
		if(key == null || content == null){
			obj = servicesTools.servicesRefused.servicesRefused("Il manque des parametres", 1001);
			response.getWriter().println(obj.toString());
		}
		else{
			try{
				obj = services.Message.addMessage(key, content);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
	}
}

