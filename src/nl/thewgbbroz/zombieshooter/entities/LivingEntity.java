package nl.thewgbbroz.zombieshooter.entities;

import java.awt.image.BufferedImage;

import nl.thewgbbroz.zombieshooter.Game;
import nl.thewgbbroz.zombieshooter.world.World;

public abstract class LivingEntity extends Entity {
	protected double hp;
	
	public LivingEntity(World world, double x, double y, double rot, BufferedImage img) {
		super(world, x, y, rot, img);
		
		hp = 100;
	}
	
	@Override
	public void update() {
		super.update();
		
		if(hp <= 0)
			remove();
	}
	
	public double getHP() {
		return hp;
	}
	
	public void setHP(double hp) {
		this.hp = hp;
	}
	
	public void damage(double dmg) {
		this.hp -= dmg;
		if(this.hp < 0)
			this.hp = 0;
		
		int amount = Game.RAND.nextInt(3) + 1;
		for(int i = 0; i < amount; i++) {
			world.addEntity(new EntityBlood(world, x, y));
		}
	}
	
	public boolean isAlive() {
		return this.hp > 0;
	}
}
