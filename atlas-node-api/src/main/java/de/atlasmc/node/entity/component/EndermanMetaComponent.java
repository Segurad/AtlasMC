package de.atlasmc.node.entity.component;

import java.util.Objects;

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
import de.atlasmc.util.annotation.Nullable;
import de.atlasmc.util.annotation.UnsafeAPI;

public class EndermanMetaComponent extends AbstractHolderBoundComponent<Entity> implements MetaDataFieldProvider {

	@NotNull
	public static final NBTCodec<EndermanMetaComponent>
	NBT_CODEC = NBTCodec
					.builder(EndermanMetaComponent.class)
					.include(AbstractHolderBoundComponent.NBT_CODEC)
					.codec("carriedBlockState", EndermanMetaComponent::getCarriedBlock, EndermanMetaComponent::setCarriedBlock, BlockData.NBT_CODEC)
					.build();

	public static final MetaDataField<Integer> 
	META_CARRIED_BLOCK = new MetaDataField<>(16, null, EntityMetaTypes.OPT_BLOCKSTATE);
	public static final MetaDataField<Boolean>
	META_IS_SCREAMING = new MetaDataField<>(17, false, EntityMetaTypes.BOOLEAN);
	public static final MetaDataField<Boolean>
	META_IS_STARING = new MetaDataField<>(18, false, EntityMetaTypes.BOOLEAN);
	
	private BlockData block;
	
	public EndermanMetaComponent(ComponentType type) {
		super(type);
	}
	
	@Override
	public void initMetaContainer(MetaDataContainer container) {
		container.set(META_CARRIED_BLOCK);
		container.set(META_IS_SCREAMING);
		container.set(META_IS_STARING);
	}
	
	@Override
	public int getMetaFieldCount() {
		return 3;
	}

	public boolean isScreaming() {
		return getHolder().getMetaContainer().getData(META_IS_SCREAMING);
	}

	public boolean isStaring() {
		return getHolder().getMetaContainer().getData(META_IS_STARING);
	}

	@Nullable
	public BlockType getCarriedBlockType() {
		return block == null ? null : block.getType();
	}

	@Nullable
	public BlockData getCarriedBlock() {
		return block != null ? block.clone() : null;
	}
	
	@UnsafeAPI
	@Nullable
	public BlockData getCarriedBlockUnsafe() {
		return block;
	}

	public void setCarriedBlock(BlockData data) {
		if (Objects.equals(block, data))
			return;
		if (data == null) {
			this.block = null;
			getHolder().getMetaContainer().setData(META_CARRIED_BLOCK, null);
		} else {
			this.block = data.clone();
			getHolder().getMetaContainer().setData(META_CARRIED_BLOCK, data.getStateID());
		}
	}
	
	/**
	 * Causes the meta field to update
	 */
	public void updateCarriedBlock() {
		getHolder().getMetaContainer().setData(META_CARRIED_BLOCK, block != null ? block.getStateID() : null);
	}
	
	@UnsafeAPI
	public void setCarriedBlockUnsafe(BlockData data) {
		if (Objects.equals(block, data))
			return;
		this.block = data;
		getHolder().getMetaContainer().setData(META_CARRIED_BLOCK, data != null ? data.getStateID() : null);
	}

	public void setCarriedBlockType(BlockType type) {
		if (block != null && block.getType() == type)
			return;
		if (type != null) {
			block = type.createBlockData();
			getHolder().getMetaContainer().setData(META_CARRIED_BLOCK, block.getStateID());
		} else if (block != null) {
			block = null;
			getHolder().getMetaContainer().setData(META_CARRIED_BLOCK, null);
		}
	}

	public boolean hasCarriedBlock() {
		return block != null;
	}

	public void setScreaming(boolean screaming) {
		getHolder().getMetaContainer().setData(META_IS_SCREAMING, screaming);
	}

	public void setStaring(boolean staring) {
		getHolder().getMetaContainer().setData(META_IS_STARING, staring);
	}
	
	@Override
	public NBTCodec<? extends EndermanMetaComponent> getNBTCodec() {
		return NBT_CODEC;
	}
	
}
