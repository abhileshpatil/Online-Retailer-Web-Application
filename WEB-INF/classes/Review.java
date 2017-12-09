import java.util.Date;

public class Review {
	private String productModelName;
	private String category;
	private double price;
	private String retailerName;
	private int retailerZip;
	private String retailerCity;
	private String retailerState;
	private String productOnSale;        //YesNo to string 
	private String manufacturerName;
	private String manufacturerRebate;
	private String userName;
	private int userAge;
	private String userGender;
	private String userOccupation;
	private int reviewRating;
	private Date reviewDate;
	private String reviewText;
	
	private int count;
	
	
	public Review(String productModelName, String category, double price, String retailerName, int retailerZip,
			String retailerCity, String retailerState, String productOnSale, String manufacturerName,
			String manufacturerRebate, String userName, int userAge, String userGender, String userOccupation,
			int reviewRating, Date reviewDate, String reviewText) {
		this.productModelName = productModelName;
		this.category = category;
		this.price = price;
		this.retailerName = retailerName;
		this.retailerZip = retailerZip;
		this.retailerCity = retailerCity;
		this.retailerState = retailerState;
		this.productOnSale = productOnSale;
		this.manufacturerName = manufacturerName;
		this.manufacturerRebate = manufacturerRebate;
		this.userName = userName;
		this.userAge = userAge;
		this.userGender = userGender;
		this.userOccupation = userOccupation;
		this.reviewRating = reviewRating;
		this.reviewDate = reviewDate;
		this.reviewText = reviewText;
	}
	
	public Review(Product p, String userName, int userAge, String userGender, String userOccupation, int reviewRating, Date reviewDate, String reviewText) {
		this.productModelName = p.getName();
		this.category = p.getCategory();
		this.price = p.getPrice();
		this.retailerName = p.getRetailer();
		this.retailerZip = p.getRetailerZip();
		this.retailerCity = p.getRetailerCity();
		this.retailerState = p.getRetailerCity();
		this.productOnSale = p.getProductOnSale();
		this.manufacturerName = p.getManufacturerName();
		if(p.getDiscount() > 0.0) {
			this.manufacturerRebate = "YES";
		} else {
			this.manufacturerRebate = "YES";
		}
		this.userName = userName;
		this.userAge = userAge;
		this.userGender = userGender;
		this.userOccupation = userOccupation;
		this.reviewRating = reviewRating;
		this.reviewDate = reviewDate;
		this.reviewText = reviewText;
	}
	
	public Review() {}
	

	public Review(String productModelName, String category, double price, String retailerName, int retailerZip,
			String retailerCity, String retailerState, String productOnSale, String manufacturerName,
			String manufacturerRebate) {
		this.productModelName = productModelName;
		this.category = category;
		this.price = price;
		this.retailerName = retailerName;
		this.retailerZip = retailerZip;
		this.retailerCity = retailerCity;
		this.retailerState = retailerState;
		this.productOnSale = productOnSale;
		this.manufacturerName = manufacturerName;
		this.manufacturerRebate = manufacturerRebate;
	}

	@Override
	public String toString() {
		return "====productModelName=" + productModelName + ", category=" + category + ", price=" + price + ", retailerName=" + retailerName + ", retailerZip=" + retailerZip + ", retailerCity=" + retailerCity
				+ ", retailerState=" + retailerState + ", productOnSale=" + productOnSale + ", manufacturerName="
				+ manufacturerName + ", manufacturerRebate=" + manufacturerRebate + ", userName=" + userName
				+ ", userAge=" + userAge + ", userGender=" + userGender + ", userOccupation=" + userOccupation
				+ ", reviewRating=" + reviewRating + ", reviewDate=" + reviewDate + ", reviewText=" + reviewText+"=======" ;
	}

	public String getProductModelName() {
		return productModelName;
	}
	public void setProductModelName(String productModelName) {
		this.productModelName = productModelName;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public double getPrice() {
		return price;
	}
	public void setPrice(double price) {
		this.price = price;
	}
	public String getRetailerName() {
		return retailerName;
	}
	public void setRetailerName(String retailerName) {
		this.retailerName = retailerName;
	}
	public int getRetailerZip() {
		return retailerZip;
	}
	public void setRetailerZip(int retailerZip) {
		this.retailerZip = retailerZip;
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
	public String isProductOnSale() {
		return productOnSale;
	}
	public void setProductOnSale(String productOnSale) {
		this.productOnSale = productOnSale;
	}
	public String getManufacturerName() {
		return manufacturerName;
	}
	public void setManufacturerName(String manufacturerName) {
		this.manufacturerName = manufacturerName;
	}
	public String isManufacturerRebate() {
		return manufacturerRebate;
	}
	public void setManufacturerRebate(String manufacturerRebate) {
		this.manufacturerRebate = manufacturerRebate;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getUserAge() {
		return userAge;
	}
	public void setUserAge(int userAge) {
		this.userAge = userAge;
	}
	public String getUserGender() {
		return userGender;
	}
	public void setUserGender(String userGender) {
		this.userGender = userGender;
	}
	public String getUserOccupation() {
		return userOccupation;
	}
	public void setUserOccupation(String userOccupation) {
		this.userOccupation = userOccupation;
	}
	public int getReviewRating() {
		return reviewRating;
	}
	public void setReviewRating(int reviewRating) {
		this.reviewRating = reviewRating;
	}
	public Date getReviewDate() {
		return reviewDate;
	}
	public void setReviewDate(Date reviewDate) {
		this.reviewDate = reviewDate;
	}
	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
}