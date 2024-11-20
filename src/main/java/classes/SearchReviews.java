package classes;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/SearchReviews")
public class SearchReviews extends HttpServlet {

    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        // Utilities for including UI components
        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        // Main content area
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Search Reviews</a>");
        pw.print("</h2><div class='entry'>");

        // Input form
        pw.print("<form method='POST' action='SearchReviews'>");
        pw.print("<label for='searchInput'>Enter text to search review:</label><br>");
        pw.print("<input type='text' id='searchInput' name='searchInput' required style='width: 50%;'><br><br>");
        pw.print("<button type='submit' class='btnbuy'>Get Reviews</button>");
        pw.print("</form>");

        pw.print("</div></div></div>");
        utility.printHtml("Footer.html");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        // Fetch input text
        String searchInput = request.getParameter("searchInput");
        System.out.println("searchInput :"+searchInput);

        // Get recommendations based on the input
        List<String> reviews = getReviews(searchInput);

        // Utilities for including UI components
        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        // Main content area
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Reviews for: " + searchInput + "</a>");
        pw.print("</h2><div class='entry'>");

        if (reviews.isEmpty()) {
            pw.print("<p>No reviews found.</p>");
        } else {
            pw.print("<ul>");
            for (String review : reviews) {
                pw.print("<li>" + review + "</li>");
            }
            pw.print("</ul>");
        }

        pw.print("</div></div></div>");
        utility.printHtml("Footer.html");
    }

    /**
     * Dummy function to simulate fetching recommendations based on semantic similarity.
     * Replace this with actual logic for semantic analysis.
     */
    private List<String> getReviews(String input) {
        List<String> reviews = new ArrayList<>();
        reviews= ElasticSearchUtils.getReviews(input);
//        if (input != null && !input.isEmpty()) {
//            recommendations.add("Recommendation 1: Similar to '" + input + "'");
//            recommendations.add("Recommendation 2: Related to '" + input + "'");
//            recommendations.add("Recommendation 3: Based on '" + input + "'");
//        }
        return reviews;
    }
}
