package classes;
import java.util.HashMap;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;


@WebServlet("/Console")


/*
	Console class contains class variables name,price,image,retailer,condition,discount and Accessories Hashmap.

	Console class constructor with Arguments name,price,image,retailer,condition,discount and Accessories .

	Accessory class contains getters and setters for name,price,image,retailer,condition,discount and Accessories .

*/

public class Console extends HttpServlet{
	private String id;
	private String name;
	private double price;
	private String image;
	private String retailer;
	private String condition;
	private double discount;
	private String productOnSale;
	private int productQuantity;
	HashMap<String,String> accessories;
	public Console(String name, double price, String image, String retailer,String condition,double discount,String productOnSale,int productQuantity){
		this.name=name;
		this.price=price;
		this.image=image;
		this.retailer = retailer;
		this.condition=condition;
		this.discount = discount;
		this.productOnSale = productOnSale;
		this.productQuantity = productQuantity;
        this.accessories=new HashMap<>();
	}

    HashMap<String,String> getAccessories() {
		return accessories;
		}

	public Console(){

	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
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

	public void setAccessories( HashMap<String,String> accessories) {
		this.accessories = accessories;
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

	public String getproductOnSale() {
		return productOnSale;
	}

	public void setproductOnSale(String productOnSale) {
		this.productOnSale = productOnSale;
	}

	public int getproductQuantity() {
		return productQuantity;
	}
	public void setproductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

}
