package de.atlascore.block.tile;

import org.joml.Vector3d;

import de.atlasmc.Material;
import de.atlasmc.block.tile.Dispenser;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Projectile;
import de.atlasmc.event.HandlerList;
import de.atlasmc.event.entity.ProjectileLounchEvent;
import de.atlasmc.inventory.Inventory;
import de.atlasmc.inventory.InventoryType;
import de.atlasmc.world.World;

public class CoreDispenser extends CoreAbstractContainerTile<Inventory> implements Dispenser {

	public CoreDispenser(Material type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return InventoryType.DISPENSER.create(this);
	}

	@Override
	public Projectile lounchProjectile(Projectile projectile, Vector3d velocity) {
		if (projectile == null)
			throw new IllegalArgumentException("Projectile can not be null!");
		World world = getWorld();
		// TODO pitch and yaw
		ProjectileLounchEvent event = new ProjectileLounchEvent(projectile, world, getX(), getY(), getZ(), 0.0f, 0.0f);
		HandlerList.callEvent(event);
		if (event.isCancelled())
			return null;
		projectile.spawn(world, event.getX(), event.getY(), event.getZ(), event.getPitch(), event.getYaw());
		if (velocity != null)
			projectile.setVelocity(velocity);
		return projectile;
	}

	@Override
	public Projectile lounchProjectile(EntityType type, Vector3d velocity) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		Projectile pro = (Projectile) type.create(getWorld());
		return lounchProjectile(pro, velocity);
	}

}
