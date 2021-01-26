package de.atlasmc.entity;

public interface Parrot {
	
	public Type getParrotType();
	
	public static enum Type {
		RED_BLUE,
		BLUE,
		GREEN,
		YELLOW_BLUE,
		GREY
	}

}
