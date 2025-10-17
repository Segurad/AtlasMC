package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface AbstractSlime extends Mob {
	
	public static final NBTCodec<AbstractSlime>
	NBT_HANDLER = NBTCodec
					.builder(AbstractSlime.class)
					.include(Mob.NBT_HANDLER)
					.intField("Size", AbstractSlime::getSize, AbstractSlime::setSize, 1)
					.boolField("wasOnGround", AbstractSlime::isOnGround, AbstractSlime::setOnGround, true)
					.build();
	
	int getSize();
	
	void setSize(int size);
	
	@Override
	default NBTCodec<? extends AbstractSlime> getNBTCodec() {
		return NBT_HANDLER;
	}

}
