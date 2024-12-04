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
	final Long2ObjectMap<CoreTrackedChunk<T>> chunks;
	
	public CoreTrackingTarget(Class<T> target) {
		this.target = target;
		this.entities = new HashSet<>();
		this.entitiesView = Collections.unmodifiableSet(entities);
		this.perceptions = new HashSet<>();
		this.chunks = new Long2ObjectOpenHashMap<>();
	}
	
	void addToChunk(long index, T entity) {
		CoreTrackedChunk<T> chunk = chunks.get(index);
		if (chunk == null) {
			chunks.put(index, chunk = new CoreTrackedChunk<>());
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
		CoreTrackedChunk<T> chunk = chunks.get(index);
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
		int maxRange = perception.perceptionRange;
		if (maxRange <= 1) {
			CoreTrackedChunk<T> chunk = chunks.get(MathUtil.toChunkPosition(perception.chunkX, perception.chunkZ));
			if (chunk == null)
				return;
			chunk.addPerception(perception, perception.chunkY);
		} else {
			// TODO add full
		}
	}
	
	void updatePerception(CoreTrackedPerception<?> perception, double x, double y, double z) {
		final int oldX = perception.chunkX;
		final int oldY = perception.chunkY;
		final int oldZ = perception.chunkZ;
		final int newX = MathUtil.toChunkCoordinate(x);
		final int newY = MathUtil.toChunkCoordinate(y);
		final int newZ = MathUtil.toChunkCoordinate(z);
		final int deltaX = oldX - newX;
		final int deltaY = oldY - newY;
		final int deltaZ = oldZ - newZ;
		int maxRange = perception.perceptionRange;
		if (maxRange <= 1) { // can only view current chunk
			if (deltaX != 0 || deltaZ != 0) { // only update chunks if x z changes
				CoreTrackedChunk<T> oldChunk = chunks.get(MathUtil.toChunkPosition(oldX, oldZ)); 
				if (oldChunk != null) {
					oldChunk.removePerception(perception, oldY);
				}
				CoreTrackedChunk<T> newChunk = chunks.get(MathUtil.toChunkPosition(newX, newZ));
				if (newChunk != null) {
					newChunk.addPerception(perception, newY);
				}
			} else if (deltaY != 0) { // update entities in chunk if chunk section change
				CoreTrackedChunk<T> chunk = chunks.get(MathUtil.toChunkPosition(newX, newZ));
				if (chunk != null) {
					chunk.updatePerceptionY(perception, oldY, newY);
				}
			}
			return;
		}
		if (deltaX > 2 || deltaY > 2) {
			removeFullArea(perception, oldX, oldY, oldZ);
			addFullArea(perception, newX, newY, newZ);
		} else {
	        // Handle X-axis movement
	        if (deltaX != 0) {
	            if (deltaX > 0) {
	            	
	            } else {
	            	
	            }
	        }

	        // Handle Z-axis movement, excluding chunks already processed by X-axis movement
	        if (deltaZ != 0) {
	            if (deltaZ > 0) {
	            	
	            } else {
	            	
	            }
	        }

	        // Handle Y-axis movement, update Y for all chunks in perception area if deltaY != 0
	        if (deltaY != 0) {
	        	final int maxX = newX + maxRange;
	        	final int maxZ = newZ + maxRange;
	            for (int xChunk = newX - maxRange; xChunk <= maxX; xChunk++) {
	                for (int zChunk = newZ - maxRange; zChunk <= maxZ; zChunk++) {
	                    CoreTrackedChunk<T> chunk = chunks.get(MathUtil.toChunkPosition(xChunk, zChunk));
	                    if (chunk != null) {
	                        chunk.updatePerceptionY(perception, oldY, newY);
	                    }
	                }
	            }
	        }
		}
	}
	
	private void addFullArea(CoreTrackedPerception<?> perception, int chunkX, int chunkY, int chunkZ) {
		int maxRange = perception.perceptionRange;
		final int maxX = chunkX + maxRange;
		final int maxZ = chunkZ + maxRange;
		final int minZ = chunkZ - maxRange;
		for (int x = chunkX - maxRange; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				CoreTrackedChunk<T> chunk = chunks.get(MathUtil.toChunkPosition(x, z));
				if (chunk != null) {
					chunk.addPerception(perception, chunkY);
				}
			}
		}
	}

	private void removeFullArea(CoreTrackedPerception<?> perception, int chunkX, int chunkY, int chunkZ) {
		int maxRange = perception.perceptionRange;
		final int maxX = chunkX + maxRange;
		final int maxZ = chunkZ + maxRange;
		final int minZ = chunkZ - maxRange;
		for (int x = chunkX - maxRange; x <= maxX; x++) {
			for (int z = minZ; z <= maxZ; z++) {
				CoreTrackedChunk<T> chunk = chunks.get(MathUtil.toChunkPosition(x, z));
				if (chunk != null) {
					chunk.removePerception(perception, chunkY);
				}
			}
		}
	}
	
	void removePerception(CoreTrackedPerception<?> perception) {
		perceptions.remove(perception);
		int maxRange = perception.perceptionRange;
		if (maxRange <= 1) {
			CoreTrackedChunk<T> chunk = chunks.get(MathUtil.toChunkPosition(perception.chunkX, perception.chunkZ));
			if (chunk == null)
				return;
			chunk.removePerception(perception, perception.chunkY);
		} else {
			// TODO remove full
		}
	}

	@Override
	public Collection<T> getEntities(int x, int z) {
		CoreTrackedChunk<T> chunk = chunks.get(MathUtil.toChunkPosition(x, z));
		if (chunk == null)
			return List.of();
		return chunk.getEntityView();
	}

	@Override
	public <C extends Collection<T>> C getEntities(int x, int z, C entities) {
		CoreTrackedChunk<T> chunk = chunks.get(MathUtil.toChunkPosition(x, z));
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
