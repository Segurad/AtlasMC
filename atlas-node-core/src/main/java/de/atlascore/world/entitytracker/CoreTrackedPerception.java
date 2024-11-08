package de.atlascore.world.entitytracker;

import de.atlasmc.entity.Entity;
import de.atlasmc.world.entitytracker.EntityPerception;

public class CoreTrackedPerception<T extends Entity> {
	
	final EntityPerception perception;
	final Entity source;
	CoreTrackingTarget<? extends T> target;
	final Class<? extends T> clazz;
	int chunkX;
	int chunkY;
	int chunkZ;
	int perceptionRange;
	
	@SuppressWarnings("unchecked")
	public CoreTrackedPerception(EntityPerception perception, Entity source) {
		this.perception = perception;
		this.clazz = (Class<? extends T>) perception.tracking();
		this.source = source;
		this.perceptionRange = (int) perception.range();
	}
	
	public void register() {
		target.addPerception(this);
	}
	
	public void unregister() {
		target.removePerception(this);
	}

	public void updatePosition(double x, double y, double z) {
		target.updatePerception(this, x, y, z);
	}

}
