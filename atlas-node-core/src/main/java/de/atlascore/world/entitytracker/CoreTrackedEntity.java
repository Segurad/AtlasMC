package de.atlascore.world.entitytracker;

import de.atlasmc.entity.Entity;
import de.atlasmc.util.MathUtil;
import de.atlasmc.world.entitytracker.EntityPerception;
import de.atlasmc.world.entitytracker.TrackerBinding;

class CoreTrackedEntity<T extends Entity> implements TrackerBinding {
	
	int tickingEntitiesPointer;
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
		if (perception != null) {
			this.perception = new CoreTrackedPerception<>(perception, entity);
			@SuppressWarnings("unchecked")
			CoreTrackingTarget<? extends T> target = (CoreTrackingTarget<? extends T>) tracker.getClosestTracker(this.perception.clazz);
			this.perception.target = target;
			this.perception.chunkX = MathUtil.toChunkCoordinate(x);
			this.perception.chunkY = MathUtil.toChunkCoordinate(y);
			this.perception.chunkZ = MathUtil.toChunkCoordinate(z);
		}
		updateTicking(entity.isTicking());
	}

	@Override
	public void updatePosition(double x, double y, double z) {
		if (tracker == null)
			throw new IllegalStateException("Tracker is unregistered!");
		tracker.updatePosition(this, x, y, z);
		if (perception != null) {
			perception.updatePosition(x, y, z);
		}
	}

	@Override
	public void updatePerception(EntityPerception perception) {
		if (tracker == null)
			throw new IllegalStateException("Tracker is unregistered!");
		// TODO update perception
	}

	@Override
	public void updatePerceptionDistance() {
		if (perception == null)
			return;
		this.perception.perceptionDistance = (int) this.perception.perception.getPerceptionDistance();
		// TODO update perception
	}

	@Override
	public void unregister() {
		if (tracker == null)
			return;
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
