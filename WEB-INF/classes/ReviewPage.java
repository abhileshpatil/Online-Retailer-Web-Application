import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class ReviewPage extends HttpServlet {
  
  CommonUtils cm= new CommonUtils();
  String header;
  private MongoDBDataStoreUtilities mongoStore = new MongoDBDataStoreUtilities();
  private MySQLDataStoreUtilities mySQLStore = new MySQLDataStoreUtilities();
  
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
		//mongoStore.selectReviewsDyanamically();
		
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/Home?review-submitted."));
	}
	
  
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    //PrintWriter out = response.getWriter();
    PrintWriter pw = response.getWriter();
    
    String productName = request.getParameter("pname");

    HttpSession session = request.getSession();
    
    // HashMap<String, Product> allProducts = (HashMap<String, Product>) cm.readFromFile("products");
	HashMap<String, Product> allProducts = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
    
    String user = (String)session.getAttribute("username");
	String utype= (String)session.getAttribute("usertype");

    Product product = allProducts.get(productName);
    
    String contentStr = "<hr><h1 style=\"background-color: #e9e9e9;padding-left: 20px;padding-top: 8px;\"> Review Product</h1> <hr>";
    if(allProducts == null || allProducts.isEmpty()) {
    	System.out.println("NO products found");
    } else {
	
    	String manufracturerDiscount;
		if(product.getDiscount() > 0.0) {
			manufracturerDiscount = "YES";
		}
		else{
			manufracturerDiscount = "NO";
		}
	    	
	    contentStr = contentStr 
    			+"<div class=\"prod_box_big\">"+
	 			"        <div class=\"center_prod_box_big\">"+
	 			"          <div> <h4 style=\"background-color: #9D260C;padding-left: 20px;padding-top: 8px;\"> Product Details</h4></div>"+
	 			"          <div class=\"details_big_box\">"+
	 			"            <div class=\"product_title_big\">"+productName+"</div>"+
	 			"          <div class=\"prod_price\">Price : <span class=\"price\">$"+product.getPrice()+"</span></div>"+
	 			
	 			"            <div class=\"specifications\"> Category: <span class=\"blue\">"+product.getCategory()+"</span><br>"+
				"               Price: <span class=\"blue\">"+product.getPrice()+"</span><br>"+
	 			"               Retailer Name: <span class=\"blue\">"+product.getRetailer()+"</span><br>"+
	 			"               Retailer Zip: <span class=\"blue\">"+product.getRetailerZip()+"</span><br>"+
	 			"               Retailer City: <span class=\"blue\">"+product.getRetailerCity()+"</span><br>"+
	 			"               Retailer State: <span class=\"blue\">"+product.getRetailerState()+"</span><br>"+
		 		
				"               Product on Sale?: <span class=\"blue\">"+product.getProductOnSale()+"</span><br>"+
				"               Manufacturer Name: <span class=\"blue\">"+product.getManufacturerName()+"</span><br>"+
				"               Manufacturer Rebate: <span class=\"blue\">"+manufracturerDiscount+"</span><br>"+
				
	 			"            </div>"+
	 		
		 						
				"          </div>"+
	 			"   	</div>     </div>";

		contentStr = contentStr 
    			+"<div class=\"prod_box_big\">"+
	 			"        <div class=\"center_prod_box_big\">"+
	 			"          <div><h4 style=\"background-color: #9D260C;padding-left: 20px;padding-top: 8px;\"> Review Above Product</h4></div>"+
	 			"          <div class=\"details_big_box\">"+
	 		"          <div class=\"contact_form\">"+
    						"<form action=\"/SmartPortables/review\" method=\"post\">"+
    			
    			"            <div class=\"form_row\">"+
    			"              <label class=\"contact\"><strong>User Name:</strong></label>"+
    			"              <input type=\"text\" name=\"uname\" class=\"contact_input\" />"+
    			"            </div>"+
    			"            <div class=\"form_row\">"+
    			"              <label class=\"contact\"><strong>User Age:</strong></label>"+
    			"              <input type=\"text\" name=\"uage\" value =\"18\" class=\"contact_input\" />"+
    			"            </div>"+
    			"            <div class=\"form_row\">"+
    			"              <label class=\"contact\"><strong>User Gender:</strong></label>"+
				    			"<select name=\"ugender\">"+
								"    <option value=\"MALE\">MALE</option>"+
								"    <option value=\"FEMALE\">FEMALE</option>"+
								"  </select>"+
    			"            </div>"+

    			"            <div class=\"form_row\">"+
    			"              <label class=\"contact\"><strong>User Rating:</strong></label>"+
				    			"<select name=\"urating\">"+
								"    <option value=\"1\">1</option>"+
								"    <option value=\"2\">2</option>"+
								"    <option value=\"3\">3</option>"+
								"    <option value=\"4\">4</option>"+
								"    <option value=\"5\">5</option>"+
								"  </select>"+
    			"            </div>"+
								
    			"            <div class=\"form_row\">"+
    			"              <label class=\"contact\"><strong>Review Date:</strong></label>"+
    			"              <input type=\"text\" name=\"ureviewdate\" value = \""+new Date()+"\" class=\"contact_input\" disabled/>"+
    			"            </div>"+	
    			"            <div class=\"form_row\">"+
    			"              <label class=\"contact\"><strong>User Occupation:</strong></label>"+
    			"              <input type=\"text\" name=\"uoccupation\" class=\"contact_input\" />"+
    			"            </div>"+

    			"            <div class=\"form_row\">"+
    			"              <label class=\"contact\"><strong>Review Text:</strong></label>"+
    			"              <textarea rows=\"4\" cols=\"50\" type=\"text\" name=\"ureviewtext\" class=\"contact_input\" ></textarea"+
    			"            </div>"+

    						"  <input type=\"hidden\" name=\"pname\" value= \""+product.getName()+"\">"+

    			"             <button type=\"submit\" value=\"Submit\" style=\"margin-left: 275px; padding: 8px; background: burlywood;\" class=\"contact\">Submit Review</button> "+
    						"</form>"+
		 						
				"          </div>"+
	 			"   	</div>     </div>";
    	
    }			

contentStr = contentStr +	"  <div class=\"bottom_prod_box_big\"></div>"+" </div>";
String basics = cm.setBasicWithCSS("styles1");
String header=cm.headertype(utype, user);
String content = "<section id=\"content\">"+ contentStr + "</section>";
String comb = basics + header + content  +cm.sidebar()+ cm.footer();
pw.println(comb);
  }
}
