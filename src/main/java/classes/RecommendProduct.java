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

@WebServlet("/RecommendProduct")
public class RecommendProduct extends HttpServlet {

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
        pw.print("<a style='font-size: 24px;'>Product Recommendations</a>");
        pw.print("</h2><div class='entry'>");

        // Input form
        pw.print("<form method='POST' action='RecommendProduct'>");
        pw.print("<label for='searchInput'>Enter a product description:</label><br>");
        pw.print("<input type='text' id='searchInput' name='searchInput' required style='width: 50%;'><br><br>");
        pw.print("<button type='submit' class='btnbuy'>Get Recommendations</button>");
        pw.print("</form>");

        pw.print("</div></div></div>");
        utility.printHtml("Footer.html");
//        Utils.add10ProductsAndReviews();
    }

   

	@Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter pw = response.getWriter();

        // Fetch input text
        String searchInput = request.getParameter("searchInput");

        // Get recommendations based on the input
        List<String> recommendations = getRecommendations(searchInput);

        // Utilities for including UI components
        Utilities utility = new Utilities(request, pw);
        utility.printHtml("Header.html");
        utility.printHtml("LeftNavigationBar.html");

        // Main content area
        pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
        pw.print("<a style='font-size: 24px;'>Recommendations for: " + searchInput + "</a>");
        pw.print("</h2><div class='entry'>");

        if (recommendations.isEmpty()) {
            pw.print("<p>No recommendations found.</p>");
        } else {
            pw.print("<ul>");
            for (String recommendation : recommendations) {
                pw.print("<li>" + recommendation + "</li>");
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
    private List<String> getRecommendations(String input) {
        List<String> recommendations = new ArrayList<>();
        recommendations= ElasticSearchUtils.getRecommendations(input);
//        if (input != null && !input.isEmpty()) {
//            recommendations.add("Recommendation 1: Similar to '" + input + "'");
//            recommendations.add("Recommendation 2: Related to '" + input + "'");
//            recommendations.add("Recommendation 3: Based on '" + input + "'");
//        }
        return recommendations;
    }
}
