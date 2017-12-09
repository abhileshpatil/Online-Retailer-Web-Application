import java.util.HashMap;
import java.util.*;

public class Product implements java.io.Serializable{
	
	private String name;
	private double price;
	private String image;
	private String retailer;
	private String condition;
	private double discount;

	private double rdiscount;
	private double rrebate;
	private double rwarranty;
	private String category = "Others";

	private HashMap<String,List<Accessory>> accessories = new HashMap<String,List<Accessory>>();
	private HashMap<String, List<Accessory>> productaccessory =new HashMap<String, List<Accessory>>();
	
	private int quantity;
	private int retailerZip;
	private String retailerCity;
	private String retailerState;
	private String manufacturerName;
	private String productOnSale;

	
	public Product(String name, double price, String image, String retailer,String condition,double discount, HashMap<String,Accessory> accessories){
		this.name=name;
		this.price=price;
		this.image=image;
		this.retailer = retailer;
		this.condition=condition;
		this.discount = discount;
		this.setAccessories(accessories);
	}
	// public Product(String name, double price, double discount, String category, String retailer, int rZip, String retailerCity, String retailerState, String pos, String mName)
	// {
	// 	this.name=name;
	// 	this.price=price;
	// 	this.discount=discount;
	// 	this.category=category;
	// 	this.retailer=retailer;
	//     this.retailerZip=rZip;
	// 	this.retailerCity=retailerCity;
	// 	this.retailerState=retailerState;
	// 	this.productOnSale=pos;
	// 	this.manufacturerName=mName;

	// }

   public Product(String name, double price,double discount){
		this.name=name;
		this.price=price;
		this.discount = discount;
	}
	public Product(String name, double price,int quantity){
		this.name=name;
		this.price=price;
		this.quantity = quantity;
	}
  
   public Product(String name, double price,double discount, String category){
		this.name=name;
		this.price=price;
		this.discount = discount;
		this.category = category;
	}

	public Product(String name, double price,double discount, String category, double rdiscount, double rwarranty, double rebate, String retailer, int retailerZip, String retailerCity, String retailerState, String productOnSale, String manufacturerName, int quantity){
		this.name=name;
		this.price=price;
		this.discount = discount;
		this.category = category;
		this.rdiscount=rdiscount;
		this.rwarranty=rwarranty;
		this.rrebate=rebate;
		this.retailer=retailer;
		this.retailerZip=retailerZip;
		this.retailerCity=retailerCity;
		this.retailerState=retailerState;
		this.productOnSale=productOnSale;
		this.manufacturerName=manufacturerName;
		this.quantity=quantity;
	}
   
   public Product(String name, double price,double discount, String category, String retailer, int retailerZip, String retailerCity, String retailerState, String productOnSale, String manufacturerName){
		this.name=name;
		this.price=price;
		this.discount = discount;
		this.category = category;
		this.retailer=retailer;
		this.retailerZip=retailerZip;
		this.retailerCity=retailerCity;
		this.retailerState=retailerState;
		this.productOnSale=productOnSale;
		this.manufacturerName=manufacturerName;
	}
	public Product(){
		
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getRetailer() {
		return retailer;
	}
	public void setRetailer(String retailer) {
		this.retailer = retailer;
	}

	public void setAccessories(HashMap<String,Accessory> accessories) {
		String key=accessories.keySet().iterator().next();
		if(productaccessory.containsKey(key))
			{
            List<Accessory>temp=new ArrayList<Accessory>();
			temp=productaccessory.get(key);
			temp.add(accessories.get(key));
			productaccessory.put(key, temp);
			}
			else{
			List<Accessory>access =new ArrayList<Accessory>();
			access.add(accessories.get(key));
            productaccessory.put(key, access);
			}
		System.out.println("In set accessory function"+productaccessory);
		this.accessories = productaccessory;
	}

	public void setAccessories1(HashMap<String,List<Accessory>> accessories)
	{
		this.accessories=accessories;
	}

	public HashMap<String,List<Accessory>> getAccessories() {
		System.out.println("In get accessory function"+accessories);
		return accessories;
	}

	public String getCondition() {
		return condition;
	}

	public void setCondition(String condition) {
		this.condition = condition;
	}

	public double getDiscount() {
		return discount;
	}

	public void setDiscount(double discount) {
		this.discount = discount;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public double getRdiscount() {
		return rdiscount;
	}

	public void setRdiscount(double rdiscount) {
		this.rdiscount = rdiscount;
	}


	public double getRwarranty() {
		return rwarranty;
	}

	public void setRwarranty(double rwarranty) {
		this.rwarranty = rwarranty;
	}

	public double getrrebate() {
		return rrebate;
	}

	public void setrrebate(double rrebate) {
		this.rrebate = rrebate;
	}

	public int getRetailerZip() {
		return retailerZip;
	}

	public void setRetailerZip(int retailerZip) {
		this.retailerZip = retailerZip;
	}

	public int getquantity() {
		return quantity;
	}

	public void setquantity(int quantity) {
		this.quantity = quantity;
	}

	public String getRetailerCity() {
		return retailerCity;
	}

	public void setRetailerCity(String retailerCity) {
		this.retailerCity = retailerCity;
	}

	public String getRetailerState() {
		return retailerState;
	}

	public void setRetailerState(String retailerState) {
		this.retailerState = retailerState;
	}


	public String getManufacturerName() {
		return manufacturerName;
	}

	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}

	public String getProductOnSale() {
		return productOnSale;
	}

	public void setProductOnSale(String productOnSale) {
		this.productOnSale = productOnSale;
	}
	
}
