import java.io.*; 
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.*;
import java.util.Map.Entry;


public class SalesReport extends HttpServlet {
    private CommonUtils cm = new CommonUtils();
    private String str=" ";
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
        HashMap<String, Order> ordersFromDb = (HashMap<String, Order>) mySQLStore.fetchAllOrders();
        // HashMap<String, Order> orderfreq
        HashMap<String, Product> products_quan =new HashMap<String, Product>();
        Map<String, Integer> myMap = new HashMap<String, Integer>();
        Map<String, String> TDate_Prod = new HashMap<String, String>();
	    
	    Product product = allProducts.get(productName);
	    HttpSession session = request.getSession();

        String user = (String)session.getAttribute("username");
	    String utype= (String)session.getAttribute("usertype");
        String contentStr="";
        contentStr = contentStr + 	
	    			"<div class=\"center_content\">"+
	    			"      <div class=\"center_title_bar\">Sales Report</div>"+
	    			"      <div class=\"prod_box_big\">"+
	    			"        <div class=\"top_prod_box_big\"></div>"+
	    			"        <div class=\"center_prod_box_big\">"+
	    			"          <div class=\"contact_form\" style=\"padding-left:15px;\">"+
	    						"<form action=\"/SmartPortables/salesreport\" method=\"get\">"+

	    		                  "    <div class=\"form_row\">"+
					"              <label class=\"contact\"><strong>Select the choice:</strong></label>"+
									"  <input type=\"hidden\" name=\"qs\" value= \""+"cd"+"\">"+
									"<select style=\"width:520px;\" name=\"qId\">"+
									"    <option value=\"1\">Generate table of all products sold and how many items of all products sold</option>"+
                                    "    <option value=\"2\">Generate a Bar Chart that shows the product name and total sales</option>"+
									"    <option value=\"3\">Generate table of total daily sales transactions</option>"+
									"  </select>"+
					"            </div>"+
	    			
	    			"             <button type=\"submit\" value=\"Submit\" style=\"margin-left: 275px; padding: 8px; background: LawnGreen;\" class=\"contact\">Submit</button> "+
	    						"</form>"+
	    			"          </div>"+
	    			"        </div>"+
	    			"        <div class=\"bottom_prod_box_big\"></div>"+
	    			"      </div>"+
	    			"    </div>";

        
        if(Integer.parseInt(request.getParameter("qId"))==1)
        {
            contentStr = contentStr +
					"<table style=\"width:100%\">"+
        "  <tr>"+
        "    <th>Product Name</th>"+
        "    <th>Price</th>"+ 
        "    <th>Product sold</th>"+
        "    <th>Total Sales </th>"+
        "  </tr>";
        for(Entry<String, Order> m :ordersFromDb.entrySet()){	
		  	Order c = m.getValue();
            String pname=(m.getKey()).substring((m.getKey()).lastIndexOf(":") + 1);
				String result[] = pname.split("-");
				result[0]=result[0].trim();
                int count = myMap.containsKey(result[0]) ? myMap.get(result[0]) : 0;
                if(c.getStatus().toString().equals("ORDERED"))
                {
                myMap.put(result[0],count+1);
                }
        }
        
        for (Map.Entry<String, Integer> entry : myMap.entrySet()){

            for(Entry<String, Product> product_detail:(mySQLStore.fetchAllProductswithName(entry.getKey())).entrySet())
            {
                Product cp = product_detail.getValue();
                contentStr=contentStr+"  <tr>"+
            "    <td>"+product_detail.getKey()+"</td>"+
            "    <td>"+"$ "+cp.getPrice()+"h</td>"+
            "    <td>"+entry.getValue()+"</td>"+
            "    <td>"+"$ "+(entry.getValue())*(cp.getPrice()-(cp.getDiscount()+cp.getRdiscount() +cp.getrrebate()))+"</td>"+
            "  </tr>";
            str=str+("["+"'"+(product_detail.getKey())+"',"+(entry.getValue())*(cp.getPrice()-(cp.getDiscount()+cp.getRdiscount() +cp.getrrebate()))+"],");
        }
        
        }
        contentStr=contentStr+"</table>";
        }

