package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Vindicator extends AbstractIllager {
	
	public static final NBTSerializationHandler<Vindicator>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Vindicator.class)
					.include(AbstractIllager.NBT_HANDLER)
					.boolField("Johnny", Vindicator::isJohnny, Vindicator::setJohnny, false)
					.build();

	void setJohnny(boolean johnny);
	
	boolean isJohnny();
	
	@Override
	default NBTSerializationHandler<? extends Vindicator> getNBTHandler() {
		return NBT_HANDLER;
	}

}
