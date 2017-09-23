package nl.thewgbbroz.zombieshooter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

public class ImageLoader {
	private static Map<String, BufferedImage> images = new HashMap<>();
	
	public static BufferedImage getImage(String name) {
		if(images.containsKey(name)) {
			return images.get(name);
		}
		
		try{
			BufferedImage img = ImageIO.read(new File("res/" + name));
			images.put(name, img);
			return img;
		}catch(IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
