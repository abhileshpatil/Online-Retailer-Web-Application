import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.*;

public class Login extends HttpServlet {
	CommonUtils store =new CommonUtils();
	User u =new User();
    String content;
	
	private MySQLDataStoreUtilities mySQLStore = new MySQLDataStoreUtilities();
	private  HashMap<String, User> usersFromDb = new HashMap<String, User>();
    
    public void init() throws ServletException {
	  //HashMap<String, User> users = (HashMap<String, User>) store.readFromFile("users");
	  HashMap<String, User> users = (HashMap<String, User>) mySQLStore.fetchAllUsers();
	  usersFromDb = users;
	  store.setUsers(users);
    }

    public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	  PrintWriter pw = response.getWriter();
	  String basics=store.setBasicWithCSS("login");
	  content = 
	"<h1>Login.</h1>"+
	"<form action=\"login\" method=\"post\">"+
    		"<div class=\"#\">"+
		"      <div class=\"#\">"+
		"        <i class=\"icon-envelope-alt\"></i><input type=\"text\" name=\"user\" placeholder=\"Username\">"+
		"      </div><br>"+
		"      <div class=\"#\">"+
		"        <i class=\"icon-unlock\"></i><input type=\"password\" placeholder=\"Password\" name=\"password\">"+
		"      </div><br>"+
		
		"      <div class=\"#\">"+
		"        <i class=\"#\"></i>"+
				"<select name=\"utype\">"+
				"    <option value=\"CUSTOMER\">CUSTOMER</option>"+
				"    <option value=\"STOREMANAGER\">STOREMANAGER</option>"+
				"    <option value=\"SALESMAN\">SALESMAN</option>"+
				"    <option value=\"RETAILER\">RETAILER</option>"+
				"  </select>"+
		"      </div>"+
				
		"    </div>"+
		"<button type=\"submit\" value=\"Login\">Login</button>"+
		"<a href=\"/SmartPortables/register\">Not a user Sign UP</a>"+
	    " </form></div>";
      pw.println(basics+content);
      }
	  
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	HashMap<String, User> usersDb = mySQLStore.fetchAllUsers();
	String user_name = request.getParameter("user");
	String user_password = request.getParameter("password");
	String usertype =request.getParameter("utype");
	PrintWriter pw = response.getWriter();

	User userFromDb = usersDb.get(user_name);
	if(usersDb.containsKey(user_name) && (userFromDb.getPassword()).equals(user_password) && (userFromDb.getUtype().equals(usertype)))
	{
		HttpSession session = request.getSession();
			session.setAttribute("username", user_name);
			session.setAttribute("usertype", usertype.toString());
			
			session.setMaxInactiveInterval(30*60);
			Cookie userName = new Cookie("user", user_name);
			userName.setMaxAge(30*60);
			response.addCookie(userName);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/Home"));
			System.out.println("-----------------------------success");
	}
	else
	{
	if(!usersDb.containsKey(user_name)) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/register?User-not-found-REGISTER-FIRST."));
		} 
	else if(!(userFromDb.getPassword()).equals(user_password)) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login?Incorrect-password-TRY-AGAIN."));
		} 
	else if(!(userFromDb.getUtype().equals(usertype))) {
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/login?Incorrect-user-type-TRY-AGAIN."));
		}
}
	}

public void prinUsertMap(HashMap<String, User> mapInFile) throws ServletException {
	  
	  for(Entry<String, User> m :mapInFile.entrySet()){
		    System.out.println(m.getKey());
			User c = m.getValue();
			System.out.println("\t Name : "+c.getName());
			System.out.println("\t type : "+c.getUtype());
	  }
  }
}