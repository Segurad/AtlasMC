package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.Bat;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreBat extends CoreMob implements Bat {

	/**
	 * 0x01 Is hanging
	 */
	protected static final MetaDataField<Byte>
	META_BAT_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final CharKey
	NBT_BAT_FLAGS = CharKey.literal("BatFlags");
	
	static {
		NBT_FIELDS.setField(NBT_BAT_FLAGS, (holder, reader) -> {
			if (holder instanceof Bat) {
				((Bat) holder).setHanging(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	public CoreBat(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_BAT_FLAGS);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public boolean isHanging() {
		return (metaContainer.getData(META_ENTITY_FLAGS) & 0x01) == 0x01;
	}
	
	@Override
	public void setHanging(boolean hanging) {
		MetaData<Byte> data = metaContainer.get(META_BAT_FLAGS);
		data.setData((byte) (hanging ? data.getData() | 0x01 : data.getData() & 0xFE));
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isHanging())
			writer.writeByteTag(NBT_BAT_FLAGS, true);
	}

}
