package de.atlasmc.entity;

import de.atlasmc.DyeColor;

public interface Cat extends TameableAnimal {
	
	public Type getCatType();
	public boolean isLying();
	public boolean isRelaxed();
	public DyeColor getCollarColor();
	
	public static enum Type {
		TABBY,
		BLACK,
		RED,
		SIAMESE,
		BRITISH_SHORTHAIR,
		CALICO,
		PERSIAN,
		RAGDOLL,
		WHITE,
		ALL_BLACK
	}

}
