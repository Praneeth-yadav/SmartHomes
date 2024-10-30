package classes;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.UUID;

@WebServlet("/FileUploadServlet")
@MultipartConfig
public class FileUploadServlet extends HttpServlet {
	static MongoCollection<Document> tickets;

    private static final String UPLOAD_DIRECTORY = "/src/main/webapp/opent"; // This can be adjusted if needed

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Servlet reached. Processing upload...");

        String uploadPath = System.getProperty("user.home") + File.separator + "uploads";
        System.out.println("uploadPath: " + uploadPath);

        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) uploadDir.mkdirs();

        String description = request.getParameter("productDescription");
        Part filePart = request.getPart("productImage");
        
        String fileName = getFileName(filePart);
        
        String ticketNumber = "TCKT" + UUID.randomUUID().toString().substring(0, 8);

        String uniqueFileName = ticketNumber + "." + getFileExtension(fileName); 
        System.out.println("Upload directory: " + uploadPath);
        System.out.println("Description: " + description);
        System.out.println("File Name: " + uniqueFileName + " " + ticketNumber);

        
        try {
            filePart.write(uploadPath + File.separator + uniqueFileName);
            String decision = GetImageDecision.getDecisionFromImage(uploadPath + File.separator + uniqueFileName, description);
            System.out.println("File decision: " + decision);

            // Insert ticket into the database with the decision
//            MySqlDataStoreUtilities.insertTicket(ticketNumber, decision); 
            MySqlDataStoreUtilities.insertTicket(ticketNumber, decision); 
        } catch (IOException e) {
            System.err.println("File upload error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "File upload failed.");
            return;
        } catch (Exception e) {
            System.err.println("FileUploadError error: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "FileUpload Generic - Error");
            return;
        }
        // Send JSON response
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();
        out.print("{\"ticketNumber\": \"" + ticketNumber + "\", \"message\": \"Upload successful\", \"statusMessage\": \"Response sent successfully.\"}");
        out.flush();
    }

    // Helper method to get the file name from the Part
    private String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 2, content.length() - 1);
            }
        }
        return null;
    }

    // Helper method to get the file extension
    private String getFileExtension(String fileName) {
        if (fileName != null && fileName.lastIndexOf('.') > 0) {
            return fileName.substring(fileName.lastIndexOf('.') + 1);
        }
        return ""; // Default to empty if no extension found
    }
    
    
    
}

