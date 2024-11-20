package classes;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Operator;
import co.elastic.clients.elasticsearch._types.query_dsl.QueryBuilders;
//import co.elastic.clients.elasticsearch.ElasticsearchTransport ;
import co.elastic.clients.elasticsearch.core.IndexRequest;
import co.elastic.clients.elasticsearch.core.IndexResponse;
import co.elastic.clients.elasticsearch.core.SearchRequest;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.transport.endpoints.BooleanResponse;
import co.elastic.clients.transport.rest_client.RestClientTransport;
import jakarta.json.JsonArray;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

//import org.json.JSONObject;
import co.elastic.clients.json.jackson.*;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
//import java.net.HttpURLConnection;
//import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ElasticSearchUtils {

    private static final String API_KEY = "apiKEy";



    public static String storeEmbeddingInElasticsearch(String productDescription,String productName,double productPrice,String ProductType, List<Double> embeddings) throws IOException {
    	try {
			DisableSSLVerification.disableSSLVerification();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Input to storeEmbeddingInElasticsearch requestData: " + productDescription + " embeddings: " + embeddings);

        // Elasticsearch URL
        String urlString = "https://localhost:9200/search-products/_doc?pipeline=ent-search-generic-ingestion";

        // Prepare the JSON payload
        double[] embeddingsArray = embeddings.stream().mapToDouble(Double::doubleValue).toArray();
        Map<String, Object> document = new HashMap<>();
        document.put("description", productDescription);
        document.put("productName", productName);
        document.put("productPrice", productPrice);
        document.put("ProductType", ProductType);
        
        document.put("embedding", embeddingsArray);

        // Convert Map to JSON string (you can use a library like Jackson or Gson to do this)
        String jsonPayload = convertMapToJson(document);

        // Open connection
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request method and headers
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "ApiKey " + API_KEY);
        connection.setDoOutput(true);  // Enable output stream for the request body
        System.out.println("Request Payload: " + jsonPayload);

        // Write the JSON payload to the output stream
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Get the response code
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Get the response and document ID
        String documentId = null;
        String version = null;
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // Extract document ID and version from the response
                String responseString = response.toString();
                documentId = extractDocumentIdFromResponse(responseString);
                version = extractVersionFromResponse(responseString);

                System.out.println("Embedding stored with ID: " + documentId + " and version: " + version);
            }
        } else {
            System.out.println("Failed to index document");
        }

        // Close connection
        connection.disconnect();

        return documentId;
    }

  
    private static String convertMapToJson(Map<String, Object> map) {
        // Create an ObjectMapper instance (Jackson's main class for JSON processing)
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // Serialize the map to a JSON string
            return objectMapper.writeValueAsString(map);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return "{}";  // Return an empty JSON object if conversion fails
        }
    }
    
    private static String extractDocumentIdFromResponse(String response) {
        String documentId = null;
        int idIndex = response.indexOf("\"_id\":\"");
        if (idIndex != -1) {
            documentId = response.substring(idIndex + 7, response.indexOf("\"", idIndex + 7));
        }
        return documentId;
    }

    // Extract version from the Elasticsearch response
    private static String extractVersionFromResponse(String response) {
        // Extract the version from the response string (example uses regex)
        String version = null;
        int versionIndex = response.indexOf("\"_version\":");
        if (versionIndex != -1) {
            version = response.substring(versionIndex + 11, response.indexOf(",", versionIndex));
        }
        return version;
    }
    
    public static List<String> getRecommendations(String input) {
    	try {
			DisableSSLVerification.disableSSLVerification();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
        List<String> recommendations = new ArrayList<>();
        List<Double> queryVector = Utils.getQueryVector(input);
        System.out.println("queryVector :"+queryVector);
//add code here 
        String urlString = "https://localhost:9200/search-products/_search";

        // Create JSON body with query vector
        String jsonBody = String.format("{\n" +
                "  \"knn\": {\n" +
                "    \"field\": \"embedding\",\n" +
                "    \"k\": 10,\n" +
                "    \"num_candidates\": 10,\n" +
                "    \"query_vector\": %s\n" +
                "  },\n" +
                "  \"_source\": [\"productName\", \"productPrice\", \"ProductType\", \"description\"]\n" +
                "}", queryVector.toString());

        try {
            // Create URL object
            URL url = new URL(urlString);

            // Open HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "ApiKey " + API_KEY);
            connection.setDoOutput(true);

            // Write the JSON body to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input1 = jsonBody.getBytes("utf-8");
                os.write(input1, 0, input1.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            // Read the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Parse the response (you can use a JSON library like Jackson or Gson)
                    // For simplicity, let's assume we're just printing it out here
                    System.out.println("Response from Elasticsearch: " + response.toString());

                    // You will need to extract the 'hits' or 'recommendations' from the response
                    // You can use a library like Jackson or Gson to parse the JSON and get the recommendations

                    // Example of parsing the response (you should use a proper JSON parsing library)
                    // For now, this is just a placeholder for extracting actual recommendations
//                    recommendations.add("Example Recommendation 1");
//                    recommendations.add("Example Recommendation 2");
                    Gson gson = new Gson();
                    JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
                    JsonObject hitsObject = jsonResponse.getAsJsonObject("hits");
                    com.google.gson.JsonArray hitsArray = hitsObject.getAsJsonArray("hits");

                    // Loop through the hits and extract the relevant data
                    for (int i = 0; i < hitsArray.size(); i++) {
                        JsonObject hit = hitsArray.get(i).getAsJsonObject();
                        JsonObject source = hit.getAsJsonObject("_source");

                        // Extract product details
                        String productName = source.get("productName").getAsString();
                        double productPrice = source.get("productPrice").getAsDouble();
                        String productType = source.get("ProductType").getAsString();
                        String description = source.get("description").getAsString();

                        // Create a recommendation string
                        String recommendation = String.format("Product: %s\n  Price:$ %.2f\n  Type: %s\n  Description: %s\n", 
                                                              productName, productPrice, productType, description);

                        // Add to recommendations list
                        recommendations.add(recommendation);
                    }

                    // Print the recommendations
                    System.out.println("Recommendations:");
                    for (String rec : recommendations) {
                        System.out.println(rec);
                    }
                }
            } else {
                System.out.println("Request failed. HTTP Response Code: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return recommendations;
    }

    public static List<String> getReviews(String input) {
    	try {
			DisableSSLVerification.disableSSLVerification();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        List<String> recommendations = new ArrayList<>();
        List<Double> queryVector = Utils.getQueryVector(input);
        System.out.println("queryVector :"+queryVector);
//add code here 
        String urlString = "https://localhost:9200/search-reviews/_search";

        // Create JSON body with query vector
        String jsonBody = String.format("{\n" +
                "  \"knn\": {\n" +
                "    \"field\": \"embedding\",\n" +
                "    \"k\": 10,\n" +
                "    \"num_candidates\": 10,\n" +
                "    \"query_vector\": %s\n" +
                "  },\n" +
                "  \"_source\": [\"productName\", \"productPrice\", \"ProductType\", \"reviewtext\"]\n" +
                "}", queryVector.toString());

        try {
            // Create URL object
            URL url = new URL(urlString);

            // Open HTTP connection
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("Authorization", "ApiKey " + API_KEY);
            connection.setDoOutput(true);

            // Write the JSON body to the output stream
            try (OutputStream os = connection.getOutputStream()) {
                byte[] input1 = jsonBody.getBytes("utf-8");
                os.write(input1, 0, input1.length);
            }

            // Get the response code
            int responseCode = connection.getResponseCode();
            System.out.println("HTTP Response Code: " + responseCode);

            // Read the response
            if (responseCode == HttpURLConnection.HTTP_OK) {
                try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }

                    // Parse the response (you can use a JSON library like Jackson or Gson)
                    // For simplicity, let's assume we're just printing it out here
                    System.out.println("Response from Elasticsearch: " + response.toString());

                    // You will need to extract the 'hits' or 'recommendations' from the response
                    // You can use a library like Jackson or Gson to parse the JSON and get the recommendations

                    // Example of parsing the response (you should use a proper JSON parsing library)
                    // For now, this is just a placeholder for extracting actual recommendations
//                    recommendations.add("Example Recommendation 1");
//                    recommendations.add("Example Recommendation 2");
                    Gson gson = new Gson();
                    JsonObject jsonResponse = gson.fromJson(response.toString(), JsonObject.class);
                    JsonObject hitsObject = jsonResponse.getAsJsonObject("hits");
                    com.google.gson.JsonArray hitsArray = hitsObject.getAsJsonArray("hits");

                    // Loop through the hits and extract the relevant data
                    for (int i = 0; i < hitsArray.size(); i++) {
                        JsonObject hit = hitsArray.get(i).getAsJsonObject();
                        JsonObject source = hit.getAsJsonObject("_source");

                        // Extract product details
                        String productName = source.get("productName").getAsString();
                        String productPrice = source.get("productPrice").getAsString();
                        String productType = source.get("ProductType").getAsString();
                        String reviewtext = source.get("reviewtext").getAsString();

                        // Create a recommendation string
                        String recommendation = String.format("Product: %s\n Price: %s\n Type: %s\n reviewtext: %s\n", 
                                                              productName, productPrice, productType, reviewtext);

                        // Add to recommendations list
                        recommendations.add(recommendation);
                    }

                    // Print the recommendations
                    System.out.println("Recommendations:");
                    for (String rec : recommendations) {
                        System.out.println(rec);
                    }
                }
            } else {
                System.out.println("Request failed. HTTP Response Code: " + responseCode);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return recommendations;
    }
    

    public static String storeEmbeddingInElasticsearchForReviews(String reviewtext,String productName,String productPrice,String ProductType, List<Double> embeddings) throws IOException {
    	try {
			DisableSSLVerification.disableSSLVerification();
		} catch (KeyManagementException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println("Input to storeEmbeddingInElasticsearch requestData: " + reviewtext + " embeddings: " + embeddings);

        // Elasticsearch URL
        String urlString = "https://localhost:9200/search-reviews/_doc?pipeline=ent-search-generic-ingestion";

        // Prepare the JSON payload
        double[] embeddingsArray = embeddings.stream().mapToDouble(Double::doubleValue).toArray();
        Map<String, Object> document = new HashMap<>();
        document.put("reviewtext", reviewtext);
        document.put("productName", productName);
        document.put("productPrice", productPrice);
        document.put("ProductType", ProductType);
        
        document.put("embedding", embeddingsArray);

        // Convert Map to JSON string (you can use a library like Jackson or Gson to do this)
        String jsonPayload = convertMapToJson(document);

        // Open connection
        URL url = new URL(urlString);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        // Set request method and headers
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setRequestProperty("Authorization", "ApiKey " + API_KEY);
        connection.setDoOutput(true);  // Enable output stream for the request body
        System.out.println("Request Payload: " + jsonPayload);

        // Write the JSON payload to the output stream
        try (OutputStream os = connection.getOutputStream()) {
            byte[] input = jsonPayload.getBytes("utf-8");
            os.write(input, 0, input.length);
        }

        // Get the response code
        int responseCode = connection.getResponseCode();
        System.out.println("Response Code: " + responseCode);

        // Get the response and document ID
        String documentId = null;
        String version = null;
        if (responseCode == HttpURLConnection.HTTP_CREATED) {
            try (BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }

                // Extract document ID and version from the response
                String responseString = response.toString();
                documentId = extractDocumentIdFromResponse(responseString);
                version = extractVersionFromResponse(responseString);

                System.out.println("Embedding stored with ID: " + documentId + " and version: " + version);
            }
        } else {
            System.out.println("Failed to index document");
        }

        // Close connection
        connection.disconnect();

        return documentId;
    }

  
}
