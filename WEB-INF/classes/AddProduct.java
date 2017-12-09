import java.io.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class AddProduct extends HttpServlet {
  
  private MySQLDataStoreUtilities mySQLStore = new MySQLDataStoreUtilities();
  private CommonUtils store = new CommonUtils();
  private Home h=new Home();
  private static String ACCESSORY = "ACCESSORY";
  private static String PRODUCT = "PRODUCT";
  private static String ADD = "ADD";
  private static String DELETE = "DELETE";
  private static String UPDATE = "UPDATE";
  
  private  HashMap<String, Product> productsFromDb = new HashMap<String, Product>();
  
  public void init() throws ServletException {
	//   HashMap<String, Product> products = (HashMap<String, Product>) store.readFromFile("products");
	  HashMap<String, Product> products = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
	  productsFromDb = products;
	  store.setProducts(products);
  }
  public void doGet(HttpServletRequest request,
                    HttpServletResponse response)
      throws ServletException, IOException {
	PrintWriter out = response.getWriter();
	
	HttpSession session =request.getSession();
	String user = (String)session.getAttribute("username");
	String utype= (String)session.getAttribute("usertype");
	
	// HashMap<String, Product> products = (HashMap<String, Product>) store.readFromFile("products");
	HashMap<String, Product> products = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
	   
	String productSelection = "";
	
	if(products == null || products.isEmpty()) {
    	System.out.println("NO products");
    } else {
		for(Entry<String, Product> m :products.entrySet()){
		  	System.out.println(m.getKey());		
		  	Product c = m.getValue();
		  	productSelection = 	productSelection +" <option value=\""+c.getName()+"\">"+c.getName()+"</option>";
		}	
    }

	String contentStr = 
				"<div class=\"center_content\">"+
			"      <div class=\"center_title_bar\">Add Product</div>"+
			"      <div class=\"prod_box_big\">"+
			"        <div class=\"top_prod_box_big\"></div>"+
			"        <div class=\"center_prod_box_big\">"+
			"          <div class=\"contact_form\">"+
						"<form action=\"products?op=ADD&type=PRODUCT\" method=\"post\">"+
			
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Name:</strong></label>"+
			"              <input type=\"text\" name=\"name\" class=\"contact_input\" />"+
			"            </div>"+
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Discount:</strong></label>"+
			"              <input type=\"text\" name=\"discount\" class=\"contact_input\" />"+
			"            </div>"+
			"            <div class=\"form_row\">"+
			               "<label class=\"contact\"><strong>Price:</strong></label>"+
			"               <input type=\"text\" name=\"price\" class=\"contact_input\" />"+
			            "</div>"+
			"            <div class=\"form_row\">"+
			               "<label class=\"contact\"><strong>Retailer name:</strong></label>"+
			"               <input type=\"text\" name=\"retailer\" class=\"contact_input\" />"+
			            "</div>"+
			"            <div class=\"form_row\">"+
			               "<label class=\"contact\"><strong>Retailer Zip:</strong></label>"+
			"               <input type=\"text\" name=\"retailerZip\" class=\"contact_input\" />"+
			            "</div>"+
			"            <div class=\"form_row\">"+
			               "<label class=\"contact\"><strong>Retailer City:</strong></label>"+
			"               <input type=\"text\" name=\"retailerCity\" class=\"contact_input\" />"+
			            "</div>"+
			"            <div class=\"form_row\">"+
			               "<label class=\"contact\"><strong>Retailer State:</strong></label>"+
			"               <input type=\"text\" name=\"retailerState\" class=\"contact_input\" />"+
			            "</div>"+
			"            <div class=\"form_row\">"+
			               "<label class=\"contact\"><strong>ProductOnSale:</strong></label>"+
			"               <input type=\"text\" name=\"productOnSale\" class=\"contact_input\" />"+
			            "</div>"+
			"            <div class=\"form_row\">"+
			               "<label class=\"contact\"><strong>manufacturerName:</strong></label>"+
			"               <input type=\"text\" name=\"manufacturerName\" class=\"contact_input\" />"+
			            "</div>"+
						"<div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Category:</strong></label>"+
							"<select name=\"category\">"+
							"    <option value=\"OTHER\">Other</option>"+
							"    <option value=\"SmartPhones\">Smart Phones</option>"+
							"    <option value=\"Laptops\">Laptops</option>"+
							"    <option value=\"SmartWatches\">SmartWatches</option>"+
							"    <option value=\"HeadPhones\">HeadPhones</option>"+
							"    <option value=\"Speakers\">Speakers</option>"+
							"    <option value=\"ExternalStorage\">ExternalStorage</option>"+
							"  </select>"+
			"            </div>"+
			"              <button type=\"submit\" value=\"Add\" style=\"margin-left: 275px; padding: 8px; background: LawnGreen;\" class=\"contact\">Add</button> "+
						"</form>"+
			"          </div>"+
			"        </div>"+
			"        <div class=\"bottom_prod_box_big\"></div>"+
			"      </div>"+
			"    </div>"+

			"<div class=\"center_content\">"+
			"      <div class=\"center_title_bar\">Update Product</div>"+
			"      <div class=\"prod_box_big\">"+
			"        <div class=\"top_prod_box_big\"></div>"+
			"        <div class=\"center_prod_box_big\">"+
			"          <div class=\"contact_form\">"+
						"<form action=\"products?op=UPDATE&type=PRODUCT\" method=\"post\">"+
			
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Name:</strong></label>"+
						"<select name=\"name\">"+ 
						productSelection +
			     		"  </select>"+
			"            </div>"+

			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>New Name:</strong></label>"+
			"              <input type=\"text\" name=\"newname\" class=\"contact_input\" />"+
			"            </div>"+
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>New Price:</strong></label>"+
			"              <input type=\"text\" name=\"price\" class=\"contact_input\" />"+
			"            </div>"+
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>New Discount:</strong></label>"+
			"              <input type=\"text\" name=\"discount\" class=\"contact_input\" />"+
			"            </div>"+
						"<div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Category:</strong></label>"+
							"<select name=\"category\">"+
							"    <option value=\"OTHER\">Other</option>"+
							"    <option value=\"SmartPhones\">Smart Phones</option>"+
							"    <option value=\"Laptops\">Laptops</option>"+
							"    <option value=\"SmartWatches\">SmartWatches</option>"+
							"    <option value=\"HeadPhones\">HeadPhones</option>"+
							"    <option value=\"Speakers\">Speakers</option>"+
							"    <option value=\"ExternalStorage\">ExternalStorage</option>"+
							"  </select>"+
			"            </div>"+
			"             <button type=\"submit\" value=\"Add\" style=\"margin-left: 275px; padding: 8px; background: LawnGreen;\" class=\"contact\">Update</button> "+
						"</form>"+
			"          </div>"+
			"        </div>"+
			"        <div class=\"bottom_prod_box_big\"></div>"+
			"      </div>"+
			"    </div>"+
			
				"<div class=\"center_content\">"+
				"      <div class=\"center_title_bar\">Delete Product</div>"+
				"      <div class=\"prod_box_big\">"+
				"        <div class=\"top_prod_box_big\"></div>"+
				"        <div class=\"center_prod_box_big\">"+
				"          <div class=\"contact_form\">"+
							"<form action=\"products?op=DELETE&type=PRODUCT\" method=\"post\">"+
				
				"            <div class=\"form_row\">"+
				"              <label class=\"contact\"><strong>Name:</strong></label>"+
									"<select name=\"name\">"+ 
									productSelection +
								"  </select>"+
				"            </div>"+
							
				"             <button type=\"submit\" value=\"Delete\" style=\"margin-left: 275px; padding: 8px; background: LawnGreen;\" class=\"contact\">Delete</button> "+
							"</form>"+
				"          </div>"+
				"        </div>"+
				"        <div class=\"bottom_prod_box_big\"></div>"+
				"      </div>"+
				"    </div>"+


			"<div class=\"center_content\">"+
			"      <div class=\"center_title_bar\">Add Accessory</div>"+
			"      <div class=\"prod_box_big\">"+
			"        <div class=\"top_prod_box_big\"></div>"+
			"        <div class=\"center_prod_box_big\">"+
			"          <div class=\"contact_form\">"+
						"<form action=\"products?op=ADD&type=ACCESSORY\" method=\"post\">"+
			
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Product Name:</strong></label>"+
								"<select name=\"name\">"+ 
								productSelection +
							"  </select>"+
			"            </div>"+
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Accessory Name:</strong></label>"+
			"              <input type=\"text\" name=\"accname\" class=\"contact_input\" />"+
			"            </div>"+
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Accessory Price:</strong></label>"+
			"              <input type=\"text\" name=\"price\" class=\"contact_input\" />"+
			"            </div>"+
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Accessory Discount:</strong></label>"+
			"              <input type=\"text\" name=\"discount\" class=\"contact_input\" />"+
			"            </div>"+
						
			"             <div><button type=\"submit\" value=\"Add\"  style=\"margin-left: 275px; padding: 8px; background: LawnGreen;\" class=\"contact\">Add</button> </div>"+
						"</form>"+
			"          </div>"+
			"        </div>"+
			"        <div class=\"bottom_prod_box_big\"></div>"+
			"      </div>"+
			"    </div>"+

			"<div class=\"center_content\">"+
			"      <div class=\"center_title_bar\">Update Accessory</div>"+
			"      <div class=\"prod_box_big\">"+
			"        <div class=\"top_prod_box_big\"></div>"+
			"        <div class=\"center_prod_box_big\">"+
			"          <div class=\"contact_form\">"+
						"<form action=\"products?op=UPDATE&type=ACCESSORY\" method=\"post\">"+
			
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Product Name:</strong></label>"+
								"<select name=\"name\">"+ 
								productSelection +
							"  </select>"+
			"            </div>"+
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Accessory Name:</strong></label>"+
			"              <input type=\"text\" name=\"accname\" class=\"contact_input\" />"+
			"            </div>"+

			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>New Accessory Name:</strong></label>"+
			"              <input type=\"text\" name=\"newaccname\" class=\"contact_input\" />"+
			"            </div>"+

			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Accessory Price:</strong></label>"+
			"              <input type=\"text\" name=\"price\" class=\"contact_input\" />"+
			"            </div>"+
			"            <div class=\"form_row\">"+
			"              <label class=\"contact\"><strong>Accessory Discount:</strong></label>"+
			"              <input type=\"text\" name=\"discount\" class=\"contact_input\" />"+
			"            </div>"+
						
			"             <div><button type=\"submit\" value=\"Add\"  style=\"margin-left: 275px; padding: 8px; background: LawnGreen;\" class=\"contact\">Update</button> </div>"+
						"</form>"+
			"          </div>"+
			"        </div>"+
			"        <div class=\"bottom_prod_box_big\"></div>"+
			"      </div>"+
			"    </div>"+
			
					"<div class=\"center_content\">"+
					"      <div class=\"center_title_bar\">Delete Accessory</div>"+
					"      <div class=\"prod_box_big\">"+
					"        <div class=\"top_prod_box_big\"></div>"+
					"        <div class=\"center_prod_box_big\">"+
					"          <div class=\"contact_form\">"+
								"<form action=\"products?op=DELETE&type=ACCESSORY\" method=\"post\">"+
					
					"            <div class=\"form_row\">"+
					"              <label class=\"contact\"><strong>Name:</strong></label>"+
										"<select name=\"name\">"+ 
										productSelection +
									"  </select>"+
					"            </div>"+

									
					"            <div class=\"form_row\">"+
					"              <label class=\"contact\"><strong>Accessory Name:</strong></label>"+
					"              <input type=\"text\" name=\"accname\" class=\"contact_input\" />"+
					"            </div>"+
								
					"             <button type=\"submit\" value=\"Delete\" style=\"margin-left: 275px; padding: 8px; background: LawnGreen;\" class=\"contact\">Delete</button> "+
								"</form>"+
					"          </div>"+
					"        </div>"+
					"        <div class=\"bottom_prod_box_big\"></div>"+
					"      </div>"+
					"    </div>"
					   ;
						
	String cs=store.setBasicWithCSS("styles1");
	String content = "<section id=\"content\">"+ contentStr + "</section>";
    out.println(cs+store.headertype(utype,user)+content+store.sidebar()+store.footer());
  }
  protected void doPost(HttpServletRequest request,
		HttpServletResponse response) throws ServletException, IOException {
	  	String type = request.getParameter("type");
	    String op = request.getParameter("op");
		String name = request.getParameter("name");
		String newname = request.getParameter("newname");
		String accname = request.getParameter("accname");
		int rZip = 60616; 
		String productOnSale;
		
		double price = 0, discount = 0;
		String newaccname = "", priceString= "", discountString= "";
		String category = "Others";

		if(!op.equals(DELETE)){
			category = request.getParameter("category");
			newaccname = request.getParameter("newaccname");
			priceString = request.getParameter("price");
			discountString = request.getParameter("discount");
			
			if(priceString == null || priceString == "" || discountString == null || discountString == "") {
				System.out.println("Pprice/discount not added..");
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Invalid-price-or-discount-TRY-AGAIN"));
		
			}
             
			 if(priceString.isEmpty())
			 {
				 price=0.0;
			 }
			 else if(discountString.isEmpty()){
                discount=0.0;
			 }
			 else{
             System.out.println(priceString+"beechhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh%%%%%%%%%% "+discountString);
			 price = Double.parseDouble(priceString) ;
			 discount = Double.parseDouble(discountString);
			 }
		}

		
		if(name == null || name == "") {
			System.out.println("Product name required..");
			response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Product-name-required-TRY-AGAIN"));
		} else {
			if(op.equals(ADD) && type.equals(PRODUCT)) {
				String retailer = request.getParameter("retailer");
				String retailerCity = request.getParameter("retailerCity");
				String retailerState = request.getParameter("retailerState");
				String manufacturerName = request.getParameter("manufacturerName");
				int retailerZip = Integer.parseInt(request.getParameter("retailerZip"));
				productOnSale = request.getParameter("productOnSale");
				// if(!retailerZip.isEmpty()) {
				// 	rZip = Integer.parseInt(retailerZip);
				// }
				
				System.out.println("PRODUCT.. ADD");
				Product productObj = new Product(name,  price, discount, category, retailer, retailerZip, retailerCity, retailerState, productOnSale, manufacturerName);
				System.out.println("category" + category +  request.getParameter("category"));
				
				// productsFromDb = (HashMap<String, Product>) store.readFromFile("products");
				productsFromDb = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
	
				if(productsFromDb == null || productsFromDb.isEmpty()) {
					productsFromDb = new HashMap<String, Product>();
				} else {
				}
				productsFromDb.put(name, productObj);
				// store.setProducts(productObj);
				
				// store.writeToFile(productsFromDb, "products");
				store.writeToDB(productObj);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Product-"+name+"-added-successfully"));
			
			} else if(op.equals(DELETE) && type.equals(PRODUCT)) {
				System.out.println("PRODUCT..DELETE");
				
				// productsFromDb = (HashMap<String, Product>) store.readFromFile("products");
				productsFromDb = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
	
				if(productsFromDb == null || productsFromDb.isEmpty()) {
					productsFromDb = new HashMap<String, Product>();
				} else {
					//prinUsertMap(usersFromDb);
					productsFromDb.remove(name);
					mySQLStore.removeProduct(name);
				}
				// store.setProducts(productsFromDb);
				// store.writeToFile(productsFromDb, "products");
				// store.writeToDB(productsFromDb);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Product-"+name+"-deleted-successfully"));
			
			} else if(op.equals(UPDATE) && type.equals(PRODUCT)) {

				// String retailer = request.getParameter("retailer");
				// String retailerCity = request.getParameter("retailerCity");
				// String retailerState = request.getParameter("retailerState");
				// String manufacturerName = request.getParameter("manufacturerName");
				// String retailerZip = request.getParameter("retailerZip");
				// productOnSale = request.getParameter("productOnSale");
				// if(!retailerZip.isEmpty()) {
				// 	rZip = Integer.parseInt(retailerZip);
				// }
					
				System.out.println("PRODUCT..UPDATE");
				if(newname == null || newname == "") {
					System.out.println("Product new name required..");
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Product-new-name-required-TRY-AGAIN"));
				}
				Product productObj = new Product(newname, price, discount, category);
				
				// productsFromDb = (HashMap<String, Product>) store.readFromFile("products");
				productsFromDb = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
	
				if(productsFromDb == null || productsFromDb.isEmpty()) {
					productsFromDb = new HashMap<String, Product>();
				} else {
					Product productDb = productsFromDb.get(name);
					productObj.setAccessories1(productDb.getAccessories());
					// productsFromDb.remove(name);
					// productsFromDb.put(newname, productObj);
					mySQLStore.updateproductdetails(newname, price, discount, category,name);
				}
				store.setProducts(productsFromDb);
				// store.writeToFile(productsFromDb, "products");
				// store.writeToDB(productsFromDb);
				response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Product-"+name+"-updated-successfully"));
			
			} else if((op.equals(ADD) && type.equals(ACCESSORY))) {
				
				if((accname == "" && accname == null)) {
					System.out.println("Accessory name required..");
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Accessory-name-required-TRY-AGAIN"));
				} else {
					System.out.println("PRODUCT.. ACCESSORY...ADD" +name+ accname + price + discount);
					Accessory accessoryObj = new Accessory(accname, price, discount);
					// productsFromDb = (HashMap<String, Product>) store.readFromFile("products");
					productsFromDb = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
					
					Product productFromMap = productsFromDb.get(name);

					HashMap<String, List<Accessory>> productaccessory = productFromMap.getAccessories();
					for(Entry<String,List<Accessory>> ma :productaccessory.entrySet()){
						List<Accessory> tem =new ArrayList<Accessory>();
						tem=ma.getValue();
                        tem.add(accessoryObj);
						productaccessory.put(accname, tem);
					}
					
					productFromMap.setAccessories1(productaccessory);
		
					productsFromDb.put(name, productFromMap);
					store.setProducts(productsFromDb);
					
					store.writeToFile(productsFromDb, "products");
					// store.writeToDB(productsFromDb);
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Accessory-"+accname+"-added-successfully"));
				}
			
			} else if((op.equals(DELETE) && type.equals(ACCESSORY))) {
				
				if((accname == "" && accname == null)) {
					System.out.println("Accessory name required..");
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Accessory-name-required-TRY-AGAIN"));
				} else {
					System.out.println("PRODUCT.. ACCESSORY...DELETE");
					// productsFromDb = (HashMap<String, Product>) store.readFromFile("products");
					productsFromDb = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
					
					Product productFromMap = productsFromDb.get(name);
					HashMap<String, List<Accessory>> productaccessory = productFromMap.getAccessories();
					productaccessory.remove(accname);
					productFromMap.setAccessories1(productaccessory);
		
					productsFromDb.put(name, productFromMap);
					
					store.setProducts(productsFromDb);
					
					store.writeToFile(productsFromDb, "products");
					// store.writeToDB(productsFromDb);
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Accessory-"+accname+"-deleted-successfully"));
				}
			} else if((op.equals(UPDATE) && type.equals(ACCESSORY))) {
				
				if((accname == "" || accname == null || newaccname == "" || newaccname == null)) {
					System.out.println("Accessory name required..");
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Accessory-name-required-TRY-AGAIN"));
				} else {
					Accessory accessoryObj = new Accessory(newaccname, price, discount);
					
					System.out.println("PRODUCT.. ACCESSORY...UPDATE");
					// productsFromDb = (HashMap<String, Product>) store.readFromFile("products");
					productsFromDb = (HashMap<String, Product>) mySQLStore.fetchAllProducts();
					
					Product productFromMap = productsFromDb.get(name);
					HashMap<String, List<Accessory>> productaccessory = productFromMap.getAccessories();
					for(Entry<String,List<Accessory>> ma :productaccessory.entrySet()){
						List<Accessory> tem =new ArrayList<Accessory>();
						tem=ma.getValue();
                        tem.remove(accname);
						tem.add(accessoryObj);
						productaccessory.put(newaccname, tem);
					}
					productFromMap.setAccessories1(productaccessory);
		
					//productsFromDb.put(name, productObj);
					store.setProducts(productsFromDb);
					
					store.writeToFile(productsFromDb, "products");
					// store.writeToDB(productsFromDb);
					response.sendRedirect(response.encodeRedirectURL(request.getContextPath() + "/myaccount/storemanager?"+"Accessory-"+accname+"-updated-successfully"));
				}
			} 
		}
		
	}
}