package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EnderDragon;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreEnderDragon extends CoreMob implements EnderDragon {

	protected static final MetaDataField<Integer>
	META_DRAGON_PHASE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 10, MetaDataType.VAR_INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final CharKey
	NBT_DRAGON_PHASE = CharKey.literal("DragonPhase");
	
	static {
		NBT_FIELDS.setField(NBT_DRAGON_PHASE, (holder, reader) -> {
			if (holder instanceof EnderDragon) {
				((EnderDragon) holder).setPhase(DragonPhase.getByID(reader.readIntTag()));
			} else reader.skipTag();
		});
	}
	
	public CoreEnderDragon(EntityType type, UUID uuid) {
		super(type, uuid);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_DRAGON_PHASE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public DragonPhase getPhase() {
		return DragonPhase.getByID(metaContainer.getData(META_DRAGON_PHASE));
	}

	@Override
	public void setPhase(DragonPhase phase) {
		if (phase == null)
			throw new IllegalArgumentException("Phase can not be null!");
		metaContainer.get(META_DRAGON_PHASE).setData(phase.getID());
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_DRAGON_PHASE, getPhase().getID());
	}

}
