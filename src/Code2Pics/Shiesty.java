package Code2Pics;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Shiesty {
 // Define a string of ASCII characters from dark to light
    private static final String ASCII_CHARS = "@%#*+=-:. ";

    // Set the width of the ASCII art
    private static final int ASCII_WIDTH = 60;  // TODO: adjust size here

    public static void main(String[] args) {
        // Image file path (Change to your image file path)

        String imagePath = "/Users/kentre/Desktop/shiesty.png"; // TODO: add pic filepath here

        try {
            // Load the image from the file
            BufferedImage image = ImageIO.read(new File(imagePath));

            // Resize the image to fit the desired ASCII art width
            BufferedImage resizedImage = resizeImage(image, ASCII_WIDTH);

            // Convert the resized image to grayscale (black and white)
            BufferedImage grayImage = convertToGrayscale(resizedImage);

            // Generate the ASCII art from the grayscale image
            String asciiArt = generateAsciiArt(grayImage);

            // Print the ASCII art to the console
            System.out.println(asciiArt);

        } catch (IOException e) {
            // If an error occurs (e.g., the file doesn't exist), print an error message
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    // Convert the image to grayscale (black and white)
    private static BufferedImage convertToGrayscale(BufferedImage image) {
        // Create a new image in grayscale format
        BufferedImage grayImage = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_GRAY);
        // Draw the original image onto the grayscale image (automatically converts to grayscale)
        grayImage.getGraphics().drawImage(image, 0, 0, null);
        return grayImage;
    }

    // Resize the image to the specified width, maintaining its aspect ratio
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
        // Calculate the target height to maintain the aspect ratio
        int targetHeight = (int) ((double) originalImage.getHeight() / originalImage.getWidth() * targetWidth);

        // Create a new resized image
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());
        resizedImage.getGraphics().drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        return resizedImage;
    }

    // Convert the grayscale image into ASCII art
    private static String generateAsciiArt(BufferedImage grayImage) {
        StringBuilder asciiArt = new StringBuilder(); // String to hold the ASCII art

        // Loop through each pixel in the image
        for (int y = 0; y < grayImage.getHeight(); y++) {
            for (int x = 0; x < grayImage.getWidth(); x++) {
                // Get the color of the current pixel
                Color pixelColor = new Color(grayImage.getRGB(x, y));
                // Use the red value (since it's grayscale, red, green, and blue are the same)
                int grayValue = pixelColor.getRed();

                // Map the gray value to an index in the ASCII_CHARS string
                int index = (grayValue * (ASCII_CHARS.length() - 1)) / 255;

                // Append the corresponding ASCII character to the string
                asciiArt.append(ASCII_CHARS.charAt(index));
            }
            asciiArt.append("\n"); // Add a newline after each row of pixels
        }

        return asciiArt.toString(); // Return the full ASCII art
}

}
