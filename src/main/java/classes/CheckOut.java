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
import javax.servlet.http.HttpSession;

import java.sql.*;

@WebServlet("/CheckOut")

//once the user clicks buy now button page is redirected to checkout page where user has to give checkout information

public class CheckOut extends HttpServlet {
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities Utility = new Utilities(request, pw);
		storeOrders(request, response);
	}
	
	protected void storeOrders(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try
        {

		HashMap<String,Store> allstores = new HashMap<String,Store> ();
		allstores = MySqlDataStoreUtilities.getStoreLocations();

        response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
        Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin())
		{
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
        HttpSession session=request.getSession(); 

		//get the order product details	on clicking submit the form will be passed to submitorder page	
		
	    String userName = session.getAttribute("username").toString();
        String orderTotal = request.getParameter("orderTotal");
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='CheckOut' action='Payment' method='post'>");
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");
		pw.print("<table  class='gridtable'><tr><td>Customer Name:</td><td>");
		pw.print(userName);
		pw.print("</td></tr>");
		// for each order iterate and display the order name price
		for (OrderItem oi : utility.getCustomerOrders()) 
		{
			pw.print("<tr><td> Product Purchased:</td><td>");
			pw.print(oi.getName()+"</td></tr><tr><td>");
			pw.print("<input type='hidden' name='orderPrice' value='"+oi.getPrice()+"'>");
			pw.print("<input type='hidden' name='orderName' value='"+oi.getName()+"'>");
			pw.print("Product Price:</td><td>"+ oi.getPrice());
			pw.print("</td></tr>");
		}
		pw.print("<tr><td>");
        pw.print("Total Order Cost</td><td>"+orderTotal);
		pw.print("<input type='hidden' name='orderTotal' value='"+orderTotal+"'>");
		pw.print("</td></tr></table><table><tr></tr><tr></tr>");	
		pw.print("<tr><td>");

     	pw.print("Customer Name</td>");
		pw.print("<td><input type='text' name='fullname'>");
		pw.print("</td></tr>");
		pw.print("<tr><td>");

		pw.print("<tr><td>");
	    pw.print("Customer Address Line1</td>");
		pw.print("<td><input type='text' name='userAddress'>");
        pw.print("</td></tr>");

		pw.print("<tr><td>");
	    pw.print("Customer Address Line2</td>");
		pw.print("<td><input type='text' name='userAddressline2'>");
        pw.print("</td></tr>");

		pw.print("<tr><td>");
	    pw.print("City</td>");
		pw.print("<td><input type='text' name='city'>");
        pw.print("</td></tr>");

		pw.print("<tr><td>");
	    pw.print("State</td>");
		pw.print("<td><input type='text' name='state'>");
        pw.print("</td></tr>");

		pw.print("<tr><td>");
	    pw.print("Zip</td>");
		pw.print("<td><input type='text' name='zip'>");
        pw.print("</td></tr>");

		pw.print("<tr><td>");
     	pw.print("Credit/accountNo</td>");
		pw.print("<td><input type='text' name='creditCardNo'>");
		pw.print("</td></tr>");

		pw.print("<tr><td>");
     	pw.print("Delivery</td>");
		pw.print("<td><input type='radio' name='delivery' id='homedelivery'><label for='homedelivery'>Home Delivery</label></td>");
		pw.print("<td><input type='radio' name='delivery' id='storepickup'><label for='storepickup'>Store Pickup</label>");
		pw.print("</td></tr>");


		pw.print("<tr><td>");
     	pw.print("<label for='pickupaddress'>Store Pickup Address:</label></td>");
		pw.print("<td><select name='pickupaddress' id='pickupaddress'>");
		pw.print("<option value='address0'> </option>");
		int i = 1;
		for(Map.Entry<String,Store> entry : allstores.entrySet()){
			String name = entry.getValue().getName();
			String street = entry.getValue().getStreet();
			String city = entry.getValue().getCity();
			String state = entry.getValue().getState();
			String zip = entry.getValue().getZip();
			String storeFullAddress = name+", "+street+", "+city+", "+state+", "+zip;
			pw.print("<p>"+name+"</p>");
			pw.print("<p>"+street+"</p>");
			pw.print("<p>"+city+"</p>");
			pw.print("<p>"+state+"</p>");
			pw.print("<p>"+zip+"</p>");
			pw.print("<option value='"+storeFullAddress+"'>"+storeFullAddress+"</option>");
			i++;
		}
		// pw.print("<option value='address1'>60612</option>");
		// pw.print("<option value='address2'>60667</option>");
		// pw.print("<option value='address3'>60678</option>");
		// pw.print("<option value='address4'>60625</option>");
		// pw.print("<option value='address5'>60634</option>");
		// pw.print("<option value='address6'>60654</option>");
		// pw.print("<option value='address7'>60647</option>");
		// pw.print("<option value='address8'>60603</option>");
		// pw.print("<option value='address9'>60789</option>");
		// pw.print("<option value='address10'>60890</option>");
		pw.print("</td></tr>");

		if(((String) session.getAttribute("usertype")).equalsIgnoreCase("retailer"))
		{
		pw.print("<tr><td>");
	    pw.print("Customer Name</td>");
		pw.print("<td><input type='text' name='customername'>");
        pw.print("</td></tr>");
		}
		pw.print("<tr><td colspan='2'>");
		pw.print("<input type='submit' name='submit' class='btnbuy'>");
        pw.print("</td></tr></table></form>");
		pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	    }
        catch(Exception e)
		{
			System.out.println("Exception storeOrders"+e);

         
		}  			
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	    {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	    }
}
