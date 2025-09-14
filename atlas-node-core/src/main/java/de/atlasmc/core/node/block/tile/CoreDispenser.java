package de.atlasmc.core.node.block.tile;

import org.joml.Vector3d;

import de.atlasmc.event.HandlerList;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.Dispenser;
import de.atlasmc.node.entity.EntityType;
import de.atlasmc.node.entity.Projectile;
import de.atlasmc.node.event.entity.ProjectileLounchEvent;
import de.atlasmc.node.inventory.Inventory;
import de.atlasmc.node.inventory.InventoryType;
import de.atlasmc.node.world.World;

public class CoreDispenser extends CoreAbstractContainerTile<Inventory> implements Dispenser {

	public CoreDispenser(BlockType type) {
		super(type);
	}

	@Override
	protected Inventory createInventory() {
		return InventoryType.DISPENSER.create(this);
	}

	@Override
	public Projectile launchProjectile(Projectile projectile, Vector3d velocity) {
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
	public Projectile launchProjectile(EntityType type, Vector3d velocity) {
		if (type == null)
			throw new IllegalArgumentException("Type can not be null!");
		Projectile pro = (Projectile) type.createEntity();
		return launchProjectile(pro, velocity);
	}

}
