package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.tile.MobSpawner;
import de.atlasmc.entity.Entity;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldSet;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreMobSpawner extends CoreTileEntity implements MobSpawner {

	protected static final NBTFieldSet<CoreMobSpawner> NBT_FIELDS;
	
	protected static final CharKey
	DELAY = CharKey.literal("Delay"),
	MAX_NEARY_ENTITIES = CharKey.literal("MaxNearbyEntities"),
	MAX_DELAY = CharKey.literal("MaxSpawnDelay"),
	MIN_DELAY = CharKey.literal("MinSpawnDelay"),
	REQUIRED_PLAYER_RANGE = CharKey.literal("RequiredPlayerRange"),
	SPAWN_COUNT = CharKey.literal("SpawnCount"),
	SPAWN_DATA = CharKey.literal("SpawnData"),
	SPAWN_POTENTIALS = CharKey.literal("SpawnPotentials"),
	SPAWN_RANGE = CharKey.literal("SpawnRange");
	
	static {
		NBT_FIELDS = CoreTileEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(SPAWN_DATA, (holder, reader) -> {
			reader.readNextEntry();
			Entity ent = Entity.getFromNBT(reader);
			holder.setDisplayedEntity(ent);
		});
		// TODO skipped fields of mob spawner
	}
	
	private Entity display;
	private SpawnerConfiguration config;
	
	public CoreMobSpawner(BlockType type) {
		super(type);
	}

	@Override
	protected NBTFieldSet<? extends CoreMobSpawner> getFieldSetRoot() {
		return NBT_FIELDS;
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
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!systemData) {
			Entity disp = display != null ? display : config != null ? config.getDisplayedEntity() : null;
			if (disp != null) {
				writer.writeCompoundTag(SPAWN_DATA);
				disp.toNBT(writer, systemData);
				writer.writeEndTag();
			}
			return;
		}
	}

}
