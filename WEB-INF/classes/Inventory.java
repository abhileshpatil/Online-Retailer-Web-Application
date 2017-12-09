import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class Inventory extends HttpServlet {
    private CommonUtils cm = new CommonUtils();
  private MySQLDataStoreUtilities mySQLStore = new MySQLDataStoreUtilities();
  private MongoDBDataStoreUtilities mongoStore = new MongoDBDataStoreUtilities();
  protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
            }

  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
      PrintWriter pw = response.getWriter();
	    
	    String productName = request.getParameter("pname");
        
	    
	    // HashMap<String, Product> allProducts = (HashMap<String, Product>) cm.readFromFile("products");
		HashMap<String, Product> allProducts = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
        HashMap<String, Product> products_quan =new HashMap<String, Product>();
	    
	    Product product = allProducts.get(productName);
	    HttpSession session = request.getSession();

        String user = (String)session.getAttribute("username");
	    String utype= (String)session.getAttribute("usertype");
        String contentStr="";
        contentStr = contentStr + 	
	    			"<div class=\"center_content\">"+
	    			"      <div class=\"center_title_bar\">Inventory Report</div>"+
	    			"      <div class=\"prod_box_big\">"+
	    			"        <div class=\"top_prod_box_big\"></div>"+
	    			"        <div class=\"center_prod_box_big\">"+
	    			"          <div class=\"contact_form\" style=\"padding-left:15px;\">"+
	    						"<form action=\"/SmartPortables/inventory\" method=\"get\">"+

	    		                  "    <div class=\"form_row\">"+
					"              <label class=\"contact\"><strong>Select the choice:</strong></label>"+
									"  <input type=\"hidden\" name=\"qs\" value= \""+"cd"+"\">"+
									"<select style=\"width:520px;\" name=\"qId\">"+
									"    <option value=\"1\">Generate table of all product and their available stock in store</option>"+
                                    "    <option value=\"2\">Generate a Bar chart that shows product names and total number of items available</option>"+
									"    <option value=\"3\">Generate table of all product currently on sale</option>"+
									"    <option value=\"4\">Generate table of all product currently have munufacture rebate</option>"+
									"  </select>"+
					"            </div>"+
	    			
	    			"             <button type=\"submit\" value=\"Submit\" style=\"margin-left: 275px; padding: 8px; background: LawnGreen;\" class=\"contact\">Submit</button> "+
	    						"</form>"+
	    			"          </div>"+
	    			"        </div>"+
	    			"        <div class=\"bottom_prod_box_big\"></div>"+
	    			"      </div>"+
	    			"    </div>";

        if((Integer.parseInt(request.getParameter("qId"))==0))
        {

        }
        else
        {
        if((Integer.parseInt(request.getParameter("qId"))==2))
{
products_quan = (HashMap<String, Product>) mySQLStore.fetchAllProductsQuantity();
contentStr=contentStr+"<div id = \"inventory\" style = \"width: 450px; height: 1700px; margin: 0 auto; margin-top: 190px; margin-left: 25px;\">"+
"    </div>"+
"      <script language = \"JavaScript\">"+
"         function drawChart() {"+
            // Define the chart to be drawn.
"            var data = google.visualization.arrayToDataTable(["+
"               ['Product','Quantity'],";

if(products_quan.isEmpty()!=true)
{
for(Entry<String, Product> m :products_quan.entrySet()){
    Product c = m.getValue();
    contentStr=contentStr+("["+"'"+m.getKey()+"',"+c.getquantity()+"],");
}
}

contentStr=contentStr+ " ]);"+

"            var options = {title: 'Total Items available', isStacked:true}; "+ 

            // Instantiate and draw the chart.
"           var chart = new google.visualization.BarChart(document.getElementById('inventory'));"+
"            chart.draw(data, options);"+
         "}"+
"         google.charts.setOnLoadCallback(drawChart);"+
"      </script>";
}
else
{
        if((Integer.parseInt(request.getParameter("qId"))==1))
        {
        products_quan = (HashMap<String, Product>) mySQLStore.fetchAllProductsQuantity();
        }
        if((Integer.parseInt(request.getParameter("qId"))==3))
        {
        products_quan = (HashMap<String, Product>) mySQLStore.fetchAllProductsOnSale();
        }
        if((Integer.parseInt(request.getParameter("qId"))==4))
        {
        products_quan = (HashMap<String, Product>) mySQLStore.fetchAllProductsWithRebate();
        }
        contentStr = contentStr +
					"<table style=\"width:100%\">"+
        "  <tr>"+
        "    <th>Product Name</th>"+
        "    <th>Price</th>"+ 
        "    <th>Stock Available</th>"+
        "  </tr>";

if(products_quan.isEmpty()!=true)
{
for(Entry<String, Product> m :products_quan.entrySet()){	
Product c = m.getValue();
System.out.println("name:"+m.getKey());
System.out.println("price:"+c.getPrice());
System.out.println("quantity:"+c.getquantity());
contentStr=contentStr+"  <tr>"+
"    <td>"+m.getKey()+"</td>"+
"    <td>"+"$ "+c.getPrice()+"h</td>"+
"    <td>"+c.getquantity()+"</td>"+
"  </tr>";
}
contentStr=contentStr+"</table>";
}

}
}
        
    contentStr = contentStr +	"  <div class=\"bottom_prod_box_big\"></div>"+" </div>";
	String basics = cm.setBasicWithCSS("styles1");
	String header=cm.headertype(utype, user);
	String content = "<section id=\"content\">"+ contentStr + "</section>";
	String comb = basics + header + content  +cm.sidebar()+ cm.footer();
	pw.println(comb);
      }
}