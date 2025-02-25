package classes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/TabletList")

public class TabletList extends HttpServlet {

	/* Trending Page Displays all the Tablets and their Information in Game Speed */

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		HashMap<String,Tablet> alltablets = new HashMap<String,Tablet> ();


		
		try{
		     alltablets = MySqlDataStoreUtilities.getTablets();
		}
		catch(Exception e)
		{
			
		}

	/* Checks the Tablets type whether it is microsft or apple or samsung */

		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, Tablet> hm = new HashMap<String, Tablet>();

		if (CategoryName == null)	
		{
			hm.putAll(alltablets);
			name = "";
		} 
		else 
		{
			if(CategoryName.equalsIgnoreCase("alexa")) 
			{	
				for(Map.Entry<String,Tablet> entry : alltablets.entrySet())
				{
				  if(entry.getValue().getRetailer().equalsIgnoreCase("alexa"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name ="Alexa";
			} 
			else if (CategoryName.equalsIgnoreCase("echoDot"))
			{
				for(Map.Entry<String,Tablet> entry : alltablets.entrySet())
				{
				  if(entry.getValue().getRetailer().equalsIgnoreCase("EchoDot"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "EchoDot";
			} 
			else if (CategoryName.equalsIgnoreCase("google")) 
			{
				for(Map.Entry<String,Tablet> entry : alltablets.entrySet())
				{
				  if(entry.getValue().getRetailer().equalsIgnoreCase("Google"))
				 {
					hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Google";
			}
	    }

		/* Header, Left Navigation Bar are Printed.

		All the tablets and tablet information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name + " Speakers</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, Tablet> entry : hm.entrySet()) {
			Tablet Tablet = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + Tablet.getName() + "</h3>");
			pw.print("<strong>" + Tablet.getPrice() + "$</strong><ul>");
			pw.print("<h6> Retailer Discount: <b>"+Tablet.getDiscount()+"</b></h6><ul>");
			pw.print("<li id='item'><img src='images/tablets/"
					+ Tablet.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='tablets'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='tablets'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+Tablet.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='hidden' name='discount' value='"+Tablet.getDiscount()+"'>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='tablets'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if (i % 3 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");
	}
}
