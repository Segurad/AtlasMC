package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface IronGolem extends AbstractGolem {
	
	public static final NBTSerializationHandler<IronGolem>
	NBT_HANDLER = NBTSerializationHandler
					.builder(IronGolem.class)
					.include(AbstractGolem.NBT_HANDLER)
					.boolField("PlayerCreated", IronGolem::isPlayerCreated, IronGolem::setPlayerCreated, false)
					.build();
	
	boolean isPlayerCreated();
	
	void setPlayerCreated(boolean playercreated);
	
	@Override
	default NBTSerializationHandler<? extends IronGolem> getNBTHandler() {
		return NBT_HANDLER;
	}

}
