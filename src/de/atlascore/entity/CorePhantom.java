package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.Phantom;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.nbt.ChildNBTFieldContainer;
import de.atlasmc.util.nbt.NBTFieldContainer;
import de.atlasmc.util.nbt.io.NBTWriter;
import de.atlasmc.world.World;

public class CorePhantom extends CoreMob implements Phantom {

	protected static final MetaDataField<Integer>
	META_PHANTOM_SIZE = new MetaDataField<>(CoreMob.LAST_META_INDEX+1, 0, MetaDataType.INT);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+1;
	
	protected static final NBTFieldContainer NBT_FIELDS;
	
	protected static final String
	//NBT_ANCHOR_X = "AX", TODO unnecessary
	//NBT_ANCHOR_Y = "AY",
	//NBT_ANCHOR_Z = "AZ",
	NBT_SIZE = "Size";
	
	static {
		NBT_FIELDS = new ChildNBTFieldContainer(CoreMob.NBT_FIELDS);
		NBT_FIELDS.setField(NBT_SIZE, (holder, reader) -> {
			if (holder instanceof Phantom) {
				((Phantom) holder).setSize(reader.readIntTag());
			} else reader.skipTag();
		});
	}
	
	public CorePhantom(EntityType type, UUID uuid, World world) {
		super(type, uuid, world);
	}

	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_PHANTOM_SIZE);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	public int getSize() {
		return metaContainer.getData(META_PHANTOM_SIZE);
	}

	@Override
	public void setSize(int size) {
		metaContainer.get(META_PHANTOM_SIZE).setData(size);	
		// TODO alter hitbox (horizontal=0.9 + 0.2*size and vertical=0.5 + 0.1 * i)
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		writer.writeIntTag(NBT_SIZE, getSize());
	}

}
