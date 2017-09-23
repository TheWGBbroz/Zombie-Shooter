package nl.thewgbbroz.zombieshooter.entities;

import nl.thewgbbroz.zombieshooter.Game;
import nl.thewgbbroz.zombieshooter.ImageLoader;
import nl.thewgbbroz.zombieshooter.ai.ZombieAI;
import nl.thewgbbroz.zombieshooter.world.World;

/**
 * @author Wouter Gritter
 * 
 * Copyright 2017 Wouter Gritter
 */
public class EntityZombie extends LivingEntity {
	public EntityZombie(World world, double x, double y, double rot) {
		super(world, x, y, rot, ImageLoader.getImage("zombie.png"));
		
		ai = new ZombieAI(this);
		
		targetSpeed = Game.RAND.nextDouble() * 0.4 + 0.3;
	}
}
