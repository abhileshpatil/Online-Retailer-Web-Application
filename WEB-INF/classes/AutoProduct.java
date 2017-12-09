import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;

import javax.servlet.*;
import javax.servlet.http.*;

public class AutoProduct extends HttpServlet{

    private CommonUtils utils = new CommonUtils();
    public static MySQLDataStoreUtilities mysql = new MySQLDataStoreUtilities();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String product_name = request.getParameter("productname");

        HttpSession session = request.getSession();

		String user = (String)session.getAttribute("username");
		String utype= (String)session.getAttribute("usertype");

        HashMap<String, Product> products = (HashMap<String, Product>) mysql.fetchAllProducts();
        Product c = products.get(product_name);

        String contentStr = "";
        contentStr = contentStr +
        "       <div class=\"prod_box_big\">"+
        "        <div class=\"top_prod_box_big\"></div>"+
        "        <div class=\"center_prod_box_big\">"+
        "          <div class=\"product_img_big\"> <a href=\"javascript:popImage('images/"+c.getCategory()+".jpg','Some Title')\" title=\"\"><img width=\"150\" height=\"150\" src=\"images/"+c.getCategory()+".jpg\" alt=\"\" border=\"0\"></a>"+
        "          </div>"+
        "          <div class=\"details_big_box\">"+
        "            <div class=\"product_title_big\">"+c.getName()+"</div>"+
        "            <div class=\"specifications\"> Category: <span class=\"blue\">"+c.getCategory()+"</span><br>"+
        "              Spectial discount: <span class=\"blue\">$"+c.getRdiscount()+"</span><br>"+
        "              Warranty Offered: <span class=\"blue\">$"+c.getRwarranty()+"</span><br>"+
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
                                 "  <a href=\"/SmartPortables/review?pname="+product_name+"\"><button type = \"submit\" value= \"Review Product\" class=\"addtocart\">Review Product</button></a>"+
					"  <a href=\"/SmartPortables/viewreview?pname="+product_name+"\"><button type = \"submit\" value= \"View Review\" class=\"addtocart\">View Review</button>"+
        "       </div>"+
        "      </div>"+
        "        <div class=\"bottom_prod_box_big\"></div>"+
        "      </div>";

	Home h=new Home();
        String header=utils.headertype(utype,user);
        String contentc = "<section id=\"content\">"+ contentStr + "</section>";
        String cs=utils.setBasicWithCSS("styles1");
        

        out.println(cs+header+contentc+utils.sidebar()+utils.footer());
  }
}


