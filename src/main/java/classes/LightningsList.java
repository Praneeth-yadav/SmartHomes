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

@WebServlet("/LightningsList")

public class LightningsList extends HttpServlet {

	/* lightnings Page Displays all the lightnings and their Information in Smart Homes */

	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the v type whether it is electronicArts or activision or takeTwoInteractive */
		HashMap<String,Lightning> alllights = new HashMap<String,Lightning> ();


		/* Checks the Tablets type whether it is microsft or sony or nintendo */
		try{
		     alllights = MySqlDataStoreUtilities.getLightnings();
		}
		catch(Exception e)
		{
			System.out.println("Exception lightnings doGet"+e);

			
		}
				
		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, Lightning> hm = new HashMap<String, Lightning>();
		
		if(CategoryName==null)
		{
			hm.putAll(alllights);
			name = "";
		}
		else
		{
		  if(CategoryName.equalsIgnoreCase("lifx"))
		  {
			for(Map.Entry<String,Lightning> entry : alllights.entrySet())
				{
				if(entry.getValue().getRetailer().equalsIgnoreCase("Lifx"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Lifx";
		  }
		  else if(CategoryName.equalsIgnoreCase("tapo"))
		  {
			for(Map.Entry<String,Lightning> entry : alllights.entrySet())
				{
				if(entry.getValue().getRetailer().equalsIgnoreCase("Tapo"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
			name = "Tapo";
		  }
		  else if(CategoryName.equalsIgnoreCase("wipro"))
		  {
			for(Map.Entry<String,Lightning> entry : alllights.entrySet())
				{
				if(entry.getValue().getRetailer().equalsIgnoreCase("Wipro"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Wipro";
		  }
		}

		/* Header, Left Navigation Bar are Printed.

		All the lightnings and lightnings information are dispalyed in the Content Section

		and then Footer is Printed*/
		
		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Lightnings</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Lightning> entry : hm.entrySet()){
			Lightning lightning = entry.getValue();
			if(i%3==1) pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+lightning.getName()+"</h3>");
			pw.print("<strong>"+ "$" + lightning.getPrice() + "</strong><ul>");
			pw.print("<h6> Retailer Discount: <b>"+lightning.getDiscount()+"</b></h6><ul>");
			pw.print("<h6>This product has 2 years warranty</h6>");
			pw.print("<li id='item'><img src='images/lightnings/"+lightning.getImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lightnings'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lightnings'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+lightning.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='hidden' name='discount' value='"+lightning.getDiscount()+"'>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='lightnings'>"+
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
