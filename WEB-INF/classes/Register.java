import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.*;

public class Register extends HttpServlet {
private CommonUtils store = new CommonUtils();
private MySQLDataStoreUtilities mySQLStore = new MySQLDataStoreUtilities();
private  HashMap<String, User> usersFromDb = new HashMap<String, User>();

public void init() throws ServletException {
	  //HashMap<String, User> users = (HashMap<String, User>) store.readFromFile("users");
	  HashMap<String, User> users = (HashMap<String, User>) mySQLStore.fetchAllUsers();
	  usersFromDb = users;
	  store.setUsers(users);    //for common util user to update
  }

    public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	PrintWriter pw = response.getWriter();
	String basics=store.setBasicWithCSS("login");
	String content = 
	"<h1>New User</h1>"+
  	"<p>Lets register..</p>"+
	"<form action=\"register\" method=\"post\">"+
    		"<div class=\"\">"+
		"      <div class=\"\">"+
		"        <i class=\"icon-envelope-alt\"></i><input type=\"text\" name=\"user\" placeholder=\"Username\">"+
		"      </div>"+
		"      <div class=\"\">"+
		"        <i class=\"icon-unlock\"></i><input type=\"password\" placeholder=\"Password\" name=\"password\">"+
		"      </div>"+
		
		"      <div class=\"\">"+
		"        <i class=\"icon-unlock\"></i><input type=\"password\" placeholder=\"Re-enter Password\" name=\"rpassword\">"+
		"      </div><br>"+
		
		"      <div class=\"\">"+
				"<select name=\"utype\">"+
				"    <option value=\"CUSTOMER\">CUSTOMER</option>"+
				"    <option value=\"STOREMANAGER\">STOREMANAGER</option>"+
				"    <option value=\"SALESMAN\">SALESMAN</option>"+
				"    <option value=\"RETAILER\">RETAILER</option>"+
				"  </select>"+
		"      </div>"+
		"    </div>"+
		"<button type=\"submit\" value=\"Register\">Register</button>"+
		 "<a href=\"/SmartPortables/login\">Already a user Sign In </a>"+
	"  </form></div>";
      pw.println(basics+content);
      }

	public void doPost(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
		  usersFromDb = mySQLStore.fetchAllUsers();
		  String user_name = request.getParameter("user");
		  String user_password = request.getParameter("password");
		  String re_password =request.getParameter("rpassword");
		  String user_type = request.getParameter("utype");
		  
		  if(user_name==null)
		  {
			  response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/register?"+"username required"));
		  }
		  else if(!user_password.equals(re_password))
		  {
			  response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/register?"+"password-mismatch-TRY-AGAIN"));
		  }
		  else if(usersFromDb.containsKey(user_name))
		  {
              response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/register?"+"TRY another Username"));
		  }
		  else
		  {
			User userObj = new User(user_name, user_password);
			userObj.setName(user_name);                           
			userObj.setUtype(user_type);

			//usersFromDb = store.getUsers();

			if(usersFromDb == null || usersFromDb.isEmpty()) {
				usersFromDb = new HashMap<String, User>();
			} else {
			}
			usersFromDb.put(user_name, userObj);
			store.setUsers(usersFromDb);
			
			//store.writeToFile(usersFromDb, "users");
			mySQLStore.insertUser(userObj);
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/Home?"+"User-registered-WELCOME-"+user_name));
		  }

	  
	  }

}