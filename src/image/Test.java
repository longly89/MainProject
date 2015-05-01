package image;

import java.awt.image.BufferedImage;
import java.awt.image.RescaleOp;
import java.io.File;

import javax.imageio.ImageIO;

public class Test {
	
	public static void main(String []agrs) {
		try {
			File img = new File("Test 11.jpg");
			BufferedImage inp = null;
			inp = ImageIO.read(img);
			RescaleOp rescaleOp = new RescaleOp(1.8f, -120, null);
			rescaleOp.filter(inp, inp);  // Source and destination are the same.
			File op = new File("image.jpg");
			ImageIO.write(inp, "jpg", op);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
