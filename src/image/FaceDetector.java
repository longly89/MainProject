package image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class FaceDetector {
	//1677 x 3048
	//720 x 1280
    public static void main(String[] args) {
    	 
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Highgui.imread("Pic 1.jpg");
        Mat HSV = new Mat(), threshold = new Mat();
        
        Imgproc.cvtColor(image, HSV, Imgproc.COLOR_BGR2HSV);
        //RED Core.inRange(HSV, new Scalar(150,100,60), new Scalar(179, 255, 255), threshold);
        //BLUE Core.inRange(HSV, new Scalar(100,30,200), new Scalar(140, 255, 255), threshold);
        //GREEN Core.inRange(HSV, new Scalar(50,70,150), new Scalar(60, 100, 255), threshold);
        //GREEN 2 Core.inRange(HSV, new Scalar(50,70,150), new Scalar(90, 200, 255), threshold);
        //BLUE 2 
        Core.inRange(HSV, new Scalar(100,90,230), new Scalar(140, 120, 255), threshold);
        
        Highgui.imwrite("output.png", threshold);
        image = Highgui.imread("output.png");
        for (int i = 0; i < image.rows(); i++) 
        	for (int j = 0; j < image.cols(); j++) {
        		double[] rgb = image.get(i, j);
        		double red = rgb[0];
        		double green = rgb[1];
        		double blue = rgb[2];
        		
        		if (red == 255.0 && green == 255.0 && blue == 255.0)
        			System.out.println(i + " " + j);
        	}
        System.out.println(image.rows() + " " + image.cols());
        
    }
}
