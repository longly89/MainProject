package camera;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamResolution;


public class TakePicture {
	//66 x 120 in
	public static void main(String[] agrs) {
		try {
			ArrayList<Webcam> web = new ArrayList<Webcam>();
			ArrayList<Webcam> webcam = new ArrayList<Webcam> (Webcam.getWebcams());
			
			for (Webcam cam: webcam) {
				cam.setCustomViewSizes(new Dimension[] { WebcamResolution.HD720.getSize() });
				cam.setViewSize(WebcamResolution.HD720.getSize());
				//if (cam.getName().toLowerCase().contains("logitech"))
					web.add(cam);
			}
			
			for (Webcam cam: web) {
				cam.open();
			}
			
			int j = 0;
				for (int i = 0; i < web.size(); i++ ) {
					j++;
					BufferedImage image = webcam.get(i).getImage();
					ImageIO.write(image, "jpg", new File("Pic " + j + ".jpg"));
				}
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
