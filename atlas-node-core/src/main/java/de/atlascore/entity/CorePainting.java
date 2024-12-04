package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Painting;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CorePainting extends CoreHanging implements Painting {

	protected static final MetaDataField<Motive> META_MOTIVE = new MetaDataField<>(CoreEntity.LAST_META_INDEX+1, Motive.KEBAB, MetaDataType.PAINTING_VARIANT);
	
	protected static final int LAST_META_INDEX = CoreEntity.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer<CorePainting> NBT_FIELDS;
	
	protected static final CharKey
	NBT_MOTIVE = CharKey.literal("Motive");
	
	static {
		NBT_FIELDS = CoreEntity.NBT_FIELDS.fork();
		NBT_FIELDS.setField(NBT_MOTIVE, (holder, reader) -> {
			holder.setMotive(Motive.getByName(reader.readStringTag()));
		});
	}
	
	public CorePainting(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_MOTIVE);
	}
	
	@Override
	protected NBTFieldContainer<? extends CorePainting> getFieldContainerRoot() {
		return NBT_FIELDS;
	}

	@Override
	public Motive getMotive() {
		return metaContainer.getData(META_MOTIVE);
	}

	@Override
	public void setMotive(Motive motive) {
		metaContainer.get(META_MOTIVE).setData(motive);
	}
	
	@Override
	public void spawn(World world, double x, double y, double z, float pitch, float yaw) {
		super.spawn(world, x, y, z, pitch, yaw);
	}
	
	@Override
	protected void update() {
		super.update();
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeStringTag(NBT_MOTIVE, getMotive().getName());
	}

}
