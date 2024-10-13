package classes;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.bson.Document;
import org.bson.conversions.Bson;

import com.mongodb.AggregationOutput;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.client.AggregateIterable;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Accumulators;
import com.mongodb.client.model.Aggregates;
import com.mongodb.client.model.Filters;
@WebServlet("/FindReviews")

public class FindReviews extends HttpServlet {
	static MongoCollection<Document> myReviews;

//	@Override
//	protected void doPost(HttpServletRequest request,
//		HttpServletResponse response) throws ServletException, IOException {
//		response.setContentType("text/html");
//		PrintWriter pw = response.getWriter();
//		Utilities utility = new Utilities(request, pw);
//
//
//		//check if the user is logged in
//		if(!utility.isLoggedin()){
//			HttpSession session = request.getSession(true);
//			session.setAttribute("login_msg", "Please Login to View your Orders");
//			response.sendRedirect("Login");
//			return;
//		}
//
//			String productName = request.getParameter("productName");
//			int productPrice = Integer.parseInt(request.getParameter("productPrice"));
// //           String productPrice = request.getParameter("productPrice");
//        	int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
//			String compareRating = request.getParameter("compareRating");
//			String comparePrice = request.getParameter("comparePrice");
//			String retailerCity = request.getParameter("retailerCity");
//			String retailerZipcode = request.getParameter("retailerZipcode");
//
//			String[] filters = request.getParameterValues("queryCheckBox");
//			String[] extraSettings = request.getParameterValues("extraSettings");
//			String dataGroupBy=request.getParameter("dataGroupBy");
//
//
//
//			boolean noFilter = false;
//			boolean filterByProduct = false;
//			boolean filterByPrice = false;
//			boolean filterByRating = false;
//			myReviews=MongoDBDataStoreUtilities.getConnection();
//			BasicDBObject query = new BasicDBObject();
//			boolean groupBy = false;
//			boolean filterByCity = false;
//			boolean groupByCity = false;
//			boolean groupByProduct = false;
//			boolean filterByZip = false;
//
//			boolean countOnly = false;
//			String groupByDropdown = request.getParameter("groupByDropdown");
//			DBCursor dbCursor=null;
//			DBObject match = null;
//			DBObject groupFields = null;
//			DBObject group = null;
//			DBObject projectFields = null;
//			DBObject project = null;
//			AggregationOutput aggregate = null;
//			String groupfield=null;
//
//			//Check for extra settings(Grouping Settings)
//			if(extraSettings != null){
//				//User has selected extra settings
//				groupBy = true;
//
//				for (String extraSetting : extraSettings) {
//					switch (extraSetting){
//						case "COUNT_ONLY":
//							//Not implemented functionality to return count only
//							countOnly = true;
//							break;
//						case "GROUP_BY":
//							//Can add more grouping conditions here
//							if(groupByDropdown.equalsIgnoreCase("GROUP_BY_CITY")){
//								groupByCity = true;
//							}else if(groupByDropdown.equalsIgnoreCase("GROUP_BY_PRODUCT")){
//								groupByProduct = true;
//							}
//							break;
//					}
//				}
//			}
//
//
//			if(filters != null && !groupBy){
//				for (String filter : filters) {
//					//Check what all filters are ON
//					//Build the query accordingly
//					switch (filter){
//						case "productName":
//							filterByProduct = true;
//							if(!productName.equalsIgnoreCase("ALL_PRODUCTS")){
//								query.put("productName", productName);
//							}
//							break;
//
//						case "productPrice":
//							filterByPrice = true;
//							if (comparePrice.equalsIgnoreCase("EQUALS_TO")) {
//								query.put("price", productPrice);
//							}else if(comparePrice.equalsIgnoreCase("GREATER_THAN")){
//								query.put("price", new BasicDBObject("$gt", productPrice));
//							}else if(comparePrice.equalsIgnoreCase("LESS_THAN")){
//								query.put("price", new BasicDBObject("$lt", productPrice));
//							}
//
//							break;
//
//						case "retailerZipcode":
//							filterByZip = true;
//							System.out.println("inside if");
//							query.put("retailerpin", retailerZipcode);
//							break;
//
//						case "retailerCity":
//							filterByCity = true;
//							if(!retailerCity.equalsIgnoreCase("All") && !groupByCity){
//								query.put("retailercity", retailerCity);
//							}
//							break;
//
//						case "reviewRating":
//							filterByRating = true;
//							if (compareRating.equalsIgnoreCase("EQUALS_TO")) {
//								query.put("reviewRating", reviewRating);
//							}else{
//								query.put("reviewRating", new BasicDBObject("$gt", reviewRating));
//							}
//							break;
//
//						default:
//							//Show all the reviews if nothing is selected
//							noFilter = true;
//							break;
//					}
//				}
//			}else{
//				//Show all the reviews if nothing is selected
//				noFilter = true;
//			}
//
//
//				//Run the query
//			if(groupBy){
//				//Run the query using aggregate function
//
//				if(groupByCity){
//					groupfield="RetailerCity";
//					groupFields = new BasicDBObject("_id", 0);
//					groupFields.put("_id", "$retailercity");
//					groupFields.put("count", new BasicDBObject("$sum", 1));
//					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
//					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
//					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
//					groupFields.put("price", new BasicDBObject("$push", "$price"));
//					groupFields.put("retailerCity", new BasicDBObject("$push", "$retailercity"));
//					groupFields.put("retailerpin", new BasicDBObject("$push", "$retailerpin"));
//
//					group = new BasicDBObject("$group", groupFields);
//
//					projectFields = new BasicDBObject("_id", 0);
//					projectFields.put("value", "$_id");
//					projectFields.put("ReviewValue", "$count");
//					projectFields.put("Product", "$productName");
//					projectFields.put("User", "$userName");
//					projectFields.put("Reviews", "$review");
//					projectFields.put("Rating", "$rating");
//				    projectFields.put("price", "$price");
//				    projectFields.put("retailerCity", "$retailerCity");
//				    projectFields.put("zipCode", "$retailerpin");
//
//					project = new BasicDBObject("$project", projectFields);
//
//					aggregate = myReviews.aggregate(group, project);
//
//					//Construct the page content
//
//				}else if(groupByProduct){
//
//					groupfield="ProductName";
//					groupFields = new BasicDBObject("_id", 0);
//					groupFields.put("_id", "$productName");
//					groupFields.put("count", new BasicDBObject("$sum", 1));
//					groupFields.put("review", new BasicDBObject("$push", "$reviewText"));
//					groupFields.put("rating", new BasicDBObject("$push", "$reviewRating"));
//					groupFields.put("productName", new BasicDBObject("$push", "$productName"));
//					groupFields.put("price", new BasicDBObject("$push", "$price"));
//					groupFields.put("retailerCity", new BasicDBObject("$push", "$retailercity"));
//					groupFields.put("zipCode", new BasicDBObject("$push", "$retailerpin"));
//
//					group = new BasicDBObject("$group", groupFields);
//
//					projectFields = new BasicDBObject("_id", 0);
//					projectFields.put("value", "$_id");
//					projectFields.put("ReviewValue", "$count");
//					projectFields.put("Product", "$productName");
//					projectFields.put("Reviews", "$review");
//					projectFields.put("Rating", "$rating");
//					projectFields.put("price", "$price");
//					projectFields.put("retailerCity", "$retailerCity");
//				    projectFields.put("zipCode", "$zipCode");
//
//					project = new BasicDBObject("$project", projectFields);
//
//					aggregate = myReviews.aggregate(group, project);
//
//					//Construct the page content
//
//				}
//			}
//			else
//			{
//
//			dbCursor= myReviews.find(query);
//			}
//
//		utility.printHtml("Header.html");
//		utility.printHtml("LeftNavigationBar.html");
//		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
//		pw.print("<a style='font-size: 24px;'>Data Analytics on Review</a>");
//		pw.print("</h2><div class='entry'>");
//		if(groupBy){
//
//		pw.print("<table class='gridtable'>");
//			constructGroupByContent(aggregate, pw,dataGroupBy);
//
//	      pw.print("</table>");
//
//		}
//
//		else if(!groupBy){
//		constructTableContent(dbCursor, pw);}
//		pw.print("</div></div></div>");
//		utility.printHtml("Footer.html");
//	}
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    response.setContentType("text/html");
	    PrintWriter pw = response.getWriter();
	    Utilities utility = new Utilities(request, pw);

