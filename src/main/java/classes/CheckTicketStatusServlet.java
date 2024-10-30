package classes;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.bson.BasicBSONObject;

@WebServlet("/CheckTicketStatusServlet")
public class CheckTicketStatusServlet extends HttpServlet {

	 @Override
	    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        // Get ticket number from request parameter
	       
		 try{
			 String ticketNumber = request.getParameter("ticketNumber");
		 

	        // Retrieve the decision based on ticket number
	        String decision = MySqlDataStoreUtilities.getDecisionForTicket(ticketNumber);

	        // Set response type to JSON
	        response.setContentType("application/json");
	        response.setCharacterEncoding("UTF-8");

	        // Create JSON response
//	        BasicBSONObject jsonResponse = new BasicBSONObject();
//	        jsonResponse.put("ticketNumber", ticketNumber);
//	        jsonResponse.put("decision", decision);

	        // Send JSON response
	        PrintWriter out = response.getWriter();
//	        out.print(jsonResponse.toString());
	        out.print("{\"ticketNumber\": \"" + ticketNumber + "\", \"status\": \"" + decision + "\", \"statusMessage\": \"Response sent successfully.\"}");
	        out.flush();
	    }
		 catch(Exception e) {
			 PrintWriter out = response.getWriter();
//		        out.print(jsonResponse.toString());
		        out.print("{\"error\": \"" + e +  "\"}");
		        out.flush();
		 }
	 }
 
}
