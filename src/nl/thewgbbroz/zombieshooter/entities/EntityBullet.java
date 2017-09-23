package nl.thewgbbroz.zombieshooter.entities;

import nl.thewgbbroz.zombieshooter.Game;
import nl.thewgbbroz.zombieshooter.ImageLoader;
import nl.thewgbbroz.zombieshooter.world.World;

public class EntityBullet extends Entity {
	private static final int SPEED = 10;
	private static final int DAMAGE = 50;
	
	private Entity shooter;
	private BulletListener bl;
	
	public EntityBullet(World world, double x, double y, double rot, Entity shooter, BulletListener bl) {
		super(world, x, y, rot, ImageLoader.getImage("bullet.png"));
		
		this.shooter = shooter;
		this.bl = bl;
		
		targetSpeed = SPEED;
		hasTileCollision = false;
	}
	
	@Override
	public void update() {
		super.update();
		
		if(ticksAlive > Game.TPS * 5) {
			remove();
			return;
		}
		
		for(Entity en : world.getEntities()) {
			if(en != shooter && en instanceof LivingEntity && intersects(en)) {
				LivingEntity le = (LivingEntity) en;
				
				le.damage(DAMAGE);
				
				if(bl != null) {
					bl.onHit(le, this);
					
					if(!le.isAlive())
						bl.onKill(le, this);
				}
				
				remove();
				
				return;
			}
		}
		
		if(checkCollision(x, y))
			remove();
	}
	
	@Override
	public int getRenderOrder() {
		return 0;
	}
	
	public interface BulletListener {
		public void onHit(LivingEntity hit, EntityBullet bullet);
		public void onKill(LivingEntity hit, EntityBullet bullet);
	}
}
