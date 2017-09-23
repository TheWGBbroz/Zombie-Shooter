package nl.thewgbbroz.zombieshooter.entities;

import nl.thewgbbroz.zombieshooter.items.Item;
import nl.thewgbbroz.zombieshooter.world.World;

public class EntityItem extends Entity {
	private Item item;
	
	public EntityItem(World world, double x, double y, double rot, Item item) {
		super(world, x, y, rot, item.getImage());
		
		this.item = item;
		
		hasTileCollision = false;
	}
	
	public void setItem(Item item) {
		this.item = item;
		this.img = item.getImage();
	}
	
	public Item getItem() {
		return item;
	}
}
