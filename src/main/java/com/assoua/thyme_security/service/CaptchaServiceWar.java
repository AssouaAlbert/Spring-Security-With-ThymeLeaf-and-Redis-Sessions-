package com.assoua.thyme_security.service;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Random;

@Service
public class CaptchaServiceWar {
    public String generateCaptchaText() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        Random random = new Random();
        StringBuilder captchaText = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            captchaText.append(chars.charAt(random.nextInt(chars.length())));
        }
        return captchaText.toString();
    }
    public void generateCaptchaImage(String captchaText, HttpServletResponse response) throws IOException {
        int width = 150;
        int height = 50;
        BufferedImage bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = bufferedImage.createGraphics();
        g2d.setColor(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
        g2d.setFont(new Font("Arial", Font.BOLD, 24));
        g2d.setColor(Color.BLACK);
        g2d.drawString(captchaText, 25, 35);
        g2d.setColor(Color.BLACK);
        Random random = new Random();
        for (int i = 0; i < 15; i++) {
            int x1 = random.nextInt(width);
            int y1 = random.nextInt(height);
            int x2 = random.nextInt(width);
            int y2 = random.nextInt(height);
            g2d.drawLine(x1, y1, x2, y2);
        }
        g2d.dispose();
        response.setContentType("image/jpeg");
        ImageIO.write(bufferedImage, "jpeg", response.getOutputStream());
    }
}
