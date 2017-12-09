import java.io.IOException;  

import java.io.PrintWriter;  
  
import javax.servlet.ServletException;  
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest;  
import javax.servlet.http.HttpServletResponse;  
import javax.servlet.http.HttpSession;  

public class Logout extends HttpServlet {  
        protected void doGet(HttpServletRequest request, HttpServletResponse response)  
                                throws ServletException, IOException {

            response.setContentType("text/html");  
            PrintWriter out=response.getWriter();  
              
              
            HttpSession session=request.getSession();  
            session.invalidate();

         
            out.print("<b>Logout Success<b>"); 
            
            out.print("<br><br><u><a href=\"/SmartPortables/Home\">Click Here for Home Page</a></u>");   
              
            out.close(); 
            
    }  
}