	    // Check if the user is logged in
	    if (!utility.isLoggedin()) {
	        HttpSession session = request.getSession(true);
	        session.setAttribute("login_msg", "Please Login to View your Orders");
	        response.sendRedirect("Login");
	        return;
	    }

	    String productName = request.getParameter("productName");
	    int productPrice = Integer.parseInt(request.getParameter("productPrice"));
	    int reviewRating = Integer.parseInt(request.getParameter("reviewRating"));
	    String compareRating = request.getParameter("compareRating");
	    String comparePrice = request.getParameter("comparePrice");
	    String retailerCity = request.getParameter("retailerCity");
	    String retailerZipcode = request.getParameter("retailerZipcode");

	    String[] filters = request.getParameterValues("queryCheckBox");
	    String[] extraSettings = request.getParameterValues("extraSettings");
	    String dataGroupBy = request.getParameter("dataGroupBy");

	    myReviews = MongoDBDataStoreUtilities.getConnection();

	    boolean groupByCity = false;
	    boolean groupByProduct = false;
	    boolean countOnly = false;

	    // Handle grouping and counting settings
	    if (extraSettings != null) {
	        for (String extraSetting : extraSettings) {
	            switch (extraSetting) {
	                case "COUNT_ONLY":
	                    countOnly = true;
	                    break;
	                case "GROUP_BY":
	                    String groupBy = request.getParameter("groupByDropdown");
	                    if (groupBy != null) {
	                        if (groupBy.equalsIgnoreCase("GROUP_BY_CITY")) {
	                            groupByCity = true;
	                        } else if (groupBy.equalsIgnoreCase("GROUP_BY_PRODUCT")) {
	                            groupByProduct = true;
	                        }
	                    }
	                    break;
	            }
	        }
	    }

