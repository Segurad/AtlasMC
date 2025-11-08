package de.atlasmc.core.node.world.entitytracker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.world.entitytracker.EntityPerception;
import de.atlasmc.node.world.entitytracker.EntityTracker;
import de.atlasmc.node.world.entitytracker.TrackerBinding;
import de.atlasmc.node.world.entitytracker.TrackingTarget;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public class CoreEntityTracker implements EntityTracker {
	
	private static final int TICKING_ENTITIES_INITIAL_CAPACITY = 32;
	private static final float TICKING_ENTITIES_GROW_FACTOR = 2;
	
	private final Map<Class<?>, CoreTrackingTarget<?>> targets;
	private final Collection<TrackingTarget<?>> targetView;
	private CoreTrackingTarget<?>[] fastTarget;
	private final CoreTrackingTarget<Entity> targetAll;
	private final Int2ObjectMap<Entity> entityByID;
	private final Map<UUID, Entity> entityByUUID;
	
	// ticking entities as double buffered implementation 
	private CoreTrackedEntity<?>[] tickingEntities; // currently modified version of persistent ticking entities
	private int tickingEntitiesSize; // number of entities in tickingEntitites
	private boolean tickingEntitiesChanged; // if ticking entities has changed
	private CoreTrackedEntity<?>[] persistentTickingEntities; // currently ticked entities
	private int persistentTickingEntitiesSize; // entities in ticked entities

	private int entityID;
	
	public CoreEntityTracker() {
		this.entityByID = new Int2ObjectOpenHashMap<>();
		this.entityByUUID = new HashMap<>();
		targetAll = new CoreTrackingTarget<>(Entity.class);
		targets = new HashMap<>();
		targets.put(Entity.class, targetAll);
		fastTarget = new CoreTrackingTarget<?>[]{ targetAll };
		this.targetView = Collections.unmodifiableCollection(targets.values());
		this.tickingEntities = new CoreTrackedEntity<?>[TICKING_ENTITIES_INITIAL_CAPACITY];
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> TrackingTarget<T> getTarget(Class<T> clazz) {
		TrackingTarget<?> target = targets.get(clazz);
		return target == null ? null : (TrackingTarget<T>) target;
	}

	@Override
	public <T extends Entity> TrackingTarget<T> createTarget(Class<T> clazz) {
		if (clazz == null)
			throw new IllegalArgumentException("Class can not be null!");
		if (!Entity.class.isAssignableFrom(clazz))
			throw new IllegalArgumentException("The given class is not assignable from Entity: " + clazz.getName());
		TrackingTarget<T> target = getTarget(clazz);
		if (target != null)
			return target;
		CoreTrackingTarget<T> newTarget = new CoreTrackingTarget<>(clazz);
		target = newTarget;
		targets.put(clazz, newTarget);
		int length = fastTarget.length;
		fastTarget = Arrays.copyOf(fastTarget, length+1);
		fastTarget[length] = newTarget;
		return target;
	}
	
	CoreTrackingTarget<?> getClosestTracker(Class<?> clazz) {
		CoreTrackingTarget<?> target = null;
	    for (CoreTrackingTarget<?> t : fastTarget) {
		    if (t.target.isAssignableFrom(clazz)) {
		    	if (target == null || target.target.isAssignableFrom(clazz)) {
		    		target = t;
		    	}
		    }
	    }
	    return target == null ? targetAll : target;
	}
	
	@Override
	public Collection<TrackingTarget<?>> getTargets() {
		return targetView;
	}

	@Override
	public Collection<Entity> getEntities() {
		return entityByUUID.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
		TrackingTarget<T> target = getTarget(clazz);
		if (target != null)
			return target.getEntities();
		ArrayList<T> entities = new ArrayList<>();
		for (Entity e : targetAll.entities) {
			if (!clazz.isInstance(e))
				continue;
			T entity = (T) e;
			entities.add(entity);
		}
		return entities;
	}

	@Override
	public Entity getEntity(int entityID) {
		return entityByID.get(entityID);
	}
	
	@Override
	public Entity getEntity(UUID uuid) {
		return entityByUUID.get(uuid);
	}

	@Override
	public Collection<Entity> getEntities(int x, int z) {
		CoreTrackedChunk<Entity> chunk = targetAll.chunks.get(MathUtil.toChunkPosition(x, z));
		return chunk == null ? List.of() : chunk.getEntityView();
	}

	@Override
	public <C extends Collection<Entity>> C getEntities(int x, int z, C entities) {
		entities.addAll(getEntities(x, z));
		return entities;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> Collection<T> getEntitesByClasses(int x, int z, Class<T> clazz) {
		TrackingTarget<T> target = getTarget(clazz);
		if (target != null)
			return target.getEntities(x, z);
		CoreTrackedChunk<Entity> chunk = targetAll.chunks.get(MathUtil.toChunkPosition(x, z));
		if (chunk == null)
			return List.of();
		ArrayList<T> entities = new ArrayList<>();
		for (Entity e : chunk.entities) {
			if (!clazz.isInstance(e))
				continue;
			T entity = (T) e;
			entities.add(entity);
		}
		return entities;
	}

	@Override
	public <T extends Entity, C extends Collection<T>> C getEntitesByClasses(int x, int z, Class<T> clazz, C entities) {
		CoreTrackingTarget<?> target = (CoreTrackingTarget<?>) targets.get(clazz);
		if (target == null)
			target = targetAll;
		CoreTrackedChunk<?> chunk = target.chunks.get(MathUtil.toChunkPosition(x, z));
		if (chunk == null || chunk.entitiesSize == 0)
			return entities;
		for (Entity e : chunk.entities) {
			if (!clazz.isInstance(e))
				continue;
			@SuppressWarnings("unchecked")
			T ent = (T) e;
			entities.add(ent);
		}
		return entities;
	}
	
	@Override
	public boolean isRegistered(Entity entity) {
		return entityByUUID.containsKey(entity.getUUID());
	}

	@Override
	public TrackerBinding register(Entity entity, EntityPerception tracker) {
		if (isRegistered(entity))
			throw new IllegalArgumentException("Entity already registered!");
		int entityID = this.entityID++;
		CoreTrackedEntity<Entity> tracked = new CoreTrackedEntity<>(entityID, entity, tracker, this);
		long chunkIndex = MathUtil.coordinatesToChunkPosition(tracked.x, tracked.z);
		for (CoreTrackingTarget<?> target : fastTarget) {
			if (!target.target.isInstance(entity))
				continue;
			@SuppressWarnings("unchecked")
			CoreTrackingTarget<Entity> etrack = (CoreTrackingTarget<Entity>) target;
			etrack.entities.add(entity);
			etrack.addToChunk(chunkIndex, entity);
		}
		this.entityByID.put(entityID, entity);
		this.entityByUUID.put(entity.getUUID(), entity);
		return tracked;
	}
	
	void updatePosition(CoreTrackedEntity<?> entity, double x, double y, double z) {
		long oldChunkIndex = MathUtil.coordinatesToChunkPosition(entity.x, entity.z);
		long newChunkIndex = MathUtil.coordinatesToChunkPosition(x, z);
		if (oldChunkIndex != newChunkIndex) {
			Entity ent = entity.entity;
			for (CoreTrackingTarget<?> target : fastTarget) {
				if (!target.target.isInstance(ent))
					continue;
				@SuppressWarnings("unchecked")
				CoreTrackingTarget<Entity> etrack = (CoreTrackingTarget<Entity>) target;
				etrack.removeFromChunk(oldChunkIndex, ent);
				etrack.addToChunk(newChunkIndex, ent);
			}
			return;
		}
		int oldY = MathUtil.toChunkCoordinate(entity.y);
		int newY = MathUtil.toChunkCoordinate(y);
		if (oldY == newY) {
			return;
		}
		Entity ent = entity.entity;
		for (CoreTrackingTarget<?> target : fastTarget) {
			if (!target.target.isInstance(ent))
				continue;
			@SuppressWarnings("unchecked")
			CoreTrackingTarget<Entity> etrack = (CoreTrackingTarget<Entity>) target;
			CoreTrackedChunk<Entity> chunk = etrack.chunks.get(newChunkIndex);
			chunk.updateEntityY(ent, oldY, newY);
		}
	}
	
	void removeEntity(CoreTrackedEntity<?> entity) {
		final Entity e = entity.entity;
		long chunkIndex = MathUtil.coordinatesToChunkPosition(entity.x, entity.y);
		for (CoreTrackingTarget<?> target : fastTarget) {
			if (!target.target.isInstance(e))
				continue;
			@SuppressWarnings("unchecked")
			CoreTrackingTarget<Entity> etrack = (CoreTrackingTarget<Entity>) target;
			etrack.entities.remove(e);
			etrack.removeFromChunk(chunkIndex, e);
		}
		this.entityByID.remove(entity.id, e);
		this.entityByUUID.remove(e.getUUID(), e);
	}
	
	void addTicking(CoreTrackedEntity<?> entity) {
		if (entity.tickingEntitiesPointer != -1)
			return;
		final int size = this.tickingEntitiesSize;
		if (size == tickingEntities.length)
			tickingEntities = Arrays.copyOf(tickingEntities, (int) (size * TICKING_ENTITIES_GROW_FACTOR));
		tickingEntities[size] = entity;
		entity.tickingEntitiesPointer = size;
		this.tickingEntitiesSize = size + 1;
		tickingEntitiesChanged = true;
	}
	
	void removeTicking(CoreTrackedEntity<?> entity) {
		int pointer = entity.tickingEntitiesPointer;
		if (pointer == -1)
			return;
		int size = tickingEntitiesSize;
		CoreTrackedEntity<?>[] entities = tickingEntities;
		CoreTrackedEntity<?> moved = entities[pointer] = entities[size];
		entities[size] = null;
		tickingEntitiesSize = size -1;
		if (moved != null)
			moved.tickingEntitiesPointer = pointer;
		tickingEntitiesChanged = true;
	}
	
	@Override
	public void tick() {
		final int size;
		final CoreTrackedEntity<?>[] entities;
		if (tickingEntitiesChanged) {
			if (tickingEntities.length != persistentTickingEntities.length) {
				entities = persistentTickingEntities = tickingEntities.clone();
				size = persistentTickingEntitiesSize = tickingEntitiesSize;
			} else {
				System.arraycopy(tickingEntities, 0, persistentTickingEntities, 0, tickingEntities.length);
				entities = persistentTickingEntities;
				size = persistentTickingEntitiesSize = tickingEntitiesSize;
			}
		} else {
			entities = persistentTickingEntities;
			size = persistentTickingEntitiesSize;
		}
		if (size == 0)
			return;
		for (int i = 0; i < size; i++) {
			CoreTrackedEntity<?> entity = entities[i];
			entity.entity.tick();
		}
	}

}
