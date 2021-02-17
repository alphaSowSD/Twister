package servlet.message;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

public class GetMessages extends HttpServlet{

	public GetMessages(){
		super();
	}

	protected void doGet(HttpServletRequest requeste, HttpServletResponse response) throws ServletException, IOException{
		
		response.setContentType("Text / Plain");
		
		String key = requeste.getParameter("key");
		String ids = requeste.getParameter("id_users");
		String [] idsplit = ids.split(" ");
		
		int [] tab = new int[idsplit.length];
		int i = 0;
		
		for(String s : idsplit){
			tab[i] = Integer.parseInt(s);
			i++;
		}
		
		JSONObject obj = new JSONObject();
		
		if(key == null){
			obj = servicesTools.servicesRefused.servicesRefused("Il manque des parametres", 1001);
			response.getWriter().println(obj.toString());
		}
		else{
			try{
				obj = services.Message.getMessages(key, tab);
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		PrintWriter out = response.getWriter();
		out.println(obj.toString());
	}
}
