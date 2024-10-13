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
import java.text.ParseException;
import java.text.SimpleDateFormat;

@WebServlet("/ViewReview")

public class ViewReview extends HttpServlet {

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities utility= new Utilities(request, pw);
		review(request, response);
	}
	
//	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//	        try
//                {           
//                response.setContentType("text/html");
//		PrintWriter pw = response.getWriter();
//                Utilities utility = new Utilities(request,pw);
//		if(!utility.isLoggedin()){
//			HttpSession session = request.getSession(true);				
//			session.setAttribute("login_msg", "Please Login to view Review");
//			response.sendRedirect("Login");
//			return;
//		}
//		 String productName=request.getParameter("name");		 
//		HashMap<String, ArrayList<Review>> hm= MongoDBDataStoreUtilities.selectReview();
//	    String userName="";
//	    String productType="";
//	    String productPrice="";
//	    String productMaker="";
//	    String reviewRating="";
//	    String storeid="";
//	    String retailercity="";
//	    String retailerstate="";
//	    String retailerpin="";
//	    String productsale="";
//	    String rebate="";
//	    String userid="";
//	    String age="";
//	    String gender="";
//	    String occupation="";
//	    String reviewDate="";
//	    String reviewText="";
//			
//                utility.printHtml("Header.html");
//		utility.printHtml("LeftNavigationBar.html");
//	
//                pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
//		pw.print("<a style='font-size: 24px;'>Review</a>");
//		pw.print("</h2><div class='entry'>");
//			
//			//if there are no reviews for product print no review else iterate over all the reviews using cursor and print the reviews in a table
//		if(hm==null)
//		{
//		pw.println("<h2>Mongo Db server is not up and running</h2>");
//		}
//		else
//		{
//                if(!hm.containsKey(productName)){
//				pw.println("<h2>There are no reviews for this product.</h2>");
//			}else{
//		for (Review r : hm.get(productName)) 
//				 {		
//		pw.print("<table class='gridtable'>");
//				pw.print("<tr>");
//				pw.print("<td> Product Name: </td>");
//				productName = r.getProductName();
//				pw.print("<td>" +productName+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> userName: </td>");
//				userName = r.getUserName();
//				pw.print("<td>" +userName+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> Product Category: </td>");
//				productType = r.getProductType();
//				pw.print("<td>" +productType+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> Product Price: </td>");
//				productPrice = r.getPrice();
//				pw.print("<td>" +productPrice+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> Store Id: </td>");
//				storeid = r.getStoreId();
//				pw.print("<td>" +storeid+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> Store City: </td>");
//				retailercity = r.getRetailerCity();
//				pw.print("<td>" +retailercity+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> Store State: </td>");
//				retailerstate = r.getStoreState();
//				pw.print("<td>" +retailerstate+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> Store Zip Code: </td>");
//				retailerpin = r.getRetailerPin();
//				pw.print("<td>" +retailerpin+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> Product on Sale: </td>");
//				productsale = r.getProductSale();
//				pw.print("<td>" +productsale+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> Manufacturer Name: </td>");
//				productMaker = r.getProductMaker();
//				pw.print("<td>" +productMaker+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> Manufacturer Rebate: </td>");
//				rebate = r.getRebate();
//				pw.print("<td>" +rebate+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> User Id: </td>");
//				userid = r.getUserId();
//				pw.print("<td>" +userid+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> User Age: </td>");
//				age = r.getUserAge();
//				pw.print("<td>" +age+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> User Gender: </td>");
//				gender = r.getUserGender();
//				pw.print("<td>" +gender+ "</td>");
//				pw.print("</tr>");
//
//				pw.print("<tr>");
//				pw.print("<td> User Occupation: </td>");
//				occupation = r.getUserOccupation();
//				pw.print("<td>" +occupation+ "</td>");
//				pw.print("</tr>");
//
//
//
//				pw.println("<tr>");
//				pw.println("<td> Review Rating: </td>");
//				reviewRating = r.getReviewRating().toString();
//				pw.print("<td>" +reviewRating+ "</td>");
//				pw.print("</tr>");
//
////				pw.print("<tr>");
////				pw.print("<td> Review Text: </td>");
////				reviewText = r.getReviewText();
////				pw.print("<td>" +reviewText+ "</td>");
////				pw.print("</tr>");
//				pw.print("<tr>");
//				pw.print("<td> Review Text: </td>");
//				reviewText = r.getReviewText();
//
//				// Check if reviewText is null and handle accordingly
//				if (reviewText != null) {
//				    pw.print("<td>" + reviewText + "</td>");
//				} else {
//				    pw.print("<td>Unavailable</td>"); // Or any placeholder text you prefer
//				}
//				pw.print("</tr>");
////
////				reviewDate = r.getReviewDate();
////				SimpleDateFormat format = new SimpleDateFormat("YYYY/MM/DD");
////				Date date = format.parse(reviewDate);
////
////				pw.print("<tr>");
////				pw.print("<td> Review Date: </td>");
////				pw.print("<td>" +date+ "</td>");
////				pw.print("</tr>");		
//				reviewDate = r.getReviewDate();
//				String formattedDate = "Unavailable"; // Default value in case of errors
//
//				if (reviewDate != null) {
//				    try {
//				        // Use 'yyyy' instead of 'YYYY'
//				        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
//				        Date date = format.parse(reviewDate);
//
//				        // Format the date to a more user-friendly format
//				        SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy");
//				        formattedDate = outputFormat.format(date);
//				    } catch (ParseException e) {
//				        // Handle parsing errors, log if needed
//				        System.out.println("Date parsing error: " + e.getMessage());
//				    }
//				}
//
//				pw.print("<tr>");
//				pw.print("<td> Review Date: </td>");
//				pw.print("<td>" + formattedDate + "</td>");
//				pw.print("</tr>");
//				pw.println("</table>");
//				}					
//							
//		}
//		}	       
//                pw.print("</div></div></div>");		
//		utility.printHtml("Footer.html");
//	                     	
//                    }
//              	catch(Exception e)
//		{
//					System.out.println("Exception viewreview"+e);
//
//                 System.out.println(e.getMessage());
//                 e.printStackTrace();
//		}  			
//       
//	 	
//		}
	protected void review(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    try {
	        response.setContentType("text/html");
	        PrintWriter pw = response.getWriter();
	        Utilities utility = new Utilities(request, pw);
	        
	        if (!utility.isLoggedin()) {
	            HttpSession session = request.getSession(true);
	            session.setAttribute("login_msg", "Please Login to view Review");
	            response.sendRedirect("Login");
	            return;
	        }
	        
	        String productName = request.getParameter("name");
	        HashMap<String, ArrayList<Review>> hm = MongoDBDataStoreUtilities.selectReview();
	        
	        utility.printHtml("Header.html");
	        utility.printHtml("LeftNavigationBar.html");

	        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
	        pw.print("<a style='font-size: 24px;'>Review</a>");
	        pw.print("</h2><div class='entry'>");
	        
	        // If there are no reviews for product print no review else iterate over all the reviews using cursor and print the reviews in a table
	        if (hm == null) {
	            pw.println("<h2>Mongo Db server is not up and running</h2>");
	        } else {
	            if (!hm.containsKey(productName)) {
	                pw.println("<h2>There are no reviews for this product.</h2>");
	            } else {
	                for (Review r : hm.get(productName)) {
	                    pw.print("<table class='gridtable'>");

	                    pw.print("<tr>");
	                    pw.print("<td> Product Name: </td>");
	                    String name = r.getProductName();
	                    pw.print("<td>" + (name != null ? name : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> User Name: </td>");
	                    String userName = r.getUserName();
	                    pw.print("<td>" + (userName != null ? userName : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> Product Category: </td>");
	                    String productType = r.getProductType();
	                    pw.print("<td>" + (productType != null ? productType : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> Product Price: </td>");
	                    String productPrice = r.getPrice();
	                    pw.print("<td>" + (productPrice != null ? productPrice : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> Store Id: </td>");
	                    String storeId = r.getStoreId();
	                    pw.print("<td>" + (storeId != null ? storeId : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> Store City: </td>");
	                    String retailerCity = r.getRetailerCity();
	                    pw.print("<td>" + (retailerCity != null ? retailerCity : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> Store State: </td>");
	                    String retailerState = r.getStoreState();
	                    pw.print("<td>" + (retailerState != null ? retailerState : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> Store Zip Code: </td>");
	                    String retailerPin = r.getRetailerPin();
	                    pw.print("<td>" + (retailerPin != null ? retailerPin : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> Product on Sale: </td>");
	                    String productSale = r.getProductSale();
	                    pw.print("<td>" + (productSale != null ? productSale : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> Manufacturer Name: </td>");
	                    String productMaker = r.getProductMaker();
	                    pw.print("<td>" + (productMaker != null ? productMaker : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> Manufacturer Rebate: </td>");
	                    String rebate = r.getRebate();
	                    pw.print("<td>" + (rebate != null ? rebate : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> User Id: </td>");
	                    String userId = r.getUserId();
	                    pw.print("<td>" + (userId != null ? userId : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> User Age: </td>");
	                    String age = r.getUserAge();
	                    pw.print("<td>" + (age != null ? age : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> User Gender: </td>");
	                    String gender = r.getUserGender();
	                    pw.print("<td>" + (gender != null ? gender : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> User Occupation: </td>");
	                    String occupation = r.getUserOccupation();
	                    pw.print("<td>" + (occupation != null ? occupation : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    pw.print("<tr>");
	                    pw.print("<td> Review Rating: </td>");
	                    String reviewRating = r.getReviewRating() != null ? r.getReviewRating().toString() : "Unavailable";
	                    pw.print("<td>" + reviewRating + "</td>");
	                    pw.print("</tr>");

	                    // Check if reviewText is null and handle accordingly
	                    pw.print("<tr>");
	                    pw.print("<td> Review Text: </td>");
	                    String reviewText = r.getReviewText();
	                    pw.print("<td>" + (reviewText != null ? reviewText : "Unavailable") + "</td>");
	                    pw.print("</tr>");

	                    // Review Date Handling
	                    String reviewDate = r.getReviewDate();
	                    String formattedDate = "Unavailable"; // Default value in case of errors

	                    if (reviewDate != null) {
	                        try {
	                            SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
	                            Date date = format.parse(reviewDate);
	                            SimpleDateFormat outputFormat = new SimpleDateFormat("MMMM d, yyyy");
	                            formattedDate = outputFormat.format(date);
	                        } catch (ParseException e) {
	                            System.out.println("Date parsing error: " + e.getMessage());
	                        }
	                    }

	                    pw.print("<tr>");
	                    pw.print("<td> Review Date: </td>");
	                    pw.print("<td>" + formattedDate + "</td>");
	                    pw.print("</tr>");
	                    
	                    pw.println("</table>");
	                    pw.println("</br>");
	                    pw.println("</br>");

	                }                    
	            }
	        }
	        
	        pw.print("</div></div></div>");
	        utility.printHtml("Footer.html");
	    } catch (Exception e) {
	        System.out.println("Exception viewreview" + e);
	        System.out.println(e.getMessage());
	        e.printStackTrace();
	    }  
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
		
            }
}
