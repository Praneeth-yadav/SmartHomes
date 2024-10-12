package classes;
import java.io.IOException;
import java.io.*;


/* 
	Review class contains class variables username,productname,reviewtext,reviewdate,reviewrating

	Review class has a constructor with Arguments username,productname,reviewtext,reviewdate,reviewrating
	  
	Review class contains getters and setters for username,productname,reviewtext,reviewdate,reviewrating
*/

public class Review implements Serializable{
	private String productName;
	private String userName;
	private String productType;
	private String productPrice;
	private String productMaker;
	private String reviewRating;
	private String storeid;
	private String retailercity;
	private String retailerstate;
	private String retailerpin;
	private String productsale;
	private String rebate;
	private String userid;
	private String age;
	private String gender;
	private String occupation;
	private String reviewDate;
	private String reviewText;
	
	public Review (String productName,String userName,String productType,String productPrice,String productMaker,String reviewRating,String storeid,String retailercity,String retailerstate,String retailerpin,String productsale,String rebate,String userid,String age,String gender,String occupation,String reviewdate,String reviewtext){
		this.productName=productName;
		this.userName=userName;
		this.productType=productType;
		this.productPrice=productPrice;
		this.productMaker=productMaker;
	 	this.reviewRating=reviewRating;
		this.storeid=storeid;
		this.retailercity= retailercity;
		this.retailerstate=retailerstate;
		this.retailerpin=retailerpin;
		this.productsale=productsale;
		this.rebate=rebate;
		this.userid=userid;
		this.age=age;
		this.gender=gender;
		this.occupation=occupation;
		this.reviewDate=reviewDate;
	 	this.reviewText=reviewText;
		
	}

	public Review(String productName, String retailerpin, String reviewRating, String reviewText) {
       this.productName = productName;
       this.retailerpin = retailerpin;
       this.reviewRating = reviewRating;
       this.reviewText = reviewText;
    }

	public String getStoreId() {
		return storeid;
	}

	public void setStoreId(String storeid) {
		this.storeid = storeid;
	}

	public String getStoreState() {
		return retailerstate;
	}

	public void setStoreState(String retailerstate) {
		this.retailerstate = retailerstate;
	}

	public String getProductSale() {
		return productsale;
	}

	public void setProductSale(String productsale) {
		this.productsale = productsale;
	}

	public String getRebate() {
		return rebate;
	}

	public void setRebate(String rebate) {
		this.rebate = rebate;
	}

	public String getUserId() {
		return userid;
	}

	public void setUserId(String userid) {
		this.userid = userid;
	}

	public String getUserAge() {
		return age;
	}

	public void setUserAge(String age) {
		this.age = age;
	}

	public String getUserGender() {
		return gender;
	}

	public void setUserGender(String gender) {
		this.gender = gender;
	}

	public String getUserOccupation() {
		return occupation;
	}

	public void setUserOccupation(String occupation) {
		this.occupation = occupation;
	}


	public String getProductName() {
		return productName;
	}
	public String getUserName() {
		return userName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getProductType() {
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getProductMaker() {
		return productMaker;
	}

	public void setProductMaker(String productMaker) {
		this.productMaker = productMaker;
	}

	public String getReviewRating() {
		return reviewRating;
	}

	public String getReviewText() {
		return reviewText;
	}
	public void setReviewText(String reviewText) {
		this.reviewText = reviewText;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setReviewRating(String reviewRating) {
		this.reviewRating = reviewRating;
	}
	public String getReviewDate() {
		return reviewDate;
	}

	public void setReviewDate(String reviewDate) {
		this.reviewDate = reviewDate;
	}
    
		public String getRetailerPin() {
		return retailerpin;
	}

	public void setRetailerPin(String retailerpin) {
		this.retailerpin = retailerpin;
	}
			public String getRetailerCity() {
		return retailercity;
	}

	public void setRetailerCity(String retailercity) {
		this.retailercity = retailercity;
	}
	
			public String getPrice() {
		return productPrice;
	}

	public void setPrice(String productPrice) {
		this.productPrice = productPrice;
	}

}
