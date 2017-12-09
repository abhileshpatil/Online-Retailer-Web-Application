import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.*;

public class CommonUtils {
    public static MySQLDataStoreUtilities mysql =new MySQLDataStoreUtilities();
	public static HashMap<String, Product> products = new HashMap<String, Product>();
	public static HashMap<String, User> users = new HashMap<String, User>();
	public static HashMap<String, Order> orders = new HashMap<String, Order>();
	public static HashMap<String, CartItem> cartItems = new HashMap<String, CartItem>();
	String BASICS;
	// public static Product products;
	private static String header ;

static void  writeToFile(Object obj, String filename){
		File ifile=new File(filename);
	   
	    try{
	    FileOutputStream fos=new FileOutputStream(ifile);
        ObjectOutputStream oos=new ObjectOutputStream(fos);
        	oos.writeObject(obj);
	        oos.flush();
	        oos.close();
	        fos.close();
	    }catch(Exception e){
			System.out.println("Error writing obj.");
		}
	
	}

static void  writeToDB(Product products){
	  mysql.insertProduct(products);
	
	}


	static Object readFromFile(String filename) {
		Object obj = null;
	    try{
	        File filei=new File(filename);
	        FileInputStream fis=new FileInputStream(filei);
	        ObjectInputStream ois=new ObjectInputStream(fis);
	
	        obj=ois.readObject();
	        ois.close();
	        fis.close();
	        
	    }catch(Exception e){
	    	System.out.println("Error reading obj.");
	    }
	    return obj;
	}

	public static HashMap<String, User> getUsers() {
	return users;
    }

	public static void setUsers(HashMap<String, User> users) {
		CommonUtils.users = users;
	}

    public static HashMap<String, Product> getProducts() {
	return products;
    }

	public static void setProducts(HashMap<String, Product> products) {
	CommonUtils.products = products;
    }

	public static HashMap<String, Order> getOrders() {
	return orders;
     }

	public static void setOrders(HashMap<String, Order> orders) {
	CommonUtils.orders = orders;
}
public static HashMap<String, CartItem> getCartItems() {
	return cartItems;
}

public static void setCartItems(HashMap<String, CartItem> cartItems) {
	CommonUtils.cartItems = cartItems;
}

