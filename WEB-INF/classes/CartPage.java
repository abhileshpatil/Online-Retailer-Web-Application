import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class CartPage extends HttpServlet {
  
  private CommonUtils store = new CommonUtils();
  private Home h =new Home();
  private User u =new User();
  private Product cartp=new Product();
  
  protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		HashMap<String, Product> cartproducts = (HashMap<String, Product>)session.getAttribute("cart");

	    HashMap<String, Accessory> cartacc = (HashMap<String, Accessory>) session.getAttribute("cartacc");
		
		
		
		String act = request.getParameter("act");
		String name = request.getParameter("name");
		String priceString = request.getParameter("price");
		String discountString = request.getParameter("discount");
		String category = request.getParameter("cat");

		if(act != null && act.equals("remove")) {
			if(cartproducts == null || cartproducts.isEmpty()) {
				cartproducts = new HashMap<String, Product>();
			} 
			cartproducts.remove(name);
			session.setAttribute("cart", cartproducts);
			System.out.println("remove product");
			
		} else if (act != null && act.equals("removeacc")) {
			if(cartacc == null || cartacc.isEmpty()) {
				cartacc = new HashMap<String, Accessory>();
			} else {

				String accname = request.getParameter("accname");
				cartacc.remove(accname);
			}
			session.setAttribute("cartacc", cartacc);
			System.out.println("remove accessory.");
			
		}  else if (act != null && act.equals("addacc")) {
			if(cartacc == null || cartacc.isEmpty()) {
				cartacc = new HashMap<String, Accessory>();
			} 
			String accname = request.getParameter("accname");
			double price = Double.parseDouble(priceString) ;
			double discount = Double.parseDouble(discountString);
			
			Accessory a = new Accessory(accname,price, discount);
			 
			 cartacc.put(accname, a);
			
			session.setAttribute("cartacc", cartacc);
			System.out.println("Add acc");
			
		} else {
			if(cartproducts == null || cartproducts.isEmpty()) {
				cartproducts = new HashMap<String, Product>();
			} 
            String category1 = request.getParameter("cat");
			double price = Double.parseDouble(priceString) ;
			double discount = Double.parseDouble(discountString);
	
	    Double retailer_discount =Double.parseDouble(request.getParameter("rdiscount"));
		Double retailer_warranty =Double.parseDouble(request.getParameter("rwarranty"));
			cartp = new Product(name, price, discount);
			cartp.setCategory(category1);
			cartp.setRdiscount(retailer_discount);
            cartp.setRwarranty(retailer_warranty);
			cartproducts.put(name, cartp);
			session.setAttribute("cart", cartproducts);
		}
		response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/cart"));
	}
	
  
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
    PrintWriter out = response.getWriter();
    
    HttpSession session = request.getSession();

    HashMap<String, Product> cartproducts = (HashMap<String, Product>) session.getAttribute("cart");
    HashMap<String, Accessory> cartacc = (HashMap<String, Accessory>) session.getAttribute("cartacc");
    
	String user = (String)session.getAttribute("username");
	String utype= (String)session.getAttribute("usertype");
	Product c= new Product();
	
    String contentStr = "<hr><h1 style=\"background-color: #800000;padding-left: 20px;padding-top: 8px;\"> My Cart</h1> <hr>";
    if(cartproducts == null || cartproducts.isEmpty()) {
    	System.out.println("NO product");
    } else {
    	System.out.println("Session product");
    	for(Entry<String, Product> m :cartproducts.entrySet()){
	
		  	c = m.getValue();
	  		 contentStr = contentStr +
	 				"<div class=\"prod_box_big\">"+
		 			"        <div class=\"top_prod_box_big\"></div>"+
		 			"        <div class=\"center_prod_box_big\">"+
		 			"          <div class=\"product_img_big\"> <a href=\"javascript:popImage('images/"+c.getCategory()+".jpg','Some Title')\" title=\"\"><img width=\"150\" height=\"150\" src=\"images/"+c.getCategory()+".jpg\" alt=\"\" border=\"0\"></a>"+
		 			"          </div>"+
		 			"          <div class=\"details_big_box\">"+
	 			"            <div class=\"product_title_big\">"+m.getKey()+"</div>"+
	 			"            <div class=\"specifications\"> Category: <span class=\"blue\">"+c.getCategory()+"</span><br>"+
	 			"              Discount: <span class=\"blue\">$"+c.getDiscount()+" </span><br>"+
	 			"            </div>"+
	 			"            <div class=\"prod_price_big\"> price : <span class=\"price\">$"+c.getPrice()+"</span></div>"+
				 			 "<form action=\"/SmartPortables/cart?act=remove\" method = \"post\">"+
					 			"  <input type=\"hidden\" name=\"name\" value= \""+c.getName()+"\">"+
					 			"  <input type=\"hidden\" name=\"price\" value=\""+c.getPrice()+"\">"+
					 			"  <input type=\"hidden\" name=\"discount\" value=\""+c.getDiscount()+"\">"+
								 "  <input type=\"hidden\" name=\"rdiscount\" value= \""+c.getRdiscount()+"\">"+
					 			"  <input type=\"hidden\" name=\"rwarranty\" value=\""+c.getRwarranty()+"\">"+
					 			"  <button type = \"submit\" value= \"Remove From Cart\" >Remove From Cart</button>"+
					 			"</form>"+
                              "<form action=\"/SmartPortables/preview\" method = \"post\">"+
						 			"  <input type=\"hidden\" name=\"name\" value= \""+c.getName()+"\">"+
						 			"  <input type=\"hidden\" name=\"price\" value=\""+c.getPrice()+"\">"+
									 "  <input type=\"hidden\" name=\"cat\" value=\""+c.getCategory()+"\">"+

					 			"  <input type=\"hidden\" name=\"rdiscount\" value= \""+c.getRdiscount()+"\">"+
					 			"  <input type=\"hidden\" name=\"rwarranty\" value=\""+c.getRwarranty()+"\">"+
						 			"  <input type=\"hidden\" name=\"discount\" value=\""+c.getDiscount()+"\">"+
						 			"  <button type = \"submit\" value= \"Add To Cart\" class=\"addtocart\">Place Order</button>"+
						 			"</form>"+
				"             </div>"+
	 			"        </div>";
	 			  	HashMap<String,List<Accessory>> accessories = c.getAccessories();
	 			  	if(!accessories.isEmpty()) {
	 			  		contentStr = contentStr +"<hr><h4 style=\"background-color: #e9e9e9;padding-left: 13px;padding-top: 8px;\"> Accessories</h4> <hr>";
	 			  	}
	 				for(Entry<String, List<Accessory>> ma :accessories.entrySet()){
	 					Accessory a = ma.getValue().get(0);
	 					contentStr = contentStr +"<div class=\"prod_box\">"+
		 							"        <div class=\"top_prod_box\"></div>"+
		 							"        <div class=\"center_prod_box\">"+
		 							"          <div class=\"product_title\"><a href=\"details.html\">"+a.getName()+"</a></div>"+
		 							"          <div class=\"product_img\"><a href=\"details.html\"><img width=\"130\" height=\"130\" src=\"images/"+c.getCategory()+"-acc.jpg\" alt=\"\" border=\"0\"></a></div>"+
		 							"             <div class=\"specifications\"> Discount: <span class=\"blue\">$"+a.getDiscount()+" </span><br></div>"+
		 							
		 							"          <div class=\"prod_price\">price : <span class=\"price\">$"+a.getPrice()+"</span></div>"+
								"        </div>"+
	 							"        <div class=\"bottom_prod_box\"></div>"+
	 							"        <div class=\"prod_details_tab\"> <a href=\"#\" title=\"Add to cart\"><img src=\"images/cart.gif\" alt=\"\" border=\"0\" class=\"left_bt\"></a>  <a href=\"details.html\" class=\"prod_details\">Buy Now</a> </div>"+
	 							"      </div>";
	 							
	 							
	 				}		
	 	
	 						contentStr = contentStr +	"        <div class=\"bottom_prod_box_big\"></div>"+
	 							"      </div>";
	  	}		
    				
	}
    if(cartacc == null || cartacc.isEmpty()) {
    	System.out.println("NO product");
    } else {
		for(Entry<String, Accessory> ma :cartacc.entrySet()){
			Accessory a = ma.getValue();
			contentStr = contentStr +"<div class=\"prod_box\">"+
					"        <div class=\"top_prod_box\"></div>"+
					"        <div class=\"center_prod_box\">"+
					"          <div class=\"product_title\"><a href=\"details.html\">"+a.getName()+"</a></div>"+
					"          <div class=\"product_img\"><a href=\"details.html\"><img width=\"130\" height=\"130\" src=\"images/"+c.getCategory()+"-acc.jpg\" alt=\"\" border=\"0\"></a></div>"+
					"             <div class=\"specifications\"> Discount: <span class=\"blue\"> $"+a.getDiscount()+" </span><br></div>"+
					
					"          <div class=\"prod_price\">price : <span class=\"price\">$"+a.getPrice()+"</span></div>"+
					
					"        </div>"+
					"        <div class=\"bottom_prod_box\"></div>"+
					"        <div class=\"prod_details_tab\"> "
					 +"<form action=\"/SmartPortables/cart?act=removeacc\" method = \"post\">"+
				
			 			"  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
			 			"  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
			 			"  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
			 			"  <button type = \"submit\" value= \"Remove\" >Remove</button>"+
			 			"</form>"+
						 "<form action=\"/SmartPortables/preview\" method = \"post\">"+
						 			"  <input type=\"hidden\" name=\"name\" value= \""+a.getName()+"\">"+
						 			"  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
									 "  <input type=\"hidden\" name=\"cat\" value=\""+c.getCategory()+"\">"+
						 			"  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
						 			"  <button type = \"submit\" value= \"Add To Cart\" class=\"addtocart\">Place Order</button>"+
						 			"</form>"+
					 " </div>"+
					"      </div>";
					
					
		}		
 	
    }
	String sp=store.setBasicWithCSS("styles1");
	
    String header=store.headertype(utype, user);
	String content = "<section id=\"content\">"+ contentStr + "</section>";

    out.println(sp+header+content+store.sidebar()+store.footer());
  }
}