        if(Integer.parseInt(request.getParameter("qId"))==2)
        {
            contentStr=contentStr+"<div id = \"inventory\" style = \"width: 550px; height: 400px; margin: 0 auto; margin-top: 190px; margin-left: 25px;\">"+
"    </div>"+
"      <script language = \"JavaScript\">"+
"         function drawChart() {"+
            // Define the chart to be drawn.
"            var data = google.visualization.arrayToDataTable(["+
"               ['Product','Total Sales'],";


for(Entry<String, Order> m :ordersFromDb.entrySet()){	
		  	Order c = m.getValue();
            String pname=(m.getKey()).substring((m.getKey()).lastIndexOf(":") + 1);
				String result[] = pname.split("-");
				result[0]=result[0].trim();
                int count = myMap.containsKey(result[0]) ? myMap.get(result[0]) : 0;
                if(c.getStatus().toString().equals("ORDERED"))
                {
                myMap.put(result[0],count+1);
                }
        }
        
        for (Map.Entry<String, Integer> entry : myMap.entrySet()){

            for(Entry<String, Product> product_detail:(mySQLStore.fetchAllProductswithName(entry.getKey())).entrySet())
            {
                Product cp = product_detail.getValue();
            contentStr=contentStr+("["+"'"+(product_detail.getKey())+"',"+(entry.getValue())*(cp.getPrice()-(cp.getDiscount()+cp.getRdiscount() +cp.getrrebate()))+"],");
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
        if(Integer.parseInt(request.getParameter("qId"))==3)
        {
        double totalsales=0;
        ArrayList<String> TDate = new ArrayList<String>();
        contentStr = contentStr +
					"<table style=\"width:100%\">"+
        "  <tr>"+
        "    <th>Date</th>"+
        "    <th>Total Sales</th>"+
        "  </tr>";
        for(Entry<String, Order> m :ordersFromDb.entrySet()){	
		  	Order c = m.getValue();
            String pname=(m.getKey()).substring((m.getKey()).lastIndexOf(":") + 1);
            String result[] = pname.split("-");
            result[0]=result[0].trim();
            if(TDate.contains((c.getOrderDate()).toString()))
            {
            }
            else
            {
                TDate.add((c.getOrderDate()).toString());
            }
        }

        for(int i=0;i<TDate.size();i++)
        {
            for(Entry<String, Order> m :ordersFromDb.entrySet()){
                Order c = m.getValue();
                String pname=(m.getKey()).substring((m.getKey()).lastIndexOf(":") + 1);
                String result[] = pname.split("-");
                result[0]=result[0].trim();
                if(((c.getOrderDate()).toString()).equals(TDate.get(i)))
                {
                if(c.getStatus().toString().equals("ORDERED"))
                {
                {
                    for(Entry<String, Product> product_detail:(mySQLStore.fetchAllProductswithName(result[0])).entrySet())
                        {
                            Product cp = product_detail.getValue();
                            totalsales=totalsales+(cp.getPrice()-(cp.getDiscount()+cp.getRdiscount() +cp.getrrebate()));
                        }

                }
                }}
            }
            contentStr=contentStr+"  <tr>"+
            "    <td>"+TDate.get(i)+"</td>"+
            "    <td>"+"$ "+totalsales+"h</td>";

            totalsales=0;


        }
        contentStr=contentStr+"</table>";

        }
    contentStr = contentStr +	"  <div class=\"bottom_prod_box_big\"></div>"+" </div>";
	String basics = cm.setBasicWithCSS("styles1");
	String header=cm.headertype(utype, user);
	String content = "<section id=\"content\">"+ contentStr + "</section>";
	String comb = basics + header + content  +cm.sidebar()+ cm.footer();
	pw.println(comb);
      }
}