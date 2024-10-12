package classes;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/Payment")

public class Payment extends HttpServlet {

		@Override
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		String msg = "good";
		String Customername= "";
		HttpSession session = request.getSession(true);

		Utilities utility = new Utilities(request, pw);
		if(!utility.isLoggedin())
		{
			session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to Pay");
			response.sendRedirect("Login");
			return;
		}
		// get the payment details like credit card no address processed from cart servlet

		String userAddressLine1=request.getParameter("userAddress");
		String userAddressLine2=request.getParameter("userAddressline2");
		String userAddress=userAddressLine1+","+userAddressLine2;
		String creditCardNo=request.getParameter("creditCardNo");
		String storeFullAddress=request.getParameter("pickupaddress");
		String result[]=storeFullAddress.split(",");
		String storeId=result[0];
		String customername = request.getParameter("fullname");

		Date date = new Date();
      	SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yy");
       	String currentDate = formatter.format(date);
		Calendar c = Calendar.getInstance();
        c.setTime(date);
		c.add(Calendar.DATE, 20);
		Date delivery = c.getTime();
		String deliveryDate = formatter.format(delivery);
		c.setTime(date);
		c.add(Calendar.DATE, 5);
		Date cancel = c.getTime();
		String cancelDate = formatter.format(cancel);
		if(session.getAttribute("usertype").equals("retailer")){
			Customername =request.getParameter("customername");
			try{
				HashMap<String,User> hm=new HashMap<>();
				hm=MySqlDataStoreUtilities.selectUser();
				if(hm.containsKey(Customername)){
					if(hm.get(Customername).getUsertype().equals("customer")){
						msg ="good";
					}else {msg ="bad";}

				}else {msg ="bad";}

			}catch(Exception e)
			{

			}
		}




		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Order</a>");
		pw.print("</h2><div class='entry'>");

		String message=MySqlDataStoreUtilities.getConnection();
		if(message.equals("Successfull"))
		{
			if (msg.equals("good"))
			{
				int orderId=utility.getOrderPaymentSize()+1;
				//iterate through each order

				for (OrderItem oi : utility.getCustomerOrders())
				{

					//set the parameter for each column and execute the prepared statement

					utility.storePayment(orderId,customername,userAddress,creditCardNo,oi.getName(),oi.getPrice(),storeFullAddress,storeId);

				}

				//remove the order details from cart after processing

				OrdersHashMap.orders.remove(utility.username());

			pw.print("<h2>Hello, "+(customername)+"</h2>");
			pw.print("<br><h2>Your Order");
			pw.print("&nbsp&nbsp");
			pw.print("has been  placed on "+(currentDate)+"</h2>");
			pw.print("<br><h2>Your Order No is "+(orderId)+"</h2>");
			pw.print("<br><h2>Your order will be delivered on "+(deliveryDate)+"</h2>");
			pw.print("<br><h3>You can cancel your order until "+(cancelDate)+"</h3>");
			}else {pw.print("<h2>Customer does not exits</h2>");}
		}
		else
		{pw.print("<h2>My Sql server is not up and running</h2>");

		}
		pw.print("</div></div></div>");
		utility.printHtml("Footer.html");
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		Utilities utility = new Utilities(request, pw);


	}
}
