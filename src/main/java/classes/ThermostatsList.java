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

@WebServlet("/ThermostatsList")

public class ThermostatsList extends HttpServlet {

	/* thermostats Page Displays all the thermostats and their Information in Smart Homes */

	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

		/* Checks the thermostats type whether it is electronicArts or activision or takeTwoInteractive */
		HashMap<String,Thermostat> allthermos = new HashMap<> ();


		/* Checks the Tablets type whether it is microsft or sony or nintendo */
		try{
		     allthermos = MySqlDataStoreUtilities.getThermostats();
		}
		catch(Exception e)
		{
			System.out.println("Exception Thermostatlist doget"+e);

		}

		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, Thermostat> hm = new HashMap<>();

		if(CategoryName==null)
		{
			hm.putAll(allthermos);
			name = "";
		}
		else
		{
		  if(CategoryName.equalsIgnoreCase("amazon"))
		  {
			for(Map.Entry<String,Thermostat> entry : allthermos.entrySet())
				{
				if(entry.getValue().getRetailer().equalsIgnoreCase("Amazon"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Amazon";
		  }
		  else if(CategoryName.equalsIgnoreCase("ecobee"))
		  {
			for(Map.Entry<String,Thermostat> entry : allthermos.entrySet())
				{
				if(entry.getValue().getRetailer().equalsIgnoreCase("Ecobee"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}
			name = "Ecobee";
		  }
		  else if(CategoryName.equalsIgnoreCase("google"))
		  {
			for(Map.Entry<String,Thermostat> entry : allthermos.entrySet())
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

		All the thermostats and thermostats information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request,pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>"+name+" Thermostats</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1; int size= hm.size();
		for(Map.Entry<String, Thermostat> entry : hm.entrySet()){
			Thermostat thermostat = entry.getValue();
			if(i%3==1) {
				pw.print("<tr>");
			}
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>"+thermostat.getName()+"</h3>");
			pw.print("<strong>"+ "$" + thermostat.getPrice() + "</strong><ul>");
			pw.print("<h6> Retailer Discount: <b>"+thermostat.getDiscount()+"</b></h6><ul>");
			pw.print("<h6>This product has 2 years warranty</h6>");
			pw.print("<li id='item'><img src='images/thermostats/"+thermostat.getImage()+"' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='thermostats'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='thermostats'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+thermostat.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='hidden' name='discount' value='"+thermostat.getDiscount()+"'>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+entry.getKey()+"'>"+
					"<input type='hidden' name='type' value='thermostats'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if(i%3==0 || i == size) {
				pw.print("</tr>");
			}
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");

	}

}
