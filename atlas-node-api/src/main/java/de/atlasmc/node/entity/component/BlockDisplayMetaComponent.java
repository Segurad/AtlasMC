package de.atlasmc.node.entity.component;

import de.atlasmc.component.AbstractHolderBoundComponent;
import de.atlasmc.component.ComponentType;
import de.atlasmc.io.metadata.MetaDataContainer;
import de.atlasmc.io.metadata.MetaDataField;
import de.atlasmc.io.metadata.MetaDataFieldProvider;
import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.entity.Entity;
import de.atlasmc.node.entity.EntityMetaTypes;
import de.atlasmc.util.annotation.NotNull;

public class BlockDisplayMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {
	
	@NotNull
	public static final NBTCodec<BlockDisplayMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(BlockDisplayMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("block_state", BlockDisplayMetaComponent::getBlockData, BlockDisplayMetaComponent::setBlockData, BlockData.NBT_CODEC)
					.build();
	
	public static final MetaDataField<Integer> META_DISPLAYED_BLOCK = new MetaDataField<>(23, 0, EntityMetaTypes.BLOCKSTATE);
	
	private BlockData block;
	
	public BlockDisplayMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 1;
	}

	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_DISPLAYED_BLOCK);
	}

	public BlockData getBlockData() {
		return block;
	}

	public void setBlockData(BlockData data) {
		int id = 0;
		if (data != null) {
			block = data;
			id = data.getStateID();
		}
		getHolder().getMetaContainer().setData(META_DISPLAYED_BLOCK, id);
	}

	public void setBlockDataType(BlockType type) {
		int id = 0;
		if (type == null) {
			block = null;
		} else if (block == null || block.getType() != type) {
			block = type.createBlockData();
			id = block.getStateID();
		}
		getHolder().getMetaContainer().setData(META_DISPLAYED_BLOCK, id);
	}

	public BlockType getBlockDataType() {
		return block != null ? block.getType() : null;
	}
	
	@Override
	public NBTCodec<? extends BlockDisplayMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}

}
