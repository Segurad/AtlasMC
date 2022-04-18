package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.SnowGolem;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreSnowGolem extends CoreMob implements SnowGolem {

	protected static final MetaDataField<Byte>
	META_SNOW_GOLEM_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0x10, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final String
	NBT_PUMPKIN = "Pumpkin";
	
	static {
		NBT_FIELDS.setField(NBT_PUMPKIN, (holder, reader) -> {
			if (holder instanceof SnowGolem) {
				((SnowGolem) holder).setPumkinHat(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	public CoreSnowGolem(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_SNOW_GOLEM_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean hasPumpkinHat() {
		return (metaContainer.getData(META_SNOW_GOLEM_FLAGS) & 0x10) == 0x10;
	}

	@Override
	public void setPumkinHat(boolean hat) {
		MetaData<Byte> data = metaContainer.get(META_SNOW_GOLEM_FLAGS);
		data.setData((byte) (hat ? data.getData() | 0x10 : data.getData() & 0xEF));
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (!hasPumpkinHat())
			writer.writeByteTag(NBT_PUMPKIN, false);
	}
	
}
