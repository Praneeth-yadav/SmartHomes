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

@WebServlet("/ConsoleList")

public class ConsoleList extends HttpServlet {

	/* Console Page Displays all the Consoles and their Information in Game Speed */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String,Console> allconsoles = new HashMap<String,Console> ();


		/* Checks the Tablets type whether it is microsft or sony or nintendo */
		try{
		    allconsoles = MySqlDataStoreUtilities.getConsoles();
		}
		catch(Exception e)
		{
			System.out.println("Exception consolelist doGet"+e);

		}
		
		HashMap<String, Console> hm = new HashMap<String, Console>();
		if(CategoryName==null){
			hm.putAll(allconsoles);
			name = "";
		}
		else
		{
		   if(CategoryName.equalsIgnoreCase("blink"))
		   {
			 for(Map.Entry<String,Console> entry : allconsoles.entrySet())
			 {
				 
				if(entry.getValue().getRetailer().equalsIgnoreCase("Blink"))
				 {
					
						
	//				hm.put(entry.getKey(),entry.getValue());
					hm.put(entry.getValue().getId(),entry.getValue());
					 
				 }
			 }
				name = "Blink";
		   }
		   else if(CategoryName.equalsIgnoreCase("eufy"))
		    {
			for(Map.Entry<String,Console> entry : allconsoles.entrySet())
				{
				 if(entry.getValue().getRetailer().equalsIgnoreCase("Eufy"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
				 name = "Eufy";
			}
			else if(CategoryName.equalsIgnoreCase("ring"))
			{
				for(Map.Entry<String,Console> entry : allconsoles.entrySet())
				{
				 if(entry.getValue().getRetailer().equalsIgnoreCase("Ring"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			   	 name = "Ring";
			}
		}
		
		/* Header, Left Navigation Bar are Printed.

		All the Console and Console information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Doorbells</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Console> entry : hm.entrySet())
		{
			Console console = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+console.getName()+"</h3>");
			pw.print("<strong>$"+console.getPrice()+"</strong><ul>");
			pw.print("<h6> Retailer Discount: <b>"+console.getDiscount()+"</b></h6><ul>");
			pw.print("<li id='item'><img src='images/consoles/"+console.getImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='consoles'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='consoles'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+console.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='hidden' name='discount' value='"+console.getDiscount()+"'>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='consoles'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) pw.print("</tr>");
			i++;
		}		
		pw.print("</table></div></div></div>");		
		utility.printHtml("Footer.html");
		
	}
}
