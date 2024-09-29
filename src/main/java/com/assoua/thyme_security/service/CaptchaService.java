package com.assoua.thyme_security.service;


import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;

import jakarta.servlet.http.HttpServletResponse;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Service
public class CaptchaService {

    public String generateCaptchaText() {
        // Generates a random CAPTCHA text (e.g., 6 alphanumeric characters)
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        // Object used to generate pseudo-random numbers from the chars string
        Random random = new Random();
        // Construct and manipulate string
        StringBuilder captchaText = new StringBuilder();
        for (int i = 0; i < 6; i++) {

            // Picks a random charter in the at position 0-chars.length()
            // Get the char at the position in string
            // Use string builder to construct string
            /*
            StringBuilder is preferred over concatenating strings using the + operator
             inside loops because it’s more efficient in terms of memory and performance.
              Each time you modify a String in Java, a new object is created since String
               is immutable. StringBuilder avoids this by modifying the same object in memory.
             */
            captchaText.append(chars.charAt(random.nextInt(chars.length())));
        }
        return captchaText.toString();
    }

    // Generates a CAPTCHA image and writes it to the HTTP response
    public void generateCaptchaImage(String captchaText, HttpServletResponse response) throws IOException {
        int width = 150;
        int height = 50;

        // Create a buffered image in memory
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        // Draw 2d shape on buffer image by calling createGraphics()
        Graphics2D g2d = bufferedImage.createGraphics();

        // Set background color
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);

        // Draw the CAPTCHA text on the image
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(Color.BLACK);
        g2d.drawString(captchaText, 25, 35);

        // Add some noise (optional)
        g2d.setColor(Color.GRAY);
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2d.drawLine(x1, y1, x2, y2);
        }

        g2d.dispose();

        // Set response content type to image/jpeg
        response.setContentType("image/jpeg");
        // Write the image to the HTTP response
        ImageIO.write(bufferedImage, "jpeg", response.getOutputStream());
    }

}
