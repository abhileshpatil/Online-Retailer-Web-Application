import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class ViewReview extends HttpServlet {
  
  CommonUtils cm= new CommonUtils();
  String header;
  private MongoDBDataStoreUtilities mongoStore = new MongoDBDataStoreUtilities();
	
  
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter pw = response.getWriter();

    HashMap<String, ArrayList<Review>> show_reviews = mongoStore.selectReview();
    String contentStr = "<hr><h1 style=\"background-color: #e9e9e9;padding-left: 20px;padding-top: 8px;\"> Review Product</h1> <hr>";
    for(Entry<String, ArrayList<Review>> rev :show_reviews.entrySet()){
            if((rev.getKey()).equals((request.getParameter("pname"))))
            {
                for(int i=0;i<(rev.getValue()).size();i++)
                {
            
            Review rp = ((rev.getValue()).get(i));
            if(i==0)
            {
            contentStr = contentStr 
    			+"<div class=\"prod_box_big\">"+
	 			"        <div class=\"top_prod_box_big\"></div>"+
	 			"        <div class=\"center_prod_box_big\">"+
	 			"          <div> <hr><h4 style=\"background-color: #e9e9e9;padding-left: 20px;padding-top: 8px;\"> Product Details</h4> <hr></div>"+
	 			"          <div class=\"details_big_box\">"+
	 			"            <div class=\"product_title_big\">"+request.getParameter("pname")+"</div>"+
	 			"          <div class=\"prod_price\">Price : <span class=\"price\">$"+rp.getPrice()+"</span></div>"+
	 			
	 			"            <div class=\"specifications\"> Category: <span class=\"blue\">"+rp.getCategory()+"</span><br>"+
				"               Price: <span class=\"blue\">"+rp.getPrice()+"</span><br>"+
	 			"               Retailer Zip: <span class=\"blue\">"+rp.getRetailerZip()+"</span><br>"+
	 			"               Retailer City: <span class=\"blue\">"+rp.getRetailerCity()+"</span><br>"+
	 			"               Retailer State: <span class=\"blue\">"+rp.getRetailerState()+"</span><br>"+
		 		
				"               Manufacturer Name: <span class=\"blue\">"+rp.getManufacturerName()+"</span><br>"+
                "            </div>"+
	 		
		 						
				"          </div>"+
	 			"   	</div>     </div>";
            }
            contentStr = contentStr+
            "<div class=\"prod_box_big\">"+
	 			"        <div class=\"center_prod_box_big\">"+
	 			"          <div> <h4 style=\"background-color: #FF0000;padding-left: 20px;padding-top: 8px;\">Review"+" "+(i+1)+"</h4></div>"+
	 			"          <div class=\"details_big_box\">"+
                "               Reviewers Name: <span class=\"blue\">"+rp.getUserName()+"</span><br>"+
                "               Product Rating: <span class=\"blue\">"+rp.getReviewRating()+"</span><br>"+
                "               Review Date: <span class=\"blue\">"+rp.getReviewDate()+"</span><br>"+
                "               Product Review: <span class=\"blue\">"+rp.getReviewText()+"</span><br>"+
				
	 			"            </div>"+
	 		
		 						
				"          </div>"+
	 			"      </div>";
            
            }
            }
    }

    HttpSession session = request.getSession();
    String user = (String)session.getAttribute("username");
	String utype= (String)session.getAttribute("usertype");    		

contentStr = contentStr +	"  <div class=\"bottom_prod_box_big\"></div>"+" </div>";
String basics = cm.setBasicWithCSS("styles1");
String header=cm.headertype(utype, user);
String content = "<section id=\"content\">"+ contentStr + "</section>";
String comb = basics + header + content  +cm.sidebar()+ cm.footer();
pw.println(comb);
  }

    protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
	}
}
