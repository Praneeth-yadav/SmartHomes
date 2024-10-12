package classes;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/ManagerHome")

public class ManagerHome extends HttpServlet {
	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		displayLogin(request, response, pw, false);
	}


	/*  Login Screen is Displayed, Registered User specifies credentials and logins into the Game Speed Application. */
	protected void displayLogin(HttpServletRequest request,
			HttpServletResponse response, PrintWriter pw, boolean error)
			throws ServletException, IOException {

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		pw.print("<br></br>");
		utility.printHtml("LeftNavigationBar.html");
		// pw.print("</div>");
		// pw.print("<div class='post' style='float: none; width: 100%'>");

		pw.print("<div id='content'style='float: none; height: 700px; width: 100%'>");

		//pw.print("<h2 class='title meta'><a style='font-size: 24px;'>Login</a></h2>"
			//	+ "<div class='entry'>"
			//	+ "<div style='width:400px; margin:25px; margin-left: auto;margin-right: auto;'>");
		pw.print("<div id='content'><div class='post' style='height: 700px'><h2 class='title meta'/>"+"<br></br>");
		pw.print("<form method='get' action='ProductModify'>"

				+"<table style='width:100%; margin-top: 30px'><thead><tr><th style='font-size: 30px'>Inventory</th><th style='font-size: 30px'>Sales Report</th></tr></thead><tbody>"

					+"<tr>"
					+"<td><a href=InventoryReport?inventoryreport=allproducts style='font-size: 20px'>All Products</a></td>"
					+"<td><a href=SalesReport?sales=soldproducts style='font-size: 20px'>Sold Products</a></td></tr>"
					+"<tr>"
					+"<td><a href=InventoryReport?inventoryreport=barchart style='font-size: 20px'>Generate Bar Chart</a></td>"
					+"<td><a href=SalesReport?sales=barchart style='font-size: 20px'>Generate Bar Chart</a></td></tr>"
					+"<tr>"
					+"<td><a href=InventoryReport?inventoryreport=productsonsale style='font-size: 20px'>Products Currently on sale</a></td>"
					+"<td><a href=SalesReport?sales=dailytransaction style='font-size: 20px'>Daily Transaction</a></td></tr>"
					+"<tr>"
					+"<td><a href=InventoryReport?inventoryreport=productsonrebate style='font-size: 20px'>Products Currently have manufacturer rebate</a></td>"
					+"<td></td></tr>"
				// +"<tr><td>"
				// + "<input type='submit' class='btnbuy' name='button' value='Add Product'style = 'width:200px'></input>"
				// +"</td>"
				// +"<td>"
				// + "<input type='submit' class='btnbuy' name='button' value='Update Product' style = 'width:200px'></input>"
				// + "</td></tr>"
				// +"<tr><td>"
				// + "<input type='submit' class='btnbuy' name='button' value='Delete Product' style = 'width:200px'></input>"
				// + "</td><td>"
				// + "<input type='submit' class='btnbuy' name='button' value='Trending' style = 'width:200px'></input>"
				// + "</td></tr>"
				+"</tbody></table>"
				+ "</form>" + "</ul></div></nav></div></div></div>");
		// pw.print("</ul></div></nav></div></div>");
		utility.printHtml("Footer.html");

	}
}