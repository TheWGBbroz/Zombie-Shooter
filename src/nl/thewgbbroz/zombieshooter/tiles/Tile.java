package nl.thewgbbroz.zombieshooter.tiles;

import java.awt.image.BufferedImage;

import nl.thewgbbroz.zombieshooter.ImageLoader;

/**
 * @author Wouter Gritter
 * 
 * Copyright 2017 Wouter Gritter
 */
public class Tile {
	private static Tile[] byId = new Tile[256];
	
	public static Tile getById(int id) {
		return byId[id];
	}
	
	public static final int SIZE = 32;
	
	public static final Tile WOOD = new Tile(1, ImageLoader.getImage("tiles/wood.png"), false, true);
	public static final Tile WALL = new Tile(2, ImageLoader.getImage("tiles/wall.png"), true, true);
	
	
	private int id;
	private BufferedImage img;
	private boolean solid;
	private boolean canRotate;
	
	protected Tile(int id, BufferedImage img, boolean solid, boolean canRotate) {
		this.id = id;
		this.img = img;
		this.solid = solid;
		this.canRotate = canRotate;
		
		byId[id] = this;
	}
	
	public BufferedImage getImage() {
		return img;
	}
	
	public boolean isSolid() {
		return solid;
	}
	
	public boolean canRotate() {
		return canRotate;
	}
	
	public int getId() {
		return id;
	}
}