	public String setBasicWithCSS( String extCode) {
	  this.BASICS = "<!doctype html>"+
				"<html>"+
				"<head>"+
				"<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />"+
				"<title>spigot - Free CSS Tobjlate by ZyPOP</title>"+
				" <script type = \"text/javascript\" src = \"https://www.gstatic.com/charts/loader.js\">"+
     " </script>"+
    "      <script type = \"text/javascript\">"+
    "     google.charts.load('current', {packages: ['corechart']});     "+
"      </script>"+
"<script type=\"text/javascript\" src=\"\"></script>"+
"<script type=\"text/javascript\">"+
"var completeTable;"+ 
"var completeField;"+
"var autoRow;"+
"var isIE;"+
"function init() {"+
"completeField = document.getElementById(\"searchId\");"+
"completeTable = document.getElementById(\"complete-table\");"+
"autoRow = document.getElementById(\"auto-row\");"+
"}"+
"function doCompletion() {"+
// "completeField = document.getElementById(\"searchId\").value;"+
// "document.location.href = \"SearchController?searchId=\"+completeField;"+
"if(completeField== null || completeField == undefined)"+
"{"+
	"completeField=document.getElementById(\"searchId\");"+
"}"+
"if(completeTable== null || completeTable == undefined)"+
"{"+
	"completeTable=document.getElementById(\"complete-table\");"+
"}"+
"if(autoRow== null || autoRow == undefined)"+
"{"+
	"autoRow=document.getElementById(\"auto-row\");"+
"}"+
"var url = \"SearchController?action=complete&searchId=\" + escape(searchId.value);"+
"req = initRequest();"+
"req.open(\"GET\", url, true);"+
"req.onreadystatechange = callback;"+
"req.send();"+
"}"+

"function initRequest() {"+
"if (window.XMLHttpRequest) {"+
"if (navigator.userAgent.indexOf('MSIE') != -1) {"+
"isIE = true;"+
"}"+
"return new XMLHttpRequest();"+
"} else if (window.ActiveXObject) {"+
"isIE = true;"+
"return new ActiveXObject(\"Microsoft.XMLHTTP\");"+
"}"+
"}"+

"function appendProduct(productName,productId) {"+
"var row;"+
"var cell;"+
"var linkElement;"+
"if (isIE) {"+
"completeTable.style.display = \'block\';"+
"row = completeTable.insertRow(completeTable.rows.length);"+
"cell = row.insertCell(0);"+
"} else {"+
"completeTable.style.display = \'table\';"+
"row = document.createElement(\"tr\");"+
"cell = document.createElement(\"td\");"+
"cell.style.padding=\"2px 0px 2px 2px\";"+
"cell.style.background=\"#FF0000\";"+
"row.appendChild(cell);"+
"completeTable.appendChild(row);"+
"}"+
"cell.className = \"popupCell\";"+
"linkElement = document.createElement(\"a\");"+
"linkElement.className = \"popupItem\";"+
"linkElement.style.padding = \"5px\";"+
"linkElement.setAttribute(\"href\", \"autoproduct?action=lookup&productname=\" + productName);"+
"linkElement.appendChild(document.createTextNode(productName));"+
"cell.appendChild(linkElement);"+
"}"+

"function parseMessages(responseXML) {"+
// no matches returned

"if (responseXML == null || responseXML == undefined) {"+
"console.log(responseXML);"+
"return false;"+
"} else {"+
"var products = responseXML.getElementsByTagName(\"products\")[0];"+
"console.log(products);"+
"if (products.childNodes.length > 0) {"+
"completeTable.setAttribute(\"bordercolor\", \"black\");"+
"completeTable.setAttribute(\"border\", \"1\");"+
"for (loop = 0; loop < products.childNodes.length; loop++) {"+
"var product = products.childNodes[loop];"+
"var productName = product.getElementsByTagName(\"productName\")[0];"+
"var productId = product.getElementsByTagName(\"id\")[0];"+
"appendProduct(productName.childNodes[0].nodeValue,"+
"productId.childNodes[0].nodeValue);"+
"}"+
"}"+
"}"+
"}"+
"function callback() {"+
"clearTable();"+
"if (req.readyState == 4) {"+
"if (req.status == 200) {"+
"console.log(req);"+
"parseMessages(req.responseXML);"+
"}"+
"}"+
"}"+
"function clearTable() {"+
"if (completeTable.getElementsByTagName(\"tr\").length > 0) {"+
"completeTable.style.display = 'none';"+
"for (loop = completeTable.childNodes.length -1; loop >= 0 ; loop--) {"+
"completeTable.removeChild(completeTable.childNodes[loop]);"+
"}"+
"}"+
"}"+

"</script>"+
				"<link rel=\"stylesheet\" href=\"/SmartPortables/styles.css\" type=\"text/css\" />"+
				"<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css\">"+
                  "<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js\"></script>"+
                  "<script src=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js\"></script>"+
				"<link rel=\"stylesheet\" href=\"/SmartPortables/"+extCode +".css\" type=\"text/css\" />"+
				"<!--[if lt IE 9]>"+
				"<script src=\"http://html5shiv.googlecode.com/svn/trunk/html5.js\"></script>"+
				"<![endif]-->"+
				"<!--"+
				"spigot, a free CSS web tobjlate by ZyPOP (zypopwebtobjlates.com/)"+
				""+
				"Download: http://zypopwebtobjlates.com/"+
				""+
				"License: Creative Commons Attribution"+
				"//-->"+
				"</head>"+
				"<body>"+
				"<script type=\"text/javascript\" src=\"\"></script>"+
				"<div id=\"container\">";
		return BASICS;
  }

