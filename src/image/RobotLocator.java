package image;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class RobotLocator {
	//1677 x 3048
	//720 x 1280
	
	private static Mat HLS;
	
	//convert row and column in pixel to block
	private static int[] getBlock(int r, int c) {
		int rr = r / 80;
		int cc = c / 80;
		if (rr * 80 < r)
			rr++;
		if (cc * 80 < c)
			cc++;
		
		return new int[] {rr, cc};
	}
	
	private static int[] getBlue() {
		Mat threshold = new Mat();
        //Core.inRange(HLS, new Scalar(100,90,220), new Scalar(160, 150, 255), threshold);
		Core.inRange(HLS, new Scalar(100,100,130), new Scalar(120, 150, 180), threshold);
        
        Highgui.imwrite("output.png", threshold);
        Mat image = Highgui.imread("output.png");
        int count = 0, sumi = 0, sumj = 0;
        for (int i = 0; i < image.rows(); i++) 
        	for (int j = 0; j < image.cols(); j++) {
        		double[] rgb = image.get(i, j);
        		double red = rgb[0];
        		double green = rgb[1];
        		double blue = rgb[2];
        		
        		if (red == 255.0 && green == 255.0 && blue == 255.0) {
//        			System.out.println(i + " " + j);
        			sumi += i;
        			sumj += j;
        			count++;
        		}
        	}
        return new int[] {sumi / count, sumj / count};
	}

	private static int[] getGreen() {
		Mat threshold = new Mat();
		//Core.inRange(HLS, new Scalar(50,70,150), new Scalar(70, 200, 255), threshold);
		Core.inRange(HLS, new Scalar(50,50,50), new Scalar(70, 120, 100), threshold);
        
        Highgui.imwrite("output.png", threshold);
        Mat image = Highgui.imread("output.png");
        int count = 0, sumi = 0, sumj = 0;
        for (int i = 0; i < image.rows(); i++) 
        	for (int j = 0; j < image.cols(); j++) {
        		double[] rgb = image.get(i, j);
        		double red = rgb[0];
        		double green = rgb[1];
        		double blue = rgb[2];
        		
        		if (red == 255.0 && green == 255.0 && blue == 255.0) {
//        			System.out.println(i + " " + j);
        			sumi += i;
        			sumj += j;
        			count++;
        		}
        	}
        return new int[] {sumi / count, sumj / count};
	}

	public static void main(String[] args) {
    	 
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Highgui.imread("image.jpg");
        HLS = new Mat();
        
        Imgproc.cvtColor(image, HLS, Imgproc.COLOR_BGR2HLS);
        //RED Core.inRange(HLS, new Scalar(150,100,60), new Scalar(179, 255, 255), threshold);
        //BLUE Core.inRange(HLS, new Scalar(100,30,200), new Scalar(140, 255, 255), threshold);
        //GREEN Core.inRange(HLS, new Scalar(50,70,150), new Scalar(60, 100, 255), threshold);
        //GREEN 2 Core.inRange(HLS, new Scalar(50,70,150), new Scalar(90, 200, 255), threshold);
        //BLUE 2 Core.inRange(HLS, new Scalar(100,90,230), new Scalar(140, 120, 255), threshold);
        
        
        int[] green = getGreen();
        /*
        int[] blue = getBlue();
        int[] robot = {(blue[0] + green[0]) / 2, (blue[1] + green[1]) / 2};
        System.out.println(robot[0] + " " + robot[1]);
        int[] loc = getBlock(robot[0], robot[1]);
        System.out.printf("%d\t%d\n", loc[0] + 1, loc[1] + 1);
        */
    }
}
