package de.atlasmc.core.node.block.tile;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.tile.MobSpawner;
import de.atlasmc.node.entity.Entity;

public class CoreMobSpawner extends CoreTileEntity implements MobSpawner {
	
	private Entity display;
	private SpawnerConfiguration config;
	
	public CoreMobSpawner(BlockType type) {
		super(type);
	}
	
	@Override
	public SpawnerConfiguration getConfiguration() {
		return config;
	}

	@Override
	public void setConfiguration(SpawnerConfiguration config) {
		this.config = config;
	}

	@Override
	public Entity getDisplayedEntity() {
		return display;
	}

	@Override
	public void setDisplayedEntity(Entity entity) {
		this.display = entity;
	}

}
