package nl.thewgbbroz.zombieshooter.items;

import java.awt.image.BufferedImage;

import nl.thewgbbroz.zombieshooter.ImageLoader;

/**
 * @author Wouter Gritter
 * 
 * Copyright 2017 Wouter Gritter
 */
public class Item {
	public static final Item GUN = new Item(ImageLoader.getImage("gun.png"));
	
	private BufferedImage image;
	
	protected Item(BufferedImage image) {
		this.image = image;
	}
	
	public BufferedImage getImage() {
		return image;
	}
}
