import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ProductView extends HttpServlet{

    private CommonUtils utils = new CommonUtils();
    public static MySQLDataStoreUtilities mysql = new MySQLDataStoreUtilities();

    public void doGet(HttpServletRequest request, HttpServletResponse response)
    throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String product_name = request.getParameter("name");

        HttpSession session = request.getSession();

		String user = (String)session.getAttribute("username");
		String utype= (String)session.getAttribute("usertype");

        // HashMap<String, Product> products = (HashMap<String, Product>) utils.readFromFile("products");
        HashMap<String, Product> products = (HashMap<String, Product>) mysql.fetchAllProducts();
        Product c = products.get(product_name);

        String content = "";
        content = content +
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
        "       </div>"+
        "      </div>"+
        "        <div class=\"bottom_prod_box_big\"></div>"+
        "      </div>";

        HashMap<String,List<Accessory>> accessories = mysql.fetchaccessory(product_name);
        for(Entry<String, List<Accessory>> ma :accessories.entrySet()){
        System.out.println("print accessory"+ma.getKey()+ma.getValue());
        }
        if(!accessories.isEmpty()) {
            content = content +
            "<div class=\"container\" style=\"width: 650px;\">"+
            "<h2>Accessories</h2>"+
            "<div id=\"accessories\" class=\"carousel slide\" data-ride=\"carousel\">"+
            "<div class=\"carousel-inner\">";
        }

        int entrycount = 0;
        // Object firstkey = accessories.keySet().toArray()[0];
        for(Entry<String, List<Accessory>> ma :accessories.entrySet()){
            entrycount++;
            List<Accessory> tempo = ma.getValue();
            for (Accessory element : tempo) {
            System.out.println("Accessory"+" "+tempo);
            if(entrycount ==1){
                System.out.println("inside if "+ ma.getKey());
                // List<Accessory> tempo = ma.getValue();
                Accessory a = element;
                content = content +
                            "<div class=\"item active\">"+
                                "<div class=\"prod_box\" style=\"margin-left:160px\">"+
                                "        <div class=\"top_prod_box\"></div>"+
                                "        <div class=\"center_prod_box\">"+
                                "          <div class=\"product_title\"><a href=\"details.html\">"+a.getName()+"</a></div>"+
                                "          <div class=\"product_img\"><a href=\"details.html\"><img width=\"130\" height=\"130\" src=\"images/"+c.getCategory()+"-acc.jpg\" alt=\"\" border=\"0\"></a></div>"+
                                "             <div class=\"specifications\"> Discount: <span class=\"blue\">$"+a.getDiscount()+" </span><br></div>"+
                                
                                "          <div class=\"prod_price\">price : <span class=\"price\">$"+a.getPrice()+"</span></div>"+
            
                                "        </div>"+
                                "        <div class=\"bottom_prod_box\"></div>"+
                                "        <div class=\"prod_details_tab\">"+
                                            "<form action=\"/SmartPortables/cart?act=addacc\" method = \"post\">"+
                                            "  <input type=\"hidden\" name=\"accname\" value= \""+a.getName()+"\">"+
                                            "  <input type=\"hidden\" name=\"price\" value=\""+a.getPrice()+"\">"+
                                            "  <input type=\"hidden\" name=\"cat\" value=\""+c.getCategory()+"\">"+
                                            "  <input type=\"hidden\" name=\"discount\" value=\""+a.getDiscount()+"\">"+
                                            "  <input type=\"hidden\" name=\"rdiscount\" value= \""+c.getRdiscount()+"\">"+
                                            "  <input type=\"hidden\" name=\"rwarranty\" value=\""+c.getRwarranty()+"\">"+
                                            "  <button type = \"submit\" value= \"+Cart\" class=\"addtocart\">+Add to Cart</button>"+
                                            "</form>"+
                                "      </div>"+  
                                "</div>"+
                            "</div>";
                            entrycount=2;
            }else{
                System.out.println("inside else "+ ma.getKey());
                Accessory next = element;
                content = content +
                "<div class=\"item\">"+
                    "<div class=\"prod_box\" style=\"margin-left:145px\">"+
                    "        <div class=\"top_prod_box\"></div>"+
                    "        <div class=\"center_prod_box\">"+
                    "          <div class=\"product_title\"><a href=\"details.html\">"+next.getName()+"</a></div>"+
                    "          <div class=\"product_img\"><a href=\"details.html\"><img width=\"130\" height=\"130\" src=\"images/"+c.getCategory()+"-acc.jpg\" alt=\"\" border=\"0\"></a></div>"+
                    "             <div class=\"specifications\"> Discount: <span class=\"blue\">$"+next.getDiscount()+" </span><br></div>"+
                    
                    "          <div class=\"prod_price\">price : <span class=\"price\">$"+next.getPrice()+"</span></div>"+

                    "        </div>"+
                    "        <div class=\"bottom_prod_box\"></div>"+
                    "        <div class=\"prod_details_tab\">"+
                                "<form action=\"/SmartPortables/cart?act=addacc\" method = \"post\">"+
                                "  <input type=\"hidden\" name=\"accname\" value= \""+next.getName()+"\">"+
                                "  <input type=\"hidden\" name=\"price\" value=\""+next.getPrice()+"\">"+
                                "  <input type=\"hidden\" name=\"discount\" value=\""+next.getDiscount()+"\">"+
                                "  <button type = \"submit\" value= \"+Cart\" class=\"addtocart\">+Cart</button>"+
                                "</form>"+
                                "<form action=\"/SmartPortables/preview?act=addacc\" method = \"post\">"+
                                    "  <input type=\"hidden\" name=\"accname\" value= \""+next.getName()+"\">"+
                                    "  <input type=\"hidden\" name=\"price\" value=\""+next.getPrice()+"\">"+
                                    "  <input type=\"hidden\" name=\"discount\" value=\""+next.getDiscount()+"\">"+
                                    "</form>"+
                    "         </div>"+
                        "</div>"+
                    "</div>";
            }
        }
        }
       

        content = content + "</div>"+
                "<a class=\"left carousel-control\" href=\"#accessories\" data-slide=\"prev\">"+
                    "<span class=\"glyphicon glyphicon-chevron-left\"></span>"+
                    "<span class=\"sr-only\">Previous</span>"+
                "</a>"+
                "<a class=\"right carousel-control\" href=\"#accessories\" data-slide=\"next\">"+
                    "<span class=\"glyphicon glyphicon-chevron-right\"></span>"+
                    "<span class=\"sr-only\">Next</span>"+
                "</a>"+
            "</div>"+
        "</div>";

        Home h=new Home();
        String header=utils.headertype(utype,user);
        String contentc = "<section id=\"content\">"+ content + "</section>";
        String cs=utils.setBasicWithCSS("styles1");
        

        out.println(cs+header+contentc+utils.sidebar()+utils.footer());
    }

}