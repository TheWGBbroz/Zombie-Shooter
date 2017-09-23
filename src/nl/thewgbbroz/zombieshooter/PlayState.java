package nl.thewgbbroz.zombieshooter;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import nl.thewgbbroz.zombieshooter.entities.Entity;
import nl.thewgbbroz.zombieshooter.entities.EntityItem;
import nl.thewgbbroz.zombieshooter.entities.EntityZombie;
import nl.thewgbbroz.zombieshooter.input.KeyHandler;
import nl.thewgbbroz.zombieshooter.items.Item;
import nl.thewgbbroz.zombieshooter.tiles.Tile;
import nl.thewgbbroz.zombieshooter.tiles.TileMap;
import nl.thewgbbroz.zombieshooter.world.World;

/**
 * @author Wouter Gritter
 * 
 * Copyright 2017 Wouter Gritter
 */
public class PlayState {
	private World world;
	private int tick;
	private boolean dead;
	
	public PlayState() {
		reset();
	}
	
	private void reset() {
		int[] tiles = {
			2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
			2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 1, 1, 1, 2,
			2, 1, 1, 1, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2,
			2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2,
		};
		
		TileMap tm = new TileMap(tiles, 16, 16);
		
		world = new World(tm);
		
		tick = 0;
		dead = false;
	}
	
	public void update() {
		if(!dead) {
			world.update();
			
			if(tick % (Game.TPS * 20) == 0 && !isGunInWorld()) {
				world.addEntity(new EntityItem(world, 180, 50, 0, Item.GUN));
			}
			
			if(Game.RAND.nextDouble() < 0.01) {
				spawnZombie();
			}
			
			if(!world.getPlayer().isAlive()) {
				// Player just died!
				dead = true;
				tick = 0;
			}
		}else{
			if(KeyHandler.isJustPressed(KeyEvent.VK_SPACE)) {
				reset();
			}
		}
		
		tick++;
	}
	
	public void render(Graphics2D g) {
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, Game.WIDTH, Game.HEIGHT);
		
		world.render(g);
		
		g.drawString("SCORE: " + world.getPlayer().getScore(), 5, 15);
		g.drawString("HEALTH: " + (int) world.getPlayer().getHP(), 80, 15);
		g.drawString("AMMO: " + world.getPlayer().getAmmo(), 175, 15);
		
		if(dead) {
			g.drawString("YOU DIED!", Game.WIDTH / 2 - 29, Game.HEIGHT / 2);
			g.drawString("Press space to restart", Game.WIDTH / 2 - 60, Game.HEIGHT / 2 + 15);
		}
	}
	
	private boolean isGunInWorld() {
		for(Entity en : world.getEntities()) {
			if(en instanceof EntityItem && ((EntityItem) en).getItem() == Item.GUN)
				return true;
		}
		
		return false;
	}
	
	private void spawnZombie() {
		int r, c;
		
		do{
			r = Game.RAND.nextInt(world.getTileMap().getRows());
			c = Game.RAND.nextInt(world.getTileMap().getCols());
		}while(world.getTileMap().getTile(r, c).isSolid());
		
		world.addEntity(new EntityZombie(world, r * Tile.SIZE, c * Tile.SIZE, 0));
	}
}
