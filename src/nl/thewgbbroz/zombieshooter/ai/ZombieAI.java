package nl.thewgbbroz.zombieshooter.ai;

import nl.thewgbbroz.zombieshooter.Game;
import nl.thewgbbroz.zombieshooter.entities.Entity;
import nl.thewgbbroz.zombieshooter.entities.EntityPlayer;

public class ZombieAI extends AI {
	private static final int ZOMBIE_DAMAGE = 20;
	
	private EntityPlayer player;
	
	private int attackCooldown;
	
	public ZombieAI(Entity entity) {
		super(entity);
		
		for(Entity en : entity.getWorld().getEntities()) {
			if(en instanceof EntityPlayer) {
				player = (EntityPlayer) en;
				break;
			}
		}
		
		if(player == null) {
			throw new IllegalStateException("No player found!");
		}
	}

	@Override
	public void update() {
		if(attackCooldown > 0)
			attackCooldown--;
		
		//double theta = Math.atan2(entity.getY() - player.getY(), entity.getX() - player.getX());
		double theta = Math.atan2(player.getY() - entity.getY(), player.getX() - entity.getX());
		entity.setTargetRot(theta);
		
		if(attackCooldown <= 0) {
			double distFromPl = entity.distSq(player);
			if(distFromPl < 15 * 15) {
				attackCooldown = Game.TPS * 1;
				player.damage(ZOMBIE_DAMAGE);
			}
		}
	}
}
