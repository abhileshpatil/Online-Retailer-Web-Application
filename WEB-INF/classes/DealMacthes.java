import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.text.DecimalFormat;
import java.lang.Math.*;

public class DealMacthes {
	
	
	public String doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	        MySQLDataStoreUtilities MySQLStore =new MySQLDataStoreUtilities();
			String filename = "C:\\apache-tomcat-7.0.34\\webapps\\SmartPortables\\WEB-INF/DealMatches.txt";
			FileReader is = null;
			try{
			 is = new FileReader(filename);
			}catch(Exception e){
				System.out.println(e);
			}
			String contentStr = "";
			
			List<String> lines = new ArrayList<String>();
			ArrayList<String> tweets = new ArrayList<String>();
			ArrayList<String> tweets_products = new ArrayList<String>();
	
			if (is != null) {
				BufferedReader reader = new BufferedReader(is);
				String text;
				
				try{
					while ((text = reader.readLine()) != null) {
						lines.add(text);
					}
				}catch(Exception e){
					System.out.println(e);
				}
			}
			
			Random r = new Random();
			HashMap<String,Integer> hm = new HashMap<String,Integer>();

			if(lines.size() > 2){
				while(hm.size()<2){
					String randomString = lines.get(r.nextInt(lines.size()));
					if(!hm.containsKey(randomString.split(" :- ")[1])){
						String sp[] = randomString.split(" :- ");
						StringBuilder sb = new StringBuilder();
						sb.append(sp[0]);
						sb.deleteCharAt(0);
						sp[0] = sb.toString();
						sp[0] = sp[0].replaceAll("\'","");
						tweets.add(sp[0]);
						tweets_products.add(sp[1]);
						hm.put(sp[1],1);
					}
				}
			}
			for(int k = 0; k < tweets_products.size(); k++){
				tweets_products.set(k,(tweets_products.get(k).split("'"))[1]);
			}

			String content = "";
			if(tweets.size() == 0){
				content = content +
				"<div> <h3>No Deals Found</h3></div>";
			}
			
			for(int z = 0; z < tweets.size(); z++){
				String[] get_link = tweets.get(z).split(" ");
				String link = get_link[get_link.length-1];
				String[] newArray = Arrays.copyOfRange(get_link, 0, get_link.length-1);
				StringBuilder builder = new StringBuilder();
				String tweet = String.join(" ", newArray);
				content = content+
				"<div><h5 style=\"margin:0px;\">"+tweet+"</h5>"+
				"<a href=\""+link+"\">"+link+"</a></div><br>";
			}
	
			contentStr = contentStr +
			"       <div class=\"prod_box_big\">"+
			"        <div class=\"center_prod_box_big\">"+
						"          <div class=\"details_big_box\" style=\"width:unset;\">"+
				"            <div class=\"product_title_big\">"+"We beat our competitors in all aspects."+"</div>"+
				"            <div class=\"product_title_big\">"+"Price-Match Guaranteed!"+"</div>"+
			"            <div class=\"specifications\"> ";
			contentStr = contentStr + content;
			contentStr = contentStr +
			"            </div>"+
			"      </div>"+
			"      </div>";
	
			HashMap<String, Product> products = (HashMap<String, Product>) MySQLStore.fetchAllProducts();

			for(int j = 0; j < tweets_products.size(); j++){
				Product c = products.get(tweets_products.get(j));
				System.out.println("category is "+c.getCategory());
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
                                 "  <a href=\"/SmartPortables/review?pname="+c.getName()+"\"><button type = \"submit\" value= \"Review Product\" class=\"addtocart\">Review Product</button></a>"+
					"  <a href=\"/SmartPortables/viewreview?pname="+c.getName()+"\"><button type = \"submit\" value= \"View Review\" class=\"addtocart\">View Review</button>"+
        "       </div>"+
        "      </div>"+
        "        <div class=\"bottom_prod_box_big\"></div>"+
        "      </div>";
			}
			return contentStr;
	}
}
