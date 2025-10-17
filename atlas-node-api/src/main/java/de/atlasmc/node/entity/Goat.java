package de.atlasmc.node.entity;

import de.atlasmc.util.nbt.codec.NBTCodec;

public interface Goat extends Animal {
	
	public static final NBTCodec<Goat>
	NBT_HANDLER = NBTCodec
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
	default NBTCodec<? extends Goat> getNBTCodec() {
		return NBT_HANDLER;
	}

}
