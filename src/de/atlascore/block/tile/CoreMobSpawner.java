package de.atlascore.block.tile;

import java.io.IOException;
import de.atlasmc.Material;
import de.atlasmc.block.tile.MobSpawner;
import de.atlasmc.entity.Entity;
import de.atlasmc.entity.EntityType;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
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
			MobSpawner spawner = (MobSpawner) holder;
			reader.readNextEntry();
			EntityType type = null;
			if (!ID.equals(reader.getFieldName())) {
				reader.mark();
				reader.search(ID);
				type = EntityType.getByName(reader.readStringTag());
				reader.reset();
			} else type = EntityType.getByName(reader.readStringTag());
			Entity ent = type.create(spawner.getWorld());
			ent.fromNBT(reader);
			spawner.setDisplayedEntity(ent);
		});
		// TODO skipped fields of mob spawner
	}
	
	private Entity display;
	private SpawnerConfiguration config;
	
	public CoreMobSpawner(Material type, Chunk chunk, int x, int y, int z) {
		super(type, chunk, x, y, z);
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
