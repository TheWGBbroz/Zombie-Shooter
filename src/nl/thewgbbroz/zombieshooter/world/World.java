package nl.thewgbbroz.zombieshooter.world;

import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import nl.thewgbbroz.zombieshooter.Camera;
import nl.thewgbbroz.zombieshooter.Game;
import nl.thewgbbroz.zombieshooter.entities.Entity;
import nl.thewgbbroz.zombieshooter.entities.EntityPlayer;
import nl.thewgbbroz.zombieshooter.tiles.TileMap;

public class World {
	private List<Entity> entities = new ArrayList<>();
	private EntityPlayer player;
	
	private List<Entity> spawnQueue = new ArrayList<>();
	
	private TileMap tm;
	
	private Camera cam;
	
	public World(TileMap tm) {
		this.tm = tm;
		
		cam = new Camera();
		
		player = new EntityPlayer(this, 50, 50, 0);
		addEntity(player);
	}
	
	public void update() {
		if(!spawnQueue.isEmpty()) {
			for(Entity en : spawnQueue) {
				entities.add(en);
			}
			spawnQueue.clear();
			
			Collections.sort(entities, new Comparator<Entity>() {
				@Override
				public int compare(Entity o1, Entity o2) {
					return o1.getRenderOrder() - o2.getRenderOrder();
				}
			});
		}
		
		for(int i = 0; i < entities.size(); i++) {
			Entity en = entities.get(i);
			en.update();
			
			if(en.isRemoved()) {
				entities.remove(i--);
			}
		}
		
		player.updateInput();
	}
	
	public void render(Graphics2D g) {
		cam.setX(player.getX() - Game.WIDTH / 2 + player.getWidth() / 2);
		cam.setY(player.getY() - Game.HEIGHT / 2 + player.getHeight() / 2);
		
		tm.render(g, cam);
		
		for(Entity en : entities) {
			en.render(g, cam);
		}
	}
	
	public TileMap getTileMap() {
		return tm;
	}
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	public List<Entity> getEntities() {
		return Collections.unmodifiableList(entities);
	}
	
	public void addEntity(Entity en) {
		if(en.getWorld() != this) {
			throw new IllegalArgumentException("entity.getWorld() must return this world object!");
		}
		
		//entities.add(en);
		spawnQueue.add(en);
	}
	
	public Camera getCamera() {
		return cam;
	}
}
