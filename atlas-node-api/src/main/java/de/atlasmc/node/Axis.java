package de.atlasmc.node;

import de.atlasmc.util.enums.EnumName;

public enum Axis implements EnumName {
	
	X,
	Y,
	Z;
	
	private final String name;
	
	private Axis() {
		this.name = name().toLowerCase().intern();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
}