	    List<Bson> pipeline = new ArrayList<>(); // Use ArrayList for dynamic size

	    // Build query filter
	    if (filters != null) {
	        for (String filter : filters) {
	            switch (filter) {
	                case "productName":
	                    if (!productName.equalsIgnoreCase("ALL_PRODUCTS")) {
	                        pipeline.add(Aggregates.match(Filters.eq("productName", productName)));
	                    }
	                    break;
	                case "productPrice":
	                    if (comparePrice.equalsIgnoreCase("EQUALS_TO")) {
	                        pipeline.add(Aggregates.match(Filters.eq("price", productPrice)));
	                    } else if (comparePrice.equalsIgnoreCase("GREATER_THAN")) {
	                        pipeline.add(Aggregates.match(Filters.gt("price", productPrice)));
	                    } else if (comparePrice.equalsIgnoreCase("LESS_THAN")) {
	                        pipeline.add(Aggregates.match(Filters.lt("price", productPrice)));
	                    }
	                    break;
	                case "retailerZipcode":
	                    pipeline.add(Aggregates.match(Filters.eq("retailerpin", retailerZipcode)));
	                    break;
	                case "retailerCity":
	                    if (!retailerCity.equalsIgnoreCase("All")) {
	                        pipeline.add(Aggregates.match(Filters.eq("retailercity", retailerCity)));
	                    }
	                    break;
	                case "reviewRating":
	                    if (compareRating.equalsIgnoreCase("EQUALS_TO")) {
	                        pipeline.add(Aggregates.match(Filters.eq("reviewRating", reviewRating)));
	                    } else {
	                        pipeline.add(Aggregates.match(Filters.gt("reviewRating", reviewRating)));
	                    }
	                    break;
	            }
	        }
	    }

	    // Group by settings
	    if (groupByCity) {
	        // Group by retailer city
	        pipeline.add(Aggregates.group("$retailercity", 
	            Accumulators.sum("count", 1),
	            Accumulators.push("productName", "$productName"),
	            Accumulators.push("reviewText", "$reviewText"),
	            Accumulators.push("reviewRating", "$reviewRating"),
	            Accumulators.push("price", "$price"),
	            Accumulators.push("retailercity", "$retailercity"),
	            Accumulators.push("retailerpin", "$retailerpin")
	        ));
	    } else if (groupByProduct) {
	        // Group by product name
	        pipeline.add(Aggregates.group("$productName", 
	            Accumulators.sum("count", 1),
	            Accumulators.push("reviewText", "$reviewText"),
	            Accumulators.push("reviewRating", "$reviewRating"),
	            Accumulators.push("price", "$price"),
	            Accumulators.push("retailercity", "$retailercity"),
	            Accumulators.push("retailerpin", "$retailerpin")
	        ));
	    }

