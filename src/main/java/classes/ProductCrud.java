package classes;
import java.io.*;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mongodb.util.JSON;

@WebServlet("/ProductCrud")

public class ProductCrud extends HttpServlet {
	public String requestData="";
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
			response.setContentType("text/html");
			PrintWriter pw = response.getWriter();
			Utilities utility = new Utilities(request, pw);
			String action = request.getParameter("button");
			
			String msg = "good";
			String producttype= "",productId="",productName="",productImage="",productManufacturer="",productCondition="",prod = "",productOnSale = "",productDescription="";
			double productPrice=0.0,productDiscount = 0.0;
			int productQuantity = 0;
			HashMap<String,Console> allconsoles = new HashMap<String,Console> ();
			HashMap<String,Tablet> alltablets = new HashMap<String,Tablet> ();
			HashMap<String,Game> allgames = new HashMap<String,Game> ();
			HashMap<String,Accessory> allaccessories=new HashMap<String,Accessory>();
//			System.out.println("request"+"producttype ="+ request.getParameter("producttype")+"productId   = "+request.getParameter("productId")+"productName = "+request.getParameter("productName")+
//			 "productPrice = "+Double.parseDouble(request.getParameter("productPrice"))+
//			 "productImage = " +request.getParameter("productImage") +
//			 "productManufacturer = "+request.getParameter("productManufacturer")+
//			 "productCondition = "+request.getParameter("productCondition")+
//			 "productDiscount = "+Double.parseDouble(request.getParameter("productDiscount"))+
//			 "productOnSale = "+request.getParameter("productOnSale")+
//			 "productDescription = "+request.getParameter("productDescription")+
//			 "productQuantity =" +Integer.parseInt(request.getParameter("productQuantity"))
//			 );
			 requestData=(String.format(
				    "Product Details: " +
				    "Type: %s ID: %s Name: %s Price: %.2f Image: %s Manufacturer: %s " +
				    "Condition: %s Discount: %.2f On Sale: %s Description: %s Quantity: %d",
				    request.getParameter("producttype"),
				    request.getParameter("productId"),
				    request.getParameter("productName"),
				    Double.parseDouble(request.getParameter("productPrice")),
				    request.getParameter("productImage"),
				    request.getParameter("productManufacturer"),
				    request.getParameter("productCondition"),
				    Double.parseDouble(request.getParameter("productDiscount")),
				    request.getParameter("productOnSale"),
				    request.getParameter("productDescription"),
				    Integer.parseInt(request.getParameter("productQuantity"))
				));
			 System.out.println("requestData"+requestData);
			if (action.equalsIgnoreCase("add") || action.equalsIgnoreCase("update"))
			{	
				 producttype = request.getParameter("producttype");
				 productId   = request.getParameter("productId");
				 productName = request.getParameter("productName"); 
				 productDescription = request.getParameter("productDescription");
				 productPrice = Double.parseDouble(request.getParameter("productPrice"));
				 productImage = request.getParameter("productImage");
				 productManufacturer = request.getParameter("productManufacturer");
				 productCondition = request.getParameter("productCondition");
				 productDiscount = Double.parseDouble(request.getParameter("productDiscount"));
				 productOnSale = request.getParameter("productOnSale");
				 productQuantity = Integer.parseInt(request.getParameter("productQuantity"));
				 
			}
			else{
				productId   = request.getParameter("productId");
			}	
			utility.printHtml("Header.html");
			utility.printHtml("LeftNavigationBar.html");

