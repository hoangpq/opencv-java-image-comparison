package org.example;

import org.example.utils.Comparison;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import org.opencv.core.Point;

/**
 * Hello world!
 */
public class App {
    static {
        String currentDirectory = System.getProperty("user.dir");
        System.load(currentDirectory + "/libs/libopencv_java440.so");
    }

    private static void test() throws IOException {
        BufferedImage image_1 = ImageIO.read(new File("assets/img3.jpg"));
        BufferedImage image_2 = ImageIO.read(new File("assets/img4.jpg"));

        try {
            double hit = Comparison.compare_image(image_1, image_2);
            System.out.println(hit);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static final int ALPHA_SLIDER_MAX = 100;
    private int alphaVal = 0;
    private final Mat matImgSrc1;
    private final Mat matImgSrc2;
    private final Mat matImgDst = new Mat();
    private JFrame frame;
    private JLabel imgLabel;

    public App() {
        //! [load]
        String imagePath1 = "assets/img3.jpg";
        String imagePath2 = "assets/img4.jpg";

        matImgSrc1 = Imgcodecs.imread(imagePath1);
        matImgSrc2 = Imgcodecs.imread(imagePath2);
        //! [load]
        if (matImgSrc1.empty()) {
            System.out.println("Empty image: " + imagePath1);
            System.exit(0);
        }
        if (matImgSrc2.empty()) {
            System.out.println("Empty image: " + imagePath2);
            System.exit(0);
        }

        //! [window]
        // Create and set up the window.
        frame = new JFrame("Linear Blend");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Set up the content pane.
        Image img = HighGui.toBufferedImage(matImgSrc2);
        addComponentsToPane(frame.getContentPane(), img);
        // Use the content pane's default BorderLayout. No need for
        // setLayout(new BorderLayout());
        // Display the window.
        frame.pack();
        frame.setVisible(true);
        //! [window]
    }

    private void addComponentsToPane(Container pane, Image img) {
        if (!(pane.getLayout() instanceof BorderLayout)) {
            pane.add(new JLabel("Container doesn't use BorderLayout!"));
            return;
        }

        JPanel sliderPanel = new JPanel();
        sliderPanel.setLayout(new BoxLayout(sliderPanel, BoxLayout.PAGE_AXIS));

        //! [create_trackbar]
        sliderPanel.add(new JLabel(String.format("Alpha x %d", ALPHA_SLIDER_MAX)));
        JSlider slider = new JSlider(0, ALPHA_SLIDER_MAX, 0);
        slider.setMajorTickSpacing(20);
        slider.setMinorTickSpacing(5);
        slider.setPaintTicks(true);
        slider.setPaintLabels(true);
        //! [create_trackbar]
        //! [on_trackbar]
        slider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                JSlider source = (JSlider) e.getSource();
                alphaVal = source.getValue();
                update();
            }
        });
        //! [on_trackbar]
        sliderPanel.add(slider);

        pane.add(sliderPanel, BorderLayout.PAGE_START);
        imgLabel = new JLabel(new ImageIcon(img));
        pane.add(imgLabel, BorderLayout.CENTER);
    }

    private void update() {
        double alpha = alphaVal / (double) ALPHA_SLIDER_MAX;
        double beta = 1.0 - alpha;
        Core.addWeighted(matImgSrc1, alpha, matImgSrc2, beta, 0, matImgDst);
        Image img = HighGui.toBufferedImage(matImgDst);
        imgLabel.setIcon(new ImageIcon(img));
        frame.repaint();
    }

    private static void templateMatching() {
        Mat source = null;
        Mat template = null;

        source = Imgcodecs.imread("assets/img1.jpg");
        template = Imgcodecs.imread("assets/object.png");

        Mat outputImage = new Mat();
        int machMethod = Imgproc.TM_CCOEFF;
        //Template matching method
        Imgproc.matchTemplate(source, template, outputImage, machMethod);

        Core.MinMaxLocResult mmr = Core.minMaxLoc(outputImage);
        Point matchLoc = mmr.maxLoc;

        //Draw rectangle on result image
        Imgproc.rectangle(source, matchLoc, new Point(matchLoc.x + template.cols(),
                matchLoc.y + template.rows()), new Scalar(255, 255, 255));

        Imgcodecs.imwrite("dist/match.jpg", source);
        System.out.println("Completed.");
    }

    public static void main(String[] args) throws Exception {
        // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        /*javax.swing.SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new App();
            }
        });*/
        templateMatching();
    }
}