	    AggregateIterable<Document> aggregate = myReviews.aggregate(pipeline);

	    utility.printHtml("Header.html");
	    utility.printHtml("LeftNavigationBar.html");
	    pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
	    pw.print("<a style='font-size: 24px;'>Data Analytics on Review</a>");
	    pw.print("</h2><div class='entry'>");

	    if (groupByCity || groupByProduct) {
	        pw.print("<table class='gridtable'>");
	        constructGroupByContent(aggregate, pw, dataGroupBy);
	        pw.print("</table>");
	    } else {
	        FindIterable<Document> dbCursor = myReviews.find();
	        constructTableContent(dbCursor, pw);
	    }

	    pw.print("</div></div></div>");
	    utility.printHtml("Footer.html");
	}

//	public void constructGroupByContent(AggregateIterable<Document> aggregate, PrintWriter pw,String dataGroupBy)
//	{
//		String tableData = "";
//		int count=0;
//		if(dataGroupBy.equalsIgnoreCase("Count"))
//		{
//
//				pw.print("<tr><td>Name</td><td>Count</td></tr>");
//
//		for (DBObject result : aggregate.results()) {
//			BasicDBObject bobj = (BasicDBObject) result;
//		 tableData = "<tr><td> "+bobj.getString("value")+"</td>&nbsp"
//						+	"<td>"+bobj.getString("ReviewValue")+"</td></tr>";
//						pw.print(tableData);
//		count++;
//		}
//		}
//
//		if(dataGroupBy.equalsIgnoreCase("Detail"))
//		{
//
//		int detailcount=0;
//				for (DBObject result : aggregate.results()) {
//				BasicDBObject bobj = (BasicDBObject) result;
//					BasicDBList productList = (BasicDBList) bobj.get("Product");
//
//				BasicDBList productReview = (BasicDBList) bobj.get("Reviews");
//				BasicDBList rating = (BasicDBList) bobj.get("Rating");
//						BasicDBList retailercity = (BasicDBList) bobj.get("retailerCity");
//						BasicDBList zipcode = (BasicDBList) bobj.get("zipCode");
//						BasicDBList price = (BasicDBList) bobj.get("price");
//
//				pw.print("<tr><td>"+ bobj.getString("value")+"</td></tr>");
//
//			while (detailcount < productList.size()) {
//					System.out.println("inside 1 inside 2"+productList.get(detailcount)+rating.get(detailcount));
//					tableData = "<tr rowspan = \"3\"><td> Product: "+productList.get(detailcount)+"</br>"
//							+   "Rating: "+rating.get(detailcount)+"</br>"
//							+   "Price: "+price.get(detailcount)+"</br>"
//							+   "retailercity: "+retailercity.get(detailcount)+"</br>"
//							+   "retailerzipcode: "+zipcode.get(detailcount)+"</br>"
//							+	"Review: "+productReview.get(detailcount)+"</td></tr>";
//
//					pw.print(tableData);
//
//					detailcount++;
//
//				}
//			detailcount=0;
//			count++;
//			}
//		}
//		if(count==0)
//		{
//			tableData = "<h2>No Data Found</h2>";
//			pw.print(tableData);
//		}
//
//	}
	
	public void constructGroupByContent(AggregateIterable<Document> aggregate, PrintWriter pw, String dataGroupBy) {
	    String tableData = "";
	    int count = 0;

	    if ("Count".equalsIgnoreCase(dataGroupBy)) {
	        pw.print("<tr><td>Name</td><td>Count</td></tr>");

	        for (Document result : aggregate) {
	            // Assuming 'value' and 'ReviewValue' are the correct keys in the Document
	            String value = result.getString("value");
	            String reviewValue = result.getString("ReviewValue");
	            tableData = "<tr><td>" + value + "</td><td>" + reviewValue + "</td></tr>";
	            pw.print(tableData);
	            count++;
	        }
	    } else if ("Detail".equalsIgnoreCase(dataGroupBy)) {
	        for (Document result : aggregate) {
	            // Extract the necessary fields from the Document
	            String value = result.getString("value");
	            BasicDBList productList = (BasicDBList) result.get("Product");
	            BasicDBList productReview = (BasicDBList) result.get("Reviews");
	            BasicDBList rating = (BasicDBList) result.get("Rating");
	            BasicDBList retailerCity = (BasicDBList) result.get("retailerCity");
	            BasicDBList zipcode = (BasicDBList) result.get("zipCode");
	            BasicDBList price = (BasicDBList) result.get("price");

	            pw.print("<tr><td>" + value + "</td></tr>");

	            // Use a nested loop to display detailed information
	            for (int detailCount = 0; detailCount < productList.size(); detailCount++) {
	                String product = productList.get(detailCount).toString();
	                String productRating = rating.get(detailCount).toString();
	                String productPrice = price.get(detailCount).toString();
	                String city = retailerCity.get(detailCount).toString();
	                String productZip = zipcode.get(detailCount).toString();
	                String review = productReview.get(detailCount).toString();

	                tableData = "<tr><td>Product: " + product + "<br>"
	                        + "Rating: " + productRating + "<br>"
	                        + "Price: " + productPrice + "<br>"
	                        + "Retailer City: " + city + "<br>"
	                        + "Retailer Zipcode: " + productZip + "<br>"
	                        + "Review: " + review + "</td></tr>";

	                pw.print(tableData);
	            }
	            count++;
	        }
	    }

	    if (count == 0) {
	        tableData = "<h2>No Data Found</h2>";
	        pw.print(tableData);
	    }
	}


