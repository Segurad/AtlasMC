package de.atlascore.world.entitytracker;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.entity.Entity;
import de.atlasmc.util.MathUtil;
import de.atlasmc.world.entitytracker.TrackingTarget;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

class CoreTrackingTarget<T extends Entity> implements TrackingTarget<T> {
	
	final Class<T> target;
	final Set<T> entities;
	final Set<T> entitiesView;
	final Set<CoreTrackedPerception<T>> perceptions;
	final Long2ObjectMap<CoreTrackedChunkEntry<T>> chunks;
	
	public CoreTrackingTarget(Class<T> target) {
		this.target = target;
		this.entities = new HashSet<>();
		this.entitiesView = Collections.unmodifiableSet(entities);
		this.perceptions = new HashSet<>();
		this.chunks = new Long2ObjectOpenHashMap<>();
	}
	
	void addToChunk(long index, T entity) {
		CoreTrackedChunkEntry<T> chunk = chunks.get(index);
		if (chunk == null) {
			chunks.put(index, chunk = new CoreTrackedChunkEntry<>());
			// TODO check if in perception
		}
		chunk.addEntity(entity);
		final int perceptionsSize = chunk.perceptionsSize;
		if (perceptionsSize == 0)
			return;
		final CoreTrackedPerception<?>[] perceptions = chunk.perceptions;
		for (int i = 0; i < perceptionsSize; i++) {
			CoreTrackedPerception<?> perception = perceptions[i];
			if (perception.source == entity || !perception.clazz.isInstance(entity))
				continue;
			int entityYChunk = MathUtil.toChunkCoordinate(entity.getY());
			if (Math.abs(perception.chunkY - entityYChunk) > perception.perceptionRange)
				continue;
			perception.perception.add(entity);
		}
	}
	
	void removeFromChunk(long index, T entity) {
		CoreTrackedChunkEntry<T> chunk = chunks.get(index);
		if (chunk == null)
			return;
		chunk.removeEntity(entity);
		final int perceptionsSize = chunk.perceptionsSize;
		if (perceptionsSize == 0)
			return;
		final CoreTrackedPerception<?>[] perceptions = chunk.perceptions;
		for (int i = 0; i < perceptionsSize; i++) {
			CoreTrackedPerception<?> perception = perceptions[i];
			if (perception.source == entity || !perception.clazz.isInstance(entity))
				continue;
			int entityYChunk = MathUtil.toChunkCoordinate(entity.getY());
			if (Math.abs(perception.chunkY - entityYChunk) > perception.perceptionRange)
				continue;
			perception.perception.remove(entity);
		}
		if (chunk.entitiesSize == 0) {
			chunks.remove(index);
		}
	}

	@Override
	public Class<T> getTarget() {
		return target;
	}

	@Override
	public Collection<T> getEntities() {
		return entitiesView;
	}
	
	@SuppressWarnings("unchecked")
	void addPerception(CoreTrackedPerception<?> perception) {
		perceptions.add((CoreTrackedPerception<T>) perception);
		// TODO add
	}
	
	void updatePerception(CoreTrackedPerception<?> perception, double x, double y, double z) {
		int newX = MathUtil.toChunkCoordinate(x);
		int newY = MathUtil.toChunkCoordinate(y);
		int newZ = MathUtil.toChunkCoordinate(z);
		int deltaX = perception.chunkX - newX;
		int deltaY = perception.chunkY - newY;
		int deltaZ = perception.chunkZ - newZ;
		int maxRange = perception.perceptionRange;
		if (maxRange <= 1) { // can only view current chunk
			if (deltaX != 0 || deltaZ != 0) { // only update chunks if x z changes
				CoreTrackedChunkEntry<T> oldChunk = chunks.get(MathUtil.toChunkPosition(perception.chunkX, perception.chunkZ)); 
				if (oldChunk != null) {
					oldChunk.removePerception(perception, perception.chunkY);
				}
				CoreTrackedChunkEntry<T> newChunk = chunks.get(MathUtil.toChunkPosition(newX, newZ));
				if (newChunk != null) {
					newChunk.addPerception(perception, newY);
				}
			} else if (deltaY != 0) { // update entities in chunk if chunk section change
				// TODO y change
			}
			return;
		}
		maxRange *= 2; // radius to diameter
		if (deltaX > maxRange || deltaY > maxRange) {
			// TODO rebuild full area
		} else {
			if (deltaX != 0) {
				// TODO move region X
			}
			if (deltaZ != 0) {
				// TODO move region Z
			}
			if (deltaY != 0) {
				// TODO rechecks all entity Y in area
			}
		}
	}
	
	void removePerception(CoreTrackedPerception<?> perception) {
		perceptions.remove(perception);
		final int maxRange = perception.perceptionRange;
		// TODO remove
	}

	@Override
	public Collection<T> getEntities(int x, int z) {
		CoreTrackedChunkEntry<T> chunk = chunks.get(MathUtil.toChunkPosition(x, z));
		if (chunk == null)
			return List.of();
		return chunk.getEntityView();
	}

	@Override
	public <C extends Collection<T>> C getEntities(int x, int z, C entities) {
		CoreTrackedChunkEntry<T> chunk = chunks.get(MathUtil.toChunkPosition(x, z));
		if (chunk == null)
			return entities;
		for (Entity e : chunk.entities) {
			@SuppressWarnings("unchecked")
			T ent = (T) e;
			entities.add(ent);
		}
		return entities;
	}

}
