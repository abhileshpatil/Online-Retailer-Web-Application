import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.net.URL;
import java.util.Map.Entry;

public class Home extends HttpServlet {	

CommonUtils cm= new CommonUtils();
public static MySQLDataStoreUtilities mysql = new MySQLDataStoreUtilities();
DealMacthes dm =new DealMacthes();
String header;
int entrycount=0;

public void init() throws ServletException {
	  SAXParser s = new SAXParser();
      HashMap<String, Product> products = null;
	try {
		URL url = getClass().getResource("ProductCatalog.xml");
		products = s.readDataFromXML(url.getPath());
		HashMap<String, Product> pr = (HashMap<String, Product>) mysql.fetchAllProducts();
		if(pr.isEmpty())
		{
		for (String name: products.keySet()){  
            cm.writeToDB(products.get(name));
        }
		} 
		// cm.writeToFile(products, "products");
	} catch (Exception e) {
	}
    	
  }

protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		if(session!=null)
		{
		String user = (String)session.getAttribute("username");
		String utype= (String)session.getAttribute("usertype");
		if(user!=null && utype!=null)
		{
		header=cm.headertype(utype, user);
		}
		else{
			header = 

			"<header>"+
				"<h1><a href=\"/\">Online<span>retailer</span></a></h1>"+
				"<h2>A great place to be</h2>"+
			"</header>"+
			"<nav>"+
				"<ul>"+
					"<li class=\"start selected\"><a href=\"/SmartPortables/Home\">Home</a></li>"+
							"                        <li><a href=\"/SmartPortables/Home?cat=SmartWatches\">SmartWatches</a></li>"+
							"                        <li><a href=\"/SmartPortables/Home?cat=Speakers\">Speakers</a></li>"+
							"                        <li><a href=\"/SmartPortables/Home?cat=SmartPhones\">Smart Phones</a></li>"+

							"            <li><a href=\"/SmartPortables/register\">Register</a></li>"+
							
							"            <li class=\"\"><a href=\"/SmartPortables/login\">Login</a></li>"+
				"</ul>"+
			"</nav>"+
			"<div id=\"body\">";
		}
		}
		
	String cat = request.getParameter("cat");
    String category = "";
    boolean isCategory = false;
    if(cat == null || cat == "") {
    	isCategory = false;
    } else if(cat.equals("SmartWatches")){
    	isCategory = true;
    	category = "SmartWatches";
    } else if(cat.equals("Speakers")){
    	isCategory = true;
    	category = "Speakers";
    }  else if(cat.equals("Laptops")){
    	isCategory = true;
    	category = "Laptops";
    } else if(cat.equals("SmartPhones")){
    	isCategory = true;
    	category = "SmartPhones";
    } else if(cat.equals("HeadPhones")){
    	isCategory = true;
    	category = "HeadPhones";
    }else if(cat.equals("ExternalStorage")){
    	isCategory = true;
    	category = "ExternalStorage";
    }

// HashMap<String, Product> products = (HashMap<String, Product>) cm.readFromFile("products");
HashMap<String, Product> products = (HashMap<String, Product>) mysql.fetchAllProducts();
response.setContentType("text/html");
PrintWriter pw = response.getWriter();
String contentStr="";
contentStr=dm.doGet(request,response);
contentStr=contentStr+"<br><h4>View All Products</h4><hr>";
if(products.isEmpty()!=true)
{
for(Entry<String, Product> m :products.entrySet()){	
		  	Product c = m.getValue();
			if(isCategory == false || (isCategory == true && category.equals(c.getCategory())))
			{
			  		 contentStr = contentStr +
					"<div class=\"prod_box_big\">"+
		 			"        <div class=\"top_prod_box_big\"></div>"+
		 			"        <div class=\"center_prod_box_big\">"+
				"            <div class=\"product_img_big\"> <a href=\"javascript:popImage('images/"+c.getCategory()+".jpg','Some Title')\" title=\"\"><img width=\"150\" height=\"150\" src=\"images/"+c.getCategory()+".jpg\" alt=\"\" border=\"0\"></a>"+
		 			"         </div>"+
		 			"          <div class=\"details_big_box\">"+
		 			"            <div class=\"product_title_big\">"+m.getKey()+"</div>"+
		 			"            <div class=\"specifications\"> Category: <span class=\"blue\">"+c.getCategory()+"</span><br>"+
		 			"              Spectial discount: <span class=\"blue\">$"+c.getRdiscount()+"</span><br>"+
		 			"              Warranty Offered: <span class=\"blue\">$"+c.getRwarranty()+"</span><br>"+
                    "       	   Retailer Rebate: <span class=\"blue\">$"+c.getrrebate()+"</span><br>"+
		 			"              Discount: <span class=\"blue\">$"+c.getDiscount()+"</span><br>"+
		 			"            </div>"+
		 			"            <div class=\"prod_price_big\"> price : <span class=\"price\">$"+c.getPrice()+"</span></div>"+
					 			"<form action=\"/SmartPortables/cart\" method = \"post\">"+ 
					 			"  <input type=\"hidden\" name=\"name\" value= \""+c.getName()+"\">"+
					 			"  <input type=\"hidden\" name=\"price\" value=\""+c.getPrice()+"\">"+
								"  <input type=\"hidden\" name=\"cat\" value=\""+c.getCategory()+"\">"+
					 			"  <input type=\"hidden\" name=\"discount\" value=\""+c.getDiscount()+"\">"+
								"  <input type=\"hidden\" name=\"rdiscount\" value= \""+c.getRdiscount()+"\">"+
					 			"  <input type=\"hidden\" name=\"rwarranty\" value=\""+c.getRwarranty()+"\">"+
					 			"  <button type = \"submit\" value= \"Add To Cart\" class=\"addtocart\">Add To Cart</button>"+
								 "</form>"+
								 "<a href=\"product_view?name="+c.getName()+"\">"+
								 "  <button type = \"submit\" value= \"Add To Cart\" class=\"addtocart\">Accessories</button>"+
					 			"</a>"+
					"             </div>"+
					"  <a href=\"/SmartPortables/review?pname="+m.getKey()+"\"><button type = \"submit\" value= \"Review Product\" class=\"addtocart\">Review Product</button></a>"+
					"  <a href=\"/SmartPortables/viewreview?pname="+m.getKey()+"\"><button type = \"submit\" value= \"View Review\" class=\"addtocart\">View Review</button>"+
		 			"        </div></div>";
			}
		 			  

}
}
contentStr = contentStr +	"  <div class=\"bottom_prod_box_big\"></div>"+" </div>";
String basics = cm.setBasicWithCSS("styles1");
String content = "<section id=\"content\">"+ contentStr + "</section>";
String comb = basics + header + content  +cm.sidebar()+ cm.footer();
pw.println(comb);
}
protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
				
			}
}