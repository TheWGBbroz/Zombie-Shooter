package nl.thewgbbroz.zombieshooter.tiles;

import java.awt.Graphics2D;
import java.util.Random;

import nl.thewgbbroz.zombieshooter.Camera;
import nl.thewgbbroz.zombieshooter.Game;

/**
 * @author Wouter Gritter
 * 
 * Copyright 2017 Wouter Gritter
 */
public class TileMap {
	private final int rows, cols;
	private final Tile[] tiles;
	
	public TileMap(int rows, int cols) {
		this.rows = rows;
		this.cols = cols;
		this.tiles = new Tile[rows * cols];
	}
	
	public TileMap(Tile[] tiles, int rows, int cols) {
		if(tiles.length != rows * cols) {
			throw new IllegalArgumentException("tiles.length must be equal to rows * cols!");
		}
		
		this.rows = rows;
		this.cols = cols;
		this.tiles = tiles;
	}
	
	public TileMap(int[] tiles, int rows, int cols) {
		if(tiles.length != rows * cols) {
			throw new IllegalArgumentException("tiles.length must be equal to rows * cols!");
		}
		
		this.rows = rows;
		this.cols = cols;
		this.tiles = new Tile[rows * cols];
		
		for(int i = 0; i < tiles.length; i++) {
			this.tiles[i] = Tile.getById(tiles[i]);
		}
	}
	
	public void render(Graphics2D g, Camera cam) {
		for(int c = 0; c < cols; c++) {
			int y = c * Tile.SIZE - cam.getYI();
			
			if(y < -Tile.SIZE || y > Game.HEIGHT)
				continue;
			
			for(int r = 0; r < rows; r++) {
				int x = r * Tile.SIZE - cam.getXI();
				
				if(x < -Tile.SIZE || x > Game.WIDTH)
					continue;
				
				Tile t = tiles[r + c * rows];
				if(t == null)
					continue;
				
				double rot = 0;
				if(t.canRotate()) {
					int rotId = new Random(r * 137840823 + c * 12349719).nextInt(4);
					switch(rotId) {
					case 1:
						rot = Math.PI * 0.5;
						break;
					case 2:
						rot = Math.PI * 1.0;
						break;
					case 3:
						rot = Math.PI * 1.5;
						break;
					}
					
					g.rotate(rot, x + Tile.SIZE / 2, y + Tile.SIZE / 2);
				}
				
				g.drawImage(t.getImage(), x, y, Tile.SIZE, Tile.SIZE, null);
				
				if(t.canRotate())
					g.rotate(-rot, x + Tile.SIZE / 2, y + Tile.SIZE / 2);
			}
		}
	}
	
	public Tile getTile(int r, int c) {
		return tiles[r + c * rows];
	}
	
	public void setTile(int r, int c, Tile t) {
		tiles[r + c * rows] = t;
	}
	
	public int getRows() {
		return rows;
	}
	
	public int getCols() {
		return cols;
	}
}