  public String headertype(String utype,String user)
{
if(utype.equals("CUSTOMER"))
{
header= 
    "<header>"+
    	"<h1><a href=\"/\">Online<span>retailer</span></a></h1>"+
        "<h2>A great place to be</h2>"+
    "</header>"+
    "<nav>"+
    	"<ul>"+
        	"<li class=\"start selected\"><a href=\"/SmartPortables/Home\">Home</a></li>"+
            "<li class=\"\"><a href=\"/SmartPortables/myaccount/customer/orders\">My Orders</a></li>"+
            "<li><a href=\"/SmartPortables/cart\">Cart</a></li>"+
			"<li class=\"\"><a href=\"/SmartPortables/myaccount\">My Accounts</a></li>"+
            "<li><a href=\"/SmartPortables/Logout\">Logout</a></li>"+
			"<li><a>"+"Hello"+" "+user+"</a></li>"+
			"<li><a><input type=\"text\" id=\"searchId\" name=\"searchId\" class=\"input\" value=\"\" onkeyup=\"doCompletion()\" style=\"color:black;\"/>"+
	// "<div id=\"auto-row\">"+
    "<table id=\"complete-table\" class=\"gridtable\" style=\"position: absolute; width:"+
"16%; height:\"20px\"; font-size: \"2px\" \"></table></a></li>"+
        "</ul>"+
    "</nav>"+

	"<div id=\"body\">";
}
else if(utype.equals("STOREMANAGER"))
{
header= 
    "<header>"+
    	"<h1><a href=\"/\">Online<span>retailer</span></a></h1>"+
        "<h2>A great place to be</h2>"+
    "</header>"+
    "<nav>"+
    	"<ul>"+
        	"<li class=\"start selected\"><a href=\"/SmartPortables/Home\">Home</a></li>"+
			"<li class=\"\"><a href=\"/SmartPortables/inventory?qId=0\">Inventory</a></li>"+
			"<li class=\"\"><a href=\"/SmartPortables/salesreport?qId=0\">Sales Report</a></li>"+
            "<li class=\"\"><a href=\"/SmartPortables/myaccount/storemanager/products\">Products</a></li>"+
			"<li class=\"\"><a href=\"/SmartPortables/myaccount\">My Accounts</a></li>"+
			"   <li class=\"\"><a href=\"/SmartPortables/analytics\">Analytics</a></li>"+
            "<li><a href=\"/SmartPortables/Logout\">Logout</a></li>"+
            "<li><a>"+"Hello"+" "+user+"</a></li>"+
        "</ul>"+
    "</nav>"+
	"<div id=\"body\">";

}else if(utype.equals("SALESMAN"))
{
header= 
    "<header>"+
    	"<h1><a href=\"/\">Online<span>retailer</span></a></h1>"+
        "<h2>A great place to be</h2>"+
    "</header>"+
    "<nav>"+
    	"<ul>"+
        	"<li class=\"start selected\"><a href=\"/SmartPortables/Home\">Home</a></li>"+
			"<li class=\"\"><a href=\"/SmartPortables/myaccount\">My Accounts</a></li>"+
            "<li class=\"\"><a href=\"/SmartPortables/myaccount/salesman/createuser\">Create Accounts</a></li>"+
			"<li class=\"\"><a href=\"#\">Manage Orders</a></li>"+
            "<li><a href=\"/SmartPortables/Logout\">Logout</a></li>"+
            "<li><a>"+"Hello"+" "+user+"</a></li>"+
        "</ul>"+
    "</nav>"+
	"<div id=\"body\">";
}
else if(utype.equals("RETAILER")){
header= 
    "<header>"+
    	"<h1><a href=\"/\">Online<span>retailer</span></a></h1>"+
        "<h2>A great place to be</h2>"+
    "</header>"+
    "<nav>"+
    	"<ul>"+
        	"<li class=\"start selected\"><a href=\"/SmartPortables/Home\">Home</a></li>"+
			"<li class=\"\"><a href=\"/SmartPortables/myaccount\">My Accounts</a></li>"+
            "<li class=\"\"><a href=\"/SmartPortables/myaccount/retailer/offer\">Offers/warranty</a></li>"+
            "<li><a href=\"/SmartPortables/Logout\">Logout</a></li>"+
            "<li><a>"+"Hello"+" "+user+"</a></li>"+
        "</ul>"+
    "</nav>"+
	"<div id=\"body\">";
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
							"                        <li><a href=\"/SmartPortables/trending\">Trending</a></li>"+
							"                        <li><a href=\"/SmartPortables/Home?cat=SmartWatches\">SmartWatches</a></li>"+
							"                        <li><a href=\"/SmartPortables/Home?cat=Speakers\">Speakers</a></li>"+
							"                        <li><a href=\"/SmartPortables/Home?cat=SmartPhones\">Smart Phones</a></li>"+

							"            <li><a href=\"/SmartPortables/register\">Register</a></li>"+
							
							"            <li class=\"\"><a href=\"/SmartPortables/login\">Login</a></li>"+
				"</ul>"+
			"</nav>"+
			"<div id=\"body\">";
}

return header;
}
public String sidebar()
{
String sidebar = "<aside class=\"sidebar\">"+
						"	"+
						"            <ul>	"+
						"               <li>"+
						"                    <h4>Categories</h4>"+
						"                    <ul>"+
						"                        <li><a href=\"/SmartPortables/trending\">Trending</a></li>"+
						"                        <li><a href=\"/SmartPortables/Home?cat=SmartWatches\">SmartWatches</a></li>"+
						"                        <li><a href=\"/SmartPortables/Home?cat=Speakers\">Speakers</a></li>"+
						"                        <li><a href=\"/SmartPortables/Home?cat=HeadPhones\">HeadPhones</a></li>"+
						"                        <li><a href=\"/SmartPortables/Home?cat=SmartPhones\">Smart Phones</a></li>"+
						"                        <li><a href=\"/SmartPortables/Home?cat=Laptops\">Laptops</a></li>"+
						"                        <li><a href=\"/SmartPortables/Home?cat=ExternalStorage\">ExternalStorage</a></li>"+
						"                    </ul>"+
						"                </li>"+
						"                "+
						"                <li>"+
						"                    <h4>About us</h4>"+
						"                    <ul>"+
						"                        <li class=\"text\">"+
						"                        	<p style=\"margin: 0;\">You will get quality products and services with best possible deals at your doorsteps</p>"+
						"                        </li>"+
						"                    </ul>"+
						"                </li>"+
						"            </ul>"+
						"		"+
						"        </aside>"+
						"    	<div class=\"clear\"></div>"+
						"    </div>";
    return sidebar;
}	
public String footer()
{
	String footer = "<footer>"+
								"        <div class=\"footer-bottom\">"+
								"            <p> © Online Retailer 2017. <a href=\"http://zypopwebtobjlates.com/\">Free CSS Website Tobjlates</a> by Abhilesh Patil</p>"+
								"         </div>"+
								"    </footer>"+
								"</div>"+
								"</body>"+
								"</html>";
return footer;
}
}