//	public void constructTableContent(FindIterable<Document> dbCursor,PrintWriter pw)
//	{
//		String tableData = "";
//			pw.print("<table class='gridtable'>");
//
//			while (dbCursor.hasNext()) {
//			BasicDBObject bobj = (BasicDBObject) dbCursor.next();
//
//			tableData =   "<tr><td align='center' colspan='2'>Review</td></tr><tr><td>Name: </td><td>" + bobj.getString("productName") + "</td></tr>"
//						+ "<tr><td>Rating:</td><td>" + bobj.getString("reviewRating") + "</td></tr>"
//						+ "<tr><td>Price:</td><td>" + bobj.getString("price") + "</td></tr>"
//						+ "<tr><td>Retailer City:</td><td>" + bobj.getString("retailercity") + "</td></tr>"
//						+ "<tr><td>Date:</td><td>" + bobj.getString("reviewDate") + "</td></tr>"
//						+ "<tr><td>Review Text:</td><td>" + bobj.getString("reviewText")+"</td><tr>"
//						+ "<tr><td>RetailerZipCode:</td><td>" + bobj.getString("retailerpin")+"</td><tr>";
//
//
//				 pw.print(tableData);
//		      }
//			  	pw.print("</table>");
//
//		//No data found
//		if(dbCursor.count() == 0){
//			tableData = "<h2>No Data Found</h2>";
//			pw.print(tableData);
//		}
//
//	}

	public void constructTableContent(FindIterable<Document> dbCursor, PrintWriter pw) {
	    String tableData = "";
	    pw.print("<table class='gridtable'>");

	    // Use a counter to check if any data is found
	    boolean dataFound = false;

	    while (dbCursor.iterator().hasNext()) {
	        Document bobj = dbCursor.iterator().next();  // Use Document instead of BasicDBObject
	        dataFound = true;  // Set dataFound to true if we retrieve any document

	        tableData = "<tr><td align='center' colspan='2'>Review</td></tr>"
	                + "<tr><td>Name:</td><td>" + bobj.getString("productName") + "</td></tr>"
	                + "<tr><td>Rating:</td><td>" + bobj.getString("reviewRating") + "</td></tr>"
	                + "<tr><td>Price:</td><td>" + bobj.getString("price") + "</td></tr>"
	                + "<tr><td>Retailer City:</td><td>" + bobj.getString("retailercity") + "</td></tr>"
	                + "<tr><td>Date:</td><td>" + bobj.getString("reviewDate") + "</td></tr>"
	                + "<tr><td>Review Text:</td><td>" + bobj.getString("reviewText") + "</td></tr>"
	                + "<tr><td>Retailer Zip Code:</td><td>" + bobj.getString("retailerpin") + "</td></tr>";

	        pw.print(tableData);
	    }

	    // Close the table after printing all rows
	    pw.print("</table>");

	    // Check if no data was found and print the message
	    if (!dataFound) {
	        pw.print("<h2>No Data Found</h2>");
	    }
	}

}
