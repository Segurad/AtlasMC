package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface AbstractSlime extends Mob {
	
	public static final NBTSerializationHandler<AbstractSlime>
	NBT_HANDLER = NBTSerializationHandler
					.builder(AbstractSlime.class)
					.include(Mob.NBT_HANDLER)
					.intField("Size", AbstractSlime::getSize, AbstractSlime::setSize, 1)
					.boolField("wasOnGround", AbstractSlime::isOnGround, AbstractSlime::setOnGround, true)
					.build();
	
	int getSize();
	
	void setSize(int size);
	
	@Override
	default NBTSerializationHandler<? extends AbstractSlime> getNBTHandler() {
		return NBT_HANDLER;
	}

}
