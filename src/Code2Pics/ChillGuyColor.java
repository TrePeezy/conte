package Code2Pics;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class ChillGuyColor {

    private static final String ASCII_CHARS = "@%#*+=-:. ";

    // Set the desired width of the ASCII art (adjust for your terminal size)
    private static final int ASCII_WIDTH = 60; // TODO: adjust size here

    public static void main(String[] args) {
        // Image file path (change this to your own image path)
        String imagePath = "/Users/kentre/Desktop/chillGuy.jpeg"; // TODO: add pic filepath here .png or .jpeg

        try {
            // Load the image from the file path
            BufferedImage image = ImageIO.read(new File(imagePath));

            // Resize the image to fit the desired width while maintaining aspect ratio
            BufferedImage resizedImage = resizeImage(image, ASCII_WIDTH);

            // Generate colored ASCII art from the resized image
            String coloredAsciiArt = generateColoredAsciiArt(resizedImage);

            // Print the colored ASCII art to the console
            System.out.println(coloredAsciiArt);

        } catch (IOException e) {
            // Print error message if there's a problem loading the image
            System.err.println("Error loading image: " + e.getMessage());
        }
    }

    // Resize the image to fit the given width while maintaining the aspect ratio
    private static BufferedImage resizeImage(BufferedImage originalImage, int targetWidth) {
        // Calculate the new height to keep the aspect ratio
        int targetHeight = (int) ((double) originalImage.getHeight() / originalImage.getWidth() * targetWidth);

        // Create a new image with the calculated width and height
        BufferedImage resizedImage = new BufferedImage(targetWidth, targetHeight, originalImage.getType());

        // Resize the image by drawing it with the new dimensions
        Graphics2D g2d = resizedImage.createGraphics();
        g2d.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g2d.dispose(); // Release resources used for resizing

        return resizedImage;
    }

    // Generate colored ASCII art from the resized image
    private static String generateColoredAsciiArt(BufferedImage image) {
        StringBuilder asciiArt = new StringBuilder();

        // Loop through every pixel in the image
        for (int y = 0; y < image.getHeight(); y++) {
            for (int x = 0; x < image.getWidth(); x++) {
                // Get the color of the current pixel
                Color pixelColor = new Color(image.getRGB(x, y));

                // Get the ASCII character corresponding to the pixel's brightness
                char asciiChar = getAsciiCharacterFromBrightness(pixelColor);

                // Get the colored ASCII character (using ANSI escape codes for color)
                String coloredChar = getColoredText(asciiChar, pixelColor);

                // Add the colored ASCII character to the string
                asciiArt.append(coloredChar);
            }
            // Add a new line after each row of characters
            asciiArt.append("\n");
        }

        return asciiArt.toString();
    }

    // Convert the pixel's brightness to an ASCII character
    private static char getAsciiCharacterFromBrightness(Color color) {
        // Calculate the grayscale value from the RGB color (weighted average)
        int grayValue = (int) (0.299 * color.getRed() + 0.587 * color.getGreen() + 0.114 * color.getBlue());

        // Map the grayscale value (0-255) to an ASCII character (from dark to light)
        int index = (grayValue * (ASCII_CHARS.length() - 1)) / 255;
        return ASCII_CHARS.charAt(index); // Return the corresponding ASCII character
    }

    // Generate the ANSI escape code for colored text
    private static String getColoredText(char asciiChar, Color color) {
        // Create the ANSI escape sequence to change the text color
        String colorCode = String.format("\033[38;2;%d;%d;%dm", color.getRed(), color.getGreen(), color.getBlue());

        // Return the colored character followed by a reset escape code to avoid affecting other text
        return colorCode + asciiChar + "\033[0m";
    }
}

