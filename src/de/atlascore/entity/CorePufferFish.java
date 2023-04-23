package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.PufferFish;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CorePufferFish extends CoreFish implements PufferFish {

	protected static final MetaDataField<Integer>
	META_PUFF_STATE = new MetaDataField<>(CoreFish.LAST_META_INDEX+1, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreFish.LAST_META_INDEX+1;
	
	protected static final CharKey
	NBT_PUFF_STATE = CharKey.of("PuffState");
	
	static {
		NBT_FIELDS.setField(NBT_PUFF_STATE, (holder, reader) -> {
			if (holder instanceof PufferFish) {
				((PufferFish) holder).setPuffState(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	public CorePufferFish(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_PUFF_STATE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}

	@Override
	public int getPuffState() {
		return metaContainer.getData(META_PUFF_STATE);
	}

	@Override
	public void setPuffState(int state) {
		if (state > 2 || state < 0) throw new IllegalArgumentException("State is not between 0 and 2: " + state);
		metaContainer.get(META_PUFF_STATE).setData(state);		
	}

	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (getPuffState() != 0)
			writer.writeIntTag(NBT_PUFF_STATE, getPuffState());
	}
	
}
