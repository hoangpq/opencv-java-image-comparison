package org.example;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;

/**
 * Template Matching
 */
public class App {
    static {
        nu.pattern.OpenCV.loadShared();
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
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
    }

    public static void main(String[] args) throws Exception {
        templateMatching();
    }
}
