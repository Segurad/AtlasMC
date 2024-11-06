package de.atlascore.world;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.world.EntityTracker;
import de.atlasmc.world.World;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;

public class CoreEntityTracker implements EntityTracker {
	
	private final World world;
	private final Map<Class<?>, TrackingTarget<?>> targets;
	private final TrackingTarget<Entity> targetAll;
	private final Int2ObjectMap<Entity> entityByID;
	private final Map<UUID, Entity> entityByUUID;
	
	private int entityID;
	
	public CoreEntityTracker(World world) {
		if (world == null)
			throw new IllegalArgumentException("World can not be null!");
		this.world = world;
		this.entityByID = new Int2ObjectOpenHashMap<>();
		this.entityByUUID = new HashMap<>();
		targetAll = new TrackingTarget<>(Entity.class);
		targets = new HashMap<>();
		targets.put(Entity.class, targetAll);
	}

	@Override
	public World getWorld() {
		return world;
	}

	@Override
	public Collection<Entity> getEntities() {
		return entityByUUID.values();
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
		TrackingTarget<?> target = targets.get(clazz);
		if (target != null)
			return (Collection<T>) target.entitiesView;
		ArrayList<T> entities = new ArrayList<>();
		for (Entity e : targetAll.entities) {
			if (clazz.isInstance(e))
				entities.add((T) e);
		}
		return entities;
	}

	@Override
	public Entity getEntity(int entityID) {
		return entityByID.get(entityID);
	}

	@Override
	public Collection<Entity> getEntities(int x, int z) {
		List<Entity> entities = targetAll.entitiesByChunk.get(toChunk(x, z));
		return entities == null ? List.of() : entities;
	}

	@Override
	public <C extends Collection<Entity>> C getEntities(int x, int z, C entities) {
		entities.addAll(getEntities(x, z));
		return entities;
	}

	@Override
	public <T extends Entity> Collection<T> getEntitesByClasses(int x, int z, Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <T extends Entity, C extends Collection<T>> C getEntitesByClasses(int x, int z, Class<T> clazz, C entities) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackerBinding register(Entity entity, double x, double y, double z, Perception<?> tracker) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private long toChunk(int x, int z) {
		long val = 0L | x;
		val <<= 32;
		val |= z;
		return val;
	}
	
	private static class TrackingTarget<E extends Entity> {
		
		private final Class<E> target;
		private final Set<E> entities;
		private final Set<E> entitiesView;
		private final Long2ObjectMap<List<E>> entitiesByChunk;
		
		public TrackingTarget(Class<E> target) {
			this.target = target;
			this.entities = new HashSet<>();
			this.entitiesView = Collections.unmodifiableSet(entities);
			this.entitiesByChunk = new Long2ObjectOpenHashMap<>();
		}
		
	}
	
	private static class TrackedEntity implements TrackerBinding {
		
		private final Entity entity;
		private Perception<?> perception;
		private double perceptionDistance;
		private double x;
		private double y;
		private double z;
		private final int id;
		
		public TrackedEntity(int id, Entity entity) {
			this.entity = entity;
			this.id = id;
		}

		@Override
		public void updatePosition(double x, double y, double z) {
			this.x = x;
			this.y = y;
			this.z = z;
		}

		@Override
		public void updatePerception(Perception<?> perception) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void updatePerceptionDistance(double distance) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void unregister() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getID() {
			return id;
		}
		
		
	}

}
