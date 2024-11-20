package classes;

import org.json.JSONArray;
import org.json.JSONObject; 
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.Base64;

public class GetImageDecision {
    private final static String apiUrl = "https://api.openai.com/v1/chat/completions"; // Adjust to the correct endpoint
    private final static String api = System.getenv("OPENAI_API_KEY"); 

    public static String getDecisionFromImage(String imagePath, String ticketDescription) {
        String decision = "Unknown"; // Default decision

        try {
            // Encode the image file to Base64
            byte[] fileContent = Files.readAllBytes(new File(imagePath).toPath());
            String base64Image = Base64.getEncoder().encodeToString(fileContent);

                        HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + api);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("model", "gpt-4o-mini");

            JSONArray messages = new JSONArray();
            System.out.println("ticketDescription :"+ticketDescription);

            String analysisMessage = "You are an AI assistant designed to analyze customer service inquiries regarding product issues. " +
                    "Based on the provided information, determine if the product falls into one of the following categories:\n\n" +
                    "1. **Refund Order**: The customer is requesting a refund for a product due to dissatisfaction or defect.\n" +
                    "2. **Replace Order**: The customer is requesting a replacement for a product that is defective or not as described.\n" +
                    "3. **Escalate to Human Agent**: The issue is complex, or the customer requires special assistance beyond your capabilities.\n\n" +
                    "**Customer Inquiry:**\n\"" + ticketDescription + "\"\n\n" +
                    "Based on the above information, classify the request into one of the three categories. If the information is insufficient to make a decision, advise to consult a human agent for further assistance.Also give me a response only with the option";

            // Add the first message
            messages.put(new JSONObject().put("role", "user").put("content", analysisMessage));
            // Add the second message with the base64 image
            messages.put(new JSONObject().put("role", "user").put("content", "data:image/jpeg;base64," + base64Image));

            jsonRequest.put("messages", messages);

            // Write the JSON request to the output stream
            try (OutputStream os = connection.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"))) {
                writer.write(jsonRequest.toString());
                writer.flush();
            }

            // Read the response
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Parse the JSON response
                JSONObject jsonResponse = new JSONObject(response.toString());
                System.out.println("jsonResponse:" + jsonResponse);

                // Extract decision from the response
                decision = jsonResponse.getJSONArray("choices")
                        .getJSONObject(0) 
                        .getJSONObject("message") 
                        .getString("content"); 

                if (decision.toLowerCase().contains("refund")) {
                    decision = "Refund Order";
                } else if (decision.toLowerCase().contains("replace")) {
                    decision = "Replace Order";
//                } else if (decision.toLowerCase().contains("escalate")) {
//                    decision = "Escalate to Human Agent";
                } else {
//                    decision = "Unknown Decision";
                  decision = "Escalate to Human Agent";

                }
            } else {
                System.err.println("API request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("Error while calling OpenAI API: " + e.getMessage());
        }

        return decision; // This should be one of: Refund Order, Replace Order, Escalate to Human Agent
    }
}

