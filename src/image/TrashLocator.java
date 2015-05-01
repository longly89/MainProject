package image;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Scalar;
import org.opencv.highgui.Highgui;
import org.opencv.imgproc.Imgproc;


public class TrashLocator {
	//1677 x 3048
	//720 x 1280
	private static int move[][] = { {-1, 0}, {1, 0}, {0, -1}, {0, 1}};
	
	private static void printmt (int mt[][]) {
		try {
			BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("mt.txt")));
			String s;
			for (int i[]: mt) {
				for (int j: i) {
					s = String.format("%5d", j);
					bw.write(s);
				}
				bw.newLine();
			}
			bw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static int checkImage(Mat image, int r, int c) {
		double[] rgb = image.get(r, c);
		double red = rgb[0];
		double green = rgb[1];
		double blue = rgb[2];
		
		if ((red == 255.0 && green == 255.0 && blue == 255.0) || (red == 120.0 && green == 20.0 && blue == 120.0))
			return 1;
		else 
			return 0;
	}
	
	private static int getMin(int a, int b) {
		if (a > b)
			return b;
		return a;
	}
	
	private static int getMax (int a, int b) {
		if (a > b) 
			return a;
		return b;
	}
	
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
	
	private static void getSize() {
		//area as pixel
		double[] set = {120,20,120};
        Mat image = Highgui.imread("output.png");
        int[][] mt = new int[image.rows()][image.cols()];
        int maxSize = 1000, r = 0, c = 0;
        for (int i = 1; i < image.rows() - 1; i++) 
        	for (int j = 1; j < image.cols() - 1; j++) {
        		double[] rgb = image.get(i, j);
        		double red = rgb[0];
        		double green = rgb[1];
        		double blue = rgb[2];
        		
        		int value = checkImage(image, i, j);
        		
        		if (red == 255.0 && green == 255.0 && blue == 255.0) {
        			System.out.println(i + " " + j);
        			image.put(i, j, set);
        		}
        		
    			if (checkImage(image, i - 1, j) == 0 && checkImage(image, i, j - 1) == 0) 
    				mt[i][j] = value;
    			else
    				if (getMin(mt[i][j - 1], mt[i - 1][j]) == 0)
    					mt[i][j] = getMax(mt[i][j - 1], mt[i - 1][j]) + value;
    				else
    					mt[i][j] = mt[i][j - 1] + mt[i - 1][j] - mt[i - 1][j - 1] + value;
    			
    			if (mt[i][j] > maxSize && mt[i][j] < 5000) {
    				maxSize = mt[i][j];
    				r = i;
    				c = j;
    			}
        	}
        
        printmt(mt);
        System.out.println(maxSize + " " + r + " " + c);
        
        //find center of object
        ArrayList<Integer> row = new ArrayList<Integer>();
        ArrayList<Integer> col = new ArrayList<Integer>();
        mt = new int[image.rows()][image.cols()];
        int minr = 10000, maxr = 0, minc = 10000, maxc = 0;
        while (true) {
        	for (int m[]: move) {
        		int rr = r + m[0];
        		int cc = c + m[1];
        		if (rr < 0 || rr >= image.rows() || cc < 0 || cc >= image.cols())
        			continue;
        		if (mt[rr][cc] == 0 && checkImage(image, rr, cc) == 1) {
        			row.add(rr);
        			col.add(cc);
        			mt[rr][cc] = 1;
        		}
        	}
        	if (row.size() == 0)
        		break;
        	
        	r = row.get(0);
        	c = col.get(0);
        	
        	if (minr > r)
        		minr = r;
        	if (maxr < r)
        		maxr = r;
        	if (minc > c)
        		minc = c;
        	if (maxc < c)
        		maxc = c;
        	
        	row.remove(0);
        	col.remove(0);
        } 
        Highgui.imwrite("out.png", image);
        System.out.printf("%d\t%d\t%d\t%d\n", minr, maxr, minc, maxc);
        System.out.printf("%d\t%d\n", (maxr + minr) / 2, (maxc + minc) / 2);
        int[] loc = getBlock((maxr + minr) / 2, (maxc + minc) / 2);
        System.out.printf("%d\t%d\n", loc[0] + 1, loc[1] + 1);
	}
	
    public static void main(String[] args) {
    	 
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat image = Highgui.imread("Test 8.jpg");
        Mat HLS = new Mat(), threshold = new Mat();
        
        Imgproc.cvtColor(image, HLS, Imgproc.COLOR_BGR2HLS);
        //Core.inRange(HLS, new Scalar(0,140,40), new Scalar(255, 255, 100), threshold);
        //Core.inRange(HLS, new Scalar(0,100,0), new Scalar(255, 150, 100), threshold);
        Core.inRange(HLS, new Scalar(50,20,0), new Scalar(100, 60, 100), threshold);
        
        Highgui.imwrite("output.png", threshold);
        //getSize();
    }
}
