package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface AngerableMob extends LivingEntity {
	
	@NotNull
	public static final NBTCodec<AngerableMob>
	NBT_CODEC = NBTCodec
					.builder(AngerableMob.class)
					.include(LivingEntity.NBT_CODEC)
					.intField("AngerTime", AngerableMob::getAngerTime, AngerableMob::setAngerTime, 0)
					// AngryAt
					.boolField("IsAngry", AngerableMob::isAngry, AngerableMob::setAngry, false) // non standard
					.build();
	
	boolean isAngry();
	
	void setAngry(boolean angry);
	
	/**
	 * Returns the time in ticks until the angry state will be reset or 0 if non
	 * @return ticks or 0
	 */
	int getAngerTime();
	
	void setAngerTime(int ticks);
	
	@Override
	default NBTCodec<? extends AngerableMob> getNBTCodec() {
		return NBT_CODEC;
	}

}
