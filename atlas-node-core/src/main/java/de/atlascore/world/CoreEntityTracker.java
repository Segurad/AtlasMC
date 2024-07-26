package de.atlascore.world;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import de.atlasmc.entity.Entity;
import de.atlasmc.world.EntityTracker;
import de.atlasmc.world.World;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

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

	@Override
	public <T extends Entity> Collection<T> getEntitiesByClass(Class<T> clazz) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Collection<Entity> getEntitesByClasses(Class<? extends Entity>... classes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getEntity(int entityID) {
		return entityByID.get(entityID);
	}

	@Override
	public Collection<Entity> getEntities(int x, int z) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <C extends Collection<Entity>> C getEntities(int x, int z, C entities) {
		// TODO Auto-generated method stub
		return null;
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
	public Collection<Entity> getEntitesByClasses(int x, int z, Class<? extends Entity>... classes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <C extends Collection<Entity>> C getEntitiesByClasses(int x, int z, C entities,
			Class<? extends Entity>... classes) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Entity getEntity(int x, int z, int entityID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public TrackerBinding register(Entity entity, double x, double y, double z, Perception<?> tracker) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private static class TrackingTarget<E extends Entity> {
		
		private final Class<E> target;
		
		public TrackingTarget(Class<E> target) {
			this.target = target;
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
