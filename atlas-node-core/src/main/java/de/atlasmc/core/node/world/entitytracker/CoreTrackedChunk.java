package de.atlasmc.core.node.world.entitytracker;

import java.util.AbstractList;
import java.util.Arrays;
import java.util.List;

import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.util.MathUtil;
import de.atlasmc.node.world.entitytracker.EntityPerception;

class CoreTrackedChunk<T extends Entity> {

	private static final int ENTITIES_INIT_CAPACITY = 5;
	private static final float ENTITIES_GROW_FACTOR = 2;
	private static final int PERCEPTIONS_INIT_CAPACITY = 5;
	private static final float PERCEPTIONS_GROW_FACTOR = 2;

	Entity[] entities;
	int entitiesSize;
	private List<T> entitiesView;
	CoreTrackedPerception<?>[] perceptions;
	int perceptionsSize;

	public CoreTrackedChunk() {
		this.entities = new Entity[ENTITIES_INIT_CAPACITY];
		this.perceptions = new CoreTrackedPerception<?>[PERCEPTIONS_INIT_CAPACITY];
	}

	void addEntity(Entity entity) {
		if (entities.length == entitiesSize) // check if grow is required
			entities = Arrays.copyOf(entities, (int) (entitiesSize * ENTITIES_GROW_FACTOR));
		entities[entitiesSize++] = entity;
	}

	void removeEntity(Entity entity) {
		final int size = entitiesSize;
		for (int i = 0; i < size; i++) {
			if (entities[i] != entity)
				continue;
			entities[i] = entities[size - 1];
			entities[size - 1] = null;
		}
		entitiesSize = size - 1;
	}

	void updateEntityY(Entity entity, int oldY, int newY) {
		final int size = perceptionsSize;
		if (size == 0)
			return;
		final CoreTrackedPerception<?>[] perceptions = this.perceptions;
		for (int i = 0; i < size; i++) {
			CoreTrackedPerception<?> perception = perceptions[i];
			if (perception.source == entity || !perception.clazz.isInstance(entity))
				continue;
			if (Math.abs(perception.chunkY - oldY) > perception.perceptionRange) {
				if (Math.abs(perception.chunkY - newY) > perception.perceptionRange)
					continue;
				perception.perception.add(entity);
			} else if (Math.abs(perception.chunkY - newY) > perception.perceptionRange) {
				perception.perception.remove(entity);
			}
		}
	}

	void updatePerceptionY(CoreTrackedPerception<?> perception, int oldY, int newY) {
		final int size = entitiesSize;
		if (size == 0)
			return;
		for (int i = 0; i < size; i++) {
			Entity entity = entities[i];
			if (perception.source == entity || !perception.clazz.isInstance(entity))
				continue;
			final double entityY = MathUtil.toChunkCoordinate(entity.getY());
			if (Math.abs(oldY - entityY) > perception.perceptionRange) {
				if (Math.abs(newY - entityY) > perception.perceptionRange)
					continue;
				perception.perception.add(entity);
			} else if (Math.abs(newY - entityY) > perception.perceptionRange) {
				perception.perception.remove(entity);
			}
		}
	}

	void addPerception(CoreTrackedPerception<?> perception, int chunkY) {
		// add entities
		final int size = this.entitiesSize;
		final Entity[] entities = this.entities;
		final EntityPerception p = perception.perception;
		final int perceptionRange = perception.perceptionRange;
		for (int i = 0; i < size; i++) {
			Entity ent = entities[i];
			if (perception.source == ent || !perception.clazz.isInstance(ent))
				continue;
			int entityYChunk = MathUtil.toChunkCoordinate(ent.getY());
			if (Math.abs(chunkY - entityYChunk) > perceptionRange)
				continue;
			p.add(ent);
		}
		// add perception
		if (perceptions.length == perceptionsSize) // check if grow is required
			perceptions = Arrays.copyOf(perceptions, (int) (perceptionsSize * PERCEPTIONS_GROW_FACTOR));
		perceptions[perceptionsSize++] = perception;
	}

	void removePerception(CoreTrackedPerception<?> perception, int chunkY) {
		// remove perception
		final int perceptionsSize = this.perceptionsSize;
		for (int i = 0; i < perceptionsSize; i++) {
			if (perceptions[i] != perception)
				continue;
			perceptions[i] = perceptions[perceptionsSize - 1];
			perceptions[perceptionsSize - 1] = null;
			this.perceptionsSize = perceptionsSize - 1;
			break;
		}
		// remove entities
		final int size = this.entitiesSize;
		final Entity[] entities = this.entities;
		final EntityPerception p = perception.perception;
		final int perceptionRange = perception.perceptionRange;
		for (int i = 0; i < size; i++) {
			Entity ent = entities[i];
			if (perception.source == ent || !perception.clazz.isInstance(ent))
				continue;
			int entityYChunk = MathUtil.toChunkCoordinate(ent.getY());
			if (Math.abs(chunkY - entityYChunk) > perceptionRange)
				continue;
			p.remove(ent);
		}
	}

	List<T> getEntityView() {
		if (entitiesView == null)
			this.entitiesView = new EntityView<>(this);
		return entitiesView;
	}

	private static final class EntityView<T extends Entity> extends AbstractList<T> {

		private final CoreTrackedChunk<T> chunk;

		public EntityView(CoreTrackedChunk<T> chunk) {
			this.chunk = chunk;
		}

		@Override
		public int size() {
			return chunk.entitiesSize;
		}

		@SuppressWarnings("unchecked")
		@Override
		public T get(int index) {
			return (T) chunk.entities[index];
		}

	}

}
