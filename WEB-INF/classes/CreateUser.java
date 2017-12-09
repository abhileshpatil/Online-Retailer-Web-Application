import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.*;

public class CreateUser extends HttpServlet {
  
  private CommonUtils store = new CommonUtils();
  private Home h =new Home();
  
  private MySQLDataStoreUtilities mySQLStore = new MySQLDataStoreUtilities();
  private  HashMap<String, User> usersFromDb = new HashMap<String, User>();
  
  public void init() throws ServletException {
		  HashMap<String, User> users = (HashMap<String, User>) mySQLStore.fetchAllUsers();
		  usersFromDb = users;
		  store.setUsers(users);
  }
  

  public void prinUsertMap(HashMap<String, User> mapInFile) throws ServletException {
	  
	  for(Entry<String, User> m :mapInFile.entrySet()){
			User c = m.getValue();
			System.out.println("\t Name : "+c.getName());
			System.out.println("\t type : "+c.getUtype());
	  }
  }
  
  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

        usersFromDb = mySQLStore.fetchAllUsers();
		// get request parameters for userID and password
		String user = request.getParameter("user");
		String pwd = request.getParameter("password");
		String rpwd = request.getParameter("rpassword");
		String utype = request.getParameter("utype");


		if(user == null || user == "") {
			System.out.println("Username required..");
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount?"+"Customer-username-required-TRY-AGAIN"));
		} else if(!pwd.equals(rpwd) ) {
			System.out.println("Password mismatch..");
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount?"+"Customer-password-mismatch-TRY-AGAIN"));
		} else if(usersFromDb.containsKey(user)){
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/salesman/createuser?"+"username already in use. Please use another user_name"));
		} 
		else {
			System.out.println("User-creation-success..");
			User userObj = new User(user, pwd);
			userObj.setName(user);
			userObj.setUtype(utype);
			userObj.setPassword(pwd);

			if(usersFromDb == null || usersFromDb.isEmpty()) {
				usersFromDb = new HashMap<String, User>();
			} else {
				//prinUsertMap(usersFromDb);
			}
			usersFromDb.put(user, userObj);
			store.setUsers(usersFromDb);
			
			mySQLStore.insertUser(userObj);
			// store.writeToFile(usersFromDb, "users");
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount?"+"Customer-created"));
		}
		
	}
	
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	store.setBasicWithCSS("login");
	HttpSession session=request.getSession();
	String user = (String)session.getAttribute("username");
	String utype= (String)session.getAttribute("usertype");
	String contentStr = 
		"<h1>Create Customer Account</h1>"+
	  	"<p>Register</p>"+
		"<form action=\"createuser\" method=\"post\">"+
	    		"<div class=\"input\">"+
			"      <div class=\"blockinput\">"+
			"        <i class=\"icon-envelope-alt\"></i><input type=\"text\" name=\"user\" placeholder=\"Username\">"+
			"      </div>"+
			"      <div class=\"blockinput\">"+
			"        <i class=\"icon-unlock\"></i><input type=\"password\" placeholder=\"Password\" name=\"password\">"+
			"      </div>"+
			
			"      <div class=\"blockinput\">"+
			"        <i class=\"icon-unlock\"></i><input type=\"password\" placeholder=\"Reenter Password\" name=\"rpassword\">"+
			"      </div><br>"+
			
			"      <div class=\"blockinput\">"+
			"        <i class=\"icon-unlock\"></i>"+
					"<select name=\"utype\">"+
					"    <option value=\"CUSTOMER\">CUSTOMER</option>"+
					"  </select>"+
			"      </div>"+
			"    </div>"+
			"<button type=\"submit\" value=\"Create\">Create</button>"+
		"  </form></div>";
						
	String content = "<section id=\"content\">"+ contentStr + "</section>";
    String sp=store.setBasicWithCSS("styles1");
    out.println(sp+store.headertype(utype,user)+content+store.sidebar()+store.footer());
  }
}
