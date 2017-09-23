package nl.thewgbbroz.zombieshooter.entities;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import nl.thewgbbroz.zombieshooter.Camera;
import nl.thewgbbroz.zombieshooter.Game;
import nl.thewgbbroz.zombieshooter.Utils;
import nl.thewgbbroz.zombieshooter.ai.AI;
import nl.thewgbbroz.zombieshooter.tiles.Tile;
import nl.thewgbbroz.zombieshooter.world.World;

public abstract class Entity {
	protected final World world;
	protected double x, y, speed, rot;
	protected BufferedImage img;
	protected boolean hasTileCollision = true;
	
	protected double targetSpeed;
	protected double speedLerpFactor = 0.1;
	
	protected double targetRot;
	protected double rotLerpFactor = 0.06;
	
	protected AI ai = null;
	
	protected int ticksAlive = 0;
	
	private boolean remove = false;
	private Rectangle rect;
	
	public Entity(World world, double x, double y, double rot, BufferedImage img) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.rot = this.targetRot = rot;
		this.img = img;
		
		rect = new Rectangle((int) x, (int) y, getWidth(), getHeight());
	}
	
	public void update() {
		ticksAlive++;
		
		if(ai != null)
			ai.update();
		
		this.speed = Utils.lerp(speed, targetSpeed, speedLerpFactor);
		this.rot = Utils.lerpRot(rot, targetRot, rotLerpFactor);
		
		if(hasTileCollision) {
			int[] stuckCoordinates = getCollisionTileCoordinates(x, y);
			if(stuckCoordinates != null) {
				// Apply force to move away from tile
				int tileCenterX = stuckCoordinates[0] * Tile.SIZE + Tile.SIZE / 2;
				int tileCenterY = stuckCoordinates[1] * Tile.SIZE + Tile.SIZE / 2;
				
				double selfCenterX = x + getWidth() / 2;
				double selfCenterY = y + getHeight() / 2;
				
				double negRot = Math.atan2(selfCenterY - tileCenterY, selfCenterX - tileCenterX);
				this.x += Math.cos(negRot) * 0.1;
				this.y += Math.sin(negRot) * 0.1;
			}
		}
		
		double xa = Math.cos(rot) * speed;
		double ya = Math.sin(rot) * speed;
		
		if((hasTileCollision && !checkCollision(x + xa, y)) || !hasTileCollision)
			x += xa;
		
		if((hasTileCollision && !checkCollision(x, y + ya)) || !hasTileCollision)
			y += ya;
		
		rect.setBounds((int) x, (int) y, getWidth(), getHeight());
	}
	
	public void render(Graphics2D g, Camera cam) {
		int sx = (int) x - cam.getXI();
		int sy = (int) y - cam.getYI();
		
		if(sx < -getWidth() || sy < -getHeight() || sx > Game.WIDTH || sy > Game.HEIGHT)
			return;
		
		g.rotate(rot, sx + img.getWidth() / 2, sy + img.getHeight() / 2);
		g.drawImage(img, sx, sy, null);
		g.rotate(-rot, sx + img.getWidth() / 2, sy + img.getHeight() / 2);
		
		//g.setColor(Color.BLACK);
		//g.drawRect(rect.x - cam.getXI(), rect.y - cam.getYI(), rect.width, rect.height);
	}
	
	protected int[] getCollisionTileCoordinates(double newX, double newY) {
		int minRow = (int) (newX / Tile.SIZE - Math.ceil((double) getWidth() / (double) Tile.SIZE));
		int maxRow = (int) (newX / Tile.SIZE + Math.ceil((double) getWidth() / (double) Tile.SIZE));
		
		int minCol = (int) (newY / Tile.SIZE - Math.ceil((double) getHeight() / (double) Tile.SIZE));
		int maxCol = (int) (newY / Tile.SIZE + Math.ceil((double) getHeight() / (double) Tile.SIZE));
		
		for(int c = minCol; c <= maxCol; c++) {
			if(c < 0 || c >= world.getTileMap().getCols())
				continue;
			
			for(int r = minRow; r <= maxRow; r++) {
				if(r < 0 || r >= world.getTileMap().getRows())
					continue;
				
				Tile t = world.getTileMap().getTile(r, c);
				if(t != null && t.isSolid()) {
					Rectangle tileRect = new Rectangle(r * Tile.SIZE, c * Tile.SIZE, Tile.SIZE, Tile.SIZE);
					if(rect.intersects(tileRect)) {
						return new int[] {r, c};
					}
				}
			}
		}
		
		return null;
	}
	
	protected boolean checkCollision(double newX, double newY) {
		return getCollisionTileCoordinates(newX, newY) != null;
	}
	
	public int getRenderOrder() {
		return 1;
	}
	
	public World getWorld() {
		return world;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getRot() {
		return rot;
	}
	
	public double getTargetRot() {
		return targetRot;
	}
	
	public void setX(double x) {
		this.x = x;
	}
	
	public void setY(double y) {
		this.y = y;
	}
	
	public void setRot(double rot) {
		this.rot = rot;
		this.targetRot = rot;
	}
	
	public void setTargetRot(double targetRot) {
		this.targetRot = targetRot;
	}
	
	public int getWidth() {
		return img.getWidth();
	}
	
	public int getHeight() {
		return img.getHeight();
	}
	
	public AI getAI() {
		return ai;
	}
	
	public boolean hasAI() {
		return ai != null;
	}
	
	public void setAI(AI ai) {
		this.ai = ai;
	}
	
	public int getTicksAlive() {
		return ticksAlive;
	}
	
	public void remove() {
		remove = true;
	}
	
	public boolean isRemoved() {
		return remove;
	}
	
	public boolean contains(int x, int y) {
		return rect.contains(x, y);
	}
	
	public boolean intersects(Entity en) {
		return rect.intersects(en.rect);
	}
	
	public double distSq(double xx, double yy) {
		return Utils.distSq(this.x + getWidth() / 2, this.y + getHeight() / 2, xx, yy);
	}
	
	public double distSq(Entity en) {
		return distSq(en.x + en.getWidth() / 2, en.y + en.getHeight() / 2);
	}
	
	public double dist(double xx, double yy) {
		return Utils.dist(this.x + getWidth() / 2, this.y + getHeight() / 2, xx, yy);
	}
	
	public double dist(Entity en) {
		return dist(en.x + en.getWidth() / 2, en.y + en.getHeight() / 2);
	}
}
