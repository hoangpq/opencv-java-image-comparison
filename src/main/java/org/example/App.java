package org.example;

import org.example.utils.Comparison;
import org.opencv.core.Core;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

/**
 * Hello world!
 */
public class App {
    static {
        String currentDirectory = System.getProperty("user.dir");
        System.load(currentDirectory + "/libs/libopencv_java440.so");
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Hello World!");
        BufferedImage image_1 = ImageIO.read(new File("assets/img1.jpg"));
        BufferedImage image_2 = ImageIO.read(new File("assets/img3.jpg"));

        try {
            double hit = Comparison.compare_image(image_1, image_2);
            System.out.println(hit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