			if(action.equalsIgnoreCase("add"))
			{
			  			if(producttype.equalsIgnoreCase("consoles")){
				  allconsoles = MySqlDataStoreUtilities.getConsoles();
				  if(allconsoles.containsKey(productId)){
					  msg = "Product already available";
					  
				  }
					  
			  }else if(producttype.equalsIgnoreCase("games"))
			  {
				  allgames = MySqlDataStoreUtilities.getGames();
				  if(allgames.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }else if (producttype.equalsIgnoreCase("tablets"))
			  {
				  alltablets = MySqlDataStoreUtilities.getTablets();
				  if(alltablets.containsKey(productId)){
					  msg = "Product already available";
				  }
			  }else if (producttype.equalsIgnoreCase("accessories"))
			  {  
					if(!request.getParameter("product").isEmpty())
						{
							prod = request.getParameter("product");
							allconsoles = MySqlDataStoreUtilities.getConsoles();
							System.out.println("allconsoles"+allconsoles.containsKey(prod));
							System.out.println("allconsoles"+allconsoles);
							if(allconsoles.containsKey(prod))
							{
								allaccessories = MySqlDataStoreUtilities.getAccessories();
								if(allaccessories.containsKey(productId)){
									msg = "Product already available";
								}
							}
							else{//check
								msg = "The product related to accessories is not available";
							}
						
						
						}else{
							msg = "Please add the prodcut name";
						}
				  
			  }	
			  if (msg.equalsIgnoreCase("good"))
			  {  
				  try
				  {
					  msg = MySqlDataStoreUtilities.addproducts(producttype,productId,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,productOnSale,productQuantity,productDescription,prod);
						List<Double> output=Utils.getEmbeddingsForProduct(productDescription);
						System.out.println("output : "+output);

						String docID=ElasticSearchUtils.storeEmbeddingInElasticsearch(productDescription,productName,productPrice,producttype, output);
						System.out.println("docID : "+docID);
				  }
				  catch(Exception e)
				  { 
						System.out.println("Exception productcurd dopost"+e);

					msg = "Product cannot be inserted";
				  }
				  msg = "Product has been successfully added";
			  }					
			}else if(action.equalsIgnoreCase("update"))
			{
				
			  if(producttype.equalsIgnoreCase("consoles")){
				  allconsoles = MySqlDataStoreUtilities.getConsoles();
				  if(!allconsoles.containsKey(productId)){
					  msg = "Product not available";
				  }
					  
			  }else if(producttype.equalsIgnoreCase("games"))
			  {
				  allgames = MySqlDataStoreUtilities.getGames();
				  if(!allgames.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }else if (producttype.equalsIgnoreCase("tablets"))
			  {
				  alltablets = MySqlDataStoreUtilities.getTablets();
				  if(!alltablets.containsKey(productId)){
					  msg = "Product not available";
				  }
			  }else if (producttype.equalsIgnoreCase("accessories"))
			  {
				  allaccessories = MySqlDataStoreUtilities.getAccessories();
				  if(!allaccessories.containsKey(productId)){
					  msg = "Product not available";
				}
			  }	
			  if (msg.equalsIgnoreCase("good"))
			  {		
				
				  try
				  {
					msg = MySqlDataStoreUtilities.updateproducts(producttype,productId,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,productOnSale,productQuantity,productDescription);
				  }
				  catch(Exception e)
				  { 
						System.out.println("Exception productcurd Product cannot be updated"+e);

					msg = "Product cannot be updated";
				  }
				  msg = "Product has been successfully updated";
			  } 
			}else if(action.equalsIgnoreCase("delete"))
			{
				  msg = "bad";
				  allconsoles = MySqlDataStoreUtilities.getConsoles();
				  if(allconsoles.containsKey(productId)){
					  msg = "good";
					 
				  }
					  
			  
				  allgames = MySqlDataStoreUtilities.getGames();
				  if(allgames.containsKey(productId)){
					  msg = "good";
				  }
			  
				  alltablets = MySqlDataStoreUtilities.getTablets();
				  if(alltablets.containsKey(productId)){
					  msg = "good";
				  }
			  
				  allaccessories = MySqlDataStoreUtilities.getAccessories();
				  if(allaccessories.containsKey(productId)){
					  msg = "good";
				}
		       		
				  if (msg.equalsIgnoreCase("good"))
				  {		
					
					  try
					  {  
						
						 msg = MySqlDataStoreUtilities.deleteproducts(productId);
					  }
					  catch(Exception e)
					  { 
							System.out.println("Exception productcurd Product cannot be deleted"+e);

						msg = "Product cannot be deleted";
					  }
					   msg = "Product has been successfully deleted";
				  }else{
					  msg = "Product not available";
				  }
			}	
				
			pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
			pw.print("<a style='font-size: 24px;'>Order</a>");
			pw.print("</h2><div class='entry'>");
			pw.print("<h4 style='color:blue'>"+msg+"</h4>");
			pw.print("</div></div></div>");		
			utility.printHtml("Footer.html");
			
	}
}