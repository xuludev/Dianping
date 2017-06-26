package cn.lucasx.util;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;

/**
 * Created by LucasX on 2016/4/27.
 */
public class Captcha {

    private static final int WIDTH = 100;
    private static final int HEIGHT = 40;

    public String captchaCode() {
        char[] alphabetLowerCase = new char[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j',
                'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z'};
        int[] numbers = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

        int ranNum1 = (int) (Math.random() * alphabetLowerCase.length);
        int ranNum2 = (int) (Math.random() * numbers.length);
        int ranNum3 = (int) (Math.random() * alphabetLowerCase.length);
        int ranNum4 = (int) (Math.random() * numbers.length);

        String captchaStr = String.valueOf(alphabetLowerCase[ranNum1]) + String.valueOf
                (numbers[ranNum2]) + String.valueOf(alphabetLowerCase[ranNum3]) +
                String.valueOf(numbers[ranNum4]);

        System.out.println(captchaStr);

        return captchaStr;
    }

    public void captchaImage(String captchaCode, String imgPath) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bufferedImage.createGraphics();
        graphics2D.setColor(Color.WHITE);
        graphics2D.fillRect(0, 0, WIDTH, HEIGHT);
        graphics2D.drawString(captchaCode, 10, 10);


        OutputStream fileOutputStream = new FileOutputStream(imgPath);
        ImageIO.write(bufferedImage, "png", fileOutputStream);
        bufferedImage.flush();

    }

    public static void main(String[] args) {
        Captcha captcha = new Captcha();
        String captchaCode = captcha.captchaCode();
        try {
            captcha.captchaImage(captchaCode, "D:/captcha.png");
        } catch (IOException e) {
            System.err.println("生成失败...");
            e.printStackTrace();
        }
    }
}
