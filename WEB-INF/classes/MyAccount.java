import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class MyAccount extends HttpServlet {
  
  private CommonUtils store = new CommonUtils();

  private MySQLDataStoreUtilities mySQLStore = new MySQLDataStoreUtilities();
  private HashMap<String, User> usersFromDb = new HashMap<String, User>();

  

  public void init() throws ServletException {
	  HashMap<String, User> users = (HashMap<String, User>) mySQLStore.fetchAllUsers();
	  usersFromDb = users;
	  store.setUsers(users);
  }
  
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    
    HttpSession session = request.getSession();

	String user = (String)session.getAttribute("username");
	String utype= (String)session.getAttribute("usertype");

	String username = (String) session.getAttribute("username");
	String headerval = (String)session.getAttribute("headerval");
	
	HashMap<String, User> usersDb = mySQLStore.fetchAllUsers();
	User userFromDb = usersDb.get(username);

	String address = "Not specified";
	if(userFromDb.getAddress() != null) {
		address = userFromDb.getAddress();
	}
	
    
	String contentStr = 
 			 "        <div class=\"top_prod_box_big\"></div>"+
 			"        <div class=\"center_prod_box_big\">"+
 			"          <div> <hr><h4 style=\"background-color: #FFFF00;padding-left: 20px;padding-top: 8px;\">Account Details</h4> <hr></div>"+
 			"          <div class=\"details_big_box\">"+
 			"            <div class=\"product_title_big\">Name: "+userFromDb.getName()+"</div>"+
 			"          <div class=\"prod_price\">Type : <span class=\"price\">"+userFromDb.getUtype()+"</span></div>"+
			"          <div class=\"prod_price\">Address : <span class=\"price\">"+address+"</span></div>"+
			"          </div>"+
 			"   	</div>     </div>";
				

	String cs=store.setBasicWithCSS("styles1");

	String content = "<section id=\"content\">"+ contentStr + "</section>";
    out.println(cs+store.headertype(utype,user)+content+store.sidebar()+store.footer());
  }
}
