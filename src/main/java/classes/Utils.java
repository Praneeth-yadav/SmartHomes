package classes;

import org.json.JSONArray;
import org.json.JSONObject; 
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Utils {
    private final static String apiUrl = "https://api.openai.com/v1/embeddings"; // Adjust to the correct endpoint
    private final static String api = System.getenv("OPENAI_API_KEY");
    private final static String model = "text-embedding-3-small";

    public static List<Double> getEmbeddingsForProduct(String requestData ) {
//        String decision = "Unknown"; // Default decision
    	List<Double> embeddingList=null;

        try {
            // Encode the image file to Base64
            

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + api);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("model", model);
            jsonRequest.put("input", requestData);

            System.out.println("requestData :	"+requestData);

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

                
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                if (dataArray.length() > 0) {
                    // Get the first embedding object
                    JSONObject embeddingObject = dataArray.getJSONObject(0);

                    // Extract the embedding array
                    JSONArray embeddingArray = embeddingObject.getJSONArray("embedding");

                    // Convert the embedding array to a list or process it as needed
                    embeddingList= new ArrayList<>();
                    for (int i = 0; i < embeddingArray.length(); i++) {
                        embeddingList.add(embeddingArray.getDouble(i));
                    }
                    
                    final int requiredDimensionality = 512;
                    if (embeddingList.size() < requiredDimensionality) {
                        // Pad with 0s if the embedding is smaller than 512
                        while (embeddingList.size() < requiredDimensionality) {
                            embeddingList.add(0.0);
                        }
                    } else if (embeddingList.size() > requiredDimensionality) {
                        // Trim if the embedding is larger than 512
                        embeddingList = embeddingList.subList(0, requiredDimensionality);
                    }

                    System.out.println("Embedding extracted: " + embeddingList);
                    System.out.println("Size embedding : "+embeddingList.size());
                } else {
                    System.err.println("No data found in the response.");
                }
            } else {
                System.err.println("API request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("Error while calling OpenAI API: " + e.getMessage());
        }

        return embeddingList; // This should be one of: Refund Order, Replace Order, Escalate to Human Agent
    }
    
    public static List<Double> getQueryVector(String queryData) {
        List<Double> queryVector = null;

        try {
            // Define the API URL and API Key
        	HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + api);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("model", model);
            jsonRequest.put("input", queryData);

            System.out.println("queryData :	"+queryData);

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

                // Parse the JSON response to get the embedding
                JSONObject jsonResponse = new JSONObject(response.toString());
                System.out.println("JSON Response: " + jsonResponse);

                // Extract the embedding data
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                if (dataArray.length() > 0) {
                    JSONObject embeddingObject = dataArray.getJSONObject(0); // Get the first embedding object
                    JSONArray embeddingArray = embeddingObject.getJSONArray("embedding");

                    // Convert the embedding array to a list (query vector)
                    queryVector = new ArrayList<>();
                    for (int i = 0; i < embeddingArray.length(); i++) {
                        queryVector.add(embeddingArray.getDouble(i));
                    }

                    // Ensure the query vector has the correct dimensionality (e.g., 512)
                    final int requiredDimensionality = 512;
                    if (queryVector.size() < requiredDimensionality) {
                        // Pad with 0s if the query vector is smaller than the required size
                        while (queryVector.size() < requiredDimensionality) {
                            queryVector.add(0.0);
                        }
                    } else if (queryVector.size() > requiredDimensionality) {
                        // Trim if the query vector is larger than the required size
                        queryVector = queryVector.subList(0, requiredDimensionality);
                    }

                    System.out.println("Query Vector: " + queryVector);
                    System.out.println("Size of Query Vector: " + queryVector.size());
                } else {
                    System.err.println("No data found in the response.");
                }
            } else {
                System.err.println("API request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("Error while calling OpenAI API: " + e.getMessage());
        }

        return queryVector;  // Return the query vector, which is the embedding of the query
    }

    
    public static String getEmbeddingsForreviews(String requestData ) {
        String decision = "Unknown"; // Default decision

        try {
            // Encode the image file to Base64
            

            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + api);
            connection.setRequestProperty("Content-Type", "application/json");

            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("model", model);
            jsonRequest.put("input", requestData);

            System.out.println("requestData :	"+requestData);

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

                
                JSONArray dataArray = jsonResponse.getJSONArray("data");
                if (dataArray.length() > 0) {
                    // Get the first embedding object
                    JSONObject embeddingObject = dataArray.getJSONObject(0);

                    // Extract the embedding array
                    JSONArray embeddingArray = embeddingObject.getJSONArray("embedding");

                    // Convert the embedding array to a list or process it as needed
                    List<Double> embeddingList = new ArrayList<>();
                    for (int i = 0; i < embeddingArray.length(); i++) {
                        embeddingList.add(embeddingArray.getDouble(i));
                    }

                    System.out.println("Embedding extracted: " + embeddingList);
                } else {
                    System.err.println("No data found in the response.");
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

