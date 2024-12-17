package de.atlascore.world.entitytracker;

import de.atlasmc.entity.Entity;
import de.atlasmc.util.MathUtil;
import de.atlasmc.world.entitytracker.EntityPerception;
import de.atlasmc.world.entitytracker.TrackerBinding;

class CoreTrackedEntity<T extends Entity> implements TrackerBinding {
	
	int tickingEntitiesPointer = -1;
	T entity;
	CoreTrackedPerception<T> perception;
	double x;
	double y;
	double z;
	final int id;
	CoreEntityTracker tracker; // set to most fitting tracker with perception
	
	public CoreTrackedEntity(int id, T entity, EntityPerception perception, CoreEntityTracker tracker) {
		this.entity = entity;
		this.x = entity.getX();
		this.y = entity.getY();
		this.z = entity.getZ();
		this.id = id;
		this.tracker = tracker;
		updatePerception(perception);
		if (entity.isTicking())
			tracker.addTicking(this);
	}

	@Override
	public void updatePosition(double x, double y, double z) {
		if (tracker == null)
			throw new IllegalStateException("Tracker is unregistered!");
		tracker.updatePosition(this, x, y, z);
		this.x = x;
		this.y = y;
		this.z = z;
		CoreTrackedPerception<T> perception = this.perception;
		if (perception != null) {
			perception.updatePosition(x, y, z);
		}
	}

	@Override
	public void updatePerception(EntityPerception perception) {
		if (tracker == null)
			throw new IllegalStateException("Tracker is unregistered!");
		CoreTrackedPerception<T> currentPerception = this.perception;
		if (currentPerception != null) {
			currentPerception.unregister();
			currentPerception = null;
		}
		if (perception != null) {
			currentPerception = new CoreTrackedPerception<>(perception, entity);
			@SuppressWarnings("unchecked")
			CoreTrackingTarget<? extends T> target = (CoreTrackingTarget<? extends T>) tracker.getClosestTracker(this.perception.clazz);
			currentPerception.target = target;
			currentPerception.chunkX = MathUtil.toChunkCoordinate(x);
			currentPerception.chunkY = MathUtil.toChunkCoordinate(y);
			currentPerception.chunkZ = MathUtil.toChunkCoordinate(z);
			currentPerception.register();
		}
		this.perception = currentPerception;
	}

	@Override
	public void updatePerceptionRange() {
		CoreTrackedPerception<T> perception = this.perception;
		if (perception == null)
			return;
		perception.updateRange();
	}

	@Override
	public void unregister() {
		if (tracker == null)
			return;
		tracker.removeTicking(this);
		tracker.removeEntity(this);
		tracker = null;
		perception = null;
		entity = null;
	}

	@Override
	public int getID() {
		return id;
	}

	@Override
	public void updateTicking(boolean ticking) {
		if (ticking) {
			tracker.addTicking(this);
		} else {
			tracker.removeTicking(this);
		}
	}

}
