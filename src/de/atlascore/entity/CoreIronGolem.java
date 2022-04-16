package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.IronGolem;
import de.atlasmc.entity.data.MetaData;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CoreIronGolem extends CoreMob implements IronGolem {

	protected static final MetaDataField<Byte>
	META_IRON_GOLEM_FLAGS = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, (byte) 0, MetaDataType.BYTE);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final String
	NBT_PLAYER_CREATED = "PlayerCreated";
	
	static {
		NBT_FIELDS.setField(NBT_PLAYER_CREATED, (holder, reader) -> {
			if (holder instanceof IronGolem) {
				((IronGolem) holder).setPlayerCreated(reader.readByteTag() == 1);
			} else reader.skipTag();
		});
	}
	
	public CoreIronGolem(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_IRON_GOLEM_FLAGS);
	}

	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public boolean isPlayerCreated() {
		return (metaContainer.getData(META_IRON_GOLEM_FLAGS) & 0x01) == 0x01;
	}

	@Override
	public void setPlayerCreated(boolean playercreated) {
		MetaData<Byte> data = metaContainer.get(META_IRON_GOLEM_FLAGS);
		data.setData((byte) (playercreated ? data.getData() | 0x01 : data.getData() & 0xFE));
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (isPlayerCreated())
			writer.writeByteTag(NBT_PLAYER_CREATED, true);
	}

}
