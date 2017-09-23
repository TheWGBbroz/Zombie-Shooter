package nl.thewgbbroz.zombieshooter.ai;

import nl.thewgbbroz.zombieshooter.entities.Entity;

public abstract class AI {
	protected Entity entity;
	
	public AI(Entity entity) {
		this.entity = entity;
	}
	
	public abstract void update();
}
