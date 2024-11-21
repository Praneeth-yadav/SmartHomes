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
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private final static String apiUrl = "https://api.openai.com/v1/embeddings"; // Adjust to the correct endpoint
    private final static String api = "APIKEY";
    private final static String model = "text-embedding-3-small";
    private final static String apiUrl_completions = "https://api.openai.com/v1/chat/completions"; // Adjust to the correct endpoint

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

//                    System.out.println("Embedding extracted: " + embeddingList);
//                    System.out.println("Size embedding : "+embeddingList.size());
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

//                    System.out.println("Query Vector: " + queryVector);
//                    System.out.println("Size of Query Vector: " + queryVector.size());
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
//                System.out.println("jsonResponse:" + jsonResponse);

                
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

//                    System.out.println("Embedding extracted: " + embeddingList);
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
    
//    public static void add10ProductsAndReviews() {
//        String product = "Unknown"; // Default decision
//        String[] productCategories= {"Smart Doorbells","Smart Doorlocks","Smart Speakers","Smart Lightning","Smart Thermostat"};
//    	try {
//            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
//            connection.setDoOutput(true);
//            connection.setRequestMethod("POST");
//            connection.setRequestProperty("Authorization", "Bearer " + api);
//            connection.setRequestProperty("Content-Type", "application/json");
//
//            JSONObject jsonRequest = new JSONObject();
//            jsonRequest.put("model", "gpt-4o-mini");
//
//            JSONArray messages = new JSONArray();
//
//            String analysisMessage = "PROMPT";            // Add the first message
//            messages.put(new JSONObject().put("role", "user").put("content", analysisMessage));
//            // Add the second message with the base64 image
//
//            jsonRequest.put("messages", messages);
//
//            // Write the JSON request to the output stream
//            try (OutputStream os = connection.getOutputStream();
//                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"))) {
//                writer.write(jsonRequest.toString());
//                writer.flush();
//            }
//
//            // Read the response
//            int responseCode = connection.getResponseCode();
//            if (responseCode == HttpURLConnection.HTTP_OK) {
//                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
//                StringBuilder response = new StringBuilder();
//                String inputLine;
//                while ((inputLine = in.readLine()) != null) {
//                    response.append(inputLine);
//                }
//                in.close();
//
//                // Parse the JSON response
//                JSONObject jsonResponse = new JSONObject(response.toString());
//                System.out.println("jsonResponse:" + jsonResponse);
//
//                // Extract decision from the response
//                product = jsonResponse.getJSONArray("choices")
//                        .getJSONObject(0) 
//                        .getJSONObject("message") 
//                        .getString("content"); 
//
//                System.out.println("Response Data :"+product);
//                try
//				  {
//					  MySqlDataStoreUtilities.addproducts(producttype,productId,productName,productPrice,productImage,productManufacturer,productCondition,productDiscount,productOnSale,productQuantity,productDescription,prod);
//						List<Double> output=Utils.getEmbeddingsForProduct(productDescription);
//						System.out.println("output : "+output);
//
//						String docID=ElasticSearchUtils.storeEmbeddingInElasticsearch(productDescription,productName,productPrice,producttype, output);
//						System.out.println("docID : "+docID);
//				  }
//				  catch(Exception e)
//				  { 
//						System.out.println("Exception productcurd dopost"+e);
//
//				  }
//            } else {
//                System.err.println("API request failed with response code: " + responseCode);
//            }
//        } catch (IOException e) {
//            System.err.println("Error while calling OpenAI API: " + e.getMessage());
//        }
//
//    }
//   	
    
    public static void add10ProductsAndReviews() {
        String[] productCategories = {"Smart Doorbells", "Smart Doorlocks", "Smart Speakers", "Smart Lighting", "Smart Thermostat"};
        
        for (String productCategory : productCategories) {
            for (int i = 0; i < 2; i++) { // Loop twice for each category
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl_completions).openConnection();
                    connection.setDoOutput(true);
                    connection.setRequestMethod("POST");
                    connection.setRequestProperty("Authorization", "Bearer " + api);
                    connection.setRequestProperty("Content-Type", "application/json");

                    JSONObject jsonRequest = new JSONObject();
                    jsonRequest.put("model", "gpt-4o-mini");

                    JSONArray messages = new JSONArray();
                    String promptMessage = "Generate a product for the category \"" + productCategory + 
                                           "\" with the following details: Product Name, Product Price, Category, and a description (max 100 words).";
                    messages.put(new JSONObject().put("role", "user").put("content", promptMessage));
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
                        String productDetails = jsonResponse.getJSONArray("choices")
                                                            .getJSONObject(0)
                                                            .getJSONObject("message")
                                                            .getString("content");

//                        System.out.println("Response Data: " + productDetails);

                        // Assuming product details are parsed or mapped to variables
                        String productName = extractField(productDetails, "Product Name");
                        double productPrice = Double.parseDouble(extractField(productDetails, "Product Price").replace("$", ""));
                        String productDescription = extractField(productDetails, "Description");

                        // Generate other necessary fields
                        String productId = UUID.randomUUID().toString();
                        String productImage = "";
                        String productManufacturer = "AI Generated";
                        String productCondition = "New";
                        int productDiscount = 10; // Example value
                        String productOnSale = "Yes"; // Example value
                        int productQuantity = 100; // Example value
                        String productCat=productCategory.replace("Smart ", "");
                        // Add the product to the database
                        try {
                            MySqlDataStoreUtilities.addproducts(productCat,productId,productName,productPrice,productImage,
                            		productManufacturer,productCondition,productDiscount,productOnSale,productQuantity,
                            		productDescription,"");
    						

                            // Generate embeddings and store in Elasticsearch
                            List<Double> output = Utils.getEmbeddingsForProduct(productDescription);
//                            System.out.println("Embeddings Output: " + output);

                            String docID = ElasticSearchUtils.storeEmbeddingInElasticsearch(
                                productDescription, productName, productPrice, productCat, output
                            );
                            System.out.println("Elasticsearch Doc ID: " + docID);
                            generateReviewsForEachProduct(productDescription,productName,productPrice,productCat);
                        } catch (Exception e) {
                            System.err.println("Exception while storing product and embeddings: " + e.getMessage());
                        }

                    } else {
                        System.err.println("API request failed with response code: " + responseCode);
                    }

                } catch (IOException e) {
                    System.err.println("Error while calling OpenAI API: " + e.getMessage());
                }
            }
        }
        System.out.println("<<<<<<<<<<<<<DONE DONE DONE DONE DONE DONE DONE DONE DONE DONE >>>>>>>>>>>>>>>>>");
    }

    /**
     * Utility method to extract fields from product details string.
     */
    private static String extractField(String productDetails, String fieldName) {
        String[] lines = productDetails.replace("*", "").split("\n");
        for (String line : lines) {
//        	System.out.println("Lines "+line);
            if (line.startsWith(fieldName + ":")) {
                return line.split(":", 2)[1].trim();
            }
        }
        return "";
    }


    
    private static void generateReviewsForEachProduct(String productDescription, String productName, double productPrice, String productType) {
        try {
            // Set up connection to OpenAI API
            HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl_completions).openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Authorization", "Bearer " + api);
            connection.setRequestProperty("Content-Type", "application/json");

            // Construct JSON request
//            JSONObject jsonRequest = new JSONObject();
//            jsonRequest.put("model", "gpt-4o-mini");
//
//            JSONArray messages = new JSONArray();
//            String promptMessage = "Generate 5 reviews for the product with the following details:\n"
//                    + "Name: " + productName + "\n"
//                    + "Description: " + productDescription + "\n"
//                    + "Price: $" + productPrice + "\n"
//                    + "Type: " + productType + "\n"
//                    + "Provide 3 positive and 2 negative reviews with detailed feedback.";
//            messages.put(new JSONObject().put("role", "user").put("content", promptMessage));
//            jsonRequest.put("messages", messages);
         // Define product-specific positive and negative keywords
            String positiveKeywords = "";
            String negativeKeywords = "";

            switch (productType.toLowerCase()) {
                case "doorbells":
                    positiveKeywords = "convenient, secure, real-time, reliable, clear video";
                    negativeKeywords = "glitchy, slow alerts, poor connection, privacy concerns";
                    break;
                case "doorlocks":
                    positiveKeywords = "secure, convenient, remote access, easy install";
                    negativeKeywords = "battery drain, app issues, unreliable, lock jams";
                    break;
                case "speakers":
                    positiveKeywords = "responsive, good sound, versatile, user-friendly";
                    negativeKeywords = "poor privacy, limited commands, connectivity issues";
                    break;
                case "lighting":
                    positiveKeywords = "customizable, energy-efficient, remote control, mood-enhancing";
                    negativeKeywords = "app problems, delay, connectivity issues, limited brightness";
                    break;
                case "thermostat":
                    positiveKeywords = "energy-saving, easy to use, efficient, remote control";
                    negativeKeywords = "difficult setup, temperature inaccuracy, app bugs, connectivity issues";
                    break;
                default:
                    System.err.println("Invalid product type.");
                    return;
            }

            // Construct JSON request with product-specific prompt
            JSONObject jsonRequest = new JSONObject();
            jsonRequest.put("model", "gpt-4o-mini");

            JSONArray messages = new JSONArray();
            String promptMessage = "Generate 5 reviews for the product with the following details:\n"
                    + "Name: " + productName + "\n"
                    + "Description: " + productDescription + "\n"
                    + "Price: $" + productPrice + "\n"
                    + "Type: " + productType + "\n"
                    + "For positive reviews, include keywords like: " + positiveKeywords + "\n"
                    + "For negative reviews, include keywords like: " + negativeKeywords + "\n"
                    + "Provide 3 positive and 2 negative reviews with detailed feedback.";
            messages.put(new JSONObject().put("role", "user").put("content", promptMessage));
            jsonRequest.put("messages", messages);

            // Write JSON request to output stream
            try (OutputStream os = connection.getOutputStream();
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"))) {
                writer.write(jsonRequest.toString());
                writer.flush();
            }

            // Read the API response
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
                String reviewDetails = jsonResponse.getJSONArray("choices")
                                                    .getJSONObject(0)
                                                    .getJSONObject("message")
                                                    .getString("content");

//                System.out.println("Generated Reviews:\n" + reviewDetails);

                // Split and process reviews (assumes reviews are separated by two newlines)
                String[] reviews = reviewDetails.split("\n\n");
                for (String reviewText : reviews) {
                    try {
                        // Generate embeddings for the review
                        List<Double> output = Utils.getEmbeddingsForProduct(reviewText);
//                        System.out.println("Embeddings Output: " + output);

                        // Store review and embeddings in Elasticsearch
                        String docID = ElasticSearchUtils.storeEmbeddingInElasticsearchForReviews(
                                reviewText, productName, Double.toString(productPrice), productType, output);
                        System.out.println("Stored Review with docID: " + docID);
                    } catch (Exception e) {
                        System.err.println("Error while storing review and embeddings: " + e.getMessage());
                    }
                }
            } else {
                System.err.println("API request failed with response code: " + responseCode);
            }
        } catch (IOException e) {
            System.err.println("Error while calling OpenAI API: " + e.getMessage());
        }
    }


}

