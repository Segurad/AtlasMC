package de.atlascore.block.tile;

import java.io.IOException;

import de.atlasmc.Material;
import de.atlasmc.block.tile.MobSpawner;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreMobSpawner extends CoreTileEntity implements MobSpawner {

	protected static final ChildNBTFieldContainer<CoreMobSpawner> NBT_FIELDS;
	
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
		NBT_FIELDS = new ChildNBTFieldContainer<>(CoreTileEntity.NBT_FIELDS);
		NBT_FIELDS.setField(SPAWN_DATA, (holder, reader) -> {
			reader.readNextEntry();
			EntityType type = null;
			if (!NBT_ID.equals(reader.getFieldName())) {
				reader.mark();
				reader.search(NBT_ID);
				type = EntityType.getByName(reader.readStringTag());
				reader.reset();
			} else type = EntityType.getByName(reader.readStringTag());
			Entity ent = type.create(null);
			ent.fromNBT(reader);
			holder.setDisplayedEntity(ent);
		});
		// TODO skipped fields of mob spawner
	}
	
	private Entity display;
	private SpawnerConfiguration config;
	
	public CoreMobSpawner(Material type) {
		super(type);
	}

	@Override
	protected NBTFieldContainer<? extends CoreMobSpawner> getFieldContainerRoot() {
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
