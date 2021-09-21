package de.atlascore.block.tile;

import de.atlasmc.Material;
import de.atlasmc.block.tile.MobSpawner;
import de.atlasmc.entity.Entity;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.world.Chunk;

public class CoreMobSpawner extends CoreTileEntity implements MobSpawner {

	protected static final ChildNBTFieldContainer NBT_FIELDS;
	
	protected static final String
	DELAY = "Delay",
	MAX_NEARY_ENTITIES = "MaxNearbyEntities",
	MAX_DELAY = "MaxSpawnDelay",
	MIN_DELAY = "MinSpawnDelay",
	REQUIRED_PLAYER_RANGE = "RequiredPlayerRange",
	SPAWN_COUNT = "SpawnCount",
	SPAWN_DATA = "SpawnData",
	SPAWN_POTENTIALS = "SpawnPotentials",
	SPAWN_RANGE = "SpawnRange";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(SPAWN_DATA, (holder, reader) -> {
			if (!(holder instanceof MobSpawner)) {
				reader.skipTag();
				return;
			}
			reader.readNextEntry();
			reader.mark();
		});
	}
	
	private Entity display;
	private SpawnerConfiguration config;
	
	public CoreMobSpawner(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
	}

	@Override
	public SpawnerConfiguration getConfiguration() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setConfiguration(SpawnerConfiguration config) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Entity getDisplayedEntity() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDisplayedEntity(Entity entity) {
		// TODO Auto-generated method stub
		
	}

}
