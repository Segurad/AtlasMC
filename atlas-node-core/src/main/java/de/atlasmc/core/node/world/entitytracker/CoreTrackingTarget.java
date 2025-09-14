package de.atlasmc.core.node.world.entitytracker;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.world.entitytracker.TrackingTarget;
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
			int x = (int) (index >> 32);
			int z = (int) (index & 0xFFFFFFFF);
			for (CoreTrackedPerception<T> perception : perceptions) {
				if (Math.abs(perception.chunkX - x) > perception.perceptionRange)
					continue;
				if (Math.abs(perception.chunkZ - z) > perception.perceptionRange)
					continue;
				chunk.addPerception(perception, perception.chunkY);
			}
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
		if (maxRange == 0) {
			CoreTrackedChunk<T> chunk = chunks.get(MathUtil.toChunkPosition(perception.chunkX, perception.chunkZ));
			if (chunk == null)
				return;
			chunk.addPerception(perception, perception.chunkY);
		} else {
			addFullArea(perception, perception.chunkX, perception.chunkY, perception.chunkZ);
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
		final int range = perception.perceptionRange;
		if (range == 0) { // can only view current chunk
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
		if (deltaX > 1 || deltaX < -1 || deltaZ > 1 || deltaZ < -1) {
			removeFullArea(perception, oldX, oldY, oldZ);
			addFullArea(perception, newX, newY, newZ);
		} else {
	        // Handle X-axis movement
	        if (deltaX != 0) {
	        	int removeX;
	        	int addX;
	            if (deltaX > 0) {
	            	removeX = oldX - range;
	            	addX = newX + range;
	            } else {
	            	removeX = oldX + range;
	            	addX = newX - range;
	            }
	            int fromZ = oldZ - range;
	            int toZ = oldZ + range;
	            long pos = MathUtil.toChunkPosition(removeX, fromZ);
	            for (int posZ = fromZ; posZ <= toZ; posZ++, pos++) {
	            	CoreTrackedChunk<T> chunk = chunks.get(pos);
	            	if (chunk != null)
	            		chunk.removePerception(perception, oldY);
	            }
	            if (deltaZ > 0) {
	            	fromZ += deltaZ;
	            } else {
	            	toZ += deltaZ;
	            }
	            pos = MathUtil.toChunkPosition(addX, fromZ);
	            for (int posZ = fromZ; posZ <= toZ; posZ++, pos++) {
	            	CoreTrackedChunk<T> chunk = chunks.get(pos);
	            	if (chunk != null)
	            		chunk.addPerception(perception, oldY);
	            }
	        }
	        // Handle Z-axis movement, excluding chunks already processed by X-axis movement
	        if (deltaZ != 0) {
	        	int removeZ;
	        	int addZ;
	            if (deltaZ > 0) {
	            	removeZ = oldZ - range;
	            	addZ = newZ + range;
	            } else {
	            	removeZ = oldZ + range;
	            	addZ = newZ - range;
	            }
	            int fromX = oldX - range;
	            if (deltaX > 0) {
	            	fromX += deltaX;
	            }
	            int toX = oldX + range;
	            long pos = MathUtil.toChunkPosition(removeZ, fromX);
	            for (int posX = fromX; posX <= toX; posX++, pos++) {
	            	CoreTrackedChunk<T> chunk = chunks.get(pos);
	            	if (chunk != null)
	            		chunk.removePerception(perception, newY);
	            }
	            if (deltaX < 0) {
	            	toX += deltaX;
	            }
	            pos = MathUtil.toChunkPosition(addZ, fromX);
	            for (int posX = fromX; posX <= toX; posX++, pos++) {
	            	CoreTrackedChunk<T> chunk = chunks.get(pos);
	            	if (chunk != null)
	            		chunk.addPerception(perception, newY);
	            }
	        }

	        // Handle Y-axis movement, update Y for all chunks in perception area if deltaY != 0
	        if (deltaY != 0) {
	        	int fromZ = oldZ - range;
	            int toZ = oldZ + range;
	            int fromX = oldX - range;
	            int toX = oldX + range;
	            if (deltaX > 0) {
	            	fromX += deltaX;
	            } else {
	            	toX += deltaX;
	            }
	            if (deltaZ > 0) {
	            	fromZ += deltaZ;
	            } else {
	            	toZ += deltaZ;
	            }
	            long pos = MathUtil.toChunkPosition(fromX, fromZ);
	    		for (int posX = fromX; posX <= toX; posX++) {
	    			pos &= 0xFFFFFFFF00000000L;
	    			pos |= fromZ;
	    			for (int posZ = fromZ; posZ <= toZ; posZ++) {
	    				CoreTrackedChunk<T> chunk = chunks.get(pos);
	    				if (chunk != null)
	    					chunk.removePerception(perception, newY);
	    				pos++;
	    			}
	    			pos += 0x100000000L;
	    		}
	        }
		}
		perception.chunkX = newX;
		perception.chunkY = newY;
		perception.chunkZ = newZ;
	}
	
	private void addFullArea(CoreTrackedPerception<?> perception, int chunkX, int chunkY, int chunkZ) {
		final int range = perception.perceptionRange;
		final int minX = chunkX - range;
		final int maxX = chunkX + range;
		final int minZ = chunkZ - range;
		final int maxZ = chunkZ + range;
		long pos = MathUtil.toChunkPosition(minX, minZ);
		for (int x = minX; x <= maxX; x++) {
			pos &= 0xFFFFFFFF00000000L;
			pos |= minZ;
			for (int z = minZ; z <= maxZ; z++) {
				CoreTrackedChunk<T> chunk = chunks.get(pos);
				if (chunk != null)
					chunk.addPerception(perception, chunkY);
				pos++;
			}
			pos += 0x0000000100000000L;
		}
	}

	private void removeFullArea(CoreTrackedPerception<?> perception, int chunkX, int chunkY, int chunkZ) {
		final int range = perception.perceptionRange;
		final int minX = chunkX - range;
		final int maxX = chunkX + range;
		final int minZ = chunkZ - range;
		final int maxZ = chunkZ + range;
		long pos = MathUtil.toChunkPosition(minX, minZ);
		for (int x = minX; x <= maxX; x++) {
			pos &= 0xFFFFFFFF00000000L;
			pos |= minZ;
			for (int z = minZ; z <= maxZ; z++) {
				CoreTrackedChunk<T> chunk = chunks.get(pos);
				if (chunk != null)
					chunk.removePerception(perception, chunkY);
				pos++;
			}
			pos += 0x100000000L;
		}
	}
	
	void removePerception(CoreTrackedPerception<?> perception) {
		perceptions.remove(perception);
		int maxRange = perception.perceptionRange;
		if (maxRange == 0) {
			CoreTrackedChunk<T> chunk = chunks.get(MathUtil.toChunkPosition(perception.chunkX, perception.chunkZ));
			if (chunk == null)
				return;
			chunk.removePerception(perception, perception.chunkY);
		} else {
			removeFullArea(perception, perception.chunkX, perception.chunkY, perception.chunkZ);
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

	public void updatePerceptionRange(CoreTrackedPerception<?> perception, int newRange) {
		// TODO improve update
		removeFullArea(perception, perception.chunkX, perception.chunkY, perception.chunkZ);
		addFullArea(perception, perception.chunkX, perception.chunkY, perception.chunkZ);
	}

}
