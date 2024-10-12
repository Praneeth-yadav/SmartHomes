package classes;

import java.io.IOException;
import java.io.PrintWriter;
import com.mongodb.MongoClient;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.DBCursor;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities utility= new Utilities(request, pw);
		review(request, response);
	}
	
	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        try
                {           
                response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
                Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);				
			session.setAttribute("login_msg", "Please Login to view Review");
			response.sendRedirect("Login");
			return;
		}
		 String productName=request.getParameter("name");		 
		HashMap<String, ArrayList<Review>> hm= MongoDBDataStoreUtilities.selectReview();
	    String userName="";
	    String productType="";
	    String productPrice="";
	    String productMaker="";
	    String reviewRating="";
	    String storeid="";
	    String retailercity="";
	    String retailerstate="";
	    String retailerpin="";
	    String productsale="";
	    String rebate="";
	    String userid="";
	    String age="";
	    String gender="";
	    String occupation="";
	    String reviewDate="";
	    String reviewText="";
			
                utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
	
                pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Review</a>");
		pw.print("</h2><div class='entry'>");
			
			//if there are no reviews for product print no review else iterate over all the reviews using cursor and print the reviews in a table
		if(hm==null)
		{
		pw.println("<h2>Mongo Db server is not up and running</h2>");
		}
		else
		{
                if(!hm.containsKey(productName)){
				pw.println("<h2>There are no reviews for this product.</h2>");
			}else{
		for (Review r : hm.get(productName)) 
				 {		
		pw.print("<table class='gridtable'>");
				pw.print("<tr>");
				pw.print("<td> Product Name: </td>");
				productName = r.getProductName();
				pw.print("<td>" +productName+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> userName: </td>");
				userName = r.getUserName();
				pw.print("<td>" +userName+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> Product Category: </td>");
				productType = r.getProductType();
				pw.print("<td>" +productType+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> Product Price: </td>");
				productPrice = r.getPrice();
				pw.print("<td>" +productPrice+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> Store Id: </td>");
				storeid = r.getStoreId();
				pw.print("<td>" +storeid+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> Store City: </td>");
				retailercity = r.getRetailerCity();
				pw.print("<td>" +retailercity+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> Store State: </td>");
				retailerstate = r.getStoreState();
				pw.print("<td>" +retailerstate+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> Store Zip Code: </td>");
				retailerpin = r.getRetailerPin();
				pw.print("<td>" +retailerpin+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> Product on Sale: </td>");
				productsale = r.getProductSale();
				pw.print("<td>" +productsale+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> Manufacturer Name: </td>");
				productMaker = r.getProductMaker();
				pw.print("<td>" +productMaker+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> Manufacturer Rebate: </td>");
				rebate = r.getRebate();
				pw.print("<td>" +rebate+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> User Id: </td>");
				userid = r.getUserId();
				pw.print("<td>" +userid+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> User Age: </td>");
				age = r.getUserAge();
				pw.print("<td>" +age+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> User Gender: </td>");
				gender = r.getUserGender();
				pw.print("<td>" +gender+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> User Occupation: </td>");
				occupation = r.getUserOccupation();
				pw.print("<td>" +occupation+ "</td>");
				pw.print("</tr>");



				pw.println("<tr>");
				pw.println("<td> Review Rating: </td>");
				reviewRating = r.getReviewRating().toString();
				pw.print("<td>" +reviewRating+ "</td>");
				pw.print("</tr>");

				pw.print("<tr>");
				pw.print("<td> Review Text: </td>");
				reviewText = r.getReviewText();
				pw.print("<td>" +reviewText+ "</td>");
				pw.print("</tr>");
				

				reviewDate = r.getReviewDate();
				SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/DD");
				Date date = format.parse(reviewDate);

				pw.print("<tr>");
				pw.print("<td> Review Date: </td>");
				pw.print("<td>" +date+ "</td>");
				pw.print("</tr>");		

				pw.println("</table>");
				}					
							
		}
		}	       
                pw.print("</div></div></div>");		
		utility.printHtml("Footer.html");
	                     	
                    }
              	catch(Exception e)
		{
                 System.out.println(e.getMessage());
		}  			
       
	 	
		}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
            }
}
