import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.ArrayList;
import java.util.*;

public class MySQLDataStoreUtilities {

	public static  Statement stmt;
	public static  Connection conn ;
	
	public void connectToMySQL(){
    	
        final String JDBC_DRIVER="com.mysql.jdbc.Driver";  
        final String DB_URL="jdbc:mysql://localhost:3306/OnlineRetailer";
        final String USER = "";                                              
        final String PASS = "";
       
	    try {
			Class.forName("com.mysql.jdbc.Driver");
		
			conn = DriverManager.getConnection(DB_URL, USER, PASS);
			stmt = conn.createStatement();
			
	    } catch (Exception e) {
			System.out.println("*************ERROR in connecting mySQL DB *******************");
			
		}  
    }
	
	public static void insertUser(User u){
		try{
			Connection conn = getConnection();
			String insertIntouserregister = "INSERT INTO UserRegister(name,password,utype) "
			+ "VALUES (?,?,?);";
			PreparedStatement pst = conn.prepareStatement(insertIntouserregister);
			pst.setString(1,u.getName());
			pst.setString(2,u.getPassword());
			pst.setString(3,u.getUtype().toString());
			pst.execute();
			System.out.println(u.getName()+"  "+ u.getPassword()+"    "+ u.getUtype().toString());
			pst.close();
			
		} catch(Exception e){
			System.out.println("*************ERROR in insert user *******************");
		}
	}


	public static void insertProduct(Product p){
		try{
			Connection conn = getConnection();
			String insertIntoProducts = "INSERT INTO Products(PName,Price,Category,discount,rdiscount,rwarranty,rrebate,retailer,retailerZip,retailerCity,retailerState,productOnSale,manufacturerName,quantity) "
			+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
			PreparedStatement pst = conn.prepareStatement(insertIntoProducts);
			pst.setString(1,p.getName());
			pst.setDouble(2,p.getPrice());
			pst.setString(3,p.getCategory());
			pst.setDouble(4,p.getDiscount());
			pst.setDouble(5,p.getRdiscount());
			pst.setDouble(6,p.getRwarranty());
			pst.setDouble(7,p.getrrebate());
			pst.setString(8,p.getRetailer());
			pst.setInt(9,p.getRetailerZip());
			pst.setString(10,p.getRetailerCity());
			pst.setString(11,p.getRetailerState());
			pst.setString(12,p.getProductOnSale());
			pst.setString(13,p.getManufacturerName());
			pst.setInt(14,p.getquantity());
			pst.execute();
			pst.close();
			PreparedStatement ast = null;
			System.out.println("-------------------------- In insert product");
			System.out.println("ACcessory ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+p.getAccessories());
			// Product cp = p.getName();
			HashMap<String,List<Accessory>> accessories = p.getAccessories();
			int entrycount=0;
			for(Entry<String, List<Accessory>> ma :accessories.entrySet()){
				List<Accessory> temp =new ArrayList<Accessory>();
				temp=ma.getValue();
				for(int i=0;i<temp.size();i++)
				{
				Accessory a =temp.get(i);
				entrycount++;
				System.out.println("mysql Accessory"+" "+entrycount+" "+p.getName()+" "+ma.getKey());
				System.out.println("mysql Accessory"+" "+entrycount+" "+a.getName());
				System.out.println("mysql Accessory"+" "+entrycount+" "+a.getPrice());
				System.out.println("mysql Accessory"+" "+entrycount+" "+a.getDiscount());

				String insertIntoAccessory = "INSERT INTO Accessory(PName,AName,APrice,Adiscount) "
			    + "VALUES (?,?,?,?);";
			    ast = conn.prepareStatement(insertIntoAccessory);
				ast.setString(1,p.getName());
				ast.setString(2,a.getName());
				ast.setDouble(3,a.getPrice());
				ast.setDouble(4,a.getDiscount());
				ast.execute();
				ast.close();
				}
			}
			
		} catch(Exception e){
			System.out.println("*************ERROR in insert products *******************");
		}
	}

	public static void insertOrder(Order o){

		System.out.println("*************insert *******************");
		try{
			
			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "INSERT INTO orders(name,description,price, orderDate, status, buyer,zip) "
			+ "VALUES (?,?,?,?,?,?,?);";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			
			pst.setString(1,o.getName());
			pst.setString(2,o.getDescription());
			pst.setDouble(3,o.getPrice());
			pst.setDate(4, new java.sql.Date(o.getOrderDate().getTime()));
			
			pst.setString(5,o.getStatus().toString());
			pst.setString(6,o.getBuyer());
			pst.setInt(7,o.getZip());
			pst.execute();
			pst.close();
			
		} catch(Exception e){
			System.out.println("*************ERROR in insert order *******************");
		}
	}

public static HashMap<String, User> fetchAllUsers()
	{
		HashMap<String, User> usersFromDB=new HashMap<String, User>();
		
		try{
			conn = getConnection();
			String q ="select * from UserRegister";
			PreparedStatement pst = conn.prepareStatement(q);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				User user= new User(rs.getString("name"), rs.getString("address"), rs.getString("credNo"),rs.getString("utype")); 
			//	user.setId(rs.getInt("id"));
				user.setPassword(rs.getString("password"));
				usersFromDB.put(rs.getString("name"), user);
			}	
			
			pst.close();
				
		}catch(Exception e){
			System.out.println("*************ERROR in fetch all users *******************");
		}
		return usersFromDB;
	}

