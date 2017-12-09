import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class AnalyticsInputPage extends HttpServlet {
  
  private CommonUtils cm = new CommonUtils();
  private MySQLDataStoreUtilities mySQLStore = new MySQLDataStoreUtilities();
  private MongoDBDataStoreUtilities mongoStore = new MongoDBDataStoreUtilities();
  
  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		// HashMap<String, Product> allProducts = (HashMap<String, Product>) cm.readFromFile("products");
		HashMap<String, Product> allProducts = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
		String productName = request.getParameter("pname");
		
		Product product = allProducts.get(productName);
		
		String userName = request.getParameter("uname");
		String uage = request.getParameter("uage");
		String userGender = request.getParameter("ugender");
		String userOccupation = request.getParameter("uoccupation");
		String urating = request.getParameter("urating");
		String reviewText = request.getParameter("ureviewtext");
		
		Date reviewDate = new Date();
		
		int userRating = Integer.parseInt(urating);
		int userAge = 18;
		if(!uage.isEmpty()) {
			userAge = Integer.parseInt(uage);
		}
		
		Review reviewObj = new Review(product, userName, userAge, userGender, userOccupation, userRating, reviewDate, reviewText);
		
		System.out.println(reviewObj);
		mongoStore.insertReview(reviewObj);
		//HashMap<String, ArrayList<Review>> reviews = mongoStore.selectReview();
		//mongoStore.selectReview();
		
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/home?review-submitted."));
	}
	
  
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	  PrintWriter pw = response.getWriter();
	    
	    String productName = request.getParameter("pname");
	    
	    // HashMap<String, Product> allProducts = (HashMap<String, Product>) cm.readFromFile("products");
		HashMap<String, Product> allProducts = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
	    
	    Product product = allProducts.get(productName);
	    HttpSession session = request.getSession();

        String user = (String)session.getAttribute("username");
	    String utype= (String)session.getAttribute("usertype");
	    
	    String contentStr = "<hr><h1 style=\"background-color: #e9e9e9;padding-left: 20px;padding-top: 8px;\"> Review Product</h1> <hr>";
	    if(allProducts == null || allProducts.isEmpty()) {
	    	System.out.println("NO products found");
	    } else {
	    	String productSelection = " <option value=\"ALL\">ALL</option>";
	    	if(allProducts == null || allProducts.isEmpty()) {
	        	System.out.println("NO products");
	        } else {
	    		for(Entry<String, Product> m :allProducts.entrySet()){
	    		  	Product c = m.getValue();
	    		  	productSelection = 	productSelection +" <option value=\""+c.getName()+"\">"+c.getName()+"</option>";
	    		}	
	        }
		
	    	
	    	contentStr = contentStr + 	
	    			"<div class=\"center_content\">"+
	    			"      <div class=\"center_title_bar\">Analytics</div>"+
	    			"      <div class=\"prod_box_big\">"+
	    			"        <div class=\"top_prod_box_big\"></div>"+
	    			"        <div class=\"center_prod_box_big\">"+
	    			"          <div class=\"contact_form\">"+
	    						"<form action=\"/SmartPortables/analytics/result\" method=\"get\">"+
	    			"  <input type=\"hidden\" name=\"qs\" value= \""+"ab"+"\">"+
	    			"            <div class=\"form_row\">"+
					"              <label class=\"contact\" style=\"width:0px\"><strong>Select Product Name:</strong></label>"+
										"<select name=\"productName\" >"+ 
										productSelection +
									"  </select>"+
					"            </div>"
					+ " <br>"+
					
					"            <div class=\"form_row\">"+
					"              <label class=\"contact\" style=\"width:0px\"><strong>Price :</strong></label>"+
	    			"              <input type=\"text\" name=\"productPrice\" value =\"0\"  />"+
					"            </div>"+
					"            <div class=\"form_row\">"+
					"              <label class=\"contact\" style=\"width:0px\"><strong>Condition for Price:</strong></label>"+
									"<select name=\"condPrice\">"+
									"    <option  value =\"GREATER\">GREATER</option>"+
									"    <option  value =\"LESS\">LESS</option>"+
									"    <option  value =\"EQUALS\">EQUALS</option>"+
									"  </select>"+
					"            </div>"+
					
					" <br>"+
					
					"            <div class=\"form_row\">"+
					"              <label class=\"contact\" style=\"width:0px\"><strong>Review Rating :</strong></label>"+
									"<select name=\"productRating\">"+
									"    <option value=\"0\">0</option>"+
									"    <option value=\"1\">1</option>"+
									"    <option value=\"2\">2</option>"+
									"    <option value=\"3\">3</option>"+
									"    <option value=\"4\">4</option>"+
									"    <option value=\"5\">5</option>"+
									"  </select>"+
					"            </div>"+
					"            <div class=\"form_row\">"+
					"              <label class=\"contact\" style=\"width:0px\"><strong>Condition for Rating:</strong></label>"+
									"<select name=\"condRating\">"+
									"    <option  value =\"GREATER\">GREATER</option>"+
									"    <option  value =\"EQUALS\">EQUALS</option>"+
									"  </select>"+
					"            </div>"+
					" <br>"+
									
					"            <div class=\"form_row\">"+
					"              <label class=\"contact\" style=\"width:0px\"><strong>Retailer City :</strong></label>"+
					"              <input type=\"text\" name=\"rCity\" class=\"contact_input\" style=\"margin-left:50px;\" />"+
					"            </div>"+
	    			
	    			"             <button type=\"submit\" value=\"Submit\" style=\"margin-left: 275px; padding: 8px; background: LawnGreen;\" class=\"contact\">Submit</button> "+
	    						"</form>"+
	    			"          </div>"+
	    			"        </div>"+
	    			"        <div class=\"bottom_prod_box_big\"></div>"+
	    			"      </div>"+
	    			"    </div>";
	    	
	    	contentStr = contentStr + 	
	    			"<div class=\"center_content\">"+
	    			"      <div class=\"center_title_bar\">Data Analytics</div>"+
	    			"      <div class=\"prod_box_big\">"+
	    			"        <div class=\"top_prod_box_big\"></div>"+
	    			"        <div class=\"center_prod_box_big\">"+
	    			"          <div class=\"contact_form\" style=\"padding-left:15px;\">"+
	    						"<form action=\"/SmartPortables/analytics/result\" method=\"get\">"+

	    		                  "    <div class=\"form_row\">"+
					"              <label class=\"contact\"><strong>Select the choice:</strong></label>"+
									"  <input type=\"hidden\" name=\"qs\" value= \""+"cd"+"\">"+
									"<select style=\"width:520px;\" name=\"qId\">"+
									"    <option value=\"4\">Print a list of how many reviews for every product</option>"+
									"    <option value=\"6\">Find highest price product reviewed in every city</option>"+
									"    <option value=\"7\">Find highest price product reviewed in every zip-code</option>"+
									"    <option value=\"8\">Get the top 5 list of liked products for every city</option>"+
									"    <option value=\"9\">Print a list of reviews grouped by City</option>"+
									"    <option value=\"10\">Print a list of reviews grouped by Retailer Zip Code</option>"+
									"    <option value=\"11\">Get the total number of products reviewed and got Rating 5 in Every City</option>"+
									"    <option value=\"12\">shows a list of most liked product in every city</option>"+
									"    <option value=\"13\">Print the median product prices per city</option>"+
									"    <option value=\"14\">Get top 5 list of most liked and expensive products sorted by retailer name for every city</option>"+
									"    <option value=\"15\">Get the top 5 list of most Disliked products sorted by retailer name for every city</option>"+
									"    <option value=\"16\">Get the top 5 list of most Disliked products sorted by retailer name for every zip-code</option>"+
									"    <option value=\"17\">Get the top 2 list of zip-codes where highest number of products got review rating 5</option>"+
									"    <option value=\"18\">Get a list of reviews where reviewer age greater than 50 and the list is sorted by age in every city</option>"+
									"    <option value=\"19\">Get the top 5 list of most liked products sorted by manufacturer name for every city</option>"+
									"  </select>"+
					"            </div>"+
	    			
	    			"             <button type=\"submit\" value=\"Submit\" style=\"margin-left: 275px; padding: 8px; background: LawnGreen;\" class=\"contact\">Submit</button> "+
	    						"</form>"+
	    			"          </div>"+
	    			"        </div>"+
	    			"        <div class=\"bottom_prod_box_big\"></div>"+
	    			"      </div>"+
	    			"    </div>";
	    	
	    	
	    }			

	contentStr = contentStr +	"  <div class=\"bottom_prod_box_big\"></div>"+" </div>";
	String basics = cm.setBasicWithCSS("styles1");
	String header=cm.headertype(utype, user);
	String content = "<section id=\"content\">"+ contentStr + "</section>";
	String comb = basics + header + content  +cm.sidebar()+ cm.footer();
	pw.println(comb);
  }
}
