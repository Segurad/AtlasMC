package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.node.block.data.BlockData;
import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface PrimedTNT extends Entity {
	
	public static final NBTSerializationHandler<PrimedTNT>
	NBT_HANDLER = NBTSerializationHandler
					.builder(PrimedTNT.class)
					.include(Entity.NBT_HANDLER)
					.shortField("fuse", PrimedTNT::getFuseTime, PrimedTNT::setFuseTime)
					.typeCompoundField("block_state", PrimedTNT::getBlockData, PrimedTNT::setBlockData, BlockData.NBT_HANDLER)
					.floatField("explosion_power", PrimedTNT::getExplosionPower, PrimedTNT::setExplosionPower)
					.uuid("owner", PrimedTNT::getSourceUUID, PrimedTNT::setSourceUUID)
					.build();
	
	int getFuseTime();
	
	void setFuseTime(int time);
	
	float getExplosionPower();
	
	void setExplosionPower(float power);
	
	BlockData getBlockData();
	
	void setBlockData(BlockData data);
	
	Entity getSource();
	
	void setSource(Entity source);
	
	UUID getSourceUUID();
	
	void setSourceUUID(UUID uuid);
	
	@Override
	default NBTSerializationHandler<? extends PrimedTNT> getNBTHandler() {
		return NBT_HANDLER;
	}

}
