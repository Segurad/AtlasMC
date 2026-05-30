package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface AbstractSlime extends Mob {
	
	@NotNull
	public static final NBTCodec<AbstractSlime>
	NBT_HANDLER = NBTCodec
					.builder(AbstractSlime.class)
					.include(Mob.NBT_CODEC)
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
