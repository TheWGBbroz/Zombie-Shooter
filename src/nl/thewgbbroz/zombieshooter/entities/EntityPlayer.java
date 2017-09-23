package nl.thewgbbroz.zombieshooter.entities;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import nl.thewgbbroz.zombieshooter.Game;
import nl.thewgbbroz.zombieshooter.ImageLoader;
import nl.thewgbbroz.zombieshooter.entities.EntityBullet.BulletListener;
import nl.thewgbbroz.zombieshooter.input.KeyHandler;
import nl.thewgbbroz.zombieshooter.items.Item;
import nl.thewgbbroz.zombieshooter.world.World;

/**
 * @author Wouter Gritter
 * 
 * Copyright 2017 Wouter Gritter
 */
public class EntityPlayer extends LivingEntity implements BulletListener {
	private static final BufferedImage PLAYER_NO_GUN = ImageLoader.getImage("player.png");
	private static final BufferedImage PLAYER_GUN = ImageLoader.getImage("player_gun.png");
	
	
	private static final double SPEED = 3;
	private static final double ROT_SPEED = 0.07;
	
	private boolean shooting;
	
	private int score;
	private int ammo;
	
	public EntityPlayer(World world, double x, double y, double rot) {
		super(world, x, y, rot, PLAYER_NO_GUN);
		
		rotLerpFactor = 0.2;
		
		ammo = 0;
	}
	
	@Override
	public void update() {
		super.update();
		
		for(Entity en : world.getEntities()) {
			if(en instanceof EntityItem && intersects(en)) {
				Item i = ((EntityItem) en).getItem();
				
				if(i == Item.GUN) {
					en.remove();
					pickupGun();
				}
			}
		}
	}
	
	public void updateInput() {
		if(KeyHandler.isDown(KeyEvent.VK_W)) {
			targetSpeed = SPEED;
		}else if(KeyHandler.isDown(KeyEvent.VK_S)) {
			targetSpeed = -SPEED;
		}else{
			targetSpeed = 0;
		}
		
		if(KeyHandler.isDown(KeyEvent.VK_A)) {
			targetRot -= ROT_SPEED;
		}else if(KeyHandler.isDown(KeyEvent.VK_D)) {
			targetRot += ROT_SPEED;
		}
		
		shooting = KeyHandler.isDown(KeyEvent.VK_SPACE);
		
		if(shooting && ticksAlive % (Game.TPS / 4) == 0)
			shoot();
	}
	
	public void shoot() {
		if(!hasGun() || ammo <= 0)
			return;
		
		world.addEntity(new EntityBullet(world, x + getWidth() / 2, y + getHeight() / 2, rot, this, this));
		ammo--;
	}
	
	public void pickupGun() {
		img = PLAYER_GUN;
		ammo += 30;
	}
	
	public boolean hasGun() {
		return img == PLAYER_GUN;
	}
	
	public int getScore() {
		return score;
	}
	
	public int getAmmo() {
		return ammo;
	}
	
	@Override
	public void onKill(LivingEntity hit, EntityBullet bullet) {
		score += 5;
	}
	
	@Override
	public void onHit(LivingEntity hit, EntityBullet bullet) {
	}
	
	@Override
	public void remove() {
		// Player can't be removed
	}
}
