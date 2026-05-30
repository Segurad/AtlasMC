package de.atlasmc.node.entity;

import de.atlasmc.nbt.codec.NBTCodec;
import de.atlasmc.util.annotation.NotNull;

public interface Vindicator extends AbstractIllager {
	
	@NotNull
	public static final NBTCodec<Vindicator>
	NBT_CODEC = NBTCodec
					.builder(Vindicator.class)
					.include(AbstractIllager.NBT_CODEC)
					.boolField("Johnny", Vindicator::isJohnny, Vindicator::setJohnny, false)
					.build();

	void setJohnny(boolean johnny);
	
	boolean isJohnny();
	
	@Override
	default NBTCodec<? extends Vindicator> getNBTCodec() {
		return NBT_CODEC;
	}

}
