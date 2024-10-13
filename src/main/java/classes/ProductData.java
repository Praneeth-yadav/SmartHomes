package classes;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/ProductData")
public class ProductData extends HttpServlet {


	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{

PrintWriter pw= response.getWriter();
response.setContentType("text/html");
 pw.println("<html>");
 pw.println("<body>");



		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		//System.out.println("Before product data");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		pw.print("<tr>");
		pw.print("<td><div id='shop_item'>");
			Product data= (Product)request.getAttribute("data");
			System.out.println("data : \n"+"producttype ="+ data.getType()+"\n productId   = "+data.getId()+"\nproductName = "+data.getName()+
					 "\nproductPrice = "+data.getPrice()+
					 "\nproductImage = " +data.getImage() +
					 "\nproductManufacturer = "+data.getRetailer()+
					 "\nproductCondition = "+data.getCondition()+
					 "\nproductDiscount = "+data.getDiscount()+
					 "\nclass =" +data.getClass()
					 );

			String productType = data.getType();
		if(productType.equalsIgnoreCase("consoles")){
			productType = "consoles";
			System.out.println("consoles test");
		}
		else if(productType.equalsIgnoreCase("games")){
			productType = "games";
		}
		else if(productType.equalsIgnoreCase("tablets")){
			productType = "tablets";
		}
		else if(productType.equalsIgnoreCase("lightnings")){
			productType = "lightnings";
		}
		else if(productType.equalsIgnoreCase("thermostats")){
			productType = "thermostats";
		}
		else{
			productType = "accessories";
		}

			pw.print("<h3>"+data.getName()+"</h3>");
			pw.print("<strong>$"+data.getPrice()+"</strong><ul>");
			pw.print("<li id='item'><img src='images/"+productType+"/"+data.getImage()+"' alt='' /></li>");
			//System.out.println("type"+data.getType());
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+data.getId()+"'>"+
					"<input type='hidden' name='type' value='"+data.getType()+"'>"+
					"<input type='hidden' name='maker' value='"+data.getRetailer()+"'>"+
					"<input type='hidden' name='price' value='"+data.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+
			"<input type='hidden' name='name' value='"+data.getId()+"'>"+
					"<input type='hidden' name='type' value='"+data.getType()+"'>"+
					"<input type='hidden' name='maker' value='"+data.getRetailer()+"'>"+
					"<input type='hidden' name='price' value='"+data.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    	"<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+
					"<input type='hidden' name='name' value='"+data.getId()+"'>"+
					"<input type='hidden' name='type' value='"+data.getType()+"'>"+
					"<input type='hidden' name='maker' value='"+data.getRetailer()+"'>"+
					"<input type='hidden' name='price' value='"+data.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");

			pw.print("</ul></div></td>");
			pw.print("</tr>");
			pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");
		}
		catch(Exception e)
		{
			System.out.println("Exception ProductData"+e);


		}
	}

	@Override
	public void destroy()	{
      // do nothing.
	}


}