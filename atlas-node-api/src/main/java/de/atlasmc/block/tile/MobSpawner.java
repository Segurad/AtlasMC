package de.atlasmc.block.tile;

import java.util.Map;

import de.atlasmc.entity.Entity;

public interface MobSpawner extends TileEntity {
	
	public SpawnerConfiguration getConfiguration();
	
	public void setConfiguration(SpawnerConfiguration config);
	
	/**
	 * Returns the displayed Entity of this Spawner or null if none<br>
	 * Note will not return the displayed Entity of the {@link SpawnerConfiguration}
	 * @return the displayed Entity or null
	 */
	public Entity getDisplayedEntity();
	
	/**
	 * Sets the displayed Entity of this Spawner (Overrides the SpawnerConfiguration)
	 * @param entity the displayed entity
	 */
	public void setDisplayedEntity(Entity entity);
	
	public interface SpawnerConfiguration {
		
		public int getDelay();
		
		public void setDelay(int delay);
		
		public int getMinDelay();
		
		public void setMinDelay(int delay);
		
		public int getMaxDelay();
		
		public void setMaxDelay(int delay);
		
		public int getRequiredPlayerRange();
		
		public void setRequiredPlayerRange(int range);
		
		public int getSpawnRange();
		
		public void setSpawnRange(int range);
		
		public int getMinSpawnCount();
		
		public int getMaxSpawnCount();
		
		public void setMinSpawnCount(int count);
		
		public void setMaxSpawnCount(int count);
		
		public int getMaxNearbyEntities();
		
		public void setMaxNearbyEntities(int value);
		
		public Map<Entity, Integer> getPotentionals();
		
		public void setPotentionals(Map<Entity, Integer> potentioals);
		
		public Entity getDisplayedEntity();
		
		public void setDisplayedEntity(Entity entity);
		
	}

}
