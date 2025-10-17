package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Vindicator extends AbstractIllager {
	
	public static final NBTCodec<Vindicator>
	NBT_HANDLER = NBTCodec
					.builder(Vindicator.class)
					.include(AbstractIllager.NBT_HANDLER)
					.boolField("Johnny", Vindicator::isJohnny, Vindicator::setJohnny, false)
					.build();

	void setJohnny(boolean johnny);
	
	boolean isJohnny();
	
	@Override
	default NBTCodec<? extends Vindicator> getNBTCodec() {
		return NBT_HANDLER;
	}

}
