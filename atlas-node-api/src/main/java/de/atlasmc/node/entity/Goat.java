package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.serialization.NBTSerializationHandler;

public interface Goat extends Animal {
	
	public static final NBTSerializationHandler<Goat>
	NBT_HANDLER = NBTSerializationHandler
					.builder(Goat.class)
					.include(Animal.NBT_HANDLER)
					.boolField("HasLeftHorn", Goat::hasLeftHorn, Goat::setLeftHorn, true)
					.boolField("HasRightHorn", Goat::hasRightHorn, Goat::setRightHorn, true)
					.boolField("IsScreamingGoat", Goat::isScreamingGoat, Goat::setScreamingGoat, false)
					.build();

	boolean isScreamingGoat();
	
	void setScreamingGoat(boolean screaming);
	
	boolean hasLeftHorn();
	
	void setLeftHorn(boolean horn);
	
	boolean hasRightHorn();
	
	void setRightHorn(boolean horn);
	
	@Override
	default NBTSerializationHandler<? extends Goat> getNBTHandler() {
		return NBT_HANDLER;
	}

}
