package de.atlasmc.entity;

import de.atlasmc.DyeColor;

public interface Llama extends ChestedHorse {
	
	public int getStrength();
	public DyeColor getCarpetColor();
	public Color getColor();
	
	public static enum Color {
		
	}
}
