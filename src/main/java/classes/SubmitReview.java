package classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/SubmitReview")

public class SubmitReview extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
	        Utilities utility= new Utilities(request, pw);
		storeReview(request, response);
	}
	protected void storeReview(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	        try
                {
                response.setContentType("text/html");
		PrintWriter pw = response.getWriter();
                Utilities utility = new Utilities(request,pw);
		if(!utility.isLoggedin()){
			HttpSession session = request.getSession(true);
			session.setAttribute("login_msg", "Please Login to add items to cart");
			response.sendRedirect("Login");
			return;
		}
                String productname=request.getParameter("productname");
                String producttype=request.getParameter("producttype");
				String productprice=request.getParameter("productprice");
                String productmaker=request.getParameter("productmaker");
                String reviewrating=request.getParameter("reviewrating");
				String storeid=request.getParameter("storeid");
				String storecity = request.getParameter("retailercity");
				String storestate = request.getParameter("retailerstate");
				String storezip=request.getParameter("zipcode");
				String productsale=request.getParameter("productsale");
				String rebate=request.getParameter("rebate");
				String userid=request.getParameter("userid");
				String age=request.getParameter("age");
				String gender=request.getParameter("gender");
				String occupation=request.getParameter("occupation");
                String reviewdate=request.getParameter("reviewdate");
                String reviewtext=request.getParameter("reviewtext");


		String message=utility.storeReview(productname,producttype,productprice,productmaker,reviewrating,storeid,storecity,storestate,storezip,productsale,rebate,userid,age,gender,occupation,reviewdate,reviewtext);
		List<Double> output=Utils.getEmbeddingsForProduct(reviewtext);
		System.out.println("output : "+output);

		String docID=ElasticSearchUtils.storeEmbeddingInElasticsearchForReviews(reviewtext,productname,productprice,producttype,output);
		System.out.println("docID : "+docID);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<form name ='Cart' action='CheckOut' method='post'>");
                pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>Review</a>");
		pw.print("</h2><div class='entry'>");
      		if(message.equalsIgnoreCase("Successfull")) {
				pw.print("<h2>Review for &nbsp"+productname+" Stored </h2>");
			} else {
				pw.print("<h2>Mongo Db is not up and running </h2>");
			}

		pw.print("</div></div></div>");
		utility.printHtml("Footer.html");

                    }
              	catch(Exception e)
		{
                 System.out.println("Submit review "+e.getMessage());
		}


		}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

            }
}
