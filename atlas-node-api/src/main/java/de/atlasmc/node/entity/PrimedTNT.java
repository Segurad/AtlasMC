package de.atlasmc.node.entity;

import java.util.UUID;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.nbt.codec.NBTCodecs;
import de.atlasmc.node.block.data.BlockData;

public interface PrimedTNT extends Entity {
	
	public static final NBTCodec<PrimedTNT>
	NBT_HANDLER = NBTCodec
					.builder(PrimedTNT.class)
					.include(Entity.NBT_CODEC)
					.shortField("fuse", PrimedTNT::getFuseTime, PrimedTNT::setFuseTime)
					.codec("block_state", PrimedTNT::getBlockData, PrimedTNT::setBlockData, BlockData.NBT_CODEC)
					.floatField("explosion_power", PrimedTNT::getExplosionPower, PrimedTNT::setExplosionPower)
					.codec("owner", PrimedTNT::getSourceUUID, PrimedTNT::setSourceUUID, NBTCodecs.UUID_CODEC)
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
	default NBTCodec<? extends PrimedTNT> getNBTCodec() {
		return NBT_HANDLER;
	}

}
