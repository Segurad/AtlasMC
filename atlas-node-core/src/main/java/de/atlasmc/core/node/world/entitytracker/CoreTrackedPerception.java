package de.atlasmc.core.node.world.entitytracker;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.world.entitytracker.EntityPerception;

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
		this.perceptionRange = MathUtil.toChunkCoordinate(perception.range());
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
	
	public void updateRange() {
		int newRange = MathUtil.toChunkCoordinate(perception.range());
		target.updatePerceptionRange(this, newRange);
		perceptionRange = newRange;
	}

}
