package classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Base64;

public class ImageToDataUrl {

    public static void main(String[] args) {
        String imagePath = "path/to/your/image.png"; // Update this with your actual image path

        try {
            String base64Image = encodeImageToBase64(imagePath);
            String dataUrl = createDataUrl(base64Image);

            // Print the data URL
            System.out.println("Data URL: " + dataUrl);

            // Use this data URL in your application (e.g., send it in an API request)
            // Example of sending it in an API call (pseudo-code)
            // String response = sendApiRequest(dataUrl);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Method to encode an image to Base64
    private static String encodeImageToBase64(String imagePath) throws IOException {
        File file = new File(imagePath);
        FileInputStream fileInputStream = new FileInputStream(file);
        byte[] bytes = new byte[(int) file.length()];
        fileInputStream.read(bytes);
        fileInputStream.close();

        // Encode to Base64
        return Base64.getEncoder().encodeToString(bytes);
    }

    // Method to create a data URL from a Base64 string
    private static String createDataUrl(String base64Image) {
        return "data:image/jpg;base64," + base64Image; // Change "png" to the correct format if needed
    }
}
