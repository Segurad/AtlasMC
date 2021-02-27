package de.atlasmc.block.data.type;

import de.atlasmc.block.data.Waterlogged;

public interface Slab extends Waterlogged {
	
	public Type getType();
	public void setType(Type tpye);
	
	public static enum Type {
		TOP,
		BOTTOM,
		DOUBLE
	}
}
