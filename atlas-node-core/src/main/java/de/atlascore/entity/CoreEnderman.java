package de.atlascore.entity;

import java.io.IOException;
import java.util.UUID;

import de.atlasmc.block.BlockType;
import de.atlasmc.block.data.BlockData;
import de.atlasmc.entity.Enderman;
import de.atlasmc.entity.EntityType;
import de.atlasmc.entity.data.MetaDataField;
import de.atlasmc.entity.data.MetaDataType;
import de.atlasmc.util.map.key.CharKey;
import de.atlasmc.util.nbt.io.NBTWriter;

public class CoreEnderman extends CoreMob implements Enderman {
	
	protected static final MetaDataField<Integer> 
	META_CARRIED_BLOCK = new MetaDataField<>(CoreMob.LAST_META_INDEX + 1, null, MetaDataType.OPT_BLOCKSTATE);
	protected static final MetaDataField<Boolean>
	META_IS_SCREAMING = new MetaDataField<>(CoreMob.LAST_META_INDEX + 2, false, MetaDataType.BOOLEAN);
	protected static final MetaDataField<Boolean>
	META_IS_STARING = new MetaDataField<>(CoreMob.LAST_META_INDEX + 3, false, MetaDataType.BOOLEAN);
	
	protected static final int LAST_META_INDEX = CoreMob.LAST_META_INDEX+3;

	protected static final CharKey
	NBT_CARRIED_BLOCK_STATE = CharKey.literal("carriedBlockState"),
	NBT_PROPERTIES = CharKey.literal("Properties");
	
	static {
		NBT_FIELDS.setField(NBT_CARRIED_BLOCK_STATE, (holder, reader) -> {
			if (!(holder instanceof Enderman entity)) {
				reader.skipTag();
				return;
			}
			reader.readNextEntry();
			BlockData data = BlockData.getFromNBT(reader);
			if (data != null)
				entity.setCarriedBlock(data);
		});
	}
	
	private BlockData block;
	private boolean blockChanged;
	
	public CoreEnderman(EntityType type, UUID uuid) {
		super(type, uuid);
	}
	
	@Override
	protected void initMetaContainer() {
		super.initMetaContainer();
		metaContainer.set(META_CARRIED_BLOCK);
		metaContainer.set(META_IS_SCREAMING);
		metaContainer.set(META_IS_STARING);
	}
	
	@Override
	protected int getMetaContainerSize() {
		return LAST_META_INDEX+1;
	}
	
	@Override
	protected void prepUpdate() {
		super.prepUpdate();
		if (blockChanged) {
			blockChanged = false;
			if (block == null)
				metaContainer.get(META_CARRIED_BLOCK).setData(null);
			else
				metaContainer.get(META_CARRIED_BLOCK).setData(block.getStateID());
		}
	}

	@Override
	public boolean isScreaming() {
		return metaContainer.getData(META_IS_SCREAMING);
	}

	@Override
	public boolean isStaring() {
		return metaContainer.getData(META_IS_STARING);
	}

	@Override
	public BlockType getCarriedBlockType() {
		return block == null ? null : block.getType();
	}

	@Override
	public BlockData getCarriedBlock() {
		return block;
	}

	@Override
	public void setCarriedBlock(BlockData data) {
		if (block == data || block.equals(data))
			return;
		blockChanged = true;
		this.block = data;
	}

	@Override
	public void setCarriedBlockType(BlockType type) {
		if (type != null) {
			blockChanged = true;
			block = type.createBlockData();
		} else if (block != null) {
			blockChanged = true;
			block = null;
		}
	}

	@Override
	public boolean hasCarriedBlock() {
		return block != null;
	}

	@Override
	public void setScreaming(boolean screaming) {
		metaContainer.get(META_IS_SCREAMING).setData(screaming);
	}

	@Override
	public void setStaring(boolean staring) {
		metaContainer.get(META_IS_STARING).setData(staring);
	}

	@Override
	public void setCarriedBlockChanged() {
		blockChanged = true;
	}
	
	@Override
	public void toNBT(NBTWriter writer, boolean systemData) throws IOException {
		super.toNBT(writer, systemData);
		if (block != null) {
			writer.writeCompoundTag(NBT_CARRIED_BLOCK_STATE);
			block.toNBT(writer, systemData);
			writer.writeEndTag();
		}
	}

}
