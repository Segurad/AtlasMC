package de.atlasmc.entity;

public interface Mooshroom extends Cow {
	
	public Variant getVariant();
	
	public static enum Variant {
		RED,
		BROWN
	}

}
