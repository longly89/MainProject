package camera;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;



public class ImageCombine {

	public static void main(String[] agrs) {
		try {
			BufferedImage image1 = ImageIO.read(new File("test1.jpg"));
			BufferedImage image2 = ImageIO.read(new File("test2.jpg"));
			BufferedImage combine = new BufferedImage(image1.getWidth() + image2.getWidth(), image1.getHeight(), BufferedImage.TYPE_INT_BGR);
			
			Graphics g = combine.getGraphics();
			g.drawImage(image1, 0, 0, null);
			g.drawImage(image2, image1.getWidth(), 0, null);
			
			ImageIO.write(combine, "bmp", new File("combine.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