	public static HashMap<String, Product> fetchAllProducts()
	{
		HashMap<String, Product> productsFromDB=new HashMap<String, Product>();
		try{
			conn = getConnection();
			String q ="select * from Products";
			PreparedStatement pst = conn.prepareStatement(q);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Product p= new Product(rs.getString("PName"), rs.getDouble("Price"), rs.getDouble("discount"), rs.getString("Category"), rs.getDouble("rdiscount"), rs.getDouble("rwarranty"), rs.getDouble("rrebate"), rs.getString("retailer"), rs.getInt("retailerZip"), rs.getString("retailerCity"), rs.getString("retailerState"), rs.getString("productOnSale"), rs.getString("manufacturerName"), rs.getInt("quantity"));
				productsFromDB.put(rs.getString("PName"), p);
			}	
			
			pst.close();
				
		}catch(Exception e){
			System.out.println("*************ERROR in fetch all orders *******************");
		}
		return productsFromDB;
	}

	public static HashMap<String, List<Accessory>> fetchaccessory(String name)
	{
		HashMap<String, List<Accessory>> accessoryFromDB=new HashMap<String, List<Accessory>>();
		List<Accessory> access = new ArrayList<Accessory>();
		try{
			conn = getConnection();
			String q ="select * from Accessory WHERE PName = ?";
			PreparedStatement pst = conn.prepareStatement(q);
			pst.setString(1,name);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Accessory a= new Accessory(rs.getString("AName"), rs.getDouble("APrice"), rs.getDouble("Adiscount"));
				access.add(a);
			}
			System.out.println("print access"+access);	
			accessoryFromDB.put(name, access);
			pst.close();
				
		}catch(Exception e){
			System.out.println("*************ERROR in fetch all accessory *******************");
		}
		return accessoryFromDB;
	}

	public static HashMap<String, Product> fetchAllProductswithName(String name)
	{
		HashMap<String, Product> productsFromDB=new HashMap<String, Product>();
		try{
			conn = getConnection();
			String q ="select * from Products Where PName = ?";
			PreparedStatement pst = conn.prepareStatement(q);
			pst.setString(1,name);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Product p= new Product(rs.getString("PName"), rs.getDouble("Price"), rs.getDouble("discount"), rs.getString("Category"), rs.getDouble("rdiscount"), rs.getDouble("rwarranty"), rs.getDouble("rrebate"), rs.getString("retailer"), rs.getInt("retailerZip"), rs.getString("retailerCity"), rs.getString("retailerState"), rs.getString("productOnSale"), rs.getString("manufacturerName"), rs.getInt("quantity"));
				productsFromDB.put(rs.getString("PName"), p);
			}	
			
			pst.close();
				
		}catch(Exception e){
			System.out.println("*************ERROR in fetch all orders *******************");
		}
		return productsFromDB;
	}

    public static HashMap<String, Product> fetchAllProductsQuantity()
	{
		HashMap<String, Product> productsFromDB_Quan=new HashMap<String, Product>();
		try{
			conn = getConnection();
			String q ="select * from Products";
			PreparedStatement pst = conn.prepareStatement(q);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Product p= new Product(rs.getString("PName"), rs.getDouble("Price"), rs.getInt("quantity"));
				productsFromDB_Quan.put(rs.getString("PName"), p);
			}	
			
			pst.close();
				
		}catch(Exception e){
			System.out.println("*************ERROR in fetch all products quantity *******************");
		}
		return productsFromDB_Quan;
	}

	public static HashMap<String,Product> autocompletion(String name)
	{
		HashMap<String,Product> hm=new HashMap<String,Product>();
		// ArrayList<String> list = new ArrayList<String>();
		String data;
		try{
			conn = getConnection();
			String q ="SELECT * FROM Products WHERE PName  LIKE ?";
			PreparedStatement pst = conn.prepareStatement(q);
			pst.setString(1,name + "%");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				// data = rs.getString("PName");
				Product p = new Product(rs.getString("PName"), rs.getDouble("Price"), rs.getDouble("discount"), rs.getString("Category"), rs.getDouble("rdiscount"), rs.getDouble("rwarranty"), rs.getDouble("rrebate"), rs.getString("retailer"), rs.getInt("retailerZip"), rs.getString("retailerCity"), rs.getString("retailerState"), rs.getString("productOnSale"), rs.getString("manufacturerName"), rs.getInt("quantity"));
                hm.put(rs.getString("PName"), p);
			}	
			
			pst.close();
				
		}catch(Exception e){
			System.out.println("*************ERROR in fetch all products quantity *******************");
		}
		return hm;
	}

	public ArrayList<String> getFrameWork(String frameWork) {
        ArrayList<String> list = new ArrayList<String>();
        PreparedStatement ps = null;
        String data;
        try {
			conn = getConnection();
        ps = conn.prepareStatement("SELECT * FROM Products  WHERE PName LIKE ?");
                ps.setString(1, frameWork + "%");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                        data = rs.getString("PName");
                        list.add(data);
                }
        } catch (Exception e) {
                System.out.println(e.getMessage());
        }
        return list;
}

	public static HashMap<String, Product> fetchAllProductsOnSale()
	{
		HashMap<String, Product> productsFromDB_Sale=new HashMap<String, Product>();
		try{
			conn = getConnection();
			String q ="select * from Products Where productOnSale = ? ";
			PreparedStatement pst = conn.prepareStatement(q);
			pst.setString(1,"YES");
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Product p= new Product(rs.getString("PName"), rs.getDouble("Price"), rs.getInt("quantity"));
				productsFromDB_Sale.put(rs.getString("PName"), p);
			}	
			
			pst.close();
				
		}catch(Exception e){
			System.out.println("*************ERROR in fetch all products on sale *******************");
		}
		return productsFromDB_Sale;
	}

	public static HashMap<String, Product> fetchAllProductsWithRebate()
	{
		HashMap<String, Product> productsFromDB_Sale=new HashMap<String, Product>();
		try{
			conn = getConnection();
			String q ="select * from Products Where rrebate > ? ";
			PreparedStatement pst = conn.prepareStatement(q);
			pst.setInt(1,0);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Product p= new Product(rs.getString("PName"), rs.getDouble("Price"), rs.getInt("quantity"));
				productsFromDB_Sale.put(rs.getString("PName"), p);
			}	
			
			pst.close();
				
		}catch(Exception e){
			System.out.println("*************ERROR in fetch all products on sale *******************");
		}
		return productsFromDB_Sale;
	}

	public static HashMap<String, Order> fetchAllOrders()
	{
		HashMap<String, Order> ordersFromDB=new HashMap<String, Order>();
		
		try{
			conn = getConnection();
			String q ="select * from orders";
			PreparedStatement pst = conn.prepareStatement(q);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Order order= new Order(rs.getString("name"), rs.getString("description"), rs.getString("buyer"), OrderStatus.fromString(rs.getString("status"))); 
				order.setOrderDate(((java.util.Date) rs.getDate("orderDate")));
				order.setId(rs.getInt("id"));
				order.setPrice(rs.getDouble("price"));
				order.setZip(rs.getInt("zip"));
				ordersFromDB.put(rs.getString("name"), order);
			}	
			
			pst.close();
				
		}catch(Exception e){
			System.out.println("*************ERROR in fetch all orders *******************");
		}
		return ordersFromDB;
	}

	public static HashMap<String, Order> fetchAllOrdersNameDate()
	{
		HashMap<String, Order> ordersFromDB=new HashMap<String, Order>();
		
		try{
			conn = getConnection();
			String q ="select * from orders";
			PreparedStatement pst = conn.prepareStatement(q);
			ResultSet rs = pst.executeQuery();
			
			while(rs.next())
			{
				Order order= new Order(rs.getString("name"), rs.getString("description"), rs.getString("buyer"), OrderStatus.fromString(rs.getString("status"))); 
				order.setOrderDate(((java.util.Date) rs.getDate("orderDate")));
				order.setId(rs.getInt("id"));
				order.setPrice(rs.getDouble("price"));
				order.setZip(rs.getInt("zip"));
				ordersFromDB.put(rs.getString("name"), order);
			}	
			
			pst.close();
				
		}catch(Exception e){
			System.out.println("*************ERROR in fetch all orders *******************");
		}
		return ordersFromDB;
	}

	public static void removeOrder(String name)
	{
		
		try{
			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "DELETE from orders WHERE name = ? ";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			
			pst.setString(1,name);
			
			pst.executeUpdate();
			pst.close();
			
		}catch(Exception e){
			System.out.println("*************ERROR in remove order *******************");
		}
	}
	
	public static void removeProduct(String name)
	{
		
		try{
			Connection conn = getConnection();
			String delAccessory = "DELETE from Accessory WHERE PName = ? ";
			PreparedStatement ast = conn.prepareStatement(delAccessory);
			ast.setString(1,name);
			ast.executeUpdate();
			ast.close();
			String delproduct = "DELETE from Products WHERE PName = ? ";
			PreparedStatement pst = conn.prepareStatement(delproduct);
			
			pst.setString(1,name);
			
			pst.executeUpdate();
			pst.close();
			
		}catch(Exception e){
			System.out.println("*************ERROR in remove Product *******************");
		}
	}

	public static void removeAccessory(String name)
	{
		
		try{
			Connection conn = getConnection();
			String delAccessory = "DELETE from Accessory WHERE AName = ? ";
			PreparedStatement ast = conn.prepareStatement(delAccessory);
			ast.setString(1,name);
			ast.executeUpdate();
			ast.close();
			
		}catch(Exception e){
			System.out.println("*************ERROR in remove Accessory *******************");
		}
	}
	public static void updateOrderStatus(String name, String status)
	{
		
		try{
			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "UPDATE orders SET status = ? WHERE name = ?  ";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,status);
			pst.setString(2,name);
			
			pst.executeUpdate();
			pst.close();
			
		}catch(Exception e){
			System.out.println("*************ERROR in remove order *******************");
		}
	}
	
	public static void updateOrderStatusAndPrice(String name, String status, double price)
	{
		
		try{
			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "UPDATE orders SET status = ? , price = ?  WHERE name = ?  ";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,status);
			pst.setDouble(2,price);
			pst.setString(3,name);
			
			pst.executeUpdate();
			pst.close();
			
		}catch(Exception e){
			System.out.println("*************ERROR in update order *******************");
		}
	}
	
	public static void updateUserAddressAndCredNo(String name, String address, String credNo)
	{
		System.out.println("*************Update *******************");
		try{
			Connection conn = getConnection();
			String insertIntoCustomerRegisterQuery = "UPDATE UserRegister SET address = ? , credNo = ? WHERE name = ?  ";
			PreparedStatement pst = conn.prepareStatement(insertIntoCustomerRegisterQuery);
			pst.setString(1,address);
			pst.setString(2,credNo);
			pst.setString(3,name);
			
			pst.executeUpdate();
			pst.close();
			
		}catch(Exception e){
			System.out.println("*************ERROR in update user *******************");
		}
	}

	public static void updateproductdetails(String name, double price, double discount, String category, String oldname)
	{
		System.out.println("*************Update product details*******************");
		System.out.println(name+" "+price+" "+discount+" "+category);
		try{
			Connection conn = getConnection();
			String updateproduct = "UPDATE Products SET PName = ? , Price = ? , discount = ? , Category = ? WHERE PName = ?";
			PreparedStatement pst = conn.prepareStatement(updateproduct);
			pst.setString(1,name);
			pst.setDouble(2,price);
			pst.setDouble(3,discount);
			pst.setString(4,category);
			pst.setString(5,oldname);
			
			pst.executeUpdate();
			pst.close();
			
		}catch(Exception e){
			System.out.println("*************ERROR in update user *******************");
		}
	}

	public static void updatequantity(String name)
	{
		System.out.println("*************Update product quantity*******************");
		System.out.println(name);
		try{
			Connection conn = getConnection();
			String updateproduct = "UPDATE Products SET quantity = quantity -1  WHERE PName = ? and quantity > 0";
			PreparedStatement pst = conn.prepareStatement(updateproduct);
			pst.setString(1,name);
			
			pst.executeUpdate();
			pst.close();
			
		}catch(Exception e){
			System.out.println("*************ERROR in update quantity *******************");
		}
	}

	public static void updatecancelquantity(String name)
	{
		System.out.println("*************Update cancel quantity*******************");
		System.out.println(name);
		try{
			Connection conn = getConnection();
			String updateproduct = "UPDATE Products SET quantity = quantity +1  WHERE PName = ? ";
			PreparedStatement pst = conn.prepareStatement(updateproduct);
			pst.setString(1,name);
			
			pst.executeUpdate();
			pst.close();
			
		}catch(Exception e){
			System.out.println("*************ERROR in cancel quantity *******************");
		}
	}

	public static void updatewarrantydiscount(String name,double val, String wd)
	{
		System.out.println("*************Update warranty and discount*******************");
		System.out.println("Update warranty"+name);
		System.out.println("Update warranty"+wd);
		try{
			Connection conn = getConnection();
			String updateproduct = "UPDATE Products SET"+" "+wd+" "+"= ?"+" "+"WHERE PName = ? ";
			PreparedStatement pst = conn.prepareStatement(updateproduct);
			pst.setDouble(1,val);
			pst.setString(2,name);
			
			pst.executeUpdate();
			pst.close();
			
		}catch(Exception e){
			System.out.println("*************ERROR in update warranty and discount *******************");
		}
	}


      public static Statement getStatement() {
    	return stmt;
    }
    
    public static Connection getConnection() {
    	return conn;
    }

}