package nl.thewgbbroz.zombieshooter.entities;

import nl.thewgbbroz.zombieshooter.Game;
import nl.thewgbbroz.zombieshooter.ImageLoader;
import nl.thewgbbroz.zombieshooter.world.World;

public class EntityBlood extends Entity {
	private static final int IMG_AMOUNT = 6;
	
	private int timeAlive;
	
	public EntityBlood(World world, double x, double y) {
		super(world, x, y, Game.RAND.nextDouble() * Math.PI * 2, ImageLoader.getImage("blood/" + Game.RAND.nextInt(IMG_AMOUNT) + ".png"));
		
		timeAlive = Game.RAND.nextInt(Game.TPS * 3) + Game.TPS * 5;
		targetSpeed = Game.RAND.nextDouble() * 1 + 0.5;
		hasTileCollision = false;
	}
	
	@Override
	public void update() {
		super.update();
		
		if(ticksAlive > timeAlive)
			remove();
		
		targetSpeed *= 0.92;
	}
	
	@Override
	public int getRenderOrder() {
		return -1;
	}
}
