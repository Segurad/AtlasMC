package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.Component;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;

public class MinecartMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	protected static final MetaDataField<Integer>
	META_CUSTOM_BLOCK_ID = new MetaDataField<>(11, 0, EntityMetaTypes.VAR_INT);
	protected static final MetaDataField<Integer>
	META_CUSTOM_BLOCK_Y = new MetaDataField<>(12, 6, EntityMetaTypes.VAR_INT);

	private BlockData customBlockData; 
	
	public MinecartMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_CUSTOM_BLOCK_ID);
		container.set(META_CUSTOM_BLOCK_Y);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 2;
	}
	
	public BlockData getCustomBlock() {
		return customBlockData;
	}

	public void setCustomBlock(BlockData data) {
		getHolder().getMetaContainer().setData(META_CUSTOM_BLOCK_ID, data == null ? 0 : data.getStateID());
		customBlockData = data;		
	}

	public int getCustomBlockY() {
		return getHolder().getMetaContainer().getData(META_CUSTOM_BLOCK_Y);
	}

	public void setCustomBlockY(int y) {
		if (y < 0 || y > 16)
			throw new IllegalArgumentException("Y must be between 0 and 16: " + y);
		getHolder().getMetaContainer().setData(META_CUSTOM_BLOCK_Y, y);
	}

	public boolean hasCustomBlock() {
		return customBlockData != null;
	}

	public void setCustomBlockType(BlockType type) {
		if (type != null)
			setCustomBlock(type.createBlockData());
		else
			setCustomBlock(null);
	}

	@Override
	public NBTCodec<? extends Component> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
