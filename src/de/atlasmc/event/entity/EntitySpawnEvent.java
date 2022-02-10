package de.atlasmc.event.entity;

import de.atlasmc.entity.Entity;
import de.atlasmc.event.Cancellable;
import de.atlasmc.event.ServerHandlerList;
import de.atlasmc.world.World;

/**
 * Called before a {@link Entity} is spawned at a {@link World}
 */
public class EntitySpawnEvent extends EntityEvent implements Cancellable {

	private static final ServerHandlerList HANDLERS = new ServerHandlerList();
	
	private final World world;
	private double x, y, z;
	private float pitch, yaw;
	private boolean cancelled;

	public EntitySpawnEvent(Entity entity, World world, double x, double y, double z, float pitch, float yaw) {
		super(entity);
		this.world = world;
		setLocation(x, y, z, pitch, yaw);
	}

	@Override
	public void setCancelled(boolean cancelled) {
		this.cancelled = cancelled;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public ServerHandlerList getHandlers() {
		return HANDLERS;
	}
	
	public static ServerHandlerList getHandlerList() {
		return HANDLERS;
	}

	public World getWorld() {
		return world;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public double getZ() {
		return z;
	}
	
	public float getPitch() {
		return pitch;
	}
	
	public float getYaw() {
		return yaw;
	}
	
	public void setLocation(double x, double y, double z) {
		setLocation(x, y, z, pitch, yaw);
	}
	
	public void setLocation(double x, double y, double z, float pitch, float yaw) {
		this.x = x;
		this.y = y;
		this.z = z;
		this.pitch = pitch;
		this.yaw = yaw;
	}

}
