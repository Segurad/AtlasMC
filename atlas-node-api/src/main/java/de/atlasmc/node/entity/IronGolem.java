package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;

public interface IronGolem extends AbstractGolem {
	
	public static final NBTCodec<IronGolem>
	NBT_HANDLER = NBTCodec
					.builder(IronGolem.class)
					.include(AbstractGolem.NBT_HANDLER)
					.boolField("PlayerCreated", IronGolem::isPlayerCreated, IronGolem::setPlayerCreated, false)
					.build();
	
	boolean isPlayerCreated();
	
	void setPlayerCreated(boolean playercreated);
	
	@Override
	default NBTCodec<? extends IronGolem> getNBTCodec() {
		return NBT_HANDLER;
	}

}
