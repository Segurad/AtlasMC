package de.atlasmc.node.entity;

import de.atlasmc.node.block.BlockType;
import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.node.block.tile.TileEntity;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface FallingBlock extends Entity {
	
	public static final NBTSerializationHandler<FallingBlock>
	NBT_HANDLER = NBTSerializationHandler
					.builder(FallingBlock.class)
					.include(Entity.NBT_HANDLER)
					.typeCompoundField("BlockState", FallingBlock::getBlockData, FallingBlock::setBlockData, BlockData.NBT_HANDLER)
					.boolField("CancelDrop", FallingBlock::getCancelDrop, FallingBlock::setCancelDrop, false)
					.boolField("DropItem", FallingBlock::getDropItem, FallingBlock::setDropItem, false)
					.floatField("FallHurtAmount", FallingBlock::getDamagePerBlock, FallingBlock::setDamagePerBlock, 0)
					.intField("FallHurtMax", FallingBlock::getMaxDamage, FallingBlock::setMaxDamage, 40)
					.boolField("HurtEntities", FallingBlock::canHurtEntities, FallingBlock::setHurtEntities, true)
					.typeCompoundField("TileEntityData", FallingBlock::getTileEntity, FallingBlock::setTileEntity, TileEntity.NBT_HANDLER)
					.intField("Time", FallingBlock::getAge, FallingBlock::setAge, 0)
					.build();

	float getDamagePerBlock();
	
	void setDamagePerBlock(float damage);
	
	boolean getCancelDrop();
	
	void setCancelDrop(boolean cancel);
	
	boolean canHurtEntities();
	
	BlockData getBlockData();
	
	boolean getDropItem();
	
	BlockType getBlockType();
	
	void setDropItem(boolean drop);
	
	void setHurtEntities(boolean hurtEntities);
	
	void setBlockData(BlockData data);
	
	void setBlockDataType(BlockType type);

	void setMaxDamage(int damage);
	
	/**
	 * Returns the max amount of damage this block can deal or -1 if no limit
	 * @return amount or -1
	 */
	int getMaxDamage();

	void setTileEntity(TileEntity tile);
	
	TileEntity getTileEntity();
	
	int getAge();
	
	void setAge(int age);
	
	@Override
	default NBTSerializationHandler<? extends FallingBlock> getNBTHandler() {
		return NBT_HANDLER;
	}
	
